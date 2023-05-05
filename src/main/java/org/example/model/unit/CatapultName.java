package org.example.model.unit;

import org.example.model.Empire;

import java.util.ArrayList;

public enum CatapultName {
    CARAPULT(150, "carapult", 50, 2, 400, 0, false, true),
    TREBUCHER(150, "trebucher", 100, 3, 600, 0, false, false),
    SIEGE_TOWER(300, "siege tower", 0, 4, 0, 15, false, true),
    BATTERNING_RAM(500, "batterning ram", 0, 4, 600, 0, false, true),
    PORTABLE_SHIELD(50, "portable shield", 0, 1, 0, 0, false, true),
    FIRE_BALLISTRA(150, "fire ballistra", 50, 2, 400, 0, true, true);
    private int hitPoint;
    private final String name;
    private final int fireRange;
    private final int numberOfEngineers;
    private final int damage;
    private final int capacity;
    private final boolean canAttackUnit;
    private final boolean canMove;
    private ArrayList<Engineer> engineers = new ArrayList<Engineer>();

    CatapultName(int hitPoint, String name, int fireRange, int numberOfEngineers, int damage, int capacity, boolean canAttackUnit, boolean canMove) {
        this.hitPoint = hitPoint;
        this.name = name;
        this.fireRange = fireRange;
        this.numberOfEngineers = numberOfEngineers;
        this.damage = damage;
        this.capacity = capacity;
        this.canAttackUnit = canAttackUnit;
        this.canMove = canMove;
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

    public void reduceHitPoint(int hitPoint) {
        this.hitPoint -= hitPoint;
    }

//    public Catapult getCatapultByName(String name) {
//        for (CatapultName catapultName : CatapultName.values()) {
//            if (catapultName.getName().equals(name)) {
//                return new Catapult(catapultName);
//            }
//        }
//        return null;
//    }
}
