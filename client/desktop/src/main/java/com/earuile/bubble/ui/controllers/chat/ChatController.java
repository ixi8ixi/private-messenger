package com.earuile.bubble.ui.controllers.chat;

import com.earuile.bubble.core.repository.info.UserInfoRepository;
import com.earuile.bubble.core.rest.interaction.MessagesRestService;
import com.earuile.bubble.public_interface.message.MessageModelDto;
import com.earuile.bubble.public_interface.chat.SendMessageDto;
import com.earuile.bubble.ui.StageRepository;
import com.earuile.bubble.ui.controllers.dialogs.DialogsController;
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
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/*
    TODO
      - Add a hold at the end of the list if a new message arrives
 */

@Component
@FxmlView("chat.fxml")
@RequiredArgsConstructor
public class ChatController {
    private static final int DEFAULT_BATCH_SIZE = 100;

    private final StageRepository stageRepository;
    private final FxWeaver fxWeaver;

    private final UserInfoRepository userInfoRepository;
    private final MessagesRestService messagesRestService;

    private final ExecutorService uiExecutorService;

    private final AtomicBoolean displayed = new AtomicBoolean(false);
    private final AtomicReference<String> currentChatId = new AtomicReference<>(null);
    private final AtomicReference<String> lastKnownId = new AtomicReference<>(null);

    @FXML
    private AnchorPane backview;

    @FXML
    private JFXListView<MessageModelDto> messagesArea;
    private final ObservableList<MessageModelDto> list = FXCollections.observableArrayList();

    @FXML
    private TextField userMessage;

    public void showForChat(String chatId) {
        Stage stage = stageRepository.getStage();
        currentChatId.set(chatId);

        uiExecutorService.execute(() -> {
            List<MessageModelDto> msg = messagesRestService.pullMessages(
                    lastKnownId.get(), currentChatId.get(), DEFAULT_BATCH_SIZE);
            if (!msg.isEmpty()) {
                lastKnownId.set(msg.getLast().messageId());
            } else {
                lastKnownId.set(null);
            }

            Platform.runLater(() -> {
                displayed.set(true);
                messagesArea.getItems().clear();
                messagesArea.getItems().addAll(msg);
                stage.getScene().setRoot(backview);
            });
        });
    }

    @FXML
    void initialize() {
        messagesArea.setItems(list);
        messagesArea.setCellFactory(p -> new MessageController());
        messagesArea.setEditable(false);

        userMessage.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                String text = userMessage.getText();
                if (text.length() < 10000) { // fixme max text length to property
                    userMessage.clear();
                    SendMessageDto dto = new SendMessageDto(
                            currentChatId.get(), userInfoRepository.info().id(), text);
                    uiExecutorService.execute(() -> messagesRestService.saveMessage(dto));
                }
            }

            if (actionEvent.getCode() == KeyCode.ESCAPE) {
                displayed.compareAndSet(true, false);
                fxWeaver.loadController(DialogsController.class).show();
            }
        });
    }

    @Scheduled(fixedDelay = 200)
    private void refresh() {
        if (displayed.get()) {
            List<MessageModelDto> msg = messagesRestService.pullMessages(
                    lastKnownId.get(), currentChatId.get(), DEFAULT_BATCH_SIZE);
            if (msg != null && !msg.isEmpty()) {
                lastKnownId.set(msg.getLast().messageId());
                Platform.runLater(() -> {
                    messagesArea.getItems().addAll(msg);
                    messagesArea.scrollTo(list.size() - 1);
                });
            }
        }
    }
}
