package org.example.model.building.castleBuilding;

import org.example.model.Empire;
import org.example.model.building.Building;
import org.example.model.building.enums.BuildingName;

public class CastleBuilding extends Building {


    public CastleBuilding(Empire empire, int x1, int y1, BuildingName buildingName) {
        super(empire, x1, y1, buildingName);
    }
}
