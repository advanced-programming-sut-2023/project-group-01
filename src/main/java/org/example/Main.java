package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.controller.SignUpAndSignInMenu;
import org.example.model.Data;
import org.example.model.InitializeMaterial;
import org.example.model.Map;
import org.example.model.UsersDatabaseJSON;
import org.example.view.graphicView.GameMenuApp;
import org.example.view.graphicView.GameSettingMenu;
import org.example.view.graphicView.TradeMenuApp;
import org.example.view.mainMenu.gameMenu.CreateMapMenu;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {
    public static Stage stage;
    public static int user = 1;
    public static String[] commands = {"C:\\Users\\ASUS\\PycharmProjects\\pythonProject\\venv\\Scripts\\python.exe" +
            " C:\\Users\\ASUS\\IdeaProjects\\AP\\project-group-01-pull\\src\\main\\resources\\CaptchaPictures\\main.py",

            "C:\\Users\\torab\\PycharmProjects\\pythonProject\\venv\\Scripts\\python.exe" +
                    " C:\\Users\\torab\\OneDrive\\Documents\\codes\\ap\\proje\\project-group-01\\src\\main\\resources\\CaptchaPictures\\main.py"};

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {


        UsersDatabaseJSON.initializeUsers();
        UsersDatabaseJSON.loadStayedLoggedInUser();
        Data.setDefaultMap(new String(Files.readAllBytes(Paths.get("my map.txt"))));


        Scanner scanner = new Scanner(System.in);

//        if (Data.getStayedLoggedIn() == null) {
//            RegisterMenu registerMenu = new RegisterMenu();
//            registerMenu.run(scanner);
//        } else {
//            MainMenu mainMenu = new MainMenu(Data.getStayedLoggedIn());
//            mainMenu.run(scanner);
//        }
        //be koda sag to git. lanat be git va sazandash boro bemir
        //goh to git va sazande va estefade konnande va yak yakishon.

        launch(args);

        UsersDatabaseJSON.saveUsersInJSON();
        UsersDatabaseJSON.saveStayedLoggedInUser();
        Process process = Runtime.getRuntime().exec("move.bat");
    }

    public static Scanner getScanner() {
        return new Scanner(System.in);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/Images/logo.png")));
        Data.setStayedLoggedIn(Data.findUserWithUsername("ali"));
        //new SignUpAndSignInMenu().start(stage);
       // new GameMenuApp(new CreateMapMenu(null).runDefaultMap(new Scanner(Data.getDefaultMap())), new ArrayList<>(), InitializeMaterial.HIGH_SOURCE).start(Main.stage);
//        FileWriter fileWriter = new FileWriter("default map 400.json");
//        fileWriter.write(new Gson().toJson( new CreateMapMenu(null).runDefaultMap(new Scanner(Data.getDefaultMap()))));
//        fileWriter.close();
//        System.exit(1);
        new GameSettingMenu().start(Main.stage);
        //new ScoreBoard().start(Main.stage);
    }

}
