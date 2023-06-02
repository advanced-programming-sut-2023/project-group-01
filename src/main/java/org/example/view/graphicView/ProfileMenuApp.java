package org.example.view.graphicView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.view.LoginMenu;

import java.net.URL;

public class ProfileMenuApp extends Application {
    public static AnchorPane profileMenuPane;
    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/ProfileMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(url);
        Scene scene = new Scene(anchorPane);
        profileMenuPane = anchorPane;
        stage.setScene(scene);
        stage.setTitle("Profile Menu");
        stage.show();
    }

}
