package com.earuile.bubble.ui;

import com.earuile.bubble.ui.controllers.MyController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PrimaryStageInitializer implements ApplicationListener<StageReadyEvent> {

    private final FxWeaver fxWeaver;

    @Autowired
    public PrimaryStageInitializer(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.stage;
        System.out.println(fxWeaver);
        Parent check = fxWeaver.loadView(MyController.class);
        System.out.println(check);
        Scene scene = new Scene(check, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}
