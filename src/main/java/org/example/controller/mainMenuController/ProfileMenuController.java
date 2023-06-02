package org.example.controller.mainMenuController;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.Main;
import org.example.controller.PasswordHash;
import org.example.model.Data;
import org.example.model.User;
import org.example.model.UsersDatabaseJSON;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.RegisterMenuCommands;
import org.example.view.graphicView.MainMenuApp;
import org.example.view.graphicView.Music;
import org.example.view.graphicView.ProfileMenuApp;
import org.example.view.graphicView.ScoreBoard;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;

import static org.example.controller.RegisterMenuController.checkValidEmail;

public class ProfileMenuController {

    public ImageView avatar;
    public static User currentUser;
    public TextField username;
    public TextField nickname;
    public TextField email;
    public TextField slogan;
    public TextField newSlogan;
    public TextField newNickname;
    public TextField newUsername;
    public TextField newUsernameStatus;
    public TextField newEmail;
    public TextField newEmailStatus;
    public VBox galleryVBox;
    public ImageView galleryBackground;

    public void initialize() throws IOException {

        Circle clip = new Circle(80, 80, 80);
        avatar.setClip(clip);
        username.setText(currentUser.getUsername());
        nickname.setText(currentUser.getNickname());
        email.setText(currentUser.getEmail());
        slogan.setText(currentUser.getSlogan());
        avatar.setImage(new Image(currentUser.getAvatarUrl().openStream()));

        usernameListener(newUsername, newUsernameStatus);
        emailListener(newEmail,newEmailStatus);

    }

    public static void emailListener(TextField email, TextField emailStatus){
        email.textProperty().addListener((observable, oldText, newText) -> {
            emailStatus.setText(checkValidEmail(newText).toString());

            if (emailStatus.getText().equals(Outputs.VALID_EMAIL.toString()))
                emailStatus.setStyle("-fx-text-fill: green;");
            else emailStatus.setStyle("-fx-text-fill: red;");
        });
    }

    public static void usernameListener(TextField username, TextField usernameStatus) {
        username.textProperty().addListener((observable, oldText, newText) -> {

            Matcher validUsernameMatcher = RegisterMenuCommands.getMatcher(newText, RegisterMenuCommands.VALID_USERNAME);
            if (!validUsernameMatcher.find()) usernameStatus.setText(Outputs.INVALID_USERNAME.toString());

            else if (Data.findUserWithUsername(newText) != null) usernameStatus.setText(Outputs.USER_EXISTS.toString());

            else usernameStatus.setText(Outputs.VALID_USERNAME.toString());

            if (usernameStatus.getText().equals(Outputs.VALID_USERNAME.toString()))
                usernameStatus.setStyle("-fx-text-fill: green;");
            else
                usernameStatus.setStyle("-fx-text-fill: red;");

        });
    }


    public Outputs changeUsername(String username) {
        Matcher validUsernameMatcher = RegisterMenuCommands.getMatcher(username, RegisterMenuCommands.VALID_USERNAME);
        if (!validUsernameMatcher.find()) return Outputs.INVALID_USERNAME;
        currentUser.setUsername(username);
        return Outputs.USERNAME_CHANGE_SUCCESSFUL;
    }

    public Outputs changeEmail(String email) {
        Matcher validEmailMatcher = RegisterMenuCommands.getMatcher(email, RegisterMenuCommands.VALID_EMAIL);
        if (!validEmailMatcher.find()) return Outputs.INVALID_EMAIL;
        currentUser.setEmail(email);
        return Outputs.EMAIL_CHANGE_SUCCESSFUL;
    }

    public Outputs changePassword(String oldPassword, String newPassword) {
        if (oldPassword.equals(newPassword)) return Outputs.SAME_NEW_PASSWORD;
        if (!(PasswordHash.getPasswordHash(oldPassword, currentUser.getSalt()).equals(currentUser.getPasswordHash())))
            return Outputs.WRONG_OLD_PASSWORD;

//        if (!RegisterMenuController.checkPasswordIsSecure(newPassword).equals(Outputs.SECURE_PASSWORD))
//            return RegisterMenuController.checkPasswordIsSecure(newPassword);

        currentUser.setPasswordHash(PasswordHash.getPasswordHash(newPassword, currentUser.getSalt()));
        return Outputs.PASSWORD_CHANGE_SUCCESSFUL;
    }

    public void changeSlogan(MouseEvent mouseEvent) throws IOException {
        currentUser.setSlogan(newSlogan.getText());
        slogan.setText(currentUser.getSlogan());
        newSlogan.setText("");
        UsersDatabaseJSON.saveUsersInJSON();
    }

