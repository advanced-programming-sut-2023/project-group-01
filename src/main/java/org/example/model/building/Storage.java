package org.example.model.building;

import org.example.model.Empire;
import org.example.model.building.enums.BuildingName;

public class Storage extends Building {

    int capacity = 200;

    public Storage(Empire empire, int x1, int y1, BuildingName buildingName) {
        super(empire, x1, y1, buildingName);
    }

    public int getCapacity() {
        return capacity;
    }
}
