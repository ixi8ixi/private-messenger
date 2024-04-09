package com.earuile.bubble.ui.controllers;

import com.earuile.bubble.rest.UserRegistrationRestService;
import com.earuile.bubble.public_interface.RegisterFormDto;
import com.earuile.bubble.ui.StageRepository;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("auth-scene.fxml")
@RequiredArgsConstructor
public class AuthController {
    private final UserRegistrationRestService userRegistrationRestService;
    private final StageRepository stageRepository;
    private final FxWeaver fxWeaver;

    @FXML
    VBox registrationPane;

    @FXML
    TextField loginField;

    @FXML
    TextField nameField;

    @FXML
    PasswordField passwordField;

    @FXML
    Label errorMessage;

    @FXML
    void initialize() {
        Scene scene = stageRepository.getStage().getScene();
        scene.getStylesheets().add("com/earuile/bubble/ui/controllers/auth-style.css");

        RegistrationReadyCallback callback = new RegistrationReadyCallback() {
            @Override
            public void enter() {
                Platform.runLater(() -> fxWeaver.loadController(ChatController.class).show());
            }

            @Override
            public void error(String text) {
                Platform.runLater(() -> errorMessage.setText(text));
            }
        };

        loginField.requestFocus();
        loginField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                nameField.requestFocus();
            }
        });

        nameField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });

        passwordField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                userRegistrationRestService.register(
                        new RegisterFormDto(
                                loginField.getText(),
                                nameField.getText(),
                                passwordField.getText()
                        ), callback
                );
            }
        });
    }

    public void show() {
        Stage stage = stageRepository.getStage();
        stage.getScene().setRoot(registrationPane);
    }
}
