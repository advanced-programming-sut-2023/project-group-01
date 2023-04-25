package org.example.model;

import org.example.model.building.Tile;

public class Map {
    private Tile[][] map;

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

    public int getSize() {
        return size;
    }

    public Tile getTile(int x, int y) {
        return map[x][y];
    }
}
