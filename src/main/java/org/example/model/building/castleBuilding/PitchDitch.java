package org.example.model.building.castleBuilding;

import org.example.model.Empire;
import org.example.model.building.enums.BuildingName;

public class PitchDitch extends CastleBuilding {
    private boolean isFiring = false;
    private int damage = 100;

    public PitchDitch(Empire empire, int x1, int y1, BuildingName buildingName) {
        super(empire, x1, y1, buildingName);
    }

    public int getDamage() {
        return damage;
    }
}
