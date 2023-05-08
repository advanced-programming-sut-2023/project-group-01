package org.example.model.building;

import org.example.model.Empire;
import org.example.model.building.enums.BuildingName;

import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;

public class Building {
    protected Empire empire;
    protected int beginX;
    protected int endX;
    protected int beginY;
    protected int endY;
    private final BuildingName buildingName;

    public Building (Empire empire, int x1, int y1, BuildingName buildingName) {
        this.empire = empire;
        this.beginX = x1;
        this.beginY = y1;
        this.endX = x1 + buildingName.getSize();
        this.endY = y1 + buildingName.getSize();
        this.buildingName = buildingName;
    }

    public BuildingName getBuildingName() {
        return buildingName;
    }

    public void removeBuilding() {
        for (int i = beginX; i < endX; i++)
            for (int j = beginY; j < endY; j++)
                getMap().getTile(i, j).setBuilding(null);
        empire.getBuildings().remove(this);
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

    public Empire getEmpire() {
        return empire;
    }

    public void setEmpire(Empire empire) {
        this.empire = empire;
    }
}
