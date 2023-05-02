package org.example.model.building.castleBuilding;

import org.example.model.Empire;
import org.example.model.building.enums.BuildingName;

public class KillingPits extends CastleBuilding {
    private int damage = 40;
    public KillingPits(Empire empire, int x1,  int y1, BuildingName buildingName) {
        super(empire, x1, y1,buildingName);
    }

    public int getDamage() {
        return damage;
    }

}
