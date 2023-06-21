package org.example.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ImageInput;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.Main;
import org.example.model.User;
import org.example.model.UsersDatabaseJSON;
import org.example.view.LoginMenu;
import org.example.view.LoginMenuApp;
import org.example.view.RegisterMenuApp;
import org.example.view.graphicView.Music;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class SignUpAndSignInMenu extends Application {
    public ImageView background;

    @Override
    public void start(Stage stage) throws Exception {
        stage = Main.stage;
        URL url = LoginMenu.class.getResource("/FXML/Main.fxml");
        AnchorPane anchorPane = FXMLLoader.load(url);
        Scene scene = new Scene(anchorPane);
        scene.setCursor(new ImageCursor(new Image(Objects.requireNonNull(Main.class.getResource("/Images/SwordIcon.png")).openStream())));
        stage.setScene(scene);
        stage.setTitle("Sign up and Sign in menu");
        stage.show();

    }

    public void initialize(){
        background.setOnScroll((ScrollEvent event) -> {
            // Adjust the zoom factor anchorPaneInSplitPan per your requirement
            System.out.println(event.getDeltaY());
        });
    }

    public void loginMenuRun(MouseEvent mouseEvent) throws Exception {
        new LoginMenuApp().start(Main.stage);
    }

    public void exitGame(MouseEvent mouseEvent) throws IOException {
        UsersDatabaseJSON.saveUsersInJSON();
        System.exit(0);
    }

    public void registerMenuRun(MouseEvent mouseEvent) throws Exception {
        new RegisterMenuApp().start(Main.stage);
    }

    public void clickSound() {
        Music.playClickSound();
    }
}
