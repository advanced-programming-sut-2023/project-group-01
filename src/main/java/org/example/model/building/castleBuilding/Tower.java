package org.example.model.building.castleBuilding;

import org.example.model.Empire;
import org.example.model.building.castleBuilding.enums.TowerType;
import org.example.model.building.enums.BuildingName;

public class Tower extends CastleBuilding {

    private final TowerType towerType;


    public Tower(Empire empire, int x1, int x2, int y1, int y2, BuildingName buildingName, TowerType towerType) {
        super(empire, x1, x2, y1, y2, buildingName);
        this.towerType = towerType;
    }
}
