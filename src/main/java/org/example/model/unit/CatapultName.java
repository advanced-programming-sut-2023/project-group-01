package org.example.model.unit;

import org.example.model.Empire;
import org.example.model.unit.enums.Speed;

import java.util.ArrayList;

public enum CatapultName {
    CARAPULT(150, "carapult", 30, 2, 400, 0, false, true, Speed.LOW.getSpeed()),
    TREBUCHER(150, "trebucher", 50, 3, 600, 0, false, false, 0),
    SIEGE_TOWER(300, "siegeTower", 0, 4, 0, 15, false, true, Speed.LOW.getSpeed()),
    BATTERNING_RAM(500, "batterningRam", 0, 4, 600, 0, false, true, Speed.LOW.getSpeed()),
    PORTABLE_SHIELD(50, "portableShield", 0, 1, 0, 0, false, true, Speed.LOW.getSpeed()),
    FIRE_BALLISTRA(150, "fireBallistra", 30, 2, 400, 0, true, true, Speed.LOW.getSpeed());
    private int hitPoint;
    private final int speed;
    private final String name;
    private final int fireRange;
    private final int numberOfEngineers;
    private final int damage;
    private final int capacity;
    private final boolean canAttackUnit;
    private final boolean canMove;
    private ArrayList<Engineer> engineers = new ArrayList<Engineer>();

    CatapultName(int hitPoint, String name, int fireRange, int numberOfEngineers, int damage, int capacity,
                 boolean canAttackUnit, boolean canMove, int speed) {
        this.hitPoint = hitPoint;
        this.name = name;
        this.fireRange = fireRange;
        this.numberOfEngineers = numberOfEngineers;
        this.damage = damage;
        this.capacity = capacity;
        this.canAttackUnit = canAttackUnit;
        this.canMove = canMove;
        this.speed = speed;
    }

    public void addEngineer(Engineer engineer) {
        this.engineers.add(engineer);
    }
    public int getHitPoint() {
        return hitPoint;
    }

    public int getFireRange() {
        return fireRange;
    }

    public int getNumberOfEngineers() {
        return numberOfEngineers;
    }

    public int getDamage() {
        return damage;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean getCanAttackUnit() {
        return canAttackUnit;
    }

    public boolean getCanMove() {
        return canMove;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public void reduceHitPoint(int hitPoint) {
        this.hitPoint -= hitPoint;
    }

}
