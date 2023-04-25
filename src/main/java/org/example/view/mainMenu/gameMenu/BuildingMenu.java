package org.example.view.mainMenu.gameMenu;

import org.example.controller.mainMenuController.gameMenuController.BuildingMenuController;
import org.example.model.User;
import org.example.model.building.Building;
import org.example.model.Empire;
import org.example.view.enums.Outputs;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BuildingMenu {
    private BuildingMenuController buildingMenuController;
    private final Empire empire;
    private final User player;
    private Building selectedBuilding;

    public BuildingMenu(Empire empire, User player) {
        this.empire = empire;
        this.player = player;
        this.buildingMenuController = new BuildingMenuController();
        buildingMenuController.setEmpire(empire);
        buildingMenuController.setBuildingMenu(this);
    }

    public void run(Scanner scanner) {
        String input;

        while (true) {
            input = scanner.nextLine();

            if (GameCommands.getMatcher(input, GameCommands.SELECT_BUILDING) != null) {
                selectBuildingChecker(GameCommands.getMatcher(input, GameCommands.SELECT_BUILDING));
            } else if (GameCommands.getMatcher(input, GameCommands.DROP_BUILDING) != null) {
                dropBuildingChecker(GameCommands.getMatcher(input, GameCommands.DROP_BUILDING));
            } else if (GameCommands.getMatcher(input, GameCommands.CREATE_UNIT) != null) {
                createUnitChecker(GameCommands.getMatcher(input, GameCommands.CREATE_UNIT));
            } else if (GameCommands.getMatcher(input, GameCommands.REPAIR) != null) {
                repairChecker();
            } else {
                System.out.println(Outputs.INVALID_COMMAND);
            }
        }
    }

    public void selectBuildingChecker(Matcher matcher) {
        String x;
        String y;
        if (matcher.group("x") == null && matcher.group("y") == null) {
            x = matcher.group("x1");
            y = matcher.group("y1");
        } else {
            x = matcher.group("x");
            y = matcher.group("y");
        }
        System.out.println(buildingMenuController.selectBuilding(x, y).toString());
    }

    public void dropBuildingChecker(Matcher matcher) {
        //TODO switching x, y, type
        String x = matcher.group("x");
        String y = matcher.group("y");
        String type = matcher.group("type");


    }

    public void createUnitChecker(Matcher matcher) {
        String type;
        String count;
        if (matcher.group("type") == null && matcher.group("type") == null) {
            type = matcher.group("type1");
            count = matcher.group("count1");
        } else {
            type = matcher.group("type");
            count = matcher.group("count");
        }
        System.out.println(buildingMenuController.createUnit(type, count).toString());
    }

    public void repairChecker() {
        System.out.println(buildingMenuController.repair().toString());
    }

    public void setBuilding(Building selectedBuilding) {
        this.selectedBuilding = selectedBuilding;
    }

    public User getPlayer() {
        return player;
    }

    public Building getSelectedBuilding() {
        return selectedBuilding;
    }
}
