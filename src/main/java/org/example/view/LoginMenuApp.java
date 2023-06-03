package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.Main;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Objects;

public class LoginMenuApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage = Main.stage;
        URL url = LoginMenu.class.getResource("/FXML/LoginMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(url);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        scene.setCursor(new ImageCursor(new Image(Objects.requireNonNull(Main.class.getResource("/Images/SwordIcon.png")).openStream())));
        stage.setTitle("Login Menu");
        stage.show();
    }
}
