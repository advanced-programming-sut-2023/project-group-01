package org.example.model.unit;

import org.example.model.Empire;
import org.example.model.User;
import org.example.model.building.Tile;
import org.example.model.building.enums.BuildingName;
import org.example.model.unit.enums.MilitaryUnitName;

public class Engineer extends MilitaryUnit {
    private boolean isWorking = false;
    private boolean haveOIl = false;

    public Engineer(Tile position, Empire empire, MilitaryUnitName militaryUnitName, int xPos, int yPos) {
        super(position, empire, militaryUnitName, xPos, yPos);
        if (empire.haveBuilding(BuildingName.OIL_SMELTER))
            this.haveOIl = true;
    }

    public void addOil() {
        this.haveOIl = true;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public boolean getHaveOil() {
        return haveOIl;
    }

    public boolean getWorking() {
        return isWorking;
    }
}
