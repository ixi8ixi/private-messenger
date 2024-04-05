package com.earuile.bubble.ui;

import com.earuile.bubble.ClientApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.InputStream;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        this.context = new SpringApplicationBuilder()
                .sources(ClientApplication.class)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Bubble");

        primaryStage.setWidth(600);
        primaryStage.setHeight(600);

        InputStream iconStream = getClass().getResourceAsStream("soap-bubble.png");
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image);
//
//        Label helloWorldLabel = new Label("Hello world!");
//        helloWorldLabel.setAlignment(Pos.CENTER);
//        Scene primaryScene = new Scene(helloWorldLabel);
//        primaryStage.setScene(primaryScene);

        primaryStage.show();

//        context.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() throws Exception {
        this.context.close();
        Platform.exit();
    }
}
