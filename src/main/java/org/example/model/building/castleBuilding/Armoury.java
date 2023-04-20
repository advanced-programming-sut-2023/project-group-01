package org.example.model.building.castleBuilding;

import org.example.model.User;
import org.example.model.building.Material;
import org.example.model.building.enums.BuildingName;

public class Armoury extends CastleBuilding {

    int[][] capacity = new int[10][5];
    public Armoury(User player, int x1, int x2, int y1, int y2, BuildingName buildingName) {
        super(player, x1, x2, y1, y2, buildingName);
    }

    public String isFull(Material material) {
        //TODO
        return null;
    }

}
