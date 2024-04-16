package com.earuile.bubble.ui.controllers.chatcell;

import com.earuile.bubble.public_interface.chat.ChatInfoDto;
import com.earuile.bubble.ui.util.UIException;
import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;

import java.io.IOException;

public class ChatCellController extends JFXListCell<ChatInfoDto> {
    @FXML
    public AnchorPane dialogPane;

    @FXML
    public Label dialogName;

    @FXML
    public Label dialogText;

    @FXML
    public Label dialogTime;

    @Getter
    private String chatId;

    private FXMLLoader loader;

    @FXML
    public void initialize() {
        dialogText.setWrapText(true);
    }

    @Override
    protected void updateItem(ChatInfoDto item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
            setGraphic(null);

        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("chatcell.fxml"));
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    // todo Add UI exception handling
                    throw new UIException(e);
                }
            }

            chatId = item.id();
            dialogName.setText(item.name());
            // fixme placeholders
            dialogText.setText("Here should be last message");
            dialogTime.setText("--:--");

            setText(null);
            setGraphic(dialogPane);
        }
    }
}
