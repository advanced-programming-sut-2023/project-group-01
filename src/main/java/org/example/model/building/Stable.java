package org.example.model.building;

import org.example.model.Empire;
import org.example.model.building.enums.BuildingName;

public class Stable extends Building {
    private int numberOfHorse = 4;
    public Stable(Empire empire, int x1, int y1, BuildingName buildingName) {
        super(empire, x1, y1, buildingName);
    }

    public void reduceNumberOfHorse() {
        numberOfHorse--;
    }

    public int getNumberOfHorse() {
        return numberOfHorse;
    }
}
