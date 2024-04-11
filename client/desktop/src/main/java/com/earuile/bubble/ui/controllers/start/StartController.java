package com.earuile.bubble.ui.controllers.start;

import com.earuile.bubble.core.db.info.UserInfoService;
import com.earuile.bubble.ui.controllers.registration.RegistrationController;
import com.earuile.bubble.ui.controllers.chat.ChatController;
import com.earuile.bubble.ui.image.ImageRepository;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@FxmlView("start.fxml")
@RequiredArgsConstructor
public class StartController {
    private final UserInfoService userInfoService;
    private final TaskExecutor threadPoolTaskExecutor;
    private final FxWeaver fxWeaver;
    private final ImageRepository imageRepository;

    @FXML
    ImageView logo;

    @FXML
    void initialize() {

        logo.setImage(imageRepository.logo()); // todo to image repository

        StartLoaderCallback callback = new StartLoaderCallback() {
            @Override
            public void toRegistration() {
                Platform.runLater(() -> fxWeaver.loadController(RegistrationController.class).show());
            }

            @Override
            public void toChats() {
                Platform.runLater(() -> fxWeaver.loadController(ChatController.class).show());
            }
        };

        threadPoolTaskExecutor.execute(() -> {
            if (userInfoService.loadInfo()) {
                callback.toChats();
            } else {
                callback.toRegistration();
            }
        });
    }
}
