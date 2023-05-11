package org.example.model.building;

import org.example.model.Empire;
import org.example.model.People;
import org.example.model.Worker;
import org.example.model.building.enums.BuildingName;

import java.util.ArrayList;

import static org.example.model.building.enums.MaterialType.STONE;
import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;

public class Building {
    protected ArrayList<Worker> workers;
    protected Empire empire;
    protected int beginX;
    protected int endX;
    protected int beginY;
    protected int endY;
    private final BuildingName buildingName;

    public Building (Empire empire, int x1, int y1, BuildingName buildingName) {
        this.empire.reduceGold(buildingName.getGoldCost());
        this.empire.reduceMaterial("stone", buildingName.getStoneCost());
        this.empire.reduceMaterial("wood", buildingName.getWoodCost());
        this.empire = empire;
        this.beginX = x1;
        this.beginY = y1;
        this.endX = x1 + buildingName.getSize();
        this.endY = y1 + buildingName.getSize();
        this.buildingName = buildingName;
        //this.workers = empire.returnPeopleForWorker(buildingName.getNumberOfWorkers(), x1, y1);
    }

    public BuildingName getBuildingName() {
        return buildingName;
    }

    public void removeBuilding() {

        for (int i = beginX; i < endX; i++)
            for (int j = beginY; j < endY; j++)
                getMap().getTile(i, j).setBuilding(null);

        for (Worker worker : workers) {
            worker.getPosition().removeUnit(worker);
            empire.getPeople().add(new People(getMap().getTile(beginX, beginY), this.getEmpire()));
        }

        empire.getBuildings().remove(this);
        this.getEmpire().getPeople().removeAll(workers);
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
