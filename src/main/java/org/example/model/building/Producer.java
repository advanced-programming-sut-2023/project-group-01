package org.example.model.building;

import org.example.model.Worker;
import org.example.model.building.enums.BuildingName;

import java.util.ArrayList;

public class Producer extends Building {

    private final ProducerType producerType;
    private ArrayList<Worker> workers = new ArrayList<>();

    private final int rate;

    public Producer(Tile position, BuildingName buildingName, ProducerType producerType) {
        super(position, buildingName);
        this.producerType = producerType;
        this.rate =1;
    }
}
