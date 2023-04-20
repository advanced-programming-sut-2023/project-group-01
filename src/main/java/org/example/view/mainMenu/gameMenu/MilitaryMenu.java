package org.example.view.mainMenu.gameMenu;

import org.example.model.Empire;
import org.example.model.People;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MilitaryMenu {

    private final Empire empire;

    private People selectedUnit;

    public MilitaryMenu(Empire empire) {
        this.empire = empire;
    }

    public void setSelectedUnit(People selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public void run(Scanner scanner) {
        String input;

        while (true) {
            input = scanner.nextLine();

            if (GameCommands.getMatcher(input, GameCommands.SELECT_UNIT) != null) {
                selectUnitChecker(GameCommands.getMatcher(input, GameCommands.SELECT_UNIT));
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
                System.out.println("invalid command");
            }
        }

    }

    public void subRun() {

    }

    public void selectUnitChecker(Matcher matcher) {

    }

    public void moveUnitChecker(Matcher matcher) {

    }

    public void patrolUnitChecker(Matcher matcher) {

    }

    public void setUnitChecker(Matcher matcher) {

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

}