package org.example.model;

import org.example.model.building.Tile;

import java.util.ArrayList;

import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;

public class Disease {
    private int reducePopularityAmount = 3;
    private ArrayList<Tile> diseasedTile = new ArrayList<>();

    public Disease(int xStart, int yStart, int xEnd, int yEnd) {
       for (int i = xStart; i < xEnd; i++) {
           for (int j = yStart; j < yEnd; j++) {
               getMap().getTile(i, j).setHaveDisease(true);
               diseasedTile.add(getMap().getTile(i , j));
           }
       }
    }

    private void removeDisease() {
        for (Tile tile : diseasedTile)
            tile.setHaveDisease(false);
    }


    public int getReducePopularityAmount() {
        return reducePopularityAmount;
    }
}