    public void changeNickname(MouseEvent mouseEvent) throws IOException {
        if (newNickname.getText().length()==0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Change Nickname Error");
            alert.setContentText("your new nickname field is empty !");
            alert.showAndWait();
        }
        else {
            currentUser.setNickname(newNickname.getText());
            nickname.setText(newNickname.getText());
            newNickname.setText("");
            UsersDatabaseJSON.saveUsersInJSON();
        }
    }

    public void changeEmail(MouseEvent mouseEvent) throws IOException {
        if (!newEmailStatus.getText().equals(Outputs.VALID_EMAIL.toString())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Change Email Error");
            alert.setContentText("your new email is invalid !");
            alert.showAndWait();
        }else {
            currentUser.setEmail(newEmail.getText());
            email.setText(newEmail.getText());
            newEmail.setText("");
            UsersDatabaseJSON.saveUsersInJSON();
        }
    }

    public void changeUsername(MouseEvent mouseEvent) throws IOException {
        if (!newUsernameStatus.getText().equals(Outputs.VALID_USERNAME.toString())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Change Username Error");
            alert.setContentText("your new username is invalid !");
            alert.showAndWait();
        }else {
            currentUser.setUsername(newUsername.getText());
            username.setText(newUsername.getText());
            newUsername.setText("");
            UsersDatabaseJSON.saveUsersInJSON();
        }
    }

    public void changePassword(MouseEvent mouseEvent) throws Exception {
        new ChangePassword().start(new Stage());
    }

    public void chooseFromFile(MouseEvent mouseEvent) throws IOException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG, PNG, JPEG Files", "*.jpg", "*.png", "*.jpeg"));
        File selectedFile = fc.showOpenDialog(Main.stage);
        if (selectedFile != null) {
            currentUser.setAvatarUrl(selectedFile.toURI().toURL());
            UsersDatabaseJSON.saveUsersInJSON();
            resetAvatar();
        } else {
            System.out.println("file is not valid");
        }
    }

    private void resetAvatar() throws IOException {
        avatar.setImage(new Image(currentUser.getAvatarUrl().openStream()));
    }
    public void scoreBoard(MouseEvent mouseEvent) throws Exception {
        new ScoreBoard().start(Main.stage);
    }

    public void onDragCheck(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        if (db.hasFiles() && checkFormatOfFile(db.getFiles().get(0)))
            dragEvent.acceptTransferModes(TransferMode.COPY);
        dragEvent.consume();
    }

    private boolean checkFormatOfFile(File file) {
        String fileName = file.getName();
        fileName = fileName.substring(fileName.lastIndexOf('.') + 1);
        return fileName.equals("png") || fileName.equals("jpg");
    }

    public void changeImageWithDrag(DragEvent dragEvent) throws MalformedURLException {
        Dragboard db = dragEvent.getDragboard();
        if (db.hasFiles()) {
            for (File file : db.getFiles()) {
                String absolutePath = file.getAbsolutePath();
                Image dbimage = new Image(absolutePath);
                avatar.setImage(dbimage);
                currentUser.setAvatarUrl(new URL("file:/"+absolutePath));
            }
            dragEvent.setDropCompleted(true);
        } else {
            dragEvent.setDropCompleted(false);
        }
    }

    @FXML
    private void showGallery(){
        galleryBackground.setVisible(true);
        galleryVBox.setVisible(true);
        BoxBlur boxblur = new BoxBlur();
        for (Node node : ProfileMenuApp.profileMenuPane.getChildren()){
            node.setEffect(boxblur);
        }
        galleryBackground.setEffect(null);
        galleryVBox.setEffect(null);
    }

    @FXML
    private void hideGallery(){
        for (Node node : ProfileMenuApp.profileMenuPane.getChildren()){
            node.setEffect(null);
        }
        galleryVBox.setVisible(false);
        galleryBackground.setVisible(false);
    }

    public void changeAvatarFromGallery(MouseEvent mouseEvent) throws IOException {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        if (imageView.getImage()!=null){
            currentUser.setAvatarUrl(new URL(imageView.getImage().getUrl()));
            avatar.setImage(new Image(currentUser.getAvatarUrl().openStream()));
            hideGallery();
        }
    }

    public void chooseFromGallery(MouseEvent mouseEvent) {
        showGallery();
    }

    public void backToMainMenu(MouseEvent mouseEvent) throws Exception {
        new MainMenuApp().start(Main.stage);
    }

    public void clickSound(MouseEvent mouseEvent) {
        Music.playClickSound();
    }
}
