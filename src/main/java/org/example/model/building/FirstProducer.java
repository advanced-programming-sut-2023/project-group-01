package org.example.model.building;

import org.example.model.Empire;
import org.example.model.building.enums.BuildingName;
import org.example.model.building.enums.FirstProducerType;

public class FirstProducer extends Building {
    private FirstProducerType producerType;

    public FirstProducer(Empire empire, int x1, int y1, BuildingName buildingName) {
        super(empire, x1, y1, buildingName);
        //TODO
        for (FirstProducerType firstProducerType : FirstProducerType.values())
            if (firstProducerType.toString().equals(buildingName.getName()))
                this.producerType = firstProducerType;
    }

    public void setProducerType(FirstProducerType producerType) {
        this.producerType = producerType;
    }

    public FirstProducerType getProducerType() {
        return producerType;
    }
}
