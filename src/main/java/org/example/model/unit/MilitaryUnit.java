package org.example.model.unit;

import org.example.model.People;
import org.example.model.User;
import org.example.model.building.Tile;
import org.example.model.unit.enums.MilitaryUnitName;

public class MilitaryUnit extends People {

    protected MilitaryUnitName militaryUnitName;
    protected int patrolX = 1000;
    protected int patrolY = 1000;
    public MilitaryUnit(Tile position, User player, MilitaryUnitName militaryUnitName) {
        super(position, player);
        this.militaryUnitName = militaryUnitName;
    }

    public void setPatrolXY(int patrolX, int patrolY) {
        this.patrolX = patrolX;
        this.patrolY = patrolY;
    }

    public MilitaryUnitName getMilitaryUnitName() {
        return militaryUnitName;
    }
}
