package org.example.model.building;

import org.example.model.Empire;
import org.example.model.People;
import org.example.model.Worker;
import org.example.model.building.enums.BuildingName;

import java.util.ArrayList;

import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;

public class Building {
    protected ArrayList<Worker> workers;
    protected Empire empire;
    protected int beginX;
    protected int endX;
    protected int beginY;
    protected int endY;
    private final BuildingName buildingName;
    protected boolean isFiring = false;
    protected int fireNumber = 0;
    protected int hitPoint;

    public Building(Empire empire, int x1, int y1, BuildingName buildingName) {
        this.empire = empire;
//        this.empire.decreaseGold((buildingName.getGoldCost()));
//        this.empire.reduceMaterial("stone", buildingName.getStoneCost());
//        this.empire.reduceMaterial("wood", buildingName.getWoodCost());
        this.beginX = x1;
        this.beginY = y1;
        this.endX = x1 + buildingName.getSize() - 1;
        this.endY = y1 + buildingName.getSize() - 1;
        this.buildingName = buildingName;
        this.hitPoint = buildingName.getHitPoint();
//        this.workers = empire.changePeopleToWorker(buildingName.getNumberOfWorkers(), x1, y1);
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

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    public int getNumberOfWorkers() {
        return workers.size();
    }

    public boolean isFiring() {
        return isFiring;
    }

    public void setFiring(boolean firing) {
        isFiring = firing;
        fireNumber = 3;
    }

    public void reduceFireNumber() {
        //TODO call after each two next turn and when isFiring is true
        if (fireNumber <= 0)
            isFiring = false;
        fireNumber--;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public void reduceHitPoint(int hitPoint) {
        this.hitPoint -= hitPoint;
    }
}
