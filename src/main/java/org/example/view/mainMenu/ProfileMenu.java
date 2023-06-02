package org.example.view.mainMenu;

import org.example.controller.mainMenuController.ProfileMenuController;
import org.example.model.Data;
import org.example.model.User;
import org.example.view.enums.CaptchaAsciiArt;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.ProfileMenuCommands;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

public class ProfileMenu {

    private final User currentUser;

    public ProfileMenu(User currentUser) {
        this.currentUser = currentUser;
    }

    public void run(Scanner scanner) throws IOException, NoSuchAlgorithmException {
        System.out.println("Now you are in Profile Menu!");
        String inputLine;
        Matcher matcher;
        setRanks();

        while (true) {
            inputLine = scanner.nextLine();

            if (ProfileMenuCommands.getMatcher(inputLine, ProfileMenuCommands.FULL_DISPLAY).find())
                System.out.println(fullDisplay());
            else if (ProfileMenuCommands.getMatcher(inputLine, ProfileMenuCommands.DISPLAY_SLOGAN).find())
                System.out.println(showSlogan());
            else if (ProfileMenuCommands.getMatcher(inputLine, ProfileMenuCommands.DISPLAY_HIGHSCORE).find())
                System.out.println(showHighscore());
            else if (ProfileMenuCommands.getMatcher(inputLine, ProfileMenuCommands.DISPLAY_RANK).find())
                System.out.println(showRank());
            else if (ProfileMenuCommands.getMatcher(inputLine, ProfileMenuCommands.REMOVE_SLOGAN).find())
                System.out.println(removeSlogan());
            else if ((matcher = ProfileMenuCommands.getMatcher(inputLine, ProfileMenuCommands.CHANGE_USERNAME)).find())
                System.out.println(changeUsername(matcher).toString());
            else if ((matcher = ProfileMenuCommands.getMatcher(inputLine, ProfileMenuCommands.CHANGE_NICKNAME)).find())
                System.out.println(changeNickname(matcher).toString());
            else if ((matcher = ProfileMenuCommands.getMatcher(inputLine, ProfileMenuCommands.CHANGE_SLOGAN)).find())
                System.out.println(changeSlogan(matcher).toString());
            else if ((matcher = ProfileMenuCommands.getMatcher(inputLine, ProfileMenuCommands.CHANGE_EMAIL)).find())
                System.out.println(changeEmail(matcher).toString());
            else if ((matcher = ProfileMenuCommands.getMatcher(inputLine, ProfileMenuCommands.CHANGE_PASSWORD)).find())
                System.out.println(changePassword(matcher, scanner).toString());
            else if (ProfileMenuCommands.getMatcher(inputLine, ProfileMenuCommands.BACK_TO_MAIN_MENU).find()) {
                MainMenu mainMenu = new MainMenu(currentUser);
                mainMenu.run(scanner);
                break;
            } else System.out.println("Invalid command in Profile Menu !");

        }
    }

    private String fullDisplay() {
        String output = "";
        output += "Your info :\n";
        output += "username : " + currentUser.getUsername() + "\n";
        output += "nickname : " + currentUser.getNickname() + "\n";
        output += "email : " + currentUser.getEmail() + "\n";
        output += "highscore : " + currentUser.getHighScore() + "\n";
        output += "rank : " + currentUser.getRank() + "\n";
        output += "security question : " + currentUser.getSecurityQuestion() + "\n";
        output += "security question's answer : " + currentUser.getSecurityAnswer() + "\n";
        output += "slogan : ";
        if (currentUser.getSlogan() == null) output += "Slogan is empty !";
        else output += currentUser.getSlogan();
        return output;

    }

    private String showSlogan() {
        if (currentUser.getSlogan() == null) return "Slogan is empty !";
        else return currentUser.getSlogan();
    }

    private String showHighscore() {
        return String.valueOf(currentUser.getHighScore());
    }

    private String showRank() {
        return String.valueOf(currentUser.getRank());
    }

    private String removeSlogan() {
        currentUser.setSlogan(null);
        return "Your slogan removed .";
    }

    private Outputs changeUsername(Matcher matcher) {
        String newUsername = matcher.group("username");
        //ProfileMenuController profileMenuController = new ProfileMenuController(currentUser);
        //return profileMenuController.changeUsername(newUsername);
        return null;
    }

    private Outputs changeNickname(Matcher matcher) {
        String newNickname = matcher.group("nickname");
        currentUser.setNickname(newNickname);
        return Outputs.NICKNAME_CHANGE_SUCCESSFUL;
    }

    private Outputs changeSlogan(Matcher matcher) {
        String newSlogan = matcher.group("slogan");
        currentUser.setSlogan(newSlogan);
        return Outputs.SLOGAN_CHANGE_SUCCESSFUL;
    }

    private Outputs changeEmail(Matcher matcher) {
        String newEmail = matcher.group("email");
        ProfileMenuController profileMenuController = null;
        return profileMenuController.changeEmail(newEmail);
    }

    private Outputs changePassword(Matcher matcher, Scanner scanner) {
        String oldPassword = matcher.group("oldPassword");
        String newPassword = matcher.group("newPassword");
        ProfileMenuController profileMenuController = null;
        Outputs output = profileMenuController.changePassword(oldPassword, newPassword);
        if (!output.equals(Outputs.PASSWORD_CHANGE_SUCCESSFUL)) return output;
        try {
            return captchaRun(scanner);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public Outputs captchaRun(Scanner scanner) throws IOException, NoSuchAlgorithmException {
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
                if (number != CaptchaAsciiArt.captchaValue) System.out.println("Your didn't enter captcha correctly.");
                else {
                    return Outputs.PASSWORD_CHANGE_SUCCESSFUL;
                }
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                continue;
            }
        }
    }

    public static void setRanks() {
        Data.getUsers().sort(new User.Sort());
        for (int i = 0; i < Data.getUsers().size(); i++) {
            Data.getUsers().get(i).setRank(i + 1);
        }
    }
}
