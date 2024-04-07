package com.earuile.bubble.ui.controllers;

import com.earuile.bubble.rest.SendMessageRestService;
import com.jfoenix.controls.JFXListView;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("main-scene.fxml")
@RequiredArgsConstructor
public class ChatController {
    private final SendMessageRestService sendMessageRestService;

    @FXML
    private JFXListView<String> messagesArea;

    ObservableList<String> list = FXCollections.observableArrayList();

    @FXML
    private TextField userMessage;

//    @FXML
//    private Button refreshButton;

    private PullMessagesCallback pullMessagesCallback;

    @FXML
    public void initialize() {
        messagesArea.setItems(list);
//        messagesArea.setCellFactory(p -> new ListCell<>() {
//
//        });

        pullMessagesCallback = messageList -> {
            messagesArea.getItems().addAll(messageList);
        };

        messagesArea.setEditable(false);
//        messagesArea.setMouseTransparent(true);
//        messagesArea.setFocusTraversable(false);

        userMessage.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                String message = userMessage.getText();
                userMessage.clear();
                sendMessageRestService.sendMessage(message);
            } else if (actionEvent.getCode() == KeyCode.SHIFT) {
                sendMessageRestService.pullMessages(pullMessagesCallback);
            }
        });

//        refreshButton.setOnAction(actionEvent -> {
//            sendMessageRestService.pullMessages(pullMessagesCallback);
//        });
    }
}
