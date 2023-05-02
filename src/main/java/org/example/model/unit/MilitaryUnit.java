package org.example.model.unit;

import org.example.Best;
import org.example.model.Empire;
import org.example.model.People;
import org.example.model.User;
import org.example.model.building.Tile;
import org.example.model.unit.enums.MilitaryUnitName;

import java.util.LinkedList;

public class MilitaryUnit extends People {

    protected MilitaryUnitName militaryUnitName;
    //TODO اگه پاترول ها یکسان بودن جابه جا نشه
    protected LinkedList<Integer> patrol = null;
    protected  LinkedList<Integer> destination = null;
    protected int patrolX1;
    protected int patrolY1;
    protected int patrolX2;
    protected int patrolY2;

    public MilitaryUnit(Tile position, Empire empire, MilitaryUnitName militaryUnitName) {
        super(position, empire);
        this.militaryUnitName = militaryUnitName;
    }

    public void setPatrolXY(int patrolX1, int patrolY1, int patrolX2, int patrolY2) {
        this.patrolX1 = patrolX1;
        this.patrolY1 = patrolY1;
        this.patrolX2 = patrolX2;
        this.patrolY2 = patrolY2;
    }

    public LinkedList<Integer> getPatrol() {
        return patrol;
    }

    public MilitaryUnitName getMilitaryUnitName() {
        return militaryUnitName;
    }
}
