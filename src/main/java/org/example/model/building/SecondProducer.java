package org.example.model.building;

import org.example.model.Empire;
import org.example.model.building.enums.BuildingName;
import org.example.model.building.enums.FirstProducerType;

public class SecondProducer extends Building {
    private SecondProducerType producerType;
    private int rate = 10;
    public SecondProducer(Empire empire, int x1, int y1, BuildingName buildingName) {
        super(empire, x1, y1, buildingName);
        for (SecondProducerType secondProducerType : SecondProducerType.values())
            if (secondProducerType.toString().equals(buildingName.getName()))
                this.producerType = secondProducerType;
    }

    public int getRate() {
        return rate;
    }

    public SecondProducerType getProducerType() {
        return producerType;
    }

    public void setProducerType(SecondProducerType producerType) {
        this.producerType = producerType;
    }
}
