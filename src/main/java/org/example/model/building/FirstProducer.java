package org.example.model.building;

import org.example.model.Empire;
import org.example.model.User;
import org.example.model.building.enums.BuildingName;
import org.example.model.building.enums.FirstProducerType;

public class FirstProducer extends Building {
    private FirstProducerType producerType;

    public FirstProducer(Empire empire, int x1, int y1, BuildingName buildingName, FirstProducerType producerType) {
        super(empire, x1, y1, buildingName);
        this.producerType = producerType;
    }

    public void setProducerType(FirstProducerType producerType) {
        this.producerType = producerType;
    }

    public FirstProducerType getProducerType() {
        return producerType;
    }
}
