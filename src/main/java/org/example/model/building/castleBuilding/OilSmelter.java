package org.example.model.building.castleBuilding;

import org.example.model.Empire;
import org.example.model.User;
import org.example.model.building.FirstProducer;
import org.example.model.building.enums.FirstProducerType;
import org.example.model.building.enums.BuildingName;
import org.example.model.unit.Engineer;

public class OilSmelter extends FirstProducer {

    private Engineer engineer;

    public OilSmelter(Empire empire, int x1, int y1, BuildingName buildingName, FirstProducerType producerType) {
        super(empire, x1,  y1,  buildingName, producerType);
    }


    public Engineer getEngineer() {
        return engineer;
    }

    public void setEngineer(Engineer engineer) {
        this.engineer = engineer;
    }
}
