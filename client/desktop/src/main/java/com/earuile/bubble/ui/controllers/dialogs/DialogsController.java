package com.earuile.bubble.ui.controllers.dialogs;

import com.earuile.bubble.core.repository.chat.ChatRepository;
import com.earuile.bubble.core.repository.info.UserInfoRepository;
import com.earuile.bubble.core.rest.interaction.ChatsRestService;
import com.earuile.bubble.core.rest.interaction.UsersRestService;
import com.earuile.bubble.public_interface.MessageModelDto;
import com.earuile.bubble.public_interface.chat.ChatInfoDto;
import com.earuile.bubble.ui.StageRepository;
import com.earuile.bubble.ui.controllers.SimpleController;
import com.earuile.bubble.ui.controllers.chat.ChatController;
import com.earuile.bubble.ui.controllers.chatcell.ChatCellController;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@FxmlView("dialogs.fxml")
@RequiredArgsConstructor
public class DialogsController implements SimpleController {
    private final StageRepository stageRepository;
    private final FxWeaver fxWeaver;

    private final UsersRestService usersRestService;
    private final UserInfoRepository userInfoRepository;

    private final ChatsRestService chatsRestService;

    private final TaskExecutor threadPoolTaskExecutor;
    private final ChatRepository chatRepository;

    private final AtomicBoolean displayed = new AtomicBoolean(false);

    @FXML
    VBox dialogPane;

    @FXML
    HBox newChatCreationPane;

    @FXML
    TextField newChatNameField;

    @FXML
    TextField newChatMembersField;

    @FXML
    private JFXListView<ChatInfoDto> dialogsList;
    ObservableList<ChatInfoDto> list = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        displayed.set(true);
        dialogsList.setItems(list);
        dialogsList.setCellFactory(p -> {
            ChatCellController cell = new ChatCellController();
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    Platform.runLater(() -> {
                        displayed.set(false);
                        fxWeaver.loadController(ChatController.class).showForChat(cell.getChatId());
                    });
                    e.consume();
                }
            });

            return cell;
        });
        dialogsList.setEditable(false);
//        threadPoolTaskExecutor.execute(() -> {
//            List<ChatInfoDto> info = usersRestService.allUserChats(userInfoRepository.info().id());
//            chatRepository.updateChats(info);
//            Platform.runLater(() -> list.addAll(chatRepository.allChats()));
//        });

        newChatCreationPane.setVisible(false);
        newChatCreationPane.setManaged(false);
        dialogPane.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.N) {
                boolean curState = newChatCreationPane.isVisible();
                newChatCreationPane.setVisible(!curState);
                newChatCreationPane.setManaged(!curState);
            }
        });

        newChatNameField.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                newChatMembersField.requestFocus();
            }
        });

        newChatMembersField.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                String chatName = newChatNameField.getText();
                String members = newChatMembersField.getText();
                threadPoolTaskExecutor.execute(() -> {
                    List<String> splittedMembers = members.isBlank()
                            ? null : Arrays.stream(members.split(",")).map(String::trim).toList();
                    chatsRestService.createChat(userInfoRepository.info().id(), chatName, splittedMembers);
                });
            }
        });
    }

    @Scheduled(fixedDelay = 200)
    private void refresh() { // todo выглядит так же, как и работает - но мы видимо в любом случае это переписываем изрядно
        if (displayed.get()) {
            List<ChatInfoDto> info = usersRestService.allUserChats(userInfoRepository.info().id());
            if (info.size() != dialogsList.getItems().size()) {
                Platform.runLater(() -> {
                    list.clear();
                    list.addAll(info.stream().sorted(Comparator.comparing(ChatInfoDto::name)).toList());
                });
            }
        }
    }

    @Override
    public void show() {
        Stage stage = stageRepository.getStage();
        dialogsList.getItems().clear(); // fixme move to initialize
        stage.getScene().setRoot(dialogPane);
    }
}
