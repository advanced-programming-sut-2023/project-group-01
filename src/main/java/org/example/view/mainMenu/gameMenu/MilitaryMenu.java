package org.example.view.mainMenu.gameMenu;

import org.example.controller.mainMenuController.gameMenuController.MilitaryMenuController;
import org.example.model.Empire;
import org.example.model.People;
import org.example.model.User;
import org.example.model.unit.MilitaryUnit;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.GameMenuCommands.MilitaryMenuCommands;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MilitaryMenu {
    private final MilitaryMenuController militaryMenuController;
    private final Empire empire;
    private ArrayList<MilitaryUnit> selectedUnit;

    public MilitaryMenu(Empire empire) {
        this.empire = empire;
        this.militaryMenuController = new MilitaryMenuController(empire, this);
    }


    public void run(Scanner scanner) {
        String input;

        while (true) {
            input = scanner.nextLine();
            Matcher matcher;

            if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.SELECT_UNIT)).find()) {
                selectUnitChecker(matcher);
            } else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.MOVE_UNIT_TO)).find()) {
                moveUnitChecker(matcher);
            } else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.PATROL_UNIT)).find()) {
                patrolUnitChecker(matcher);
            } else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.SET_STATE)).find()) {
                setUnitChecker(matcher);
            } else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.ATTACK)).find()) {
                attackChecker(matcher);
            } else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.POUR_OIL)).find()) {
                pourOilChecker(matcher);
            } else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.DIG_TUNNEL)).find()) {
                digTunnelChecker(matcher);
            } else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.BUILD_Q)).find()) {
                buildChecker(matcher);
            } else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.DISBAND_UNIT)).find()) {
                disbandUnit();
            } else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.CANCEL_PATROL_UNIT)).find()) {
                cancelPatrolUnitChecker();
            } else {
                System.out.println(Outputs.INVALID_COMMAND);
            }
        }
    }

    public void selectUnitChecker(Matcher matcher) {
        String x = matcher.group("x");
        String y = matcher.group("y");
        System.out.println(militaryMenuController.selectUnit(x, y).toString());
    }

    public void moveUnitChecker(Matcher matcher) {
        String x = matcher.group("x");
        String y = matcher.group("y");
        System.out.println(militaryMenuController.moveUnit(x, y));
    }

    public void patrolUnitChecker(Matcher matcher) {
        String x1 = matcher.group("x1");
        String y1 = matcher.group("y1");
        String x2 = matcher.group("x2");
        String y2 = matcher.group("y2");
        System.out.println(militaryMenuController.patrolUnit(x1, y1, x2, y2).toString());
    }

    public void cancelPatrolUnitChecker() {
        System.out.println(militaryMenuController.cancelPatrol());
    }

    public void setUnitChecker(Matcher matcher) {
        String x = matcher.group("x");
        String y = matcher.group("y");
        String s = matcher.group("state");
        System.out.println(militaryMenuController.setUnit(x, y, s).toString());
    }

    public void attackChecker(Matcher matcher) {

    }

    public void pourOilChecker(Matcher matcher) {
        System.out.println(militaryMenuController.pourOil(matcher.group("direction")));
    }

    public void digTunnelChecker(Matcher matcher) {
        String x = matcher.group("x");
        String y = matcher.group("y");
        System.out.println(militaryMenuController.digTunnel(x, y));
    }

    public void buildChecker(Matcher matcher) {

    }

    public void disbandUnit() {

    }


    public void setSelectedUnit(ArrayList<MilitaryUnit> selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public ArrayList<MilitaryUnit> getSelectedUnit() {
        return selectedUnit;
    }

}

//    public MilitaryUnit findMilitary(int x, int y) {
//        for (People people : empire.getMap().getTile(x, y).getPeople()) {
//            if (people instanceof MilitaryUnit && player.equals(people.getPlayer())) {
//                return (MilitaryUnit) people;
//            }
//        }
//        return null;
//    }