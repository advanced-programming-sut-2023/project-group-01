package org.example.view.mainMenu.gameMenu;

import org.example.model.Map;
import org.example.view.enums.commands.GameMenuCommands.CreateMapMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class CreateMapMenu {
    public Map run(Scanner scanner) {
        Map gameMap = new Map(200);
        while (true) {
            String command = scanner.nextLine();
            Matcher matcher;
            if ((matcher = CreateMapMenuCommands.getMatcher(command, CreateMapMenuCommands.Temp)) != null)
                setTextureForATileChecker(matcher);
            else if ((matcher = CreateMapMenuCommands.getMatcher(command, CreateMapMenuCommands.Temp)) != null)
                setTextureForARectangleChecker(matcher);
            else if ((matcher = CreateMapMenuCommands.getMatcher(command, CreateMapMenuCommands.Temp)) != null)
                clearChecker(matcher);
            else if ((matcher = CreateMapMenuCommands.getMatcher(command, CreateMapMenuCommands.Temp)) != null)
                dropRockChecker(matcher);
            else if ((matcher = CreateMapMenuCommands.getMatcher(command, CreateMapMenuCommands.Temp)) != null)
                dropTreeChecker(matcher);
            else if ((matcher = CreateMapMenuCommands.getMatcher(command, CreateMapMenuCommands.Temp)) != null)
                dropBuildingChecker(matcher);
            else if ((matcher = CreateMapMenuCommands.getMatcher(command, CreateMapMenuCommands.Temp)) != null)
                dropUnitChecker(matcher);
            else if (command.equals("finish")) return gameMap;
            else System.out.println("invalid command!");
        }
    }

    public void setTextureForATileChecker(Matcher matcher) {

    }

    public void setTextureForARectangleChecker(Matcher matcher) {

    }

    public void clearChecker(Matcher matcher) {

    }

    public void dropRockChecker(Matcher matcher) {

    }

    public void dropTreeChecker(Matcher matcher) {

    }

    public void dropBuildingChecker(Matcher matcher) {

    }

    public void dropUnitChecker(Matcher matcher) {

    }


}
