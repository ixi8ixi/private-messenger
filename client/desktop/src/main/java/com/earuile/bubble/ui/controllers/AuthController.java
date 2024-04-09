package com.earuile.bubble.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("auth-scene.fxml")
@RequiredArgsConstructor
public class AuthController {
    @FXML
    TextField userIdField;


}
