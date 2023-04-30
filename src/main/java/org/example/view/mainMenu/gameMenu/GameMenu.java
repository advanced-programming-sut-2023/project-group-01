package org.example.view.mainMenu.gameMenu;

import org.example.controller.mainMenuController.gameMenuController.GameMenuController;
import org.example.model.Empire;
import org.example.model.Map;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.GameMenuCommands.GameMenuCommands;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private final ArrayList<Empire> empires;

    private Map map;

    public static Empire thisEmpire;

    private int turnNumber = 1;

    public GameMenu(ArrayList<Empire> empires) {
        this.empires = empires;
        thisEmpire = empires.get(0);
    }

    public void setThisUser(Empire thisEmpire) {
        this.thisEmpire = thisEmpire;
    }

    public void setGameMap(Map map) {
        this.map = map;
    }

    public Empire getThisEmpire() {
        return thisEmpire;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void run(Scanner scanner) {
        map =  new CreateMapMenu().run(scanner);
        while (true){
            String command = scanner.nextLine();
            Matcher matcher;
            if((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_MAP)) != null)
                showMapChecker(matcher, scanner);
        }
    }

    public void nextTurn() {

    }

    public void showMapChecker(Matcher matcher, Scanner scanner){
        int xOfMap = Integer.parseInt(matcher.group("xOfMap"));
        int yOfMap = Integer.parseInt(matcher.group("yOfMap"));
        Outputs outputs = new GameMenuController().showMap(map.getTileWhitXAndY(xOfMap, yOfMap));
        System.out.println(outputs.toString());
        if(outputs.equals(Outputs.SUCCESS)) new MapMenu(map, xOfMap, yOfMap).run(scanner);
    }

}
