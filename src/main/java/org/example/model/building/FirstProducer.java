package org.example.model.building;

import org.example.model.Empire;
import org.example.model.building.enums.BuildingName;
import org.example.model.building.enums.FirstProducerType;

public class FirstProducer extends Building {
    private FirstProducerType producerType;
    private int rate = 5;

    public FirstProducer(Empire empire, int x1, int y1, BuildingName buildingName) {
        super(empire, x1, y1, buildingName);
        for (FirstProducerType firstProducerType : FirstProducerType.values())
            if (firstProducerType.toString().equals(buildingName.getName())) this.producerType = firstProducerType;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setProducerType(FirstProducerType producerType) {
        this.producerType = producerType;
    }

    public int getRate() {
        return rate;
    }

    public FirstProducerType getProducerType() {
        return producerType;
    }

    public void createMaterial() {
        if (empire.checkCapacity(producerType.getProduct(), rate))
            empire.addMaterial(producerType.getProduct().getName(), rate);
    }
}
