package org.example.model.building.castleBuilding;

import org.example.model.Empire;
import org.example.model.User;
import org.example.model.building.Material;
import org.example.model.building.enums.BuildingName;

public class Armoury extends CastleBuilding {

    int[][] capacity = new int[10][5];

    public Armoury(Empire empire, int x1, int y1, BuildingName buildingName) {
        super(empire, x1, y1, buildingName);
    }


    public String isFull(Material material) {
        //TODO
        return null;
    }

}
