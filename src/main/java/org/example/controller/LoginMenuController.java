package org.example.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.Main;
import org.example.controller.mainMenuController.ProfileMenuController;
import org.example.model.Data;
import org.example.model.User;
import org.example.model.UsersDatabaseJSON;
import org.example.view.enums.Outputs;
import org.example.view.graphicView.MainMenu;
import org.example.view.graphicView.Music;
import org.example.view.graphicView.ProfileMenu;

import java.io.FileInputStream;
import java.io.IOException;

import static org.example.controller.RegisterMenuController.checkPasswordIsSecure;

public class LoginMenuController {

    public ImageView background;
    public ImageView submit;
    public ImageView back;
    public TextField username;
    public PasswordField hiddenPassword;
    public Text passwordText;
    public ImageView captchaImage;
    public VBox captchaVBox;
    public TextField captcha;
    public Text forgotPassword;
    public PasswordField newHiddenPassword;
    public VBox forgotPasswordVBox;
    public int forgotPasswordCounter;
    public TextField question;
    public TextField passwordStatus;
    public TextField answer;

    public void initialize() throws IOException {
        forgotPasswordCounter = 0;
        runCaptcha();

        newHiddenPassword.textProperty().addListener((observable, oldText, newText) -> {
            if (newHiddenPassword.getText().length() != 0) {
                passwordStatus.setText(checkPasswordIsSecure(newText).toString());
                if (passwordStatus.getText().equals(Outputs.SECURE_PASSWORD.toString()))
                    passwordStatus.setStyle("-fx-text-fill: green;");
                else passwordStatus.setStyle("-fx-text-fill: red;");
            }
        });


    }
    public Outputs login(String username, String password, String stayLoggedIn) {
        if (username == null || password == null) return Outputs.NOT_ENOUGH_DATA;

        User user;
        if ((user = Data.findUserWithUsername(username)) == null) return Outputs.NOT_EXISTING_USERNAME;

        if (!(user.getPasswordHash().equals(PasswordHash.getPasswordHash(password, user.getSalt()))))
            return Outputs.WRONG_PASSWORD;

        if (stayLoggedIn != null) Data.setStayedLoggedIn(user);

        return Outputs.SUCCESS;
    }

    public Outputs forgetPassword(String securityAnswer, String username) {
        if (securityAnswer.equals(Data.findUserWithUsername(username).getSecurityAnswer())) return Outputs.SUCCESS;
        return Outputs.WRONG_ANSWER;
    }

    public void setNewPassword(String password, String username) {
        User user = Data.findUserWithUsername(username);
        user.setPasswordHash(PasswordHash.getPasswordHash(password, user.getSalt()));
    }

    public void submit(MouseEvent mouseEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        if (forgotPasswordCounter%2==1){
            alert.setHeaderText("Forget Password Error");
            User user = Data.findUserWithUsername(username.getText());
            if (user==null){
                alert.setContentText("invalid username !");
                alert.showAndWait();
            }
            else if (!answer.getText().equals(user.getSecurityAnswer())){
                alert.setContentText("wrong answer !");
                alert.showAndWait();
            }else if (!passwordStatus.getText().equals(Outputs.SECURE_PASSWORD.toString())){
                alert.setContentText("Weak new password !");
                alert.showAndWait();
            }else {
                user.setPasswordHash(PasswordHash.getPasswordHash(newHiddenPassword.getText(),user.getSalt()));
                UsersDatabaseJSON.saveUsersInJSON();
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Success");
                success.setHeaderText("Password changed");
                success.setContentText("Password successfully changed ! Now you can login again .");
                success.showAndWait();
                newHiddenPassword.setText("");
                answer.setText("");
                passwordStatus.setText("");
                forgotPassword();
            }
        }else {
            alert.setHeaderText("Login Error");
            if (username.getText().length()==0){
                alert.setContentText("username field is empty!");
                alert.showAndWait();
                return;
            }
            if (Data.findUserWithUsername(username.getText())==null){
                alert.setContentText("username not found !");
                alert.showAndWait();
                return;
            }
            User user = Data.findUserWithUsername(username.getText());
            if (!PasswordHash.getPasswordHash(hiddenPassword.getText(),user.getSalt()).equals(user.getPasswordHash())){
                alert.setContentText("Wrong Password !");
                hiddenPassword.setText("");
                runCaptcha();
                alert.showAndWait();
            }else if (!captcha.getText().equals(String.valueOf(CaptchaGenerator.captchaValue))){
                alert.setContentText("Wrong Captcha !");
                runCaptcha();
                alert.showAndWait();
            }else {
                ProfileMenuController.currentUser = user;
                new MainMenu().start(Main.stage);
            }
        }
        
    }

    public void toggleVisiblePassword(MouseEvent mouseEvent) {
        publicShowPassword(hiddenPassword);
    }

    static void publicShowPassword(PasswordField hiddenPassword) {
        String password = hiddenPassword.getText();
        hiddenPassword.setPromptText(password);
        hiddenPassword.clear();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000),
                actionEvent -> {
                    hiddenPassword.setText(password);
                }));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public void forgotPassword() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Forget Password Error");
        if (forgotPasswordCounter%2==0){
            if (username.getText().length()==0){
                alert.setContentText("username field is empty!");
                alert.showAndWait();
                return;
            }
            if (Data.findUserWithUsername(username.getText())==null){
                alert.setContentText("username not found !");
                alert.showAndWait();
                return;
            }
            captchaVBox.setVisible(false);
            forgotPasswordVBox.setVisible(true);
            question.setText(Data.findUserWithUsername(username.getText()).getSecurityQuestion());
        }else {
            captchaVBox.setVisible(true);
            forgotPasswordVBox.setVisible(false);
        }
        forgotPasswordCounter++;
    }

    public void runCaptcha() throws IOException {
        captcha.setText("");
        CaptchaGenerator.captchaGenerator();
        captchaImage.setImage(new Image(new FileInputStream( CaptchaGenerator.captchaValue +".png")));
    }

    public void toggleVisibleNewPassword(MouseEvent mouseEvent) {
        publicShowPassword(newHiddenPassword);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new Main().start(Main.stage);
    }

    public void clickSound(MouseEvent mouseEvent) {
        Music.playClickSound();
    }
}
