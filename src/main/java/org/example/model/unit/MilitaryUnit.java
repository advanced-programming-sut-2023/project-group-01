package org.example.model.unit;

import org.example.model.Empire;
import org.example.model.People;
import org.example.model.building.Tile;
import org.example.model.unit.enums.MilitaryUnitName;

import java.util.LinkedList;

import static java.lang.Integer.MAX_VALUE;
import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;

public class MilitaryUnit extends People {

    protected MilitaryUnitName militaryUnitName;
    //TODO اگه پاترول ها یکسان بودن جابه جا نشه

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
        //System.out.println("in military unit :: x : " + xPos + " | yPos :" + yPos + "\n");
        this.xPos = xPos;
        this.yPos = yPos;
        this.militaryUnitName = militaryUnitName;

        getMap().getTile(xPos, yPos).addUnit(this);
    }

    public void setPatrolXY(int patrolX1, int patrolY1, int patrolX2, int patrolY2) {
        this.patrolX1 = patrolX1;
        this.patrolY1 = patrolY1;
        this.patrolX2 = patrolX2;
        this.patrolY2 = patrolY2;
    }

    public void goToDestination(int xDestination, int yDestination) {
        goToPos(xDestination, yDestination);
        this.xDestination = MAX_VALUE;
        this.yDestination = MAX_VALUE;
    }

    public void movePatrol(int x, int y) {
       this.xPos = x;
       this.yPos = y;
       this.xDestination = MAX_VALUE;
       this.yDestination = MAX_VALUE;
    }

    public void setDestination(int xPos, int yPos, int xDestination, int yDestination) {
        this.xDestination = xDestination;
        this.yDestination = yDestination;
        goToPos(xPos, yPos);
    }
    public void goToPos(int xPos, int yPos) {
        //TODO check
        getMap().getTile(xPos, yPos).removeUnit(this);
        this.xPos = xPos;
        this.yPos = yPos;
        getMap().getTile(xPos, yPos).addUnit(this);
        this.patrolX1 = MAX_VALUE;
        this.patrolX2 = MAX_VALUE;
        this.patrolY1 = MAX_VALUE;
        this.patrolY2 = MAX_VALUE;
    }

    public void changePatrol() {
        int x = patrolX1;
        int y = patrolY1;
        patrolX1 = patrolX2;
        patrolY1 = patrolY2;
        patrolX2 = x;
        patrolY2 = y;
    }

    public void cancelPatrol() {
        this.patrolX1 = MAX_VALUE;
        this.patrolY1 = MAX_VALUE;
        this.patrolY1 = MAX_VALUE;
        this.patrolY2 = MAX_VALUE;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getPatrolX2() {
        return patrolX2;
    }

    public int getPatrolY2() {
        return patrolY2;
    }

    public int getXDestination() {
        return xDestination;
    }

    public int getYDestination() {
        return yDestination;
    }

    public MilitaryUnitName getMilitaryUnitName() {
        return militaryUnitName;
    }
}
