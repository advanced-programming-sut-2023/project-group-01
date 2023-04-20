package org.example.model.building;

import org.example.model.People;
import org.example.model.User;
import org.example.model.building.enums.BuildingName;

import java.util.ArrayList;

public class Building {
    private final User currentUser;
    protected Tile position;
    protected int beginX;
    protected int endX;
    protected int beginY;
    protected int endY;
    private final BuildingName buildingName;

    public Building (User currentUser, Tile position, BuildingName buildingName) {
        this.currentUser = currentUser;
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
