package com.earuile.bubble.ui.controllers.dialogs;

import com.earuile.bubble.public_interface.DialogModelDto;
import com.earuile.bubble.ui.StageRepository;
import com.earuile.bubble.ui.controllers.SimpleController;
import com.earuile.bubble.ui.controllers.chat.ChatController;
import com.earuile.bubble.ui.controllers.chatcell.ChatCellController;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("dialogs.fxml")
@RequiredArgsConstructor
public class DialogsController implements SimpleController {
    private final StageRepository stageRepository;
    private final FxWeaver fxWeaver;

    @FXML
    AnchorPane dialogPane;

    @FXML
    private JFXListView<DialogModelDto> dialogsList;
    ObservableList<DialogModelDto> list = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        dialogsList.setItems(list);
        dialogsList.setCellFactory(p -> {
            ChatCellController cell = new ChatCellController();
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    Platform.runLater(() -> fxWeaver.loadController(ChatController.class).show());
                    e.consume();
                }
            });

            return cell;
        });
        dialogsList.setEditable(false);
    }

    @Override
    public void show() {
        Stage stage = stageRepository.getStage();
        stage.getScene().setRoot(dialogPane);
    }
}
