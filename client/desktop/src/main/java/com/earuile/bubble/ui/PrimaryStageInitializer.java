package com.earuile.bubble.ui;

import com.earuile.bubble.ui.controllers.AuthController;
import com.earuile.bubble.ui.controllers.ChatController;
import com.earuile.bubble.ui.controllers.StartController;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class PrimaryStageInitializer implements ApplicationListener<StageReadyEvent> {

    private final StageRepository stageRepository;
    private final FxWeaver fxWeaver;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage primaryStage = event.stage;
        primaryStage.setTitle("Bubble");

        primaryStage.setWidth(600);
        primaryStage.setHeight(600);

        InputStream iconStream = getClass().getResourceAsStream("soap-bubble.png");
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image);

        Scene scene = new Scene(fxWeaver.loadView(StartController.class), 520, 600);
        scene.getStylesheets().add("/com/earuile/bubble/ui/controllers/start-style.css");
        primaryStage.setScene(scene);

        stageRepository.setStage(primaryStage);
        primaryStage.show();
    }
}
