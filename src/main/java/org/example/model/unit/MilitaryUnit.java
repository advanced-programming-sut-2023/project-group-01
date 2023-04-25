package org.example.model.unit;

import org.example.model.Empire;
import org.example.model.People;
import org.example.model.User;
import org.example.model.building.Tile;
import org.example.model.unit.enums.MilitaryUnitName;

public class MilitaryUnit extends People {

    protected MilitaryUnitName militaryUnitName;
    //TODO اگه پاترول ها یکسان بودن جابه جا نشه
    protected int patrolX1;
    protected int patrolY1;
    protected int patrolX2;
    protected int patrolY2;
    public MilitaryUnit(Tile position, User player, MilitaryUnitName militaryUnitName) {
        super(position, player);
        this.militaryUnitName = militaryUnitName;
    }

    public void setPatrolXY(int patrolX1, int patrolY1, int patrolX2, int patrolY2) {
        this.patrolX1 = patrolX1;
        this.patrolY1 = patrolY1;
        this.patrolX2 = patrolX2;
        this.patrolY2 = patrolY2;
    }

    public MilitaryUnitName getMilitaryUnitName() {
        return militaryUnitName;
    }
}
