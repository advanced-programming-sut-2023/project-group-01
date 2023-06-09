package org.example.view.mainMenu;

import org.example.model.Data;
import org.example.model.User;
import org.example.model.UsersDatabaseJSON;
//import org.example.view.RegisterMenu;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.MainMenuCommands;
import org.example.view.mainMenu.gameMenu.GameMenu;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu {

    private final User currentUser;

    public MainMenu(User currentUser) {
        this.currentUser = currentUser;
    }

    public void run(Scanner scanner) throws IOException, NoSuchAlgorithmException {
        UsersDatabaseJSON.saveStayedLoggedInUser();
        System.out.println("Now you are in Main Menu!");
        String inputLine;
        Matcher matcher;
        Outputs output;

        while (true) {
            inputLine = scanner.nextLine();

            if (MainMenuCommands.getMatcher(inputLine, MainMenuCommands.USER_LOGOUT).find()) {
                Data.setStayedLoggedIn(null);
                UsersDatabaseJSON.saveStayedLoggedInUser();
//                RegisterMenu registerMenu = new RegisterMenu();
//                registerMenu.run(scanner);
                break;
            } else if (MainMenuCommands.getMatcher(inputLine, MainMenuCommands.ENTER_PROFILE_MENU).find()) {
                ProfileMenu profileMenu = new ProfileMenu(currentUser);
                profileMenu.run(scanner);
                break;
            } else if (inputLine.equals("start game")) {
                GameMenu gameMenu = new GameMenu(currentUser);
                gameMenu.run(scanner);
                break;
            } else if (inputLine.equals("exit project")) break;
            else System.out.println("Invalid command in Main Menu!");
        }
    }

}
