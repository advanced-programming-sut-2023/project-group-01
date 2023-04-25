package org.example.model.building.castleBuilding;

import org.example.model.User;
import org.example.model.building.FirstProducer;
import org.example.model.building.enums.FirstProducerType;
import org.example.model.building.enums.BuildingName;
import org.example.model.unit.Engineer;

public class OilSmelter extends FirstProducer {

    private Engineer engineer;
    public OilSmelter(User player, int x1, int x2, int y1, int y2, BuildingName buildingName, FirstProducerType firstProducerType) {
        super(player, x1, x2, y1, y2, buildingName, firstProducerType);
    }

    public Engineer getEngineer() {
        return engineer;
    }

    public void setEngineer(Engineer engineer) {
        this.engineer = engineer;
    }
}
