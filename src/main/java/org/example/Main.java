package org.example;

import org.example.model.Data;
import org.example.model.Empire;
import org.example.model.User;
import org.example.model.UsersDatabaseJSON;
import org.example.model.building.Building;
import org.example.model.building.Tile;
import org.example.model.building.castleBuilding.Stairs;
import org.example.model.building.castleBuilding.Tower;
import org.example.model.building.castleBuilding.enums.TowerType;
import org.example.model.building.enums.BuildingName;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;
import org.example.view.RegisterMenu;
import org.example.view.enums.BackgroundColor;
import org.example.view.mainMenu.MainMenu;
import org.example.view.mainMenu.gameMenu.GameMenu;
import org.example.view.mainMenu.gameMenu.MilitaryMenu;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {

//        Scanner scanner = new Scanner(System.in);
//
//        Tile[][] map = new Tile[200][200];
//        for (int i = 0; i < 200; i++) {
//            map[i] = new Tile[200];
//            for (int j = 0; j < 200; j++) {
//                map[i][j] = new Tile();
//            }
//        }
//
//        User user = new User(null, null,null, null,null, null,null, null);
//        Empire empire = new Empire(null, user);
//        map[10][10].setBuilding(new Tower(null, 10, 10, BuildingName.ROUND_TOWER, TowerType.ROUND_TOWER));
//        Stairs stairs = new Stairs(empire, 10, 9, BuildingName.WALL);
//        map[10][9].setBuilding(stairs);
//        MilitaryUnit militaryUnit = new MilitaryUnit(map[10][10], null, MilitaryUnitName.ASSASSINS, 10, 10);
////        militaryUnit.getMilitaryUnitName().getVoice().playVoice(Voice.ASSASSINS);
//
//        BestPath bestPath = new BestPath(null);
//        LinkedList<Integer> path = bestPath.input(map,10,10, 20, 20, true);
//        LinkedList<Integer> path2 = bestPath.input(map,10, 10, 20, 20, false);
//
//
//        for (int keke : path) {
//            System.out.println( "x : " + keke/200 + " | y : " + keke % 200);
//            System.out.println();
//        }
//
//        for (int goh : path2) {
//            System.out.println("x : " + goh / 200);
//            System.out.println("y : " + goh % 200);
//        }
//
//
//        MilitaryMenu militaryMenu = new MilitaryMenu(null, new GameMenu(user));
//        militaryMenu.run();
//
//
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