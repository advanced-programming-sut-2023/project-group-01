package org.example.controller.mainMenuController.gameMenuController;

import org.example.model.building.Building;
import org.example.model.building.Tile;
import org.example.model.building.enums.BuildingCategory;
import org.example.model.building.enums.BuildingName;
import org.example.model.building.enums.TypeOfTile;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;
import org.example.view.enums.Outputs;
import org.example.view.mainMenu.gameMenu.GameMenu;

import java.util.Random;

import static org.example.view.mainMenu.gameMenu.CreateMapMenu.gameMap;
import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;
import static org.example.view.mainMenu.gameMenu.GameMenu.getThisEmpire;

public class CreateMapMenuController {

    public Outputs setTextureForATile(Tile tile, String type) {
        if (tile == null)
            return Outputs.INVALID_COORDINATES;
        if (tile.getBuilding() != null)
            return Outputs.TILE_NOT_EMPTY;
        if (TypeOfTile.getTypeOfTileWithName(type) != null) {
            tile.setTypeOfTile(TypeOfTile.getTypeOfTileWithName(type));
            return Outputs.SUCCESS;
        } else return Outputs.INVALID_TYPE_OF_TILE;
    }

    public Outputs setTextureForARectangle(int x1, int y1, int x2, int y2, String type) {
        if (x1 > x2 || y1 < y2 ||
                gameMap.getTileWhitXAndY(x1, y1) == null ||
                gameMap.getTileWhitXAndY(x2, y2) == null)
            return Outputs.INVALID_COORDINATES;
        TypeOfTile typeOfTile = TypeOfTile.getTypeOfTileWithName("type");
        if (typeOfTile == null) return Outputs.INVALID_TYPE_OF_TILE;
        for (int i = x1; i <= x2; i++)
            for (int j = y2; j <= y1; j++)
                if (gameMap.getTileWhitXAndY(i, j).getBuilding() != null)
                    return Outputs.TILE_NOT_EMPTY;
        for (int i = x1; i <= x2; i++)
            for (int j = y2; j <= y1; j++)
                gameMap.getTileWhitXAndY(i, j).setTypeOfTile(typeOfTile);
        return Outputs.SUCCESS;

    }

    public Outputs clear(Tile tile) {
        if (tile == null) return Outputs.INVALID_COORDINATES;
        tile.removeAllUnit();
        tile.setBuilding(null);
        tile.setTypeOfTile(TypeOfTile.NORMAL_GROUND);
        return Outputs.SUCCESS;
    }

    public Outputs dropRock(Tile tile, String direction) {
        if (tile == null) return Outputs.INVALID_COORDINATES;
        if (direction.equals("r")) direction = GenerateRandomDirection();
        try {
            tile.setTypeOfTile(TypeOfTile.valueOf(direction.toUpperCase() + "_ROCK"));
        } catch (IllegalArgumentException e) {
            return Outputs.INVALID_DIRECTION;
        }
        return Outputs.SUCCESS;
    }

    public String GenerateRandomDirection() {
        Random random = new Random();
        char[] chars = "ensw".toCharArray();
        return String.valueOf(chars[random.nextInt(chars.length)]);
    }

    public Outputs dropTree(int xOfMap, int yOfMap, String type) {
        Tile tile = getMap().getTile(xOfMap, yOfMap);
        if (tile == null) return Outputs.INVALID_COORDINATES;
        BuildingName buildingName = BuildingName.getBuildingNameWithName(type);
        if (buildingName == null) return Outputs.INVALID_TYPE_OF_TREE;
        if (!buildingName.getBuildingCategory().equals(BuildingCategory.TREES))
            return Outputs.INVALID_TYPE_OF_TREE;
        if (tile.getBuilding() != null)
            return Outputs.TILE_NOT_EMPTY;
        if (buildingName.getTypeCanBuildBuilding() != tile.getTypeOfTile())
            return Outputs.INAPPROPRIATE_TYPE_OF_TILE;
        tile.setBuilding(new Building(getThisEmpire(), xOfMap, yOfMap, buildingName));
        return Outputs.SUCCESS;
    }

    public Outputs dropBuilding(int xOfBuilding, int yOfBuilding, String type) {
        if (gameMap.getTileWhitXAndY(xOfBuilding, yOfBuilding) == null)
            return Outputs.INVALID_COORDINATES;
        BuildingName buildingName = BuildingName.getBuildingNameWithName(type);
        if (buildingName == null)
            return Outputs.INVALID_TYPE_OF_BUILDING;
        if (buildingName.getBuildingCategory().equals(BuildingCategory.TREES))
            return Outputs.INVALID_TYPE_OF_BUILDING;
        int buildingSize = buildingName.getSize();
        if (gameMap.getTileWhitXAndY(xOfBuilding + buildingSize, yOfBuilding + buildingSize) == null)
            return Outputs.INVALID_COORDINATES;
        if (BuildingMenuController.isPositionFull(buildingName, xOfBuilding, yOfBuilding))
            return Outputs.TILE_NOT_EMPTY;
        if (BuildingMenuController.isGroundSuitable(buildingName, xOfBuilding, yOfBuilding))
            return Outputs.INAPPROPRIATE_TYPE_OF_TILE;
        BuildingMenuController.putBuilding(buildingName, xOfBuilding, yOfBuilding, getThisEmpire());
        return Outputs.SUCCESS;
    }

    public Outputs dropUnit(int xOfBuilding, int yOfBuilding, String type, int count) {
        Tile tile = getMap().getTileWhitXAndY(xOfBuilding, yOfBuilding);
        if (tile == null) return Outputs.INVALID_COORDINATES;
        if (count <= 0) return Outputs.INVALID_COUNT;
        MilitaryUnitName militaryUnitName = MilitaryUnitName.getMilitaryUnitWhitName(type);
        if (militaryUnitName == null) return Outputs.INVALID_TYPE_OF_UNIT;
        for (int i = 0; i < count; i++) {
            tile.addUnit(new MilitaryUnit(tile, getThisEmpire(),militaryUnitName, xOfBuilding, yOfBuilding));
        }

        return Outputs.SUCCESS;
    }


}
