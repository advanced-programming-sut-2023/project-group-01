package Test;

import org.example.controller.RegisterMenuController;
import org.example.model.Data;
import org.example.model.User;
import org.example.view.RegisterMenu;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.RegisterMenuCommands;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.assertNull;

public class RegisterMenuTest {

    private final RegisterMenuController registerMenuController = new RegisterMenuController();
    private final RegisterMenu registerMenu = new RegisterMenu();
    private Outputs output = null;
    private Matcher matcher;
    private String inputLine;


    @org.junit.Test
    public void additionalInputs() throws NoSuchAlgorithmException {
        inputLine = "user create -u username1 additional";
        matcher = RegisterMenuCommands.getMatcher(inputLine, RegisterMenuCommands.REGISTER_REGEX);
        if (matcher.find()) output = registerMenu.register(matcher, inputLine);
        Assertions.assertEquals(Outputs.INVALID_REGISTRATION_INPUT, output);
    }

    @org.junit.Test
    public void invalidRegisterCommand() {
        inputLine = "chertopert";
        matcher = RegisterMenuCommands.getMatcher(inputLine, RegisterMenuCommands.REGISTER_REGEX);
        if (matcher.find()) output = null;
        else output = Outputs.INVALID_COMMAND;
        Assertions.assertEquals(Outputs.INVALID_COMMAND, output);
    }

    @org.junit.Test
    public void weakPassword1() throws NoSuchAlgorithmException {
        inputLine = "user create -u username1 -p password password -n nickname -email saeed";
        matcher = RegisterMenuCommands.getMatcher(inputLine, RegisterMenuCommands.REGISTER_REGEX);
        if (matcher.find()) output = registerMenu.register(matcher, inputLine);
        Assertions.assertEquals(Outputs.PASSWORD_WITHOUT_NUMBER, output);
    }

    @org.junit.Test
    public void weakPassword2() throws NoSuchAlgorithmException {
        inputLine = "user create -u username1 -p password2 password2 -n nickname -email saeed";
        matcher = RegisterMenuCommands.getMatcher(inputLine, RegisterMenuCommands.REGISTER_REGEX);
        if (matcher.find()) output = registerMenu.register(matcher, inputLine);
        Assertions.assertEquals(Outputs.PASSWORD_WITHOUT_CAPITAL_LETTER, output);
    }

    @org.junit.Test
    public void weakPassword3() throws NoSuchAlgorithmException {
        inputLine = "user create -u username1 -p Password2 Password2 -n nickname -email saeed";
        matcher = RegisterMenuCommands.getMatcher(inputLine, RegisterMenuCommands.REGISTER_REGEX);
        if (matcher.find()) output = registerMenu.register(matcher, inputLine);
        Assertions.assertEquals(Outputs.PASSWORD_WITHOUT_SPECIAL_CHARACTER, output);
    }

    @org.junit.Test
    public void weakPassword4() throws NoSuchAlgorithmException {
        inputLine = "user create -u username1 -p Paod2 Paod2 -n nickname -email saeed";
        matcher = RegisterMenuCommands.getMatcher(inputLine, RegisterMenuCommands.REGISTER_REGEX);
        if (matcher.find()) output = registerMenu.register(matcher, inputLine);
        Assertions.assertEquals(Outputs.SHORT_PASSWORD, output);
    }

    @org.junit.Test
    public void weakPassword5() throws NoSuchAlgorithmException {
        inputLine = "user create -u username1 -p PASSSS2 PASSSS2 -n nickname -email saeed";
        matcher = RegisterMenuCommands.getMatcher(inputLine, RegisterMenuCommands.REGISTER_REGEX);
        if (matcher.find()) output = registerMenu.register(matcher, inputLine);
        Assertions.assertEquals(Outputs.PASSWORD_WITHOUT_SMALL_LETTER, output);
    }

    @org.junit.Test
    public void randomPassword() throws NoSuchAlgorithmException {
        inputLine = "user create -u username1 -p random -n nickname -email saeed@gmail.com";
        matcher = RegisterMenuCommands.getMatcher(inputLine, RegisterMenuCommands.REGISTER_REGEX);
        if (matcher.find()) output = registerMenu.register(matcher, inputLine);
        Assertions.assertEquals(Outputs.RANDOM_PASSWORD_CONFIRMATION, output);
    }

