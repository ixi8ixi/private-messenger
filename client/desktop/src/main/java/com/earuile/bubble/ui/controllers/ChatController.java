package com.earuile.bubble.ui.controllers;

import com.earuile.bubble.rest.SendMessageRestService;
import com.earuile.bubble.public_interface.MessageModelDto;
import com.earuile.bubble.ui.controllers.message.MessageController;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
@FxmlView("main-scene.fxml")
@RequiredArgsConstructor
public class ChatController {
    private final SendMessageRestService sendMessageRestService;

    @FXML
    private JFXListView<MessageModelDto> messagesArea;
    ObservableList<MessageModelDto> list = FXCollections.observableArrayList();
    private AtomicBoolean atBottom = new AtomicBoolean(false);

    @FXML
    private TextField userMessage;

    private final PullMessagesCallback pullMessagesCallback = messageList -> {
        messagesArea.getItems().addAll(messageList);
        messagesArea.scrollTo(list.size() - 1);
//        if (atBottom.get()) {
//        }
    };

    @FXML
    public void initialize() {
        messagesArea.setItems(list);
        messagesArea.setCellFactory(p -> new MessageController());
        messagesArea.setEditable(false);

        userMessage.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                String message = userMessage.getText();
                userMessage.clear();
                sendMessageRestService.sendMessage(message);
            }
        });

//        for (Node node: li)

        sendMessageRestService.pullMessages(pullMessagesCallback);
    }

    @Scheduled(fixedDelay = 200)
    public void refresh() {
        sendMessageRestService.pullMessages(pullMessagesCallback);
    }
}
