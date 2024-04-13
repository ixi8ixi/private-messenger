package com.earuile.bubble.ui.controllers.registration;

import com.earuile.bubble.core.db.info.UserInfoService;
import com.earuile.bubble.core.rest.interaction.UsersRestService;
import com.earuile.bubble.core.util.LocalizedMessageException;
import com.earuile.bubble.public_interface.RegisterFormDto;
import com.earuile.bubble.public_interface.UserInfoDto;
import com.earuile.bubble.ui.StageRepository;
import com.earuile.bubble.ui.controllers.SimpleController;
import com.earuile.bubble.ui.controllers.chat.ChatController;
import com.earuile.bubble.ui.controllers.dialogs.DialogsController;
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
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
@FxmlView("registration.fxml")
@RequiredArgsConstructor
public class RegistrationController implements SimpleController {
    private final StageRepository stageRepository;
    private final FxWeaver fxWeaver;

    private final UsersRestService usersRestService;
    private final UserInfoService userInfoService;
    private final TaskExecutor threadPoolTaskExecutor;

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
                RegisterFormDto registerDto = new RegisterFormDto(
                        loginField.getText(),
                        passwordField.getText(),
                        nameField.getText()
                );
                taskToRegister(registerDto);
            }
        });
    }

    public void show() {
        Stage stage = stageRepository.getStage();
        stage.getScene().setRoot(registrationPane);
    }

    private void taskToRegister(RegisterFormDto registerFormDto) {
        threadPoolTaskExecutor.execute(() -> {
            try {
                UserInfoDto infoDto = usersRestService.createNewUser(registerFormDto);
                userInfoService.updateInfo(infoDto);
                Platform.runLater(() -> fxWeaver.loadController(DialogsController.class).show());
            } catch (LocalizedMessageException e) {
                Platform.runLater(() -> errorMessage.setText(e.getFormattedMessage()));
            }
        });
    }
}
