package com.earuile.bubble.ui.controllers.chat;

import com.earuile.bubble.core.rest.SendMessageRestService;
import com.earuile.bubble.public_interface.MessageModelDto;
import com.earuile.bubble.ui.StageRepository;
import com.earuile.bubble.ui.controllers.SimpleController;
import com.earuile.bubble.ui.controllers.message.MessageController;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/*
    TODO
      - Add a hold at the end of the list if a new message arrives
 */

@Component
@FxmlView("chat.fxml")
@RequiredArgsConstructor
public class ChatController implements SimpleController {
    private final SendMessageRestService sendMessageRestService;
    private final StageRepository stageRepository;
    private final AtomicBoolean displayed = new AtomicBoolean(false);

    @FXML
    private AnchorPane backview;

    @FXML
    private JFXListView<MessageModelDto> messagesArea;
    ObservableList<MessageModelDto> list = FXCollections.observableArrayList();

    @FXML
    private TextField userMessage;

    private final PullMessagesCallback pullMessagesCallback = messageList -> {
        messagesArea.getItems().addAll(messageList);
        messagesArea.scrollTo(list.size() - 1);
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

        sendMessageRestService.pullMessages(pullMessagesCallback);
    }

    // todo перенести в сервис
    //   это должно работать только если сейчас отображается окно чата
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
