package org.example;

import org.example.model.Data;
import org.example.model.UsersDatabaseJSON;
import org.example.view.RegisterMenu;
import org.example.view.mainMenu.MainMenu;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {

        UsersDatabaseJSON.initializeUsers();
        UsersDatabaseJSON.loadStayedLoggedInUser();
        Scanner scanner = new Scanner(System.in);

        if (Data.getStayedLoggedIn() == null) {
            RegisterMenu registerMenu = new RegisterMenu();
            registerMenu.run(scanner);
        } else {
            MainMenu mainMenu = new MainMenu(Data.getStayedLoggedIn());
            mainMenu.run(scanner);
        }

        UsersDatabaseJSON.saveUsersInJSON();
        UsersDatabaseJSON.saveStayedLoggedInUser();
    }

    public static Scanner getScanner() {
        return new Scanner(System.in);
    }
}