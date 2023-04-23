package org.example.view.mainMenu.gameMenu;

import org.example.controller.mainMenuController.gameMenuController.MilitaryMenuController;
import org.example.model.Empire;
import org.example.model.People;
import org.example.model.User;
import org.example.model.unit.MilitaryUnit;
import org.example.view.enums.Outputs;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MilitaryMenu {
    private final MilitaryMenuController militaryMenuController;
    private final Empire empire;
    private User player;
    private People selectedUnit;

    public MilitaryMenu(Empire empire, User player) {
        this.empire = empire;
        this.player = player;
        this.militaryMenuController = new MilitaryMenuController(empire, player);

    }


    public void run(Scanner scanner) {
        String input;

        while (true) {
            input = scanner.nextLine();

            if (GameCommands.getMatcher(input, GameCommands.FIRST_SELECT_UNIT) != null) {
                selectUnitChecker(GameCommands.getMatcher(input, GameCommands.FIRST_SELECT_UNIT));
            } else if (GameCommands.getMatcher(input, GameCommands.MOVE_UNIT) != null) {
                moveUnitChecker(GameCommands.getMatcher(input, GameCommands.MOVE_UNIT));
            } else if (GameCommands.getMatcher(input, GameCommands.PATROL_UNIT) != null) {
                patrolUnitChecker(GameCommands.getMatcher(input, GameCommands.PATROL_UNIT));
            } else if (GameCommands.getMatcher(input, GameCommands.SET_STATE) != null) {
                setUnitChecker(GameCommands.getMatcher(input, GameCommands.SET_STATE));
            } else if (GameCommands.getMatcher(input, GameCommands.ATTACK) != null) {
                attackChecker(GameCommands.getMatcher(input, GameCommands.ATTACK));
            } else if (GameCommands.getMatcher(input, GameCommands.Pour_oil) != null) {
                pourOilChecker(GameCommands.getMatcher(input, GameCommands.Pour_oil));
            } else if (GameCommands.getMatcher(input, GameCommands.DIG_TUNNEL) != null) {
                digTunnelChecker(GameCommands.getMatcher(input, GameCommands.DIG_TUNNEL));
            } else if (GameCommands.getMatcher(input, GameCommands.BUILD_Q) != null) {
                buildChecker(GameCommands.getMatcher(input, GameCommands.BUILD_Q));
            } else if (GameCommands.getMatcher(input, GameCommands.DISBAND_UNIT) != null) {
                disbandUnit();
            } else {
                System.out.println(Outputs.INVALID_COMMAND);
            }
        }
    }

    public void selectUnitChecker(Matcher matcher) {
        String x;
        String y;
        if (matcher.group("x") == null && matcher.group("x") == null) {
            x = matcher.group("x1");
            y = matcher.group("y1");
        } else {
            x = matcher.group("x");
            y = matcher.group("y");
        }
        System.out.println(militaryMenuController.selectUnit(x, y).toString());
    }

//    public MilitaryUnit findMilitary(int x, int y) {
//        for (People people : empire.getMap().getTile(x, y).getPeople()) {
//            if (people instanceof MilitaryUnit && player.equals(people.getPlayer())) {
//                return (MilitaryUnit) people;
//            }
//        }
//        return null;
//    }

    public void moveUnitChecker(Matcher matcher) {
        String x = matcher.group("x");
        String y = matcher.group("y");
//        String result = commonCheck(x, y);
//
//        if (result != null) {
//            System.out.println(result);
//        } else if (selectedUnit == null) {
//            System.out.println(Outputs.EMPTY_SELECTED_UNIT.toString());
//            //TODO complete this when type of tiles finished
//        } else if (empire.getMap().getTile(Integer.parseInt(x),Integer.parseInt(y)).equals("") ||
//                empire.getMap().getTile(Integer.parseInt(x),Integer.parseInt(y)).equals("")) {
//            System.out.println(Outputs.WRONG_PLACE.toString());
//        } else {
//            MilitaryMenuController controller = new MilitaryMenuController(empire, player);
//            int xPos = Integer.parseInt(x);
//            int yPos = Integer.parseInt(y);
//            controller.moveUnit(xPos, yPos, findMilitary(xPos,yPos));
//        }
    }

    public void patrolUnitChecker(Matcher matcher) {
        //TODO COMPLETE
        //TODO selected
//        String x1 = matcher.group("x1");
//        String y1 = matcher.group("y1");
//        String result1 = commonCheck(x1, y1);
//
//        String x2 = matcher.group("x2");
//        String y2 = matcher.group("y2");
//        String result2 = commonCheck(x2, y2);
//
//        //TODO
//        if (result1 != null) {
//            System.out.println(result1);
//        } else if (result2 != null) {
//            System.out.println(result2);
//        } else {
//         //TODO
//         findMilitary(Integer.parseInt(x1), Integer.parseInt(y1)).setPatrolXY(Integer.parseInt(x2), Integer.parseInt(y2));
//        }
    }

    public void cancelPatrolUnitChecker(Matcher matcher) {
        //TODO COMPLETE
        //TODO selected
        String x = matcher.group("x");
        String y = matcher.group("y");
        //String result = commonCheck(x, y);

//        if (result != null) {
//            System.out.println(result);
//        } else {
//            //TODO
//
//        }
    }

    public void setUnitChecker(Matcher matcher) {
        String x = matcher.group("x");
        String y = matcher.group("y");
        //  String result = commonCheck(x, y);
        String s = matcher.group("s");

//        if (result != null) {
//            System.out.println(result);
//        }  else if (selectedUnit == null) {
//            System.out.println(Outputs.EMPTY_SELECTED_UNIT.toString());
//        } else {
//            if ()
//        }
    }

    public void attackChecker(Matcher matcher) {

    }

    public void pourOilChecker(Matcher matcher) {

    }

    public void digTunnelChecker(Matcher matcher) {

    }

    public void buildChecker(Matcher matcher) {

    }

    public void disbandUnit() {

    }

    public void setSelectedUnit(People selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    public void setSelectPeople(MilitaryUnit militaryUnit) {
        this.selectedUnit = militaryUnit;
    }
}