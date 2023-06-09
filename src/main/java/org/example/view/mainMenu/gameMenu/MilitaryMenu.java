package org.example.view.mainMenu.gameMenu;

import org.example.controller.mainMenuController.gameMenuController.MilitaryMenuController;
import org.example.model.Empire;
import org.example.model.unit.MilitaryUnit;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.GameMenuCommands.MilitaryMenuCommands;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MilitaryMenu {
    private final MilitaryMenuController militaryMenuController;
    private final Empire empire;
    private ArrayList<MilitaryUnit> selectedUnit = new ArrayList<>();

    public MilitaryMenu(Empire empire, GameMenu gameMenu) {
        this.empire = empire;
        this.militaryMenuController = new MilitaryMenuController(empire, this);
    }


    public void run(Scanner scanner) {
        System.out.println("You are in military menu");
        String input;

        while (true) {
            input = scanner.nextLine();
            Matcher matcher;

            if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.SELECT_UNIT)).matches())
                selectUnitChecker(matcher);
            else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.MOVE_UNIT_TO)).matches())
                moveUnitChecker(matcher);
            else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.PATROL_UNIT)).matches())
                patrolUnitChecker(matcher);
            else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.CANCEL_PATROL_UNIT)).matches())
                cancelPatrolUnitChecker(matcher);
            else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.SET_STATE)).matches())
                setUnitChecker(matcher);
            else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.ATTACK)).matches())
                attackChecker(matcher);
            else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.ATTACK_E)).matches())
                attackChecker(matcher);
            else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.POUR_OIL)).matches())
                pourOilChecker(matcher);
            else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.DIG_TUNNEL)).matches())
                digTunnelChecker(matcher);
            else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.BUILD_Q)).matches())
                buildChecker(matcher);
            else if (MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.DISBAND_UNIT).matches()) disbandUnit();
            else if ((matcher = MilitaryMenuCommands.getMatcher(input, MilitaryMenuCommands.CANCEL_PATROL_UNIT)).matches())
                cancelPatrolUnitChecker(matcher);
            else if (input.equals("exit")) return;
            else System.out.println("Invalid command in Military menu!");
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
        System.out.println(militaryMenuController.moveUnit(x, y).toString());
    }

    public void patrolUnitChecker(Matcher matcher) {
        String x1 = matcher.group("x1");
        String y1 = matcher.group("y1");
        String x2 = matcher.group("x2");
        String y2 = matcher.group("y2");
        System.out.println(militaryMenuController.patrolUnit(x1, y1, x2, y2).toString());
    }

    public void cancelPatrolUnitChecker(Matcher matcher) {
        String x = matcher.group("x");
        String y = matcher.group("y");
        System.out.println(militaryMenuController.cancelPatrol(x, y).toString());
    }

    public void setUnitChecker(Matcher matcher) {
        String x = matcher.group("x");
        String y = matcher.group("y");
        String s = matcher.group("state");
        System.out.println(militaryMenuController.setUnit(x, y, s).toString());
    }

    public void attackChecker(Matcher matcher) {
        String x = matcher.group("x");
        String y = matcher.group("y");
//        System.out.println(militaryMenuController.attack(x, y).toString());
    }

    public void pourOilChecker(Matcher matcher) {
        System.out.println(militaryMenuController.pourOil(matcher.group("direction")).toString());
    }

    public void digTunnelChecker(Matcher matcher) {
        String x = matcher.group("x");
        String y = matcher.group("y");
//       System.out.println(militaryMenuController.digTunnel(x, y).toString());
    }

    public void buildChecker(Matcher matcher) {
        String equipment = matcher.group("equipmentName");
//        System.out.println(militaryMenuController.build(equipment).toString());
    }

    public void disbandUnit() {
        int x = empire.getEmpireBuilding().getX();
        int y = empire.getEmpireBuilding().getY();

//        Outputs outputs = militaryMenuController.disbandUnit(x, y);
//        if (outputs != null) System.out.println(outputs.toString());
//        else System.out.println(Outputs.SUCCESSFUL_DISBAND);
    }

    public void setSelectedUnit(ArrayList<MilitaryUnit> selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public ArrayList<MilitaryUnit> getSelectedUnit() {
        return selectedUnit;
    }

}