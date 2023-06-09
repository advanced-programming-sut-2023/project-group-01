package org.example.model.building.castleBuilding;

import org.example.model.Empire;
import org.example.model.building.enums.BuildingName;

public class CagedDogs extends CastleBuilding {

    private int damage = 50;

    public CagedDogs(Empire empire, int x1, int y1, BuildingName buildingName) {
        super(empire, x1, y1,buildingName);
    }

    public int getDamage() {
        return damage;
    }
}
