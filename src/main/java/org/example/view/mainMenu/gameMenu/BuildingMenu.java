package org.example.view.mainMenu.gameMenu;

import org.example.controller.mainMenuController.gameMenuController.BuildingMenuController;
import org.example.model.building.Building;
import org.example.model.Empire;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.GameMenuCommands.BuildingMenuCommands;
import org.example.view.enums.commands.GameMenuCommands.MilitaryMenuCommands;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class BuildingMenu {
    private BuildingMenuController buildingMenuController;
    private final Empire empire;
    private Building selectedBuilding;

    public BuildingMenu(Empire empire) {
        this.empire = empire;
        this.buildingMenuController = new BuildingMenuController(empire, this);
    }

    public void run(Scanner scanner) {
        System.out.println("You are in building menu!");
        String input;

        while (true) {
            input = scanner.nextLine();
            Matcher matcher;
            if ((matcher = BuildingMenuCommands.getMatcher(input, BuildingMenuCommands.SELECT_BUILDING)).matches())
                selectBuildingChecker(matcher);
            else if ((matcher = BuildingMenuCommands.getMatcher(input, BuildingMenuCommands.DROP_BUILDING)).matches())
                dropBuildingChecker(matcher);
            else if ((matcher = BuildingMenuCommands.getMatcher(input, BuildingMenuCommands.CREATE_UNIT)).matches())
                createUnitChecker(matcher);
            else if (BuildingMenuCommands.getMatcher(input, BuildingMenuCommands.REPAIR).matches())
                repairChecker();
            else if (BuildingMenuCommands.getMatcher(input, BuildingMenuCommands.DESTROY_BUILDING).matches())
                destroyBuilding();
            else if (input.equals("exit"))
                break;
            else
                System.out.println("Invalid Command in Building menu!");
        }
    }

    public void selectBuildingChecker(Matcher matcher) {
        String x = matcher.group("x");
        String y = matcher.group("y");
        System.out.println(buildingMenuController.selectBuilding(x, y).toString());
    }

    public void dropBuildingChecker(Matcher matcher) {
        //TODO switching x, y, type
        String x = matcher.group("x");
        String y = matcher.group("y");
        String type = matcher.group("type");
        System.out.println(buildingMenuController.dropBuilding(x, y, type).toString());
    }

    public void destroyBuilding() {
        System.out.println(buildingMenuController.destroyBuilding(selectedBuilding).toString());
    }

    public void createUnitChecker(Matcher matcher) {
        String type = matcher.group("type");
        String count = matcher.group("count");
        System.out.println(buildingMenuController.createUnit(type, count).toString());
    }

    public void repairChecker() {
        System.out.println(buildingMenuController.repair().toString());
    }

    public void setBuilding(Building selectedBuilding) {
        this.selectedBuilding = selectedBuilding;
    }


    public Empire getEmpire() {
        return empire;
    }

    public Building getSelectedBuilding() {
        return selectedBuilding;
    }
}