    @org.junit.Test
    public void invalidEmail() throws NoSuchAlgorithmException {
        inputLine = "user create -u username1 -p @Ramz1 @Ramz1 -n nickname -email saeedgmail.com";
        matcher = RegisterMenuCommands.getMatcher(inputLine, RegisterMenuCommands.REGISTER_REGEX);
        if (matcher.find()) output = registerMenu.register(matcher, inputLine);
        Assertions.assertEquals(Outputs.INVALID_EMAIL, output);
    }

    @org.junit.Test
    public void wrongPasswordConfirm() throws NoSuchAlgorithmException {
        inputLine = "user create -u username1 -p @Ramz1 @Ramz2 -n nickname -email saeed@gmail.com";
        matcher = RegisterMenuCommands.getMatcher(inputLine, RegisterMenuCommands.REGISTER_REGEX);
        if (matcher.find()) output = registerMenu.register(matcher, inputLine);
        Assertions.assertEquals(Outputs.WRONG_PASSWORD_CONFIRM, output);
    }

    @org.junit.Test
    public void invalidSecurityQuestionNumber() {
        output = registerMenuController.securityQuestion(5, "ans", "ans");
        Assertions.assertEquals(Outputs.INVALID_QUESTION_NUMBER, output);
    }

    @org.junit.Test
    public void wrongAnswerConfirm() {
        output = registerMenuController.securityQuestion(3, "ans", "ans2");
        Assertions.assertEquals(Outputs.WRONG_ANSWER_CONFIRM, output);
    }

    @org.junit.Test
    public void successfullSecurityQuestionPick() {
        RegisterMenuController.temporaryCreatedUser = new User(null, null, null, null, null, null, null, null);
        output = registerMenuController.securityQuestion(3, "ans", "ans");
        Assertions.assertEquals(Outputs.SUCCESS, output);
    }

    @org.junit.Test
    public void wrongRandomPasswordConfirm() {
        String password = registerMenuController.createRandomPassword();
        output = registerMenuController.randomPasswordConfirmation("wrong");
        Assertions.assertEquals(Outputs.WRONG_PASSWORD_CONFIRM, output);
    }

    @org.junit.Test
    public void randomPasswordConfirm() {
        String password = registerMenuController.createRandomPassword();
        output = registerMenuController.randomPasswordConfirmation(password);
        assertNull(output);
    }

    @org.junit.Test
    public void isRandomPasswordSecure() {
        String randomPassword = registerMenuController.createRandomPassword();
        output = RegisterMenuController.checkPasswordIsSecure(randomPassword);
        Assertions.assertEquals(Outputs.SECURE_PASSWORD, output);
    }

    @org.junit.Test
    public void wrongCaptchaAnswer() throws Exception {
        Scanner scanner = new Scanner("12345");
        registerMenu.captchaRun(scanner);
        Assertions.assertEquals("WrongCaptcha", RegisterMenu.unitTestTempOutput);
    }

    @org.junit.Test
    public void nonNumberCaptchaAnswer() throws Exception {
        Scanner scanner = new Scanner("12sss3");
        registerMenu.captchaRun(scanner);
        Assertions.assertEquals("WrongNumber", RegisterMenu.unitTestTempOutput);
    }

    @org.junit.Test
    public void regenerateCaptcha() throws Exception {
        Scanner scanner = new Scanner("0\n1232");
        registerMenu.captchaRun(scanner);
        Assertions.assertEquals("WrongCaptcha", RegisterMenu.unitTestTempOutput);
    }

    @org.junit.Test
    public void securityQuestionRun() {
        Scanner scanner = new Scanner("question pick -q 1 -a dad -c dad");
        registerMenu.securityQuestionRun(scanner);
        Assertions.assertEquals("Success", RegisterMenu.unitTestTempOutput);
    }

    @org.junit.Test
    public void wrongSecurityQuestionRun() {
        Scanner scanner = new Scanner("question pick -q 1 -a dad");
        registerMenu.securityQuestionRun(scanner);
        Assertions.assertEquals("Success", RegisterMenu.unitTestTempOutput);
    }

