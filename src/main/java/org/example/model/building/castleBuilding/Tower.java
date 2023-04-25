package org.example.model.building.castleBuilding;

import org.example.model.User;
import org.example.model.building.castleBuilding.enums.TowerType;
import org.example.model.building.enums.BuildingName;
import org.example.model.building.Tile;

public class Tower extends CastleBuilding {

    private final TowerType towerType;

    public Tower(User player, int x1, int x2, int y1, int y2, BuildingName buildingName, TowerType towerType) {
        super(player, x1, x2, y1, y2, buildingName);
        this.towerType = towerType;
    }
}
