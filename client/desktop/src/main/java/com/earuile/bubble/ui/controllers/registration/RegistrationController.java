package com.earuile.bubble.ui.controllers.registration;

import com.earuile.bubble.core.rest.config.property.UsersRestInteractionProperty;
import com.earuile.bubble.public_interface.RegisterFormDto;
import com.earuile.bubble.ui.StageRepository;
import com.earuile.bubble.ui.controllers.SimpleController;
import com.earuile.bubble.ui.controllers.chat.ChatController;
import javafx.application.Platform;
import javafx.fxml.FXML;
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
@FxmlView("registration.fxml")
@RequiredArgsConstructor
public class RegistrationController implements SimpleController {
//    private final UserRegistrationRestService userRegistrationRestService;

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

//        loginField.requestFocus();
//        loginField.setOnKeyPressed(keyEvent -> {
//            if (keyEvent.getCode() == KeyCode.ENTER) {
//                nameField.requestFocus();
//            }
//        });
//
//        nameField.setOnKeyPressed(keyEvent -> {
//            if (keyEvent.getCode() == KeyCode.ENTER) {
//                passwordField.requestFocus();
//            }
//        });
//
//        passwordField.setOnKeyPressed(keyEvent -> {
//            if (keyEvent.getCode() == KeyCode.ENTER) {
//                userRegistrationRestService.register(
//                        new RegisterFormDto(
//                                loginField.getText(),
//                                nameField.getText(),
//                                passwordField.getText()
//                        ), callback
//                );
//            }
//        });
    }

    public void show() {
        Stage stage = stageRepository.getStage();
        stage.getScene().setRoot(registrationPane);
    }
}
