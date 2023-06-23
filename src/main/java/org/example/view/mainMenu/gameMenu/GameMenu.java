package org.example.view.mainMenu.gameMenu;

import org.example.controller.NextTurn;
import org.example.controller.mainMenuController.gameMenuController.BuildingMenuController;
import org.example.controller.mainMenuController.gameMenuController.GameMenuController;
import org.example.model.*;
import org.example.model.building.Building;
import org.example.model.building.castleBuilding.enums.EmpireBuilding;
import org.example.model.building.enums.BuildingName;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.GameMenuCommands.GameMenuCommands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

import static org.example.model.Data.findUserWithUsername;

public class GameMenu {
    private static ArrayList<Empire> empires = new ArrayList<Empire>();

    private NextTurn nextTurn;
    private final User player;

    private static Map map;

    private static Empire thisEmpire;

    private int turnNumber;

    public GameMenu(User player) {
        this.player = player;
    }

    public static void setEmpires(ArrayList<Empire> empires) {
        GameMenu.empires = empires;
    }

    public void setThisUser(Empire thisEmpire) {
        this.thisEmpire = thisEmpire;
    }

    public void setGameMap(Map map) {
        this.map = map;
    }

    public boolean setEmpires(int numberOfEmpires, Scanner scanner, InitializeMaterial initializeMaterial) {
        try {
            UsersDatabaseJSON.saveUsersInJSON();
            UsersDatabaseJSON.saveStayedLoggedInUser();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        player.setInGame(true);
        Empire empire = new Empire(EmpireBuilding.valueOf("EMPIRE_" + 1), player);
        GameMenu.getEmpires().add(empire);
        GameMenu.setThisEmpire(empire);
        for (int i = 2; i <= numberOfEmpires; i++)
            while (true) {
                System.out.println("Please enter username of player\n +" + "for cancel game enter \"exit game\"");
                String username = scanner.nextLine();
                User user = findUserWithUsername(username);
                if (user != null) {
                    if (!user.getInGame()) {
                        user.setInGame(true);
                        empires.add(new Empire(EmpireBuilding.valueOf("EMPIRE_" + i), user));
                        System.out.println("User added successfully!");
                        break;
                    } else System.out.println("You have already added this player to the game.");
                } else if (username.equals("exit game")) return false;
                else System.out.println("User not found");
            }
        System.out.println("All of the players added to the game.");
        nextTurn = new NextTurn(this);
        turnNumber = empires.size();
        addEmpireBuildingsToMap();
        setMaterialForEmpires(initializeMaterial);
        return true;
    }

    private void setMaterialForEmpires(InitializeMaterial initializeMaterial) {
        for (int i = 0; i < empires.size(); i++)
            InitializeMaterial.setSources(empires.get(i), initializeMaterial);
    }

    public static void setThisEmpire(Empire thisEmpire) {
        GameMenu.thisEmpire = thisEmpire;
    }

    public static Empire getThisEmpire() {
        return thisEmpire;
    }

    public int getTurnNumber() {
        return turnNumber / empires.size();
    }

    public static Map getMap() {
        if (map != null) return map;
        else return CreateMapMenu.gameMap;
    }

    public void run(Scanner scanner) {
        setGameMapSize(scanner);
        if (setEmpires(setNumberOfEmpires(scanner), scanner, setLevelOfGame(scanner))) {
            map = new CreateMapMenu(player).run(scanner);
            while (true) {
                String command = scanner.nextLine();
                Matcher matcher;
                if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_MAP)) != null)
                    showMapChecker(matcher, scanner);
                else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.GO_TO_EMPIRE_MENU)) != null)
                    new EmpireMenu(thisEmpire).run(scanner);
                else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.GET_TURN_NUMBER)) != null)
                    System.out.println("The turn number is " + getTurnNumber());
                else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.GET_PLAYER)) != null)
                    System.out.println("The player is " + thisEmpire.getPlayer().getUsername());
                else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.NEXT_TURN)) != null) {
                    turnNumber++;
                  //  nextTurn.nextTurn();
                } else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.MILITARY_MENU)) != null) {
                    MilitaryMenu militaryMenu = new MilitaryMenu(thisEmpire, this);
                    militaryMenu.run(scanner);
                } else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.BUILDING_MENU)) != null) {
                    BuildingMenu buildingMenu = new BuildingMenu(thisEmpire);
                    buildingMenu.run(scanner);
                } else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SHOP_MENU)) != null) {
                    ShopMenu shopMenu = new ShopMenu();
                    shopMenu.run(scanner);
                } else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.TRADE_MENU)) != null) {
                    TradeMenu tradeMenu = new TradeMenu();
                    tradeMenu.run(scanner);
                } else System.out.println("Invalid command in Game Menu !");
            }
        } else {
            setUsersGameFinished();
            System.out.println("Game canceled");
        }
    }

    public static void setMap(Map map) {
        GameMenu.map = map;
    }

    private InitializeMaterial setLevelOfGame(Scanner scanner) {
        InitializeMaterial initializeMaterial;
        outer:
        while (true) {
            System.out.println("please enter which level of source you want?");
            System.out.println("1. low source");
            System.out.println("2. middle source");
            System.out.println("3. high source");
            String level = scanner.nextLine();
            switch (level) {
                case "1":
                    initializeMaterial = InitializeMaterial.LOW_SOURCE;
                    break outer;
                case "2":
                    initializeMaterial = InitializeMaterial.MIDDLE_SOURCE;
                    break outer;
                case "3":
                    initializeMaterial = InitializeMaterial.HIGH_SOURCE;
                    break outer;
                default:
                    System.out.println("Invalid level please try again");
                    break;
            }
        }
        return initializeMaterial;
    }

    public void setGameMapSize(Scanner scanner) {
        while (true) {
            System.out.println("please enter size for map\n" + "1. 200 * 200\n" + "2. 400 * 400");
            switch (scanner.nextLine()) {
                case "1":
                    CreateMapMenu.gameMap = new Map(200);
                    System.out.println("Size of map is 200 * 200");
                    return;
                case "2":
                    CreateMapMenu.gameMap = new Map(400);
                    System.out.println("Size of map is 400 * 400");
                    return;
                default:
                    System.out.println("Invalid size");
            }
        }
    }


    public static ArrayList<Empire> getEmpires() {
        return empires;
    }

    public void showMapChecker(Matcher matcher, Scanner scanner) {
        matcher.find();
        int xOfMap = Integer.parseInt(matcher.group("xOfMap"));
        int yOfMap = Integer.parseInt(matcher.group("yOfMap"));
        Outputs outputs = new GameMenuController().showMap(map.getTileWhitXAndY(xOfMap, yOfMap));
        System.out.println(outputs.toString());
        if (outputs.equals(Outputs.SUCCESS)) new MapMenu(map, xOfMap, yOfMap).run(scanner);
    }

    private void addEmpireBuildingsToMap() {
        for (int i = 1; i <= empires.size(); i++) {
            Building building = new Building(empires.get(i - 1), EmpireBuilding.valueOf("EMPIRE_" + i).getX(), EmpireBuilding.valueOf("EMPIRE_" + i).getY(), BuildingName.EMPIRE_CASTLE);
            getMap().getTile(EmpireBuilding.valueOf("EMPIRE_" + i).getX(), EmpireBuilding.valueOf("EMPIRE_" + i).getY()).setBuilding(building);
            empires.get(i - 1).addToBuildings(building);
            BuildingMenuController.putBuilding(BuildingName.STOCKPILE, EmpireBuilding.valueOf("EMPIRE_" + i).getX() + 1, EmpireBuilding.valueOf("EMPIRE_" + i).getY(), empires.get(i - 1));
        }
    }

    public int setNumberOfEmpires(Scanner scanner) {
        System.out.println("Please enter the number of players between 2,8 :");
        while (true) try {
            int numberOfEmpire = Integer.parseInt(scanner.nextLine());
            if (numberOfEmpire >= 2 && numberOfEmpire <= 8) return numberOfEmpire;
            else System.out.println("Please enter a number between 2 and 8");
        } catch (NumberFormatException e) {
            System.out.println("Your input not a number please try again");
        }
    }

    public void setUsersGameFinished() {
        for (int i = 0; i < empires.size(); i++)
            empires.get(i).getPlayer().setInGame(false);
    }

    public static Empire getEmpireWhitUsername(String username) {
        for (int i = 0; i < empires.size(); i++)
            if (empires.get(i).getPlayer().getUsername().equals(username)) return empires.get(i);
        return null;
    }
}
