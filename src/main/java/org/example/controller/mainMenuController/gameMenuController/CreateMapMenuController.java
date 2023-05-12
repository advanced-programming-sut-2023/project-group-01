package org.example.controller.mainMenuController.gameMenuController;

import org.example.model.Worker;
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

    public Outputs setTextureForATile(int x, int y, String type) {

        TypeOfTile typeOfTile = TypeOfTile.getTypeOfTileWithName(type);
        if (typeOfTile != null) {
            if (typeOfTile.equals(TypeOfTile.SMALL_POND)) return setSmallPond(x, y);
            else if (typeOfTile.equals(TypeOfTile.BIG_POND)) return setBigPond(x, y);
            else {
                Tile tile = gameMap.getTileWhitXAndY(x, y);
                if (tile == null) return Outputs.INVALID_COORDINATES;
                if (tile.getBuilding() != null) return Outputs.TILE_NOT_EMPTY;
                tile.setTypeOfTile(typeOfTile);
                return Outputs.SUCCESS;
            }
        } else return Outputs.INVALID_TYPE_OF_TILE;
    }

    private Outputs setSmallPond(int x, int y) {
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                Tile tile = gameMap.getTileWhitXAndY(x + i, y + j);
                if (tile == null) return Outputs.INVALID_COORDINATES;
                if (tile.getBuilding() != null) return Outputs.TILE_NOT_EMPTY;
            }
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                Tile tile = gameMap.getTileWhitXAndY(x + i, y + j);
                tile.setTypeOfTile(TypeOfTile.SMALL_POND);
            }
        return Outputs.SUCCESS;
    }

    public Outputs setBigPond(int x, int y) {
        for (int i = -2; i <= 2; i++)
            for (int j = -2; j <= 2; j++) {
                Tile tile = gameMap.getTileWhitXAndY(x, y);
                if (tile == null) return Outputs.INVALID_COORDINATES;
                if (tile.getBuilding() != null) return Outputs.TILE_NOT_EMPTY;
            }
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                Tile tile = gameMap.getTileWhitXAndY(x + i, y + j);
                tile.setTypeOfTile(TypeOfTile.BIG_POND);
            }
        return Outputs.SUCCESS;
    }

    public Outputs setTextureForARectangle(int x1, int y1, int x2, int y2, String type) {
        if (x1 > x2 || y1 < y2 || gameMap.getTileWhitXAndY(x1, y1) == null || gameMap.getTileWhitXAndY(x2, y2) == null)
            return Outputs.INVALID_COORDINATES;
        TypeOfTile typeOfTile = TypeOfTile.getTypeOfTileWithName(type);
        if (typeOfTile == null) return Outputs.INVALID_TYPE_OF_TILE;
        if (typeOfTile.equals(TypeOfTile.SMALL_POND) || typeOfTile.equals(TypeOfTile.BIG_POND))
            return Outputs.SET_POND_INVALID;
        for (int i = x1; i <= x2; i++)
            for (int j = y2; j <= y1; j++)
                if (gameMap.getTileWhitXAndY(i, j).getBuilding() != null) return Outputs.TILE_NOT_EMPTY;
        for (int i = x1; i <= x2; i++)
            for (int j = y2; j <= y1; j++)
                gameMap.getTileWhitXAndY(i, j).setTypeOfTile(typeOfTile);
        return Outputs.SUCCESS;

    }

    public Outputs clear(Tile tile) {
        if (tile == null) return Outputs.INVALID_COORDINATES;
        tile.removeAllUnit();
        Building building = tile.getBuilding();
        if (building != null) {
            building.getEmpire().getBuildings().remove(building);
            int beginX = building.getBeginX();
            int beginY = building.getBeginY();
            int endX = building.getEndX();
            int endY = building.getEndY();
            building.getEmpire().getPeople().removeAll(getMap().getTile(beginX, beginY).getPeople());
            getThisEmpire().reduceMaxPopulation(building.getBuildingName().getNumberOfWorkers());
            getMap().getTile(beginX, beginY).removeAllUnit();
            for (int i = beginX; i < endX; i++) {
                for (int j = beginY; j < endY; j++) {
                    GameMenu.getMap().getTile(i, j).setBuilding(null);
                }
            }
        }
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
        if (!buildingName.getBuildingCategory().equals(BuildingCategory.TREES)) return Outputs.INVALID_TYPE_OF_TREE;
        if (tile.getBuilding() != null) return Outputs.TILE_NOT_EMPTY;
        if (buildingName.getTypeCanBuildBuilding() != tile.getTypeOfTile()) return Outputs.INAPPROPRIATE_TYPE_OF_TILE;
        tile.setBuilding(new Building(null, xOfMap, yOfMap, buildingName));
        return Outputs.SUCCESS;
    }

    public Outputs dropBuilding(int xOfBuilding, int yOfBuilding, String type) {
        if (gameMap.getTileWhitXAndY(xOfBuilding, yOfBuilding) == null) return Outputs.INVALID_COORDINATES;
        BuildingName buildingName = BuildingName.getBuildingNameWithName(type);
        if (buildingName == null) return Outputs.INVALID_TYPE_OF_BUILDING;
        if (buildingName.getBuildingCategory().equals(BuildingCategory.TREES)) return Outputs.INVALID_TYPE_OF_BUILDING;
        int buildingSize = buildingName.getSize();
        if (gameMap.getTileWhitXAndY(xOfBuilding + buildingSize, yOfBuilding + buildingSize) == null)
            return Outputs.INVALID_COORDINATES;
        if (BuildingMenuController.isPositionFull(buildingName, xOfBuilding, yOfBuilding))
            return Outputs.TILE_NOT_EMPTY;
        if (BuildingMenuController.isGroundSuitable(buildingName, xOfBuilding, yOfBuilding))
            return Outputs.INAPPROPRIATE_TYPE_OF_TILE;
        BuildingMenuController.putBuilding(buildingName, xOfBuilding, yOfBuilding, getThisEmpire());
        for (int i = 0; i < buildingName.getNumberOfWorkers(); i++) {
            Worker worker = new Worker(getMap().getTile(xOfBuilding, yOfBuilding), getThisEmpire());
            getThisEmpire().addPeople(worker);
            getMap().getTileWhitXAndY(xOfBuilding, yOfBuilding).addUnit(worker);
        }
        getThisEmpire().increaseMaxPopulation(buildingName.getNumberOfWorkers());
        return Outputs.SUCCESS;
    }

    public Outputs dropUnit(int xOfBuilding, int yOfBuilding, String type, int count) {
        Tile tile = getMap().getTileWhitXAndY(xOfBuilding, yOfBuilding);
        if (tile == null) return Outputs.INVALID_COORDINATES;
        if (count <= 0) return Outputs.INVALID_COUNT;
        MilitaryUnitName militaryUnitName = MilitaryUnitName.getMilitaryUnitWhitName(type);
        if (militaryUnitName == null) return Outputs.INVALID_TYPE_OF_UNIT;
        for (int i = 0; i < count; i++) {
            tile.addUnit(new MilitaryUnit(tile, getThisEmpire(), militaryUnitName, xOfBuilding, yOfBuilding));
        }

        return Outputs.SUCCESS;
    }


}
