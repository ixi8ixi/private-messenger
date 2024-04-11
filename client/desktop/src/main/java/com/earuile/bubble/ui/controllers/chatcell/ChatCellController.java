package com.earuile.bubble.ui.controllers.chatcell;

import com.earuile.bubble.public_interface.DialogModelDto;
import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ChatCellController extends JFXListCell<DialogModelDto> {
    @FXML
    public AnchorPane dialogPane;

    @FXML
    public Label dialogName;

    @FXML
    public Label dialogText;

    @FXML
    public Label dialogTime;

    private FXMLLoader loader;



    @FXML
    public void initialize() {
        dialogText.setWrapText(true);
    }

    @Override
    protected void updateItem(DialogModelDto item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
            setGraphic(null);

        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("chatcell.fxml"));
                loader.setController(this);

//                getStylesheets().add("com/earuile/bubble/ui/controllers/message/message-style.css");

                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e); // fixme !
                }
            }

            dialogName.setText(item.name());
            dialogText.setText(item.lastMessage());
            dialogTime.setText(item.time());

            setText(null);
            setGraphic(dialogPane);
        }
    }
}
