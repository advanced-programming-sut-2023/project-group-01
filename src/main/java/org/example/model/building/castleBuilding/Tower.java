package org.example.model.building.castleBuilding;

import org.example.model.building.castleBuilding.enums.TowerType;
import org.example.model.building.enums.BuildingName;
import org.example.model.building.Tile;

public class Tower extends CastleBuilding {

    private final TowerType towerType;
    public Tower(Tile position, BuildingName buildingName, TowerType towerAndCastleType) {
        super(position, buildingName);
        this.towerType = towerAndCastleType;
    }

}
