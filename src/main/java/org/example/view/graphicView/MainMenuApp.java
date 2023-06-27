package org.example.view.graphicView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.Main;
import org.example.controller.SignUpAndSignInMenu;
import org.example.controller.mainMenuController.ProfileMenuController;
import org.example.view.LoginMenu;

import java.net.URL;
import java.util.Objects;

public class MainMenuApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/MainMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(url);
        Scene scene = new Scene(anchorPane);
        scene.setCursor(new ImageCursor(new Image(Objects.requireNonNull(Main.class.getResource("/Images/SwordIcon.png")).openStream())));
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }


    public void logout(MouseEvent mouseEvent) throws Exception {
        ProfileMenuController.currentUser = null;
        new SignUpAndSignInMenu().start(Main.stage);
    }

    public void startGame(MouseEvent mouseEvent) throws Exception {
        new GameSettingMenu().start(Main.stage);
    }

    public void enterProfileMenu(MouseEvent mouseEvent) throws Exception {
        new ProfileMenuApp().start(Main.stage);
    }

    public void clickSound(MouseEvent mouseEvent) {
        Music.playClickSound();
    }
}
