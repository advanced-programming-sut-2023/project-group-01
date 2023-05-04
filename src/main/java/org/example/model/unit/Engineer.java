package org.example.model.unit;

import org.example.model.Empire;
import org.example.model.User;
import org.example.model.building.Tile;
import org.example.model.unit.enums.MilitaryUnitName;

public class Engineer extends MilitaryUnit {
    private boolean isWorking = false;

    public Engineer(Tile position, Empire empire, MilitaryUnitName militaryUnitName, int xPos, int yPos) {
        super(position, empire, militaryUnitName, xPos, yPos);
    }


    public void setWorking(boolean working) {
        isWorking = working;
    }

    public boolean getWorking() {
        return isWorking;
    }
}
