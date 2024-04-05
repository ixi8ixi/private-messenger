package com.earuile.bubble.ui.controllers;

import com.earuile.bubble.rest.MessengerServerRestService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpRequest;

@Component
@FxmlView("main-scene.fxml")
@RequiredArgsConstructor
public class MyController {
    private final MessengerServerRestService messengerServerRestService;

    @FXML
    private TextArea messagesArea;

    @FXML
    private TextField userMessage;

    @FXML
    public void initialize() {
        messagesArea.setEditable(false);
        messagesArea.setMouseTransparent(true);
        messagesArea.setFocusTraversable(false);

        userMessage.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                String message = userMessage.getText();
                userMessage.clear();
                messagesArea.appendText("Me >>  " + message + System.lineSeparator());

                String serverMessage = messengerServerRestService.sendMessage(message);
                messagesArea.appendText("Server >>  " + serverMessage + System.lineSeparator());
            }
        });
    }
}
