package org.example.model.building;

import org.example.model.Empire;
import org.example.model.User;
import org.example.model.building.castleBuilding.CastleBuilding;
import org.example.model.building.enums.BuildingName;

public class Gatehouse extends CastleBuilding {

    //TODO دروازه یا پل هست
    private boolean isClosed = false;

    public Gatehouse(Empire empire, int x1, int x2, int y1, int y2, BuildingName buildingName) {
        super(empire, x1, x2, y1, y2, buildingName);
    }


    public void setClosed() {
        isClosed = true;
    }

    public void setOpened() {
        isClosed = false;
    }
    public boolean getClosed() {
        return isClosed;
    }
}
