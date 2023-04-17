package org.example.model.building.castleBuilding;

import org.example.model.building.enums.BuildingName;
import org.example.model.building.Tile;

public class Barrack extends CastleBuilding {
    private BarrackEnum barrackEnum;

    public Barrack(Tile position,  BuildingName buildingName){
        super(position, buildingName);

    }
}
