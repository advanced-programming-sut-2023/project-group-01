package org.example.model.building.castleBuilding;

import org.example.model.building.enums.BuildingName;
import org.example.model.building.Tile;
import org.example.model.building.castleBuilding.enums.MercenaryPostEnum;

public class MercenaryPost extends CastleBuilding {
    private MercenaryPostEnum mercenaryPostEnum;

    public MercenaryPost(Tile position, BuildingName buildingName) {
        super(position, buildingName);

    }
}
