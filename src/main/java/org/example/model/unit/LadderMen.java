package org.example.model.unit;

import org.example.model.Empire;
import org.example.model.building.Tile;
import org.example.model.unit.enums.MilitaryUnitName;

public class LadderMen extends MilitaryUnit {
    private boolean havingLadder = true;

    public LadderMen(Tile position, Empire empire, MilitaryUnitName militaryUnitName, int xPos, int yPos) {
        super(position, empire, militaryUnitName, xPos, yPos);

    }


    public void setHavingLadder(boolean havingLadder) {
        this.havingLadder = havingLadder;
    }

    public boolean getHavingLadder() {
        return havingLadder;
    }
}