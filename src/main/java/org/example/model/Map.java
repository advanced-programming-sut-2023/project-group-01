package org.example.model;

import org.example.model.building.Tile;
import org.example.model.building.castleBuilding.enums.EmpireBuilding;

import java.util.ArrayList;

public class Map {
    private Tile[][] map;

    private ArrayList<EmpireBuilding> empireBuildings = new ArrayList<>();

    private int size;

    public Map(int size) {
        this.size = size;
        this.map = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            map[i] = new Tile[size];
            for (int j = 0; j < size; j++) {
                map[i][j] = new Tile();
            }
        }
    }

    public void addPeople(int x, int y, People person) {
        map[x][y].addUnit(person);
    }

    public void removePeople(int x, int y, People person) {
        map[x][y].removeUnit(person);
    }

    public Tile[][] getMap() {
        return map;
    }

    public Tile getTileWhitXAndY(int xOfMap, int yOfMap) {
        try {
            return map[xOfMap][yOfMap];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public int getSize() {
        return size;
    }

    public Tile getTile(int x, int y) {
        return getTileWhitXAndY(x, y);
    }
}
