package org.example.model.unit;

import org.example.model.Empire;
import org.example.model.People;
import org.example.model.building.Tile;
import org.example.model.unit.enums.MilitaryUnitName;

import java.util.LinkedList;

import static java.lang.Integer.MAX_VALUE;

public class MilitaryUnit extends People {

    protected MilitaryUnitName militaryUnitName;
    //TODO اگه پاترول ها یکسان بودن جابه جا نشه
    protected LinkedList<Integer> patrol = null;
    protected  LinkedList<Integer> destination = null;

    //TODO set xPos & yPOS
    protected int xPos;
    protected int yPos;
    protected int xDestination;
    protected int yDestination;
    protected int patrolX1 = MAX_VALUE;
    protected int patrolY1 = MAX_VALUE;
    protected int patrolX2 = MAX_VALUE;
    protected int patrolY2 = MAX_VALUE;

    public MilitaryUnit(Tile position, Empire empire, MilitaryUnitName militaryUnitName, int xPos, int yPos) {
        super(position, empire);
        this.xPos = xPos;
        this.yPos = yPos;
        this.militaryUnitName = militaryUnitName;
    }

    public void setPatrolXY(int patrolX1, int patrolY1, int patrolX2, int patrolY2, LinkedList<Integer> patrol) {
        this.patrolX1 = patrolX1;
        this.patrolY1 = patrolY1;
        this.patrolX2 = patrolX2;
        this.patrolY2 = patrolY2;
        this.patrol = patrol;
        this.destination.clear();
    }

    public LinkedList<Integer> getPatrol() {
        return patrol;
    }

    public void goToDestination(int xDestination, int yDestination) {
        goToPos(xDestination, yDestination);
        this.xDestination = MAX_VALUE;
        this.yDestination = MAX_VALUE;
    }

    public void setDestination(int xDestination, int yDestination, int xPos, int yPos) {
        this.xDestination = xDestination;
        this.yDestination = yDestination;
        goToPos(xPos, yPos);
    }
    public void goToPos(int xPos, int yPos) {
        //TODO check
        empire.getMap().getTile(xPos + 1, yPos + 1).removeUnit(this);
        this.xPos = xPos;
        this.yPos = yPos;
        empire.getMap().getTile(xPos + 1, yPos + 1).addUnit(this);
        this.patrol.clear();
        this.patrolX1 = MAX_VALUE;
        this.patrolX2 = MAX_VALUE;
        this.patrolY1 = MAX_VALUE;
        this.patrolY2 = MAX_VALUE;
    }

    public void cancelPatrol() {
        this.patrolX1 = MAX_VALUE;
        this.patrolY1 = MAX_VALUE;
        this.patrolY1 = MAX_VALUE;
        this.patrolY2 = MAX_VALUE;
        this.patrol.clear();
        this.destination.clear();
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public MilitaryUnitName getMilitaryUnitName() {
        return militaryUnitName;
    }
}
