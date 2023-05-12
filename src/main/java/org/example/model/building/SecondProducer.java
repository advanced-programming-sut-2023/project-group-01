package org.example.model.building;

import org.example.model.Empire;
import org.example.model.building.enums.BuildingName;
import org.example.model.building.enums.SecondProducerType;

public class SecondProducer extends Building {
    private SecondProducerType producerType;
    private int rate = 5;

    public SecondProducer(Empire empire, int x1, int y1, BuildingName buildingName) {
        super(empire, x1, y1, buildingName);
        for (SecondProducerType secondProducerType : SecondProducerType.values())
            if (secondProducerType.toString().equals(buildingName.getName())) this.producerType = secondProducerType;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public SecondProducerType getProducerType() {
        return producerType;
    }

    public void setProducerType(SecondProducerType producerType) {
        this.producerType = producerType;
    }

    public void createMaterial() {
        if (empire.havingMaterial(producerType.getFirstEnterance(), rate) && empire.havingMaterial(producerType.getSecondEnterance2(), rate)) {
            if (producerType.getFirstProduct() != null && empire.checkCapacity(producerType.getFirstProduct(), rate))
                empire.addMaterial(producerType.getFirstProduct().getName(), rate);
            if (producerType.getSecondProduct() != null && empire.checkCapacity(producerType.getSecondProduct(), rate))
                empire.addMaterial(producerType.getSecondProduct().getName(), rate);
        }
    }
}
