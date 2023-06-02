package org.example.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.Main;
import org.example.controller.mainMenuController.ProfileMenuController;
import org.example.model.Data;
import org.example.model.User;
import org.example.model.UsersDatabaseJSON;
import org.example.view.RegisterMenu;
import org.example.view.enums.Outputs;
import org.example.view.enums.RandomSlogans;
import org.example.view.enums.SecurityQuestion;
import org.example.view.enums.commands.RegisterMenuCommands;
import org.example.view.graphicView.Music;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Matcher;

public class RegisterMenuController {

    public static User temporaryCreatedUser;
    public ImageView background;
    public PasswordField hiddenPassword;
    public TextField usernameStatus;
    public TextField username;
    public TextField passwordStatus;
    public TextField emailStatus;
    public TextField email;
    public Text passwordText;
    public TextField nickname;
    public CheckBox chooseSlogan;
    public TextField slogan;
    public HBox sloganHBox;
    public ImageView submit;
    public VBox registerBoxes;
    public VBox securityQuestionVBox;
    public ChoiceBox securityQuestion;
    public TextField answer;
    public VBox captchaVBox;
    public ImageView captchaImage;
    public TextField captcha;
    public ChoiceBox famousSlogans;
    private boolean randomPassword;
    private static boolean isSecurityQuestion;


    public void initialize() throws InterruptedException, FileNotFoundException {
        isSecurityQuestion = false;
        usernameStatus.setEditable(false);
        emailStatus.setEditable(false);
        passwordStatus.setEditable(false);
        background.setImage(new Image(new FileInputStream("src/main/resources/images/RegisterMenu.png")));
        addFamousSlogans();

        ProfileMenuController.usernameListener(username, usernameStatus);

        hiddenPassword.textProperty().addListener((observable, oldText, newText) -> {
            if (hiddenPassword.getText().length() != 0) {
                passwordStatus.setText(checkPasswordIsSecure(newText).toString());
                if (passwordStatus.getText().equals(Outputs.SECURE_PASSWORD.toString()))
                    passwordStatus.setStyle("-fx-text-fill: green;");
                else passwordStatus.setStyle("-fx-text-fill: red;");
            }
        });

        famousSlogans.setOnAction((event) -> {
            String slog = (String) famousSlogans.getSelectionModel().getSelectedItem();
            slogan.setText(slog);
        });

        ProfileMenuController.emailListener(email,emailStatus);

    }

    private void addFamousSlogans(){
        famousSlogans.getItems().add(RandomSlogans.getSlogan(0));
        famousSlogans.getItems().add(RandomSlogans.getSlogan(1));
        famousSlogans.getItems().add(RandomSlogans.getSlogan(2));
        famousSlogans.getItems().add(RandomSlogans.getSlogan(3));
        famousSlogans.getItems().add(RandomSlogans.getSlogan(4));
        famousSlogans.setValue("Famous slogans");
    }

    public static Outputs checkValidEmail(String mail) {
        if (Data.findUserWithEmail(mail) != null) return Outputs.EMAIL_EXISTS;
        Matcher validEmailMatcher = RegisterMenuCommands.getMatcher(mail, RegisterMenuCommands.VALID_EMAIL);
        if (!validEmailMatcher.find()) return Outputs.INVALID_EMAIL;
        return Outputs.VALID_EMAIL;
    }

    public static Outputs checkPasswordIsSecure(String password) {
        Matcher matcher = RegisterMenuCommands.getMatcher(password, RegisterMenuCommands.SECURE_PASSWORD);
        if (!matcher.find()) return Outputs.SHORT_PASSWORD;
        if (matcher.group("number") == null) return Outputs.PASSWORD_WITHOUT_NUMBER;
        if (matcher.group("smallLetter") == null) return Outputs.PASSWORD_WITHOUT_SMALL_LETTER;
        if (matcher.group("capitalLetter") == null) return Outputs.PASSWORD_WITHOUT_CAPITAL_LETTER;
        if (matcher.group("specialCharacter") == null) return Outputs.PASSWORD_WITHOUT_SPECIAL_CHARACTER;
        return Outputs.SECURE_PASSWORD;
    }

