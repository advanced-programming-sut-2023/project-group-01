package org.example.model.building.castleBuilding;

import org.example.model.building.enums.BuildingName;
import org.example.model.building.Tile;
import org.example.model.building.castleBuilding.enums.TowerAndGateHouseType;

public class TowerAndGateHouse extends CastleBuilding {

    private final TowerAndGateHouseType towerAndCastleType;
    public TowerAndGateHouse(Tile position, BuildingName buildingName, TowerAndGateHouseType towerAndCastleType) {
        super(position, buildingName);
        this.towerAndCastleType = towerAndCastleType;
    }

}
