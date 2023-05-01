package org.example.view.mainMenu.gameMenu;

import org.example.controller.mainMenuController.gameMenuController.GameMenuController;
import org.example.model.Empire;
import org.example.model.Map;
import org.example.model.User;
import org.example.model.building.castleBuilding.EmpireBuilding;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.GameMenuCommands.GameMenuCommands;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;

import static org.example.model.Data.findUserWithUsername;

public class GameMenu {
    private ArrayList<Empire> empires;

    private final User player;

    private static Map map;

    private static Empire thisEmpire;

    private int turnNumber = 1;

    public GameMenu(User player) {
        this.player = player;
    }


    public void setThisUser(Empire thisEmpire) {
        this.thisEmpire = thisEmpire;
    }

    public void setGameMap(Map map) {
        this.map = map;
    }

    public boolean setEmpires(int numberOfEmpires, Scanner scanner) {
        player.setInGame(true);
        empires.add(new Empire(EmpireBuilding.valueOf("EMPIRE_" + 1), player));
        thisEmpire = empires.get(0);
        for (int i = 2; i <= numberOfEmpires; i++)
            while (true) {
                System.out.println("Please enter username of player\n +" +
                        "for cancel game enter exit");
                String username = scanner.nextLine();
                User user = findUserWithUsername(username);
                if (user != null) {
                    if (!user.getInGame()) {
                        user.setInGame(true);
                        empires.add(new Empire(EmpireBuilding.valueOf("EMPIRE_" + i), user));
                        break;
                    } else System.out.println("User in a game now and can't play");
                } else if (username.equals("enter exit")) return false;
                else System.out.println("The user not found");
            }
        return true;
    }

    public static Empire getThisEmpire() {
        return thisEmpire;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public static Map getMap() {
        return map;
    }

    public void run(Scanner scanner) {
        map = new CreateMapMenu().run(scanner);
        if (setEmpires(setNumberOfEmpires(scanner), scanner))
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
                else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.NEXT_TURN)) != null)
                    nextTurn();
                else System.out.println("Invalid command");
            }
        else {
            setUsersGameFinished();
            System.out.println("Game canceled");
        }
    }

    public void nextTurn() {
        turnNumber++;
    }

    public void showMapChecker(Matcher matcher, Scanner scanner) {
        int xOfMap = Integer.parseInt(matcher.group("xOfMap"));
        int yOfMap = Integer.parseInt(matcher.group("yOfMap"));
        Outputs outputs = new GameMenuController().showMap(map.getTileWhitXAndY(xOfMap, yOfMap));
        System.out.println(outputs.toString());
        if (outputs.equals(Outputs.SUCCESS)) new MapMenu(map, xOfMap, yOfMap).run(scanner);
    }

    public int setNumberOfEmpires(Scanner scanner) {
        while (true)
            try {
                int numberOfEmpire = Integer.parseInt(scanner.nextLine());
                if (numberOfEmpire >= 2 && numberOfEmpire <= 8) return numberOfEmpire;
                else System.out.println("Please enter a number between 2 and 8");
            } catch (NumberFormatException e) {
                System.out.println("Your input not a number please try again");
            }
    }

    public void setUsersGameFinished(){
        for (int i = 0; i< empires.size(); i++)
            empires.get(i).getPlayer().setInGame(false);
    }
}
