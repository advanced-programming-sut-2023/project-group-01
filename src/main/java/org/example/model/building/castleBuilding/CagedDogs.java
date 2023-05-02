package org.example.model.building.castleBuilding;

import org.example.model.Empire;
import org.example.model.User;
import org.example.model.building.enums.BuildingName;

public class CagedDogs extends CastleBuilding {
    //TODO
    private int dogsNumber = 4;

    public CagedDogs(Empire empire, int x1, int y1, BuildingName buildingName) {
        super(empire, x1, y1,buildingName);
    }


    public void releaseDogs() {
        this.dogsNumber = 0;
    }
}
