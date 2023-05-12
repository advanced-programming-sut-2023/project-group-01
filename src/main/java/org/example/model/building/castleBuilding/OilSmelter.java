package org.example.model.building.castleBuilding;

import org.example.model.Empire;
import org.example.model.building.SecondProducer;
import org.example.model.building.enums.BuildingName;
import org.example.model.unit.Engineer;

public class OilSmelter extends SecondProducer {

    private Engineer engineer;

    public OilSmelter(Empire empire, int x1, int y1, BuildingName buildingName) {
        super(empire, x1, y1, buildingName);
        empire.initializeOil();
    }

    public Engineer getEngineer() {
        return engineer;
    }

    public void setEngineer(Engineer engineer) {
        this.engineer = engineer;
    }
}
