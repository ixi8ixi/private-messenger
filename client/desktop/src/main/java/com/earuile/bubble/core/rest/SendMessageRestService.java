package com.earuile.bubble.core.rest;

import com.earuile.bubble.core.db.info.UserInfoService;
import com.earuile.bubble.core.rest.dto.*;
import com.earuile.bubble.core.rest.config.MessengerServerRestProperty;
import com.earuile.bubble.public_interface.MessageModelDto;
import com.earuile.bubble.ui.controllers.chat.PullMessagesCallback;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class SendMessageRestService {
    private static final String DEFAULT_CHAT_ID = "CH-18855369-805a-4412-acf3-18eb0104dc54";
    private static final int REFRESH_BATCH_SIZE = 3;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private final UserInfoService userInfoService;
    private final RestTemplate restTemplate;
    private final TaskExecutor threadPoolTaskExecutor;
    private final MessengerServerRestProperty messengerServerRestProperty;

    private final AtomicBoolean refreshInProgress = new AtomicBoolean(false);
    private final AtomicReference<String> lastKnownId = new AtomicReference<>(null);

    public SendMessageRestService(RestTemplate restTemplate, TaskExecutor threadPoolTaskExecutor,
                                  MessengerServerRestProperty messengerServerRestProperty, UserInfoService userInfoService) {
        this.restTemplate = restTemplate;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.messengerServerRestProperty = messengerServerRestProperty;
        this.userInfoService = userInfoService;
    }

    public void sendMessage(String message) {
        threadPoolTaskExecutor.execute(() -> sendRequest(
                new MessageRequest(DEFAULT_CHAT_ID, new TextMessage(userInfoService.id(), message, null, null)),
                HttpMethod.POST, MessageResponse.class));
    }

    public void pullMessages(PullMessagesCallback pullMessagesCallback) {
        if (refreshInProgress.compareAndSet(false, true)) {
            threadPoolTaskExecutor.execute(() -> {
                GetMessageRequest request = new GetMessageRequest(null, DEFAULT_CHAT_ID, REFRESH_BATCH_SIZE, lastKnownId.get());
                ResponseEntity<GetMessageResponse> response = sendRequest(request, HttpMethod.GET, GetMessageResponse.class);

                List<TextMessage> textMessages = checkRefreshList(response);
                if (textMessages == null) {
                    return;
                }

                String newLastKnownId = textMessages.getLast().id();
                List<MessageModelDto> texts = textMessages.stream().map(
                        textMessage -> {
                            LocalTime lt = LocalTime.ofInstant(Instant.ofEpochMilli(textMessage.timeDate()), ZoneId.systemDefault());
                            return new MessageModelDto(
                                    textMessage.userId().substring(30), // todo change it in future
                                    textMessage.textData().substring(0, textMessage.textData().length() - 8), // todo temporary
                                    lt.format(TIME_FORMATTER)
                            );
                        }
                ).toList();

                Platform.runLater(() -> {
                    pullMessagesCallback.addMessages(texts);
                    lastKnownId.set(newLastKnownId);
                    refreshInProgress.set(false);
                    pullMessages(pullMessagesCallback);
                });
            });
        }
    }

    private <T, U> ResponseEntity<U> sendRequest(T requestBody, HttpMethod httpMethod, Class<U> responseClass) {
        HttpEntity<T> request = new HttpEntity<>(requestBody);
        return restTemplate.exchange(messengerServerRestProperty.uri(), httpMethod, request, responseClass);
    }

    private List<TextMessage> checkRefreshList(HttpEntity<GetMessageResponse> response) {
        GetMessageResponse responseBody = response.getBody();
        if (responseBody == null) {
            refreshInProgress.set(false);
            return null;
        }

        List<TextMessage> textMessages = responseBody.textMessages();
        if (textMessages.isEmpty()) {
            refreshInProgress.set(false);
            return null;
        }
        return textMessages;
    }
}
