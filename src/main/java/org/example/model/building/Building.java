package org.example.model.building;

import org.example.model.People;
import org.example.model.User;
import org.example.model.building.enums.BuildingName;

import java.util.ArrayList;

public class Building {
    private final User currentUser;
    protected int beginX;
    protected int endX;
    protected int beginY;
    protected int endY;
    private final BuildingName buildingName;

    public Building (User player, int x1, int x2, int y1, int y2, BuildingName buildingName) {
        this.currentUser = player;
        this.beginX = x1;
        this.endX = x2;
        this.beginY = y1;
        this.endY = y2;
        this.buildingName = buildingName;
    }

    public BuildingName getBuildingName() {
        return buildingName;
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
