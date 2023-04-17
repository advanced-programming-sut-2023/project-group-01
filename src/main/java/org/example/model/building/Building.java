package org.example.model.building;

import org.example.model.People;
import org.example.model.building.enums.BuildingName;

import java.util.ArrayList;

public class Building {
//    private final User currentUser;
    protected Tile position;
    protected int beginX;
    protected int endX;
    protected int beginY;
    protected int endY;
    private ArrayList<People> workers = new ArrayList<>();
    private final BuildingName buildingName;

    public Building (Tile position, BuildingName buildingName) {
        this.position = position;
        this.buildingName = buildingName;
    }

    public BuildingName getBuildingName() {
        return buildingName;
    }

    public Tile getPosition() {
        return position;
    }

    public void setPosition(Tile position) {
        this.position = position;
    }

    public int getBeginX() {
        return beginX;
    }

    public int getEndX() {
        return endX;
    }

    public int getBeginY() {
        return beginY;
    }

    public int getEndY() {
        return endY;
    }
}
/*
    protected int hp;
    protected String name;
    protected int numberOfWorkers;
    protected int goldOfBuilding;
    protected int stoneOfBuilding;
    protected int woodOfBuilding;
 */