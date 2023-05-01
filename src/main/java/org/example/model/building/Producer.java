package org.example.model.building;

import org.example.model.User;
import org.example.model.Worker;
import org.example.model.building.enums.BuildingName;

import java.util.ArrayList;

public class Producer extends Building {

    private final ProducerType producerType;
    private ArrayList<Worker> workers = new ArrayList<>();

    private final int rate;


    public Producer(User player, int x1, int x2, int y1, int y2, BuildingName buildingName, ProducerType producerType) {
        super(player, x1, x2, y1, y2, buildingName);
        this.producerType = producerType;
        this.rate =1;
    }
}
