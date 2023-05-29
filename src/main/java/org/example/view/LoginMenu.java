package org.example.view;

import org.example.controller.LoginMenuController;
//import org.example.controller.RegisterMenuController;
import org.example.model.Data;
import org.example.model.User;
import org.example.view.enums.BackgroundColor;
import org.example.view.enums.CaptchaAsciiArt;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.LoginMenuCommands;
import org.example.view.enums.commands.RegisterMenuCommands;
import org.example.view.mainMenu.MainMenu;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

public class LoginMenu {

    private final LoginMenuController loginMenuController = new LoginMenuController();
    private final RegisterMenu registerMenu = new RegisterMenu();

    public void run(Scanner scanner) throws NoSuchAlgorithmException, IOException {
        System.out.println("Now you are in Login Menu!");
        String inputLine;
        Matcher matcher;
        Outputs output;
        int forbiddenTime = 0;

        while (true) {
            inputLine = scanner.nextLine();

            if ((matcher = LoginMenuCommands.getMatcher(inputLine, LoginMenuCommands.LOGIN_REGEX)).find()) {
                if ((output = login(matcher, inputLine)).equals(Outputs.INVALID_LOGIN_INPUT)) {
                    System.out.println(Outputs.INVALID_LOGIN_INPUT.toString());
                } else if (!output.equals(Outputs.SUCCESS)) {
                    errorColorChange();
                    System.out.println(output);
                    if (output.equals(Outputs.WRONG_PASSWORD)) {
                        forbiddenTime += 5;
                        System.out.println("You can't login for " + forbiddenTime + " seconds.");
                        try {
                            TimeUnit.SECONDS.sleep(forbiddenTime);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        resetBackgroundColor();
                        System.out.println("Now you can login again.");
                    }
                    resetBackgroundColor();
                } else {
                    captchaRun(scanner, matcher.group("username"));
                }

            } else if ((matcher = LoginMenuCommands.getMatcher(inputLine, LoginMenuCommands.FORGET_PASSWORD)).find()) {
                System.out.println(forgetPasswordRun(matcher, scanner).toString());
            } else if (LoginMenuCommands.getMatcher(inputLine, LoginMenuCommands.ENTER_REGISTER_MENU).find()) {
//                registerMenu.run(scanner);
                break;
            } else if (RegisterMenuCommands.getMatcher(inputLine, RegisterMenuCommands.EXIT).find()) break;
            else {
                errorColorChange();
                System.out.println("Invalid command in login menu !");
                resetBackgroundColor();
            }
        }
    }

    private static void resetBackgroundColor() {
        BackgroundColor.changeColor(BackgroundColor.ANSI_RESET);
    }

    private static void errorColorChange() {
        BackgroundColor.changeColor(BackgroundColor.ANSI_RED_COLOR);
        BackgroundColor.changeColor(BackgroundColor.ANSI_BLACK_TEXT);
    }

    public Outputs login(Matcher matcher, String line) {
        String username = matcher.group("username");
        String password = matcher.group("password");
        String stayLoggedIn = matcher.group("stayLoggedIn");
        if (!line.equals(line.replaceAll("--stay-logged-in$", ""))) stayLoggedIn = "yes";
        if (checkNoAdditionalInLogin(username, password, line).equals(Outputs.INVALID_LOGIN_INPUT))
            return Outputs.INVALID_LOGIN_INPUT;
        return loginMenuController.login(username, password, stayLoggedIn);
    }

    private Outputs checkNoAdditionalInLogin(String username, String password, String line) {
        line = line.replace("user login", "");
        line = line.replaceAll("-u " + username, "");
        line = line.replaceAll("-p " + password, "");
        line = line.replaceAll("--stay-logged-in", "");
        line = line.replaceAll("\\s", "");
        if (line.equals("")) return Outputs.VALID_LOGIN_INPUT;
        return Outputs.INVALID_LOGIN_INPUT;
    }

    public void captchaRun(Scanner scanner, String username) throws IOException, NoSuchAlgorithmException {
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
                if (number == 0) continue;
//                if (number != CaptchaAsciiArt.captchaValue)
//                    RegisterMenu.printError("Your didn't enter captcha correctly.");
//                else {
//                    RegisterMenu.printSuccess("login successful !");
//                    MainMenu mainMenu = new MainMenu(Data.findUserWithUsername(username));
//                    mainMenu.run(scanner);
//                    break;
//                }
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
//                continue;
            }
        }
    }

    public Outputs forgetPasswordRun(Matcher matcher, Scanner scanner) {
        String username = matcher.group("username");
        if (username == null) return Outputs.INVALID_FORGET_PASSWORD_INPUT;

        User user = Data.findUserWithUsername(username);
        if (user == null) return Outputs.NOT_EXISTING_USERNAME;

        System.out.println("Answer your security question below :");
        System.out.println(user.getSecurityQuestion());
        String inputLine;
        while (true) {
            inputLine = scanner.nextLine();
            if (loginMenuController.forgetPassword(inputLine, username).equals(Outputs.SUCCESS))
                return newPasswordRun(scanner, username);
            else System.out.println(loginMenuController.forgetPassword(inputLine, username).toString());
        }
    }

    public Outputs newPasswordRun(Scanner scanner, String username) {
        String inputLine;

        while (true) {
            System.out.println("Please enter your new password :");
            inputLine = scanner.nextLine();
//            if (RegisterMenuController.checkPasswordIsSecure(inputLine).equals(Outputs.SECURE_PASSWORD)) {
//                loginMenuController.setNewPassword(inputLine, username);
//                return Outputs.PASSWORD_CHANGE_SUCCESSFUL;
//            } else System.out.println(RegisterMenuController.checkPasswordIsSecure(inputLine).toString());
        }
    }
}
