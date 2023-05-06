package org.example.view.mainMenu.gameMenu;

import org.example.model.Map;
import org.example.model.building.Tile;
import org.example.model.building.enums.BuildingCategory;
import org.example.model.building.enums.TypeOfTile;
import org.example.view.enums.BackgroundColor;
import org.example.view.enums.Outputs;
import org.example.view.enums.ShowMapAsciiArt;
import org.example.view.enums.commands.GameMenuCommands.MapMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenu {


    private final Map map;

    private int xOfMap;

    private int yOfMap;

    public MapMenu(Map map, int xOfMap, int yOfMap) {
        this.map = map;
        this.xOfMap = xOfMap;
        this.yOfMap = yOfMap;
    }

    public void run(Scanner scanner) {
        showMap();
        while (true) {
            String command = scanner.nextLine();
            Matcher matcher;
            if((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.MOVE_MAP)) != null)
                moveMapChecker(matcher);
            else if((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SHOW_DETAIL)) != null)
                showDetailOfMapChecker(matcher);
            else if(command.equals("exit")) return;
            else System.out.println("Invalid command!");
        }
    }

    public void showMap() {
        for (int j = 2; j >= -2; j--) {
            for (int line = 1; line <= 5; line++) {
                for (int i = -2; i <= 2; i++) {
                    try {
                        Tile tile = map.getTileWhitXAndY(xOfMap + i, yOfMap + j);
                        setColorInTerminal(tile);
                        if (tile == null) System.out.print("     ");
                        else if (!tile.getPeople().isEmpty())
                            System.out.print(ShowMapAsciiArt.valueOf("S" + line));
                        else if (tile.getBuilding() != null) {
                            BuildingCategory buildingCategory = tile.getBuilding().getBuildingName().getBuildingCategory();
                            if (buildingCategory.equals(BuildingCategory.CASTLE_BUILDING))
                                System.out.print(ShowMapAsciiArt.valueOf("W" + line));
                            else if (buildingCategory.equals(BuildingCategory.TREES))
                                System.out.print(ShowMapAsciiArt.valueOf("T" + line));
                            else System.out.print(ShowMapAsciiArt.valueOf("B" + line));
                        } else System.out.print("#####");
                        System.out.print("|");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Please try again");
                    }
                }
                System.out.println();
            }
            System.out.println("------------------------------");
        }
    }

    public void setColorInTerminal(Tile tile){
        if (tile == null) BackgroundColor.changeColor(BackgroundColor.ANSI_BLACK_BACKGROUND);
        else if (tile.getTypeOfTile().equals(TypeOfTile.NORMAL_GROUND))
            BackgroundColor.changeColor(BackgroundColor.ANSI_BLACK_BACKGROUND);
        else if(tile.getTypeOfTile().equals(TypeOfTile.GRASSLAND))
            BackgroundColor.changeColor(BackgroundColor.ANSI_GREEN_BACKGROUND);
        else if(tile.getTypeOfTile().equals(TypeOfTile.IRON_MINE))
            BackgroundColor.changeColor(BackgroundColor.ANSI_WHITE_BACKGROUND);
        else if(tile.getTypeOfTile().equals(TypeOfTile.MEADOW))
            BackgroundColor.changeColor(BackgroundColor.ANSI_YELLOW_BACKGROUND);
        else if(tile.getTypeOfTile().equals(TypeOfTile.SEA))
            BackgroundColor.changeColor(BackgroundColor.ANSI_BLUE_BACKGROUND);
        else if(tile.getTypeOfTile().equals(TypeOfTile.STONE_MINE))
            BackgroundColor.changeColor(BackgroundColor.ANSI_PURPLE_BACKGROUND);
    }

    public void moveMapChecker(Matcher matcher) {
        matcher.find();
        int up = 0;
        int down = 0;
        int left = 0;
        int right = 0;
        if (matcher.group("up") != null)
            if (matcher.group("upCount") != null)
                up = Integer.parseInt(matcher.group("upCount"));
            else up = 1;

        if (matcher.group("down") != null)
            if (matcher.group("downCount") != null)
                down = Integer.parseInt(matcher.group("downCount"));
            else down = 1;
        if (matcher.group("left") != null)
            if (matcher.group("leftCount") != null)
                left = Integer.parseInt(matcher.group("leftCount"));
            else left = 1;
        if (matcher.group("right") != null)
            if (matcher.group("rightCount") != null)
                right = Integer.parseInt(matcher.group("rightCount"));
            else right = 1;
        int newX = xOfMap + right - left;
        int newY = yOfMap + up - down;
        if(map.getTileWhitXAndY(newX, newY) != null) {
            xOfMap = newX;
            yOfMap = newY;
            showMap();
        }
        else System.out.println(Outputs.INVALID_COORDINATES);
    }

    public void showDetailOfMapChecker(Matcher matcher) {
        matcher.find();
        int newX = Integer.parseInt(matcher.group("xOfMap"));
        int newY = Integer.parseInt(matcher.group("yOfMap"));
        Tile tile;
        if((tile = map.getTileWhitXAndY(newX, newY)) != null){
            System.out.println("The type of tile is " + tile.getTypeOfTile());
            //TODO show units
            if(tile.getBuilding() != null)
                System.out.println("The building " + tile.getBuilding().getBuildingName().name());
        }
        else System.out.println(Outputs.INVALID_COORDINATES);
    }
}
