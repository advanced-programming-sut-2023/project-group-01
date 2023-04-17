package org.example.model.building.castleBuilding;

import org.example.model.building.enums.BuildingName;
import org.example.model.building.Tile;

public class DamagingBuilding extends CastleBuilding {
    private int damage;

    public DamagingBuilding(Tile position, BuildingName buildingName) {
        super(position, buildingName);

    }
}
