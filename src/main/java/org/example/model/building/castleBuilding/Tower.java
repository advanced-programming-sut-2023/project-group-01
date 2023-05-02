package org.example.model.building.castleBuilding;

import org.example.model.Empire;
import org.example.model.User;
import org.example.model.building.castleBuilding.enums.TowerType;
import org.example.model.building.enums.BuildingName;
import org.example.model.building.Tile;

public class Tower extends CastleBuilding {

    private final TowerType towerType;


    public Tower(Empire empire, int x1, int y1, BuildingName buildingName, TowerType towerType) {
        super(empire, x1, y1, buildingName);
        this.towerType = towerType;
    }
}
