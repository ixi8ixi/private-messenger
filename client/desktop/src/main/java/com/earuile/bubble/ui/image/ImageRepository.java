package com.earuile.bubble.ui.image;

import jakarta.annotation.PostConstruct;
import javafx.scene.image.Image;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class ImageRepository {
    private Image image;

    @PostConstruct
    private void loadImage() {
        InputStream iconStream = getClass().getResourceAsStream("../bubbles_1fae7.png");
        image = new Image(iconStream);
    }

    public Image logo() {
        return image;
    }
}
