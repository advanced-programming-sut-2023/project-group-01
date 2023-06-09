package org.example.model.unit;

import org.example.model.Empire;
import org.example.model.People;
import org.example.model.building.Tile;
import org.example.model.unit.enums.MilitaryUnitName;

import static java.lang.Integer.MAX_VALUE;
import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;

public class MilitaryUnit extends People {

    protected MilitaryUnitName militaryUnitName;
    protected int xAttack;
    protected int yAttack;
    protected int xPos;
    protected int yPos;
    protected int xDestination;
    protected int yDestination;
    protected int patrolX1 = MAX_VALUE;
    protected int patrolY1 = MAX_VALUE;
    protected int patrolX2 = MAX_VALUE;
    protected int patrolY2 = MAX_VALUE;
    protected boolean isMoved;
    protected boolean isAttacked;
    protected boolean haveFire = false;
    protected int hitPoint;

    public MilitaryUnit(Tile position, Empire empire, MilitaryUnitName militaryUnitName, int xPos, int yPos) {
        super(position, empire);
        if (empire != null) {
            empire.removePeople();
            empire.addUnit(this);
        }
        this.xPos = xPos;
        this.yPos = yPos;
        this.xDestination = -1;
        this.yDestination = -1;
        this.xAttack = -1;
        this.yAttack = -1;
        this.militaryUnitName = militaryUnitName;
        if (!(this instanceof Catapult))
            this.hitPoint = militaryUnitName.getHitPoint();
        getMap().getTile(xPos, yPos).addUnit(this);
    }

    public void setDest(int xDestination, int yDestination) {
        this.xDestination = xDestination;
        this.yDestination = yDestination;
        this.patrolX1 = MAX_VALUE;
        this.patrolX2 = MAX_VALUE;
        this.patrolY1 = MAX_VALUE;
        this.patrolY2 = MAX_VALUE;
    }

    public void goToDestination(int xDestination, int yDestination) {
        goToPos(xDestination, yDestination);
        this.xDestination = MAX_VALUE;
        this.yDestination = MAX_VALUE;
        this.patrolX1 = MAX_VALUE;
        this.patrolX2 = MAX_VALUE;
        this.patrolY1 = MAX_VALUE;
        this.patrolY2 = MAX_VALUE;
    }

    public void goToPos(int xPos, int yPos) {
        getMap().getTile(this.xPos, this.yPos).removeUnit(this);
        this.xPos = xPos;
        this.yPos = yPos;

        getMap().getTile(xPos, yPos).addUnit(this);
        this.isMoved = true;
        if (getMap().getTile(xPos, yPos).getBuilding() != null && getMap().getTile(xPos, yPos).
                getBuilding().getBuildingName().getType().equals("gateHouse"))
            getMap().getTile(xPos, yPos).getBuilding().setEmpire(empire);
    }

    public int getXAttack() {
        return xAttack;
    }

    public int getYAttack() {
        return yAttack;
    }

    public void setXAttack(int xAttack) {
        this.xAttack = xAttack;
    }

    public void setYAttack(int yAttack) {
        this.yAttack = yAttack;
    }

    public void setPatrolXY(int patrolX1, int patrolY1, int patrolX2, int patrolY2) {
        this.patrolX1 = patrolX1;
        this.patrolY1 = patrolY1;
        this.patrolX2 = patrolX2;
        this.patrolY2 = patrolY2;
        this.xDestination = MAX_VALUE;
        this.yDestination = MAX_VALUE;
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
        this.patrolX2 = MAX_VALUE;
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

    public int getPatrolX1() {
        return patrolX1;
    }

    public int getPatrolY1() {
        return patrolY1;
    }

    public boolean isHaveFire() {
        return haveFire;
    }

    public void setMoved() {
        isMoved = true;
    }

    public void setNotMoved() {
        isMoved = false;
    }

    public boolean isMoved() {
        return isMoved;
    }

    public MilitaryUnitName getMilitaryUnitName() {
        return militaryUnitName;
    }

    public void setHaveFire(boolean haveFire) {
        this.haveFire = haveFire;
    }

    public void removeUnit() {
        this.getEmpire().removePeople(this);
        getMap().getTile(xPos, yPos).removeUnit(this);
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
