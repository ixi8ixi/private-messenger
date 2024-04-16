package com.earuile.bubble.ui.controllers.start;

import com.earuile.bubble.core.repository.DataLoader;
import com.earuile.bubble.ui.controllers.dialogs.DialogsController;
import com.earuile.bubble.ui.controllers.registration.RegistrationController;
import com.earuile.bubble.ui.image.ImageRepository;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
@FxmlView("start.fxml")
@RequiredArgsConstructor
public class StartController {
    private final ImageRepository imageRepository;
    private final FxWeaver fxWeaver;

    private final DataLoader dataLoader;

    private final ExecutorService uiExecutorService;

    @FXML
    ImageView logo;

    @FXML
    void initialize() {
        logo.setImage(imageRepository.logo());

        uiExecutorService.execute(() -> {
            if (dataLoader.load()) {
                Platform.runLater(() -> fxWeaver.loadController(DialogsController.class).show());
            } else {
                Platform.runLater(() -> fxWeaver.loadController(RegistrationController.class).show());
            }
        });
    }
}
