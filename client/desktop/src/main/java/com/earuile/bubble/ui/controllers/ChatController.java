package com.earuile.bubble.ui.controllers;

import com.earuile.bubble.rest.SendMessageRestService;
import com.earuile.bubble.public_interface.MessageModelDto;
import com.earuile.bubble.ui.StageRepository;
import com.earuile.bubble.ui.controllers.message.MessageController;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
    private final StageRepository stageRepository;
    private final AtomicBoolean displayed = new AtomicBoolean(false);

    @FXML
    private AnchorPane backview;

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
        Scene scene = stageRepository.getStage().getScene();
        scene.getStylesheets().add("com/earuile/bubble/ui/controllers/chat-style.css");

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

    // todo move to service
    @Scheduled(fixedDelay = 200)
    private void refresh() {
        if (displayed.get()) {
            sendMessageRestService.pullMessages(pullMessagesCallback);
        }
    }

    public void show() {
        Stage stage = stageRepository.getStage();
        displayed.set(true);
        stage.getScene().setRoot(backview);
    }
}
