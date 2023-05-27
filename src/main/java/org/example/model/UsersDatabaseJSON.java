package org.example.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.view.RegisterMenu;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class UsersDatabaseJSON {

    public static void saveUsersInJSON() throws IOException {

        FileWriter fileWriter = new FileWriter("data.json");
        fileWriter.write(new Gson().toJson(Data.getUsers()));
        fileWriter.close();
        System.out.println("Users data saved to file successfully !");
    }

    public static void initializeUsers() {
        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get("data.json")));
            ArrayList<User> users = new Gson().fromJson(json, new TypeToken<ArrayList<User>>() {
            }.getType());
            if (users != null) Data.setUsers(users);
            RegisterMenu.printSuccess("Users data initialized successfully !");
        } catch (IOException e) {
            RegisterMenu.printError("Unable to read from database");
        }

    }

    public static void saveStayedLoggedInUser() throws IOException {
        FileWriter fileWriter = new FileWriter("loggedIn.json");
        fileWriter.write(new Gson().toJson(Data.getStayedLoggedIn()));
        fileWriter.close();
    }

    public static void loadStayedLoggedInUser() {
        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get("loggedIn.json")));
            User user = new Gson().fromJson(json, new TypeToken<User>() {
            }.getType());
            if (user != null)
                Data.setStayedLoggedIn(Data.findUserWithUsername(user.getUsername()));

        } catch (IOException e) {

        }
    }
}
