package org.example.model;

import org.example.model.building.Tile;
import org.example.model.building.castleBuilding.EmpireBuilding;
import org.example.model.enums.Color;

import java.util.ArrayList;

public class Map {
    private Tile[][] map;

    private ArrayList<EmpireBuilding> empireBuildings = new ArrayList<>();

    private int size;

    public Map(int size) {
        this.map = new Tile[size][size];
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
            return map[xOfMap - 1][yOfMap - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public int getSize() {
        return size;
    }

    public Tile getTile(int x, int y) {
        return map[x][y];
    }
}
