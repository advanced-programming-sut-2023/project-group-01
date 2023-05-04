package org.example.model.building.castleBuilding;

import org.example.model.Empire;
import org.example.model.building.enums.BuildingName;

public class Wall extends CastleBuilding {

    private boolean haveLadder = false;

    public Wall(Empire empire, int x1, int y1, BuildingName buildingName) {
        super(empire, x1, y1, buildingName);
    }


    public void setHaveLadder(boolean haveLadder) {
        this.haveLadder = haveLadder;
    }

    public boolean getHaveLadder() {
        return haveLadder;
    }
}
