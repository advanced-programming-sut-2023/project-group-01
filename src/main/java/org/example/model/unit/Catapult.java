package org.example.model.unit;

import org.example.model.Empire;
import org.example.model.building.Tile;
import org.example.model.unit.enums.MilitaryUnitName;

public class Catapult extends MilitaryUnit {
    private final CatapultName catapultName;

    public Catapult(Tile position, Empire empire, int xPos, int yPos, CatapultName catapultName) {
        super(position, empire, null, xPos, yPos);
        this.catapultName = catapultName;
    }


    public CatapultName getCatapultName() {
        return catapultName;
    }
}
