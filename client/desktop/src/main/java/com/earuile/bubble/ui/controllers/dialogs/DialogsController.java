package com.earuile.bubble.ui.controllers.dialogs;

import com.earuile.bubble.core.repository.info.UserInfoRepository;
import com.earuile.bubble.core.rest.interaction.ChatsRestService;
import com.earuile.bubble.core.rest.interaction.UsersRestService;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@FxmlView("dialogs.fxml")
@RequiredArgsConstructor
public class DialogsController implements SimpleController {
    private final StageRepository stageRepository;
    private final FxWeaver fxWeaver;

    private final UsersRestService usersRestService;
    private final ChatsRestService chatsRestService;

    private final UserInfoRepository userInfoRepository;

    private final ExecutorService uiExecutorService;

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

    @Override
    public void show() {
        Stage stage = stageRepository.getStage();
        stage.getScene().setRoot(dialogPane);
    }

    @FXML
    public void initialize() {
        displayed.set(true);

        setUpDialogsList();
        setUpNewChatCreationPane();
    }

    private void setUpDialogsList() {
        dialogsList.getItems().clear();
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
    }

    private void setUpNewChatCreationPane() {
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
                uiExecutorService.execute(() -> {
                    List<String> splittedMembers = splittedUserIds(members);
                    chatsRestService.createChat(userInfoRepository.info().id(), chatName, splittedMembers);
                });
            }
        });
    }

    private List<String> splittedUserIds(String members) {
        return members.isBlank() ? List.of() : Arrays.stream(members.split(",")).map(String::trim).toList();
    }

    // todo Temporary method. Will rewrite when we switch to HTTP2
    @Scheduled(fixedDelay = 200)
    private void refresh() {
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
}
