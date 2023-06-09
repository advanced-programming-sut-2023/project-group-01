package org.example.view.mainMenu.gameMenu;

import org.example.model.Map;
import org.example.model.People;
import org.example.model.building.Tile;
import org.example.model.building.enums.BuildingCategory;
import org.example.model.building.enums.TypeOfTile;
import org.example.model.unit.Catapult;
import org.example.model.unit.CatapultName;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;
import org.example.view.enums.BackgroundColor;
import org.example.view.enums.Outputs;
import org.example.view.enums.ShowMapAsciiArt;
import org.example.view.enums.commands.GameMenuCommands.GameMenuCommands;
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
            if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.MOVE_MAP)) != null)
                moveMapChecker(matcher);
            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SHOW_DETAIL)) != null)
                showDetailOfMapChecker(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_MAP)) != null)
                showMapMoveCheck(matcher);
            else if (command.equals("exit")) return;
            else System.out.println("Invalid command in Map Menu !");
        }
    }

    private void showMapMoveCheck(Matcher matcher) {
        matcher.find();
        int xOfMap = Integer.parseInt(matcher.group("xOfMap"));
        int yOfMap = Integer.parseInt(matcher.group("yOfMap"));
        if (map.getTileWhitXAndY(xOfMap, yOfMap) == null) {
            System.out.println(Outputs.INVALID_COORDINATES.toString());
            return;
        }
        this.xOfMap = xOfMap;
        this.yOfMap = yOfMap;
        showMap();
    }

    public void showMap() {
        for (int j = 2; j >= -2; j--) {
            for (int line = 1; line <= 5; line++) {
                for (int i = -2; i <= 2; i++) {
                    try {
                        Tile tile = map.getTileWhitXAndY(xOfMap + i, yOfMap + j);
                        setColorInTerminal(tile);
                        if (tile == null) System.out.print("     ");
                        else if (!tile.getPeople().isEmpty()) System.out.print(ShowMapAsciiArt.valueOf("S" + line));
                        else if (tile.getBuilding() != null) {
                            BuildingCategory buildingCategory = tile.getBuilding().getBuildingName().getBuildingCategory();
                            if (buildingCategory.equals(BuildingCategory.CASTLE_BUILDING))
                                System.out.print(ShowMapAsciiArt.valueOf("W" + line));
                            else if (buildingCategory.equals(BuildingCategory.TREES))
                                System.out.print(ShowMapAsciiArt.valueOf("T" + line));
                            else System.out.print(ShowMapAsciiArt.valueOf("B" + line));
                        } else System.out.print("#####");
                        BackgroundColor.changeColor(BackgroundColor.ANSI_RESET);
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

    public void setColorInTerminal(Tile tile) {
        if (tile == null) BackgroundColor.changeColor(BackgroundColor.ANSI_BLACK_BACKGROUND);
        else if (tile.getTypeOfTile().equals(TypeOfTile.GRASSLAND)) {
            BackgroundColor.changeColor(BackgroundColor.ANSI_GREEN_BACKGROUND);
            BackgroundColor.changeColor(BackgroundColor.ANSI_BLACK_TEXT);
        } else if (tile.getTypeOfTile().equals(TypeOfTile.IRON_MINE))
            BackgroundColor.changeColor(BackgroundColor.ANSI_WHITE_BACKGROUND);
        else if (tile.getTypeOfTile().equals(TypeOfTile.FULL_MEADOW))
            BackgroundColor.changeColor(BackgroundColor.ANSI_YELLOW_BACKGROUND);
        else if (tile.getTypeOfTile().equals(TypeOfTile.MEADOW))
            BackgroundColor.changeColor(BackgroundColor.ANSI_YELLOW);
        else if (tile.getTypeOfTile().isWater()) {
            BackgroundColor.changeColor(BackgroundColor.ANSI_BLACK_TEXT);
            BackgroundColor.changeColor(BackgroundColor.ANSI_BLUE_BACKGROUND);
        } else if (tile.getTypeOfTile().equals(TypeOfTile.STONE_MINE))
            BackgroundColor.changeColor(BackgroundColor.ANSI_PURPLE_BACKGROUND);
        else if (tile.getTypeOfTile().equals(TypeOfTile.OIL_GROUND)) {
            BackgroundColor.changeColor(BackgroundColor.ANSI_BLACK_BACKGROUND);
            BackgroundColor.changeColor(BackgroundColor.ANSI_BLUE);
        } else if (tile.getTypeOfTile().equals(TypeOfTile.PLAIN)) {
            BackgroundColor.changeColor(BackgroundColor.ANSI_YELLOW);
            BackgroundColor.changeColor(BackgroundColor.ANSI_GREEN_BACKGROUND);
        } else if (tile.getTypeOfTile().equals(TypeOfTile.BEACH)) {
            BackgroundColor.changeColor(BackgroundColor.ANSI_YELLOW_BACKGROUND);
            BackgroundColor.changeColor(BackgroundColor.ANSI_BLUE);
        } else BackgroundColor.changeColor(BackgroundColor.ANSI_BLACK_BACKGROUND);
    }

    public void moveMapChecker(Matcher matcher) {
        matcher.find();
        int up = 0;
        int down = 0;
        int left = 0;
        int right = 0;
        if (matcher.group("up") != null)
            if (matcher.group("upCount") != null) up = Integer.parseInt(matcher.group("upCount"));
            else up = 1;

        if (matcher.group("down") != null)
            if (matcher.group("downCount") != null) down = Integer.parseInt(matcher.group("downCount"));
            else down = 1;
        if (matcher.group("left") != null)
            if (matcher.group("leftCount") != null) left = Integer.parseInt(matcher.group("leftCount"));
            else left = 1;
        if (matcher.group("right") != null)
            if (matcher.group("rightCount") != null) right = Integer.parseInt(matcher.group("rightCount"));
            else right = 1;
        int newX = xOfMap + right - left;
        int newY = yOfMap + up - down;
        if (map.getTileWhitXAndY(newX, newY) != null) {
            xOfMap = newX;
            yOfMap = newY;
            showMap();
        } else System.out.println(Outputs.INVALID_COORDINATES);
    }

    public void showDetailOfMapChecker(Matcher matcher) {
        matcher.find();
        int newX = Integer.parseInt(matcher.group("xOfMap"));
        int newY = Integer.parseInt(matcher.group("yOfMap"));
        Tile tile;
        if ((tile = map.getTileWhitXAndY(newX, newY)) != null) {
            System.out.println("The type of tile is " + tile.getTypeOfTile());
            showUnits(tile);
            if (tile.getBuilding() != null)
                System.out.println("The building " + tile.getBuilding().getBuildingName().name());
        } else System.out.println(Outputs.INVALID_COORDINATES);
    }

    private void showUnits(Tile tile) {
        int number = 0;
        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values())
            if ((number = findNumber(tile, militaryUnitName)) != 0)
                System.out.println(militaryUnitName.getName() + ": " + number);
        for (CatapultName catapultName : CatapultName.values())
            if ((number = findCatapultNumber(tile, catapultName)) != 0)
                System.out.println(catapultName.getName() + ": " + number);
    }

    private int findCatapultNumber(Tile tile, CatapultName catapultName) {
        int number = 0;
        for (People person : tile.getPeople())
            if (person instanceof Catapult && ((Catapult) person).getCatapultName().equals(catapultName))
                number++;
        return number;
    }

    private int findNumber(Tile tile, MilitaryUnitName militaryUnitName) {
        int number = 0;
        for (People person : tile.getPeople())
            if (person instanceof MilitaryUnit && !(person instanceof Catapult) && ((MilitaryUnit) person).getMilitaryUnitName().equals(militaryUnitName))
                number++;
        return number;
    }
}
