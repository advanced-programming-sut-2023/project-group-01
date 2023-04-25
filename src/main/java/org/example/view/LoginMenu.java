package org.example.view;

import org.example.controller.LoginMenuController;
import org.example.controller.RegisterMenuController;
import org.example.model.Data;
import org.example.view.enums.CaptchaAsciiArt;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.LoginMenuCommands;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.concurrent.TimeUnit;

public class LoginMenu {

    LoginMenuController loginMenuController = new LoginMenuController();
    LoginMenu loginMenu = new LoginMenu();
    int forbiddenTime;

    public void run(Scanner scanner) {
        String inputLine;
        Matcher matcher;
        Outputs output;

        while (true) {
            inputLine = scanner.nextLine();
            forbiddenTime=0;

            if ((matcher=LoginMenuCommands.getMatcher(inputLine,LoginMenuCommands.LOGIN_REGEX)).find()){
                if ((output=login(matcher,inputLine)).equals(Outputs.INVALID_LOGIN_INPUT)){
                    System.out.println(Outputs.INVALID_LOGIN_INPUT.toString());
                    break;
                }
                else if (!output.equals(Outputs.SUCCESS)){
                    System.out.println(output);
                    if (output.equals(Outputs.WRONG_PASSWORD)){
                        forbiddenTime+=5;
                        System.out.println("You can't login for "+forbiddenTime+" seconds.");
                        try {
                            TimeUnit.SECONDS.sleep(forbiddenTime);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                }
                else {
                    System.out.println("login successful !");
                }

            }
        }
    }

    public Outputs login(Matcher matcher,String line) {
        String username = matcher.group("username");
        String password = matcher.group("password");

        if (checkNoAdditionalInLogin(username,password,line).equals(Outputs.INVALID_LOGIN_INPUT))
            return Outputs.INVALID_LOGIN_INPUT;
        return loginMenuController.login(username,password);
    }

    private Outputs checkNoAdditionalInLogin(String username, String password, String line) {
        line = line.replaceAll("-u " + username, "");
        line = line.replaceAll("-p " + password, "");
        if (line.equals(""))
            return Outputs.VALID_LOGIN_INPUT;
        return Outputs.INVALID_LOGIN_INPUT;
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
                    System.out.println("login successful !");
                    //TODO:function after login
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

    public Outputs forgetPassword(Matcher matcher) {
        return null;
    }
}
