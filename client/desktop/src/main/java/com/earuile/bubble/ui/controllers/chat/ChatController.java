package com.earuile.bubble.ui.controllers.chat;

import com.earuile.bubble.core.controller.ChatMessageDataController;
import com.earuile.bubble.core.repository.info.UserInfoRepository;
import com.earuile.bubble.core.rest.interaction.MessagesRestService;
import com.earuile.bubble.core.rest.interaction.UsersRestService;
import com.earuile.bubble.public_interface.MessageModelDto;
import com.earuile.bubble.public_interface.SendMessageDto;
import com.earuile.bubble.ui.StageRepository;
import com.earuile.bubble.ui.controllers.SimpleController;
import com.earuile.bubble.ui.controllers.message.MessageController;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipError;

/*
    TODO
      - Add a hold at the end of the list if a new message arrives
 */

@Component
@FxmlView("chat.fxml")
@RequiredArgsConstructor
public class ChatController {
    private final AtomicBoolean displayed = new AtomicBoolean(false);
    private final AtomicReference<String> currentChatId = new AtomicReference<>(null);
    private final AtomicReference<String> lastKnownId = new AtomicReference<>(null);

    private final StageRepository stageRepository;

    private final ChatMessageDataController chatMessageDataController;

    private final TaskExecutor threadPoolTaskExecutor;

    private final UserInfoRepository userInfoRepository;

    @FXML
    private AnchorPane backview;

    @FXML
    private JFXListView<MessageModelDto> messagesArea;
    private final ObservableList<MessageModelDto> list = FXCollections.observableArrayList();

    @FXML
    private TextField userMessage;

    @FXML
    void initialize() {
        messagesArea.setItems(list);
        messagesArea.setCellFactory(p -> new MessageController());
        messagesArea.setEditable(false);

        userMessage.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                String text = userMessage.getText();
                userMessage.clear();
                SendMessageDto dto = new SendMessageDto(
                        currentChatId.get(), userInfoRepository.info().id(), text);
                threadPoolTaskExecutor.execute(() -> chatMessageDataController.sendMessage(dto));
            }
        });
    }

    @Scheduled(fixedDelay = 200)
    private void refresh() {
       if (displayed.get()) {
           List<MessageModelDto> msg = chatMessageDataController.pullMessagesFromServer(currentChatId.get(), lastKnownId.get());
           if (msg != null && !msg.isEmpty()) {
               lastKnownId.set(msg.getLast().messageId());
               Platform.runLater(() -> messagesArea.getItems().addAll(msg));
           }
       }
    }

    public void showForChat(String chatId) {
        Stage stage = stageRepository.getStage();
        currentChatId.set(chatId);
        displayed.set(true);
        messagesArea.getItems().clear();

        stage.getScene().setRoot(backview);
    }
}
