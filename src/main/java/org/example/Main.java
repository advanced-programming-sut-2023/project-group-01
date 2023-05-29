package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.controller.CaptchaGenerator;
import org.example.controller.SignUpAndSignInMenu;
import org.example.model.Data;
import org.example.model.UsersDatabaseJSON;
import org.example.view.LoginMenuApp;
import org.example.view.RegisterMenu;
import org.example.view.RegisterMenuApp;
import org.example.view.mainMenu.MainMenu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main extends Application {
    public static Stage stage;
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {


        UsersDatabaseJSON.initializeUsers();
        UsersDatabaseJSON.loadStayedLoggedInUser();
        Data.setDefaultMap(new String(Files.readAllBytes(Paths.get("DefaultMap.txt"))));


        Scanner scanner = new Scanner(System.in);

//        if (Data.getStayedLoggedIn() == null) {
//            RegisterMenu registerMenu = new RegisterMenu();
//            registerMenu.run(scanner);
//        } else {
//            MainMenu mainMenu = new MainMenu(Data.getStayedLoggedIn());
//            mainMenu.run(scanner);
//        }

        launch(args);

        UsersDatabaseJSON.saveUsersInJSON();
        UsersDatabaseJSON.saveStayedLoggedInUser();

    }

    public static Scanner getScanner() {
        return new Scanner(System.in);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        new SignUpAndSignInMenu().start(stage);
    }


}