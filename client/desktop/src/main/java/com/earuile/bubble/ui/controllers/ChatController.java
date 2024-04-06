package com.earuile.bubble.ui.controllers;

import com.earuile.bubble.rest.SendMessageRestService;
import javafx.fxml.FXML;
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
    @FXML
    private ListView<String> messagesArea;

    @FXML
    private TextField userMessage;

    @FXML
    public void initialize() {
        messagesArea.setEditable(false);
//        messagesArea.setMouseTransparent(true);
//        messagesArea.setFocusTraversable(false);

        userMessage.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                String message = userMessage.getText();
                userMessage.clear();
                messagesArea.getItems().add("Me >>  " + message);

//                String serverMessage = sendMessageRestService.sendMessage(message);
//                messagesArea.getItems().add("Server >>  " + serverMessage);
            }
        });
    }
}