    @org.junit.Test
    public void randomSlogan() {
        registerMenu.randomSlogan();
        Assertions.assertEquals("Success", RegisterMenu.unitTestTempOutput);
    }

    @org.junit.Test
    public void invalidRegisterMenuCommand() throws NoSuchAlgorithmException, IOException {
        Scanner scanner = new Scanner("chertopert");
        registerMenu.run(scanner);
        Assertions.assertEquals("Success", RegisterMenu.unitTestTempOutput);
    }

    @org.junit.Test
    public void wrongRandomPasswordConfirmRun() throws NoSuchAlgorithmException, IOException {
        Scanner scanner = new Scanner("chertopert");
        registerMenu.randomPassConfirmRun(scanner);
        Assertions.assertEquals("Success", RegisterMenu.unitTestTempOutput);
    }

    @org.junit.Test
    public void randomPasswordConfirmRun() throws NoSuchAlgorithmException, IOException {
        RegisterMenu.randomPass = "Temp";
        Scanner scanner = new Scanner(RegisterMenu.randomPass);
        registerMenu.randomPassConfirmRun(scanner);
        Assertions.assertEquals("Success", RegisterMenu.unitTestTempOutput);
    }

    @org.junit.Test
    public void registerRun() throws NoSuchAlgorithmException, IOException {
        Scanner scanner = new Scanner("user create -u username1 -p @Ramz1 @Ramz1 -n nick -email mail@a.com -s random");
        registerMenu.run(scanner);
        Assertions.assertEquals("Success", RegisterMenu.unitTestTempOutput);
    }

    @org.junit.Test
    public void registerRandomPasswordRun() throws NoSuchAlgorithmException, IOException {
        Scanner scanner = new Scanner("user create -u username1 -p random -n nick -email mail@a.com -s slog");
        registerMenu.run(scanner);
        Assertions.assertEquals("Success", RegisterMenu.unitTestTempOutput);
    }

    @org.junit.Test
    public void nullRegisterInput() throws NoSuchAlgorithmException, IOException {
        output = registerMenuController.registerValidationCheck(null, null, null, null, null, null, null);
        Assertions.assertEquals(Outputs.NOT_ENOUGH_DATA, output);
    }

    @org.junit.Test
    public void invalidUsername() throws NoSuchAlgorithmException, IOException {
        output = registerMenuController.registerValidationCheck("@", "a", "a", "a", "a", "a", "a");
        Assertions.assertEquals(Outputs.INVALID_USERNAME, output);
    }

    @org.junit.Test
    public void duplicateUser() throws NoSuchAlgorithmException, IOException {
        Data.addUser(new User("username11", "a", "a", "a", "a", "a", "a", null));
        output = registerMenuController.registerValidationCheck("username11", "@Ramz1", "ab@f.c", "a", "@Ramz1", "a", "a");
        Assertions.assertEquals(Outputs.USER_EXISTS, output);
    }

    @org.junit.Test
    public void duplicateEmail() throws NoSuchAlgorithmException, IOException {
        Data.addUser(new User("username11", "a", "a", "ab@f.c", "a", "a", "a", null));
        output = registerMenuController.registerValidationCheck("username1", "@Ramz1", "ab@f.c", "a", "@Ramz1", "a", "a");
        Assertions.assertEquals(Outputs.EMAIL_EXISTS, output);
    }

    @org.junit.Test
    public void nullSloganWithSwitch() throws NoSuchAlgorithmException, IOException {
        output = registerMenuController.registerValidationCheck("username1", "@Ramz1", "ab@dddf.c", "a", "@Ramz1", null, "a");
        Assertions.assertEquals(Outputs.NOT_ENOUGH_DATA, output);
    }

    @org.junit.Test
    public void withoutErrorRegisteration() throws NoSuchAlgorithmException, IOException {
        output = registerMenuController.registerUser("username1", "@Ramz1", "ab@dddf.c", "a", "@Ramz1", "slog", "a");
        Assertions.assertEquals(Outputs.SUCCESS, output);
    }
}