    public Outputs registerUser(String username, String password, String email, String nickname, String passwordConfirm, String slogan, String sloganSwitch) throws NoSuchAlgorithmException {

        randomPassword = false;
        boolean randomSlogan = false;
        Outputs output;

        if (!((output = registerValidationCheck(username, password, email, nickname, passwordConfirm, slogan, sloganSwitch)).equals(Outputs.VALID_REGISTRATION_INPUT)))
            return output;

        if (password.equals("random")) {
            randomPassword = true;
            password = createRandomPassword();
            RegisterMenu.randomPass = password;
        }


        byte[] salt = createNewSalt();
        String passwordHash = PasswordHash.getPasswordHash(password, salt);

        temporaryCreatedUser = new User(username, passwordHash, nickname, email, slogan, null, null, salt);
        if (slogan != null && slogan.equals("random")) return Outputs.RANDOM_SLOGAN;


        if (randomPassword) return Outputs.RANDOM_PASSWORD_CONFIRMATION;

        return Outputs.SUCCESS;

    }

    public Outputs randomPasswordConfirmation(String passwordConfirm) {
        if (passwordConfirm.equals(RegisterMenu.randomPass)) return null;
        return Outputs.WRONG_PASSWORD_CONFIRM;
    }

    public Outputs securityQuestion(int number, String answer, String answerConfirm) {
        if (number > 3) return Outputs.INVALID_QUESTION_NUMBER;
        if (!answer.equals(answerConfirm)) return Outputs.WRONG_ANSWER_CONFIRM;
        temporaryCreatedUser.setSecurityQuestion(SecurityQuestion.getQuestion(number - 1));
        temporaryCreatedUser.setSecurityAnswer(answer);
        return Outputs.SUCCESS;
    }

    public Outputs registerValidationCheck(String username, String password, String email, String nickname, String passwordConfirm, String slogan, String sloganSwitch) {

        boolean randomPassword = false;

        if (username == null || password == null || email == null || nickname == null) return Outputs.NOT_ENOUGH_DATA;

        if (password.equals("random")) randomPassword = true;

        Matcher validUsernameMatcher = RegisterMenuCommands.getMatcher(username, RegisterMenuCommands.VALID_USERNAME);
        if (!validUsernameMatcher.find()) return Outputs.INVALID_USERNAME;

        if (Data.findUserWithUsername(username) != null) return Outputs.USER_EXISTS;

        if (!randomPassword) {
            Outputs passwordStatus = checkPasswordIsSecure(password);
            if (!passwordStatus.equals(Outputs.SECURE_PASSWORD)) return passwordStatus;
        }


        if (!randomPassword) if (!password.equals(passwordConfirm)) return Outputs.WRONG_PASSWORD_CONFIRM;


        if (Data.findUserWithEmail(email) != null) return Outputs.EMAIL_EXISTS;

        if ((sloganSwitch != null) && (slogan == null)) return Outputs.NOT_ENOUGH_DATA;

        Matcher validEmailMatcher = RegisterMenuCommands.getMatcher(email, RegisterMenuCommands.VALID_EMAIL);
        if (!validEmailMatcher.find()) return Outputs.INVALID_EMAIL;

        return Outputs.VALID_REGISTRATION_INPUT;
    }

    public String createRandomPassword() {
        CharacterRule smallLetter = new CharacterRule(EnglishCharacterData.LowerCase);
        CharacterRule capitalletter = new CharacterRule(EnglishCharacterData.UpperCase);
        CharacterRule digit = new CharacterRule(EnglishCharacterData.Digit);
        CharacterRule specialCharacter = new CharacterRule(EnglishCharacterData.Special);
        PasswordGenerator passGen = new PasswordGenerator();
        Random random = new Random();
        String password = passGen.generatePassword(random.nextInt(10) + 6, specialCharacter, smallLetter, capitalletter, digit);
        RegisterMenu.randomPass = password.replaceAll("\"", "#");
        return password.replaceAll("\"", "#");
    }


