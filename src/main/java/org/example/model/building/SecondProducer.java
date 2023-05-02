package org.example.model.building;

import org.example.model.Empire;
import org.example.model.building.enums.BuildingName;

public class SecondProducer extends Building {
    private SecondProducer producerType;

    public SecondProducer(Empire empire, int x1, int y1, BuildingName buildingName) {
        super(empire, x1, y1, buildingName);
    }


    public void setProducerType(SecondProducer producerType) {
        this.producerType = producerType;
    }

    public SecondProducer getProducerType() {
        return producerType;
    }
}
