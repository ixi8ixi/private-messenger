package com.earuile.bubble.ui.controllers.message;

import com.earuile.bubble.public_interface.message.MessageModelDto;
import com.earuile.bubble.ui.util.UIException;
import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MessageController extends JFXListCell<MessageModelDto> {
    @FXML
    public AnchorPane anPane;

    @FXML
    public Label username;

    @FXML
    public Label text;

    @FXML
    public Label time;

    private FXMLLoader loader;

    @FXML
    public void initialize() {
        text.setWrapText(true);
    }

    @Override
    protected void updateItem(MessageModelDto item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
            setGraphic(null);

        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("message.fxml"));
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    // todo Add UI exception handling
                    throw new UIException(e);
                }
            }

            username.setText(item.username());
            text.setText(item.text());
            time.setText(item.time());

            setText(null);
            setGraphic(anPane);
        }
    }
}
