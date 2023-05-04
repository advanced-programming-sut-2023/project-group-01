package org.example.controller;

import org.example.model.Empire;
import org.example.model.building.Tile;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class NextTurn {
    private ArrayList<Empire> empires;
    private ArrayList<Tile> attackingTiles;
    private LinkedHashMap<Tile, Tile> archerAttackingTile;
    private int numberOfEmpires;
    private  int turnNumber;
    public NextTurn() {
        this.attackingTiles = new ArrayList<>();
        this.archerAttackingTile = new LinkedHashMap<Tile, Tile>();
        this.turnNumber = 0;
    }

    public void addEmpire(Empire empire) {
        this.empires.add(empire);
    }

}