    private byte[] createNewSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }


    public void toggleVisiblePassword() throws InterruptedException {
        LoginMenuController.publicShowPassword(hiddenPassword);
    }

    @FXML
    private void checkValidUsername() {
        String username2 = username.getText();
        Matcher validUsernameMatcher = RegisterMenuCommands.getMatcher(username2, RegisterMenuCommands.VALID_USERNAME);
        if (!validUsernameMatcher.find()) usernameStatus.setText(Outputs.INVALID_USERNAME.toString());

        if (Data.findUserWithUsername(username2) != null) usernameStatus.setText(Outputs.USER_EXISTS.toString());

        usernameStatus.setText(Outputs.VALID_USERNAME.toString());

    }

    public void randomPassword(MouseEvent mouseEvent) {
        String randomPassword = createRandomPassword();
        passwordStatus.setText("random password:" + randomPassword);

        hiddenPassword.setPromptText(randomPassword);
        hiddenPassword.clear();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000),
                actionEvent -> {
                    hiddenPassword.setText(randomPassword);
                    passwordStatus.setText(Outputs.SECURE_PASSWORD.toString());
                }));
        timeline.setCycleCount(1);
        timeline.play();

    }

    public void chooseSlogan(MouseEvent mouseEvent) {
        if (chooseSlogan.isSelected()){
            sloganHBox.setVisible(true);
        }
        else sloganHBox.setVisible(false);
    }

    public void randomSlogan(MouseEvent mouseEvent) {
        slogan.setText(RandomSlogans.getRandomSlogan());
    }

    public void submit(MouseEvent mouseEvent) throws Exception {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Register Error");

        if (!isSecurityQuestion){
            if (!usernameStatus.getText().equals(Outputs.VALID_USERNAME.toString())){
                alert.setContentText("your username is invalid");
                alert.showAndWait();
            }else if (!emailStatus.getText().equals(Outputs.VALID_EMAIL.toString())){
                alert.setContentText("your email is invalid");
                alert.showAndWait();
            }
            else if (!passwordStatus.getText().equals(Outputs.SECURE_PASSWORD.toString())){
                alert.setContentText("your password is invalid");
                alert.showAndWait();
            }else if (nickname.getText().length()==0){
                alert.setContentText("your nickname field is empty");
                alert.showAndWait();
            }else if (chooseSlogan.isSelected()&&slogan.getText().length()==0) {
                alert.setContentText("your slogan field is empty");
                alert.showAndWait();
            }
            else {
                byte[] salt = createNewSalt();
                String passwordHash = PasswordHash.getPasswordHash(hiddenPassword.getText(),salt);
                temporaryCreatedUser = new User(username.getText(),passwordHash,nickname.getText(),email.getText(),slogan.getText(),null,null,salt);
                runSecurityQuestion();
            }
        }else {
            if (temporaryCreatedUser.getSecurityQuestion()==null){
                alert.setContentText("your didn't choose a security question");
                alert.showAndWait();
            }
            else if (answer.getText().length()==0){
                alert.setContentText("your answer field is empty");
                alert.showAndWait();
            }else if (captcha.getText().length()==0){
                alert.setContentText("your captcha field is empty");
                alert.showAndWait();
            }else if (!captcha.getText().equals(String.valueOf(CaptchaGenerator.captchaValue))){
                alert.setContentText("your captcha answer is wrong");
                alert.showAndWait();
                runCaptcha();
            }else {
                temporaryCreatedUser.setSecurityAnswer(answer.getText());
                Data.addUser(temporaryCreatedUser);
                UsersDatabaseJSON.saveUsersInJSON();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText(temporaryCreatedUser.getUsername()+" successfully created !");
                alert.setTitle("Success");
                alert.setHeaderText("Successful Register");
                alert.showAndWait();
                new SignUpAndSignInMenu().start(Main.stage);
            }
        }

    }

    public void runSecurityQuestion() throws IOException {
        isSecurityQuestion = true;
        background.setImage(new Image(new FileInputStream("src/main/resources/images/SecurityQuestion.png")));
        registerBoxes.setVisible(false);
        captchaVBox.setVisible(true);
        runCaptcha();
        securityQuestionVBox.setVisible(true);
        securityQuestion.getItems().add(SecurityQuestion.getQuestion(0));
        securityQuestion.getItems().add(SecurityQuestion.getQuestion(1));
        securityQuestion.getItems().add(SecurityQuestion.getQuestion(2));


        securityQuestion.setOnAction((event) -> {
            String question = (String) securityQuestion.getSelectionModel().getSelectedItem();
            temporaryCreatedUser.setSecurityQuestion(question);
        });

    }

    public void runCaptcha() throws IOException {
        captcha.setText("");
        CaptchaGenerator.captchaGenerator();
        captchaImage.setImage(new Image(new FileInputStream( CaptchaGenerator.captchaValue +".png")));
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new Main().start(Main.stage);
    }

    public void clickSound(MouseEvent mouseEvent) {
        Music.playClickSound();
    }
}
