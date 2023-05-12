package org.example.view.mainMenu.gameMenu;


import org.example.controller.mainMenuController.gameMenuController.CreateMapMenuController;
import org.example.model.Data;
import org.example.model.Map;
import org.example.model.User;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.GameMenuCommands.CreateMapMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;


public class CreateMapMenu {

    public static Map gameMap;
    private final User player;
    public CreateMapMenu(User player){
        this.player = player;
    }
    public Map run(Scanner scanner) {
        while (true) {
            String command = scanner.nextLine();
            Matcher matcher;
            if ((matcher = CreateMapMenuCommands.getMatcher
                    (command, CreateMapMenuCommands.SET_TEXTURE_FOR_A_TILE)) != null)
                System.out.println(setTextureForATileChecker(matcher).toString());
            else if ((matcher = CreateMapMenuCommands.getMatcher
                    (command, CreateMapMenuCommands.SET_TEXTURE_FOR_A_RECTANGLE)) != null)
                System.out.println(setTextureForARectangleChecker(matcher).toString());
            else if ((matcher = CreateMapMenuCommands.getMatcher(command, CreateMapMenuCommands.CLEAR)) != null)
                System.out.println(clearChecker(matcher).toString());
            else if ((matcher = CreateMapMenuCommands.getMatcher(command, CreateMapMenuCommands.DROP_ROCK)) != null)
                System.out.println(dropRockChecker(matcher).toString());
            else if ((matcher = CreateMapMenuCommands.getMatcher(command, CreateMapMenuCommands.DROP_TREE)) != null)
                System.out.println(dropTreeChecker(matcher).toString());
            else if ((matcher = CreateMapMenuCommands.getMatcher(command, CreateMapMenuCommands.DROP_BUILDING)) != null)
                System.out.println(dropBuildingChecker(matcher).toString());
            else if ((matcher = CreateMapMenuCommands.getMatcher(command, CreateMapMenuCommands.DROP_UNIT)) != null)
                System.out.println(dropUnitChecker(matcher).toString());
            else if (command.equals("finish")) return gameMap;
            else if (command.equals("set default map")){
                System.out.println("Default map set successfully!");
                return runDefaultMap(new Scanner(Data.getDefaultMap()));
            }
            else System.out.println("invalid command in Create Map Menu!");
        }
    }

    public Map runDefaultMap(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            Matcher matcher;
            if ((matcher = CreateMapMenuCommands.getMatcher
                    (command, CreateMapMenuCommands.SET_TEXTURE_FOR_A_TILE)) != null)
                setTextureForATileChecker(matcher);
            else if ((matcher = CreateMapMenuCommands.getMatcher
                    (command, CreateMapMenuCommands.SET_TEXTURE_FOR_A_RECTANGLE)) != null)
                setTextureForARectangleChecker(matcher);
            else if ((matcher = CreateMapMenuCommands.getMatcher(command, CreateMapMenuCommands.CLEAR)) != null)
                clearChecker(matcher);
            else if ((matcher = CreateMapMenuCommands.getMatcher(command, CreateMapMenuCommands.DROP_ROCK)) != null)
                dropRockChecker(matcher);
            else if ((matcher = CreateMapMenuCommands.getMatcher(command, CreateMapMenuCommands.DROP_TREE)) != null)
                dropTreeChecker(matcher);
            else if ((matcher = CreateMapMenuCommands.getMatcher(command, CreateMapMenuCommands.DROP_BUILDING)) != null)
                dropBuildingChecker(matcher);
            else if ((matcher = CreateMapMenuCommands.getMatcher(command, CreateMapMenuCommands.DROP_UNIT)) != null)
                dropUnitChecker(matcher);
            else if (command.equals("finish")) return gameMap;

        }
        return gameMap;
    }


    public Outputs setTextureForATileChecker(Matcher matcher) {
        matcher.find();
        int xOfMap = Integer.parseInt(matcher.group("xOfMap"));
        int yOfMap = Integer.parseInt(matcher.group("yOfMap"));
        String type = matcher.group("type");
        Outputs outputs = new CreateMapMenuController().setTextureForATile
                (xOfMap, yOfMap, type);
        return outputs;
    }

    public Outputs setTextureForARectangleChecker(Matcher matcher) {
        matcher.find();
        int x1OfMap = Integer.parseInt(matcher.group("x1OfMap"));
        int y1OfMap = Integer.parseInt(matcher.group("y1OfMap"));
        int x2OfMap = Integer.parseInt(matcher.group("x2OfMap"));
        int y2OfMap = Integer.parseInt(matcher.group("y2OfMap"));
        String type = matcher.group("type");
        Outputs outputs = new CreateMapMenuController().setTextureForARectangle
                (x1OfMap, y1OfMap, x2OfMap, y2OfMap, type);
        return outputs;
    }

    public Outputs clearChecker(Matcher matcher) {
        matcher.find();
        int xOfMap = Integer.parseInt(matcher.group("xOfMap"));
        int yOfMap = Integer.parseInt(matcher.group("yOfMap"));
        Outputs outputs = new CreateMapMenuController().clear(gameMap.getTileWhitXAndY(xOfMap, yOfMap));
        return outputs;
    }

    public Outputs dropRockChecker(Matcher matcher) {
        matcher.find();
        int xOfMap = Integer.parseInt(matcher.group("xOfMap"));
        int yOfMap = Integer.parseInt(matcher.group("yOfMap"));
        String direction = matcher.group("direction");
        Outputs outputs = new CreateMapMenuController().dropRock
                (gameMap.getTileWhitXAndY(xOfMap, yOfMap), direction);
        return outputs;
    }

    public Outputs dropTreeChecker(Matcher matcher) {
        matcher.find();
        int xOfMap = Integer.parseInt(matcher.group("xOfMap"));
        int yOfMap = Integer.parseInt(matcher.group("yOfMap"));
        String type = matcher.group("type");
        Outputs outputs = new CreateMapMenuController().dropTree
                (xOfMap, yOfMap, type);
        return outputs;
    }

    public Outputs dropBuildingChecker(Matcher matcher) {
        matcher.find();
        int xOfMap = Integer.parseInt(matcher.group("xOfMap"));
        int yOfMap = Integer.parseInt(matcher.group("yOfMap"));
        String type = matcher.group("type");
        Outputs outputs = new CreateMapMenuController().dropBuilding
                (xOfMap, yOfMap, type);
        return outputs;
    }

    public Outputs dropUnitChecker(Matcher matcher) {
        matcher.find();
        int xOfMap = Integer.parseInt(matcher.group("xOfMap"));
        int yOfMap = Integer.parseInt(matcher.group("yOfMap"));
        String type = matcher.group("type");
        int count = Integer.parseInt(matcher.group("count"));
        Outputs outputs = new CreateMapMenuController().dropUnit(xOfMap, yOfMap, type, count);
        return outputs;
    }

}
