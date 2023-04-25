package org.example.view;

import org.example.controller.RegisterMenuController;
import org.example.model.Data;
import org.example.view.enums.CaptchaAsciiArt;
import org.example.view.enums.Outputs;
import org.example.view.enums.RandomSlogans;
import org.example.view.enums.SecurityQuestion;
import org.example.view.enums.commands.RegisterMenuCommands;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

public class RegisterMenu {

    private static final RegisterMenuController registerMenuController = new RegisterMenuController();
    private static final LoginMenu loginmenu = new LoginMenu();
    private static final RegisterMenu registerMenu = new RegisterMenu();
    public static String randomPass;

    public void run(Scanner scanner) throws NoSuchAlgorithmException, IOException {
        System.out.println("Now you are in Register Menu!");
        String inputLine;
        Matcher matcher;
        randomPass = null;

        while (true) {
            inputLine = scanner.nextLine();

            if ((matcher = RegisterMenuCommands.getMatcher(inputLine, RegisterMenuCommands.REGISTER_REGEX)).find()) {
                Outputs output = register(matcher, inputLine);
                if (output.equals(Outputs.RANDOM_SLOGAN)) {
                    System.out.println(output.toString() + randomSlogan());
                    if (randomPass != null)
                        randomPassConfirmRun(scanner);
                    securityQuestionRun(scanner);
                    break;
                }
                if (output.equals(Outputs.RANDOM_PASSWORD_CONFIRMATION)) {
                    System.out.println(output.toString() + randomPass + " .Please re enter random password to confirm");
                    randomPassConfirmRun(scanner);
                    break;
                }
                if (output.equals(Outputs.SUCCESS)) {
                    securityQuestionRun(scanner);
                    break;
                }
                System.out.println(output.toString());
            }
            else if ((matcher = RegisterMenuCommands.getMatcher(inputLine,RegisterMenuCommands.ENTER_LOGIN_MENU)).find()){
                loginmenu.run(scanner);
                break;
            }else if (RegisterMenuCommands.getMatcher(inputLine,RegisterMenuCommands.EXIT).find())
                break;
            else System.out.println("Invalid command in register menu!");
        }
    }

    public void randomPassConfirmRun(Scanner scanner) {
        String inputLine;
        Matcher matcher;

        while (true) {
            inputLine = scanner.nextLine();

            if ((matcher = RegisterMenuCommands.getMatcher(inputLine, RegisterMenuCommands.RANDOM_PASSWORD_CONFIRMATION_REGEX)).find()) {
                if (registerMenuController.randomPasswordConfirmation(matcher.group("passwordConfirm")) == null) {
                    System.out.println("Password successfully confirmed.");
                    securityQuestionRun(scanner);
                    break;
                } else
                    System.out.println(registerMenuController.randomPasswordConfirmation(matcher.group("passwordConfirm")).toString());
            }
        }
    }

    public void securityQuestionRun(Scanner scanner) {
        String inputLine;
        Matcher matcher;
        Outputs output;

        System.out.println("Pickup your security question and answer it.");
        System.out.println("1." + SecurityQuestion.getQuestion(0));
        System.out.println("2." + SecurityQuestion.getQuestion(1));
        System.out.println("3." + SecurityQuestion.getQuestion(2));
        while (true) {
            inputLine = scanner.nextLine();

            if ((matcher = RegisterMenuCommands.getMatcher(inputLine, RegisterMenuCommands.PICK_QUESTION_REGEX)).find()) {
                output = pickSecurityQuestion(matcher);
                if (output.equals(Outputs.SUCCESS)) {
                    captchaRun(scanner);
                    break;
                }
                System.out.println(output);
            }
        }
    }

    public void captchaRun(Scanner scanner) {
        String inputLine;
        Matcher matcher;


        while (true) {
            System.out.println(CaptchaAsciiArt.captchaGenerator());
            System.out.println("Enter captcha or enter \"0\" to generate a new captcha.");
            inputLine = scanner.nextLine();
            int number = -1;
            boolean isValid = true;

            try {
                number = Integer.parseInt(inputLine);
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Invalid input");
                isValid = false;
            }
            if (isValid) {
                if (number == 0)
                    continue;
                if (number != CaptchaAsciiArt.captchaValue)
                    System.out.println("Your didn't enter captcha correctly.");
                else {
                    Data.addUser(RegisterMenuController.temporaryCreatedUser);
                    System.out.println("Register done successfully!");
                    try {
                        registerMenu.run(scanner);
                    } catch (NoSuchAlgorithmException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                continue;
            }
        }
    }


    private Outputs pickSecurityQuestion(Matcher matcher) {
        if (matcher.group("number") == null || matcher.group("answer") == null || matcher.group("answerConfirm") == null)
            return Outputs.NOT_ENOUGH_DATA;

        int number = Integer.parseInt(matcher.group("number"));
        String answer = matcher.group("answer");
        String answerConfirm = matcher.group("answerConfirm");
        return registerMenuController.securityQuestion(number, answer, answerConfirm);
    }

    private String randomSlogan() {
        String slogan = RandomSlogans.getRandomSlogan();
        RegisterMenuController.temporaryCreatedUser.setSlogan(slogan);
        return slogan;
    }

    private Outputs register(Matcher matcher, String line) throws NoSuchAlgorithmException {

        String username = matcher.group("username");
        String password = matcher.group("password");
        String email = matcher.group("email");
        String nickname = matcher.group("nickname");
        String passwordConfirm = matcher.group("passwordConfirm");
        String slogan = matcher.group("slogan");
        String sloganSwitch = matcher.group("sloganSwitch");

        if (CheckNoAdditional(line, username, password, email, nickname, passwordConfirm, slogan).equals(Outputs.INVALID_REGISTRATION_INPUT))
            return CheckNoAdditional(line, username, password, email, nickname, passwordConfirm, slogan);
        return registerMenuController.registerUser(username, password, email, nickname, passwordConfirm, slogan, sloganSwitch);
    }

    private Outputs CheckNoAdditional(String line, String username, String password, String email, String nickname,
                                      String passwordConfirm, String slogan) {

        line = line.replaceAll("-u " + username, "");
        if (passwordConfirm != null)
            line = line.replaceAll("-p " + password + " " + passwordConfirm, "");
        else
            line = line.replaceAll("-p " + password, "");
        line = line.replaceAll("-email " + email, "");
        line = line.replaceAll("-n " + nickname, "");
        line = line.replaceAll("-s " + slogan, "");
        line = line.replaceAll("-s", "");
        line = line.replaceAll("user create", "");
        line = line.replaceAll("\\s", "");
        if (line.equals(""))
            return Outputs.VALID_REGISTRATION_INPUT;
        return Outputs.INVALID_REGISTRATION_INPUT;

    }
}
