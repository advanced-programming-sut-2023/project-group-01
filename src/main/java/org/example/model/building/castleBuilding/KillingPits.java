package org.example.model.building.castleBuilding;

import org.example.model.Empire;
import org.example.model.building.enums.BuildingName;

public class KillingPits extends CastleBuilding {
    private int damage = 40;


    public KillingPits(Empire empire, int x1, int x2, int y1, int y2, BuildingName buildingName) {
        super(empire, x1, x2, y1, y2, buildingName);
    }
}
