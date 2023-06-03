package org.example.controller.mainMenuController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.Main;
import org.example.controller.CaptchaGenerator;
import org.example.controller.PasswordHash;
import org.example.model.Data;
import org.example.model.UsersDatabaseJSON;
import org.example.view.LoginMenu;
import org.example.view.enums.Outputs;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static org.example.controller.RegisterMenuController.checkPasswordIsSecure;

public class ChangePassword extends Application {

    private final Stage stage = new Stage();
    public TextField currentPassword;
    public TextField newPassword;
    public TextField newPasswordStatus;
    public ImageView captchaImage;
    public TextField captcha;

    @Override
    public void start(Stage stage) throws Exception {
        stage = this.stage;
        URL url = LoginMenu.class.getResource("/FXML/ChangePassword.fxml");
        AnchorPane anchorPane = FXMLLoader.load(url);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        scene.setCursor(new ImageCursor(new Image(Objects.requireNonNull(Main.class.getResource("/Images/SwordIcon.png")).openStream())));
        stage.setTitle("Change Password");
        stage.show();
    }
    
    public void initialize() throws IOException {
        runCaptcha();
        newPassword.textProperty().addListener((observable, oldText, newText) -> {
            if (newPassword.getText().length() != 0) {
                newPasswordStatus.setText(checkPasswordIsSecure(newText).toString());
                if (newPasswordStatus.getText().equals(Outputs.SECURE_PASSWORD.toString()))
                    newPasswordStatus.setStyle("-fx-text-fill: green;");
                else newPasswordStatus.setStyle("-fx-text-fill: red;");
            }
        });
    }

    public void runCaptcha() throws IOException {
        captcha.setText("");
        CaptchaGenerator.captchaGenerator();
        captchaImage.setImage(new Image(new FileInputStream(CaptchaGenerator.captchaValue +".png")));
    }

    public void submit(MouseEvent mouseEvent) throws IOException {
        if (!newPasswordStatus.getText().equals(Outputs.SECURE_PASSWORD.toString())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Change Password Error");
            alert.setContentText("your new password is not secure !");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Change Password Error");
            if (!PasswordHash.getPasswordHash(currentPassword.getText(),ProfileMenuController.currentUser.getSalt()).equals(ProfileMenuController.currentUser.getPasswordHash())){
                alert.setContentText("your current password is wrong!");
                alert.showAndWait();
            }else if (!captcha.getText().equals(String.valueOf(CaptchaGenerator.captchaValue))){
                runCaptcha();
                alert.setContentText("your entered captcha is wrong!");
                alert.showAndWait();
            }else {
                ProfileMenuController.currentUser.setPasswordHash(PasswordHash.getPasswordHash(newPassword.getText(),ProfileMenuController.currentUser.getSalt()));
                UsersDatabaseJSON.saveUsersInJSON();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Change Password Successful");
                alert.setContentText("your password successfully changed !");
                alert.showAndWait();
            }
        }
    }
}
