package org.example.model.building;

import org.example.model.Empire;
import org.example.model.building.enums.BuildingName;
import org.example.model.building.enums.FirstProducerType;

public class FirstProducer extends Building {
    private FirstProducerType producerType;

    public FirstProducer(Empire empire, int x1, int x2, int y1, int y2, BuildingName buildingName, FirstProducerType producerType) {
        super(empire, x1, x2, y1, y2, buildingName);
        this.producerType = producerType;
    }

    public void setProducerType(FirstProducerType producerType) {
        this.producerType = producerType;
    }

    public FirstProducerType getProducerType() {
        return producerType;
    }
}
