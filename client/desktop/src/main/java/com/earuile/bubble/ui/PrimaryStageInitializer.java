package com.earuile.bubble.ui;

import com.earuile.bubble.ui.config.SceneStyleProperty;
import com.earuile.bubble.ui.controllers.start.StartController;
import com.earuile.bubble.ui.image.ImageRepository;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class PrimaryStageInitializer implements ApplicationListener<StageReadyEvent> {

    private final StageRepository stageRepository;
    private final FxWeaver fxWeaver;
    private final SceneStyleProperty sceneStyleProperty;
    private final ImageRepository imageRepository;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage primaryStage = event.stage;
        primaryStage.setTitle("Bubble");

        primaryStage.setWidth(600);
        primaryStage.setHeight(600);


        primaryStage.getIcons().add(imageRepository.logo());

        Scene scene = new Scene(fxWeaver.loadView(StartController.class), 520, 600);
        loadStyles(scene);
        stageRepository.setStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadStyles(Scene scene) {
        sceneStyleProperty.include().forEach(style -> scene.getStylesheets().add(style));
    }
}
