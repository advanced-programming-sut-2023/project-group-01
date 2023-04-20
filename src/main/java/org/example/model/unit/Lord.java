package org.example.model.unit;

import org.example.Main;
import org.example.model.User;
import org.example.model.building.Tile;
import org.example.model.unit.enums.MilitaryUnitName;

public class Lord extends MilitaryUnit {
    private final int attack = 200;
    private int hitPoint = 500;

    public Lord(Tile position, User player, MilitaryUnitName militaryUnitName) {
        super(position, player, militaryUnitName);
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getAttack() {
        return attack;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }
}
