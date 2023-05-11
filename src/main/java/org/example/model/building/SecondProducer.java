package org.example.model.building;

import org.example.model.Empire;
import org.example.model.building.enums.BuildingName;

public class SecondProducer extends Building {
    private SecondProducerType producerType;

    public SecondProducer(Empire empire, int x1, int y1, BuildingName buildingName, SecondProducerType secondProducerType) {
        super(empire, x1, y1, buildingName);
        this.producerType = secondProducerType;
    }

    public SecondProducerType getProducerType() {
        return producerType;
    }

    public void setProducerType(SecondProducerType producerType) {
        this.producerType = producerType;
    }
}
