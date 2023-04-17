package org.example.model.unit.enums;

import org.example.model.enums.Color;
import org.example.model.enums.HitPoint;
import org.example.model.building.Material;

public enum MilitaryUnitName {
    ARCHER(Color.RED, GunShot.HIGH, MilitaryUnitType.EUROPEAN, "Archer", Attack.LOW, HitPoint.UNIT_LOW, Speed.HIGH, CostOfUnits.LOW, null, null, false),
    CROSSBOW_MEN(Color.RED, GunShot.MIDDLE, MilitaryUnitType.EUROPEAN, "Crossbowmen", Attack.LOW, HitPoint.UNIT_MIDDLE, Speed.LOW, CostOfUnits.LOW, null, null, false),
    SPEAR_MEN(Color.RED, GunShot.ZERO, MilitaryUnitType.EUROPEAN, "Spearmen", Attack.MIDDLE, HitPoint.UNIT_SO_LOW, Speed.MIDDLE, CostOfUnits.SO_LOW, null, null, false),
    PIKE_MEN(Color.RED, GunShot.ZERO, MilitaryUnitType.EUROPEAN, "Pikemen", Attack.MIDDLE, HitPoint.UNIT_HIGH, Speed.LOW, CostOfUnits.MIDDLE, null, null, false),
    MACE_MEN(Color.RED, GunShot.ZERO, MilitaryUnitType.EUROPEAN, "Macemen", Attack.HIGH, HitPoint.UNIT_MIDDLE, Speed.MIDDLE, CostOfUnits.MIDDLE, null, null, false),
    SWORDSMEN(Color.RED, GunShot.ZERO, MilitaryUnitType.EUROPEAN, "Swordsmen", Attack.SO_HIGH, HitPoint.UNIT_SO_LOW, Speed.VERY_LOW, CostOfUnits.SO_HIGH, null, null, false),
    KNIGHT(Color.RED, GunShot.ZERO, MilitaryUnitType.EUROPEAN, "Knight", Attack.SO_HIGH, HitPoint.UNIT_HIGH, Speed.SO_HIGH, CostOfUnits.SO_HIGH, null, null, true),
    TUNNELER(Color.RED, GunShot.ZERO, MilitaryUnitType.EUROPEAN, "Tunneler", Attack.MIDDLE, HitPoint.UNIT_SO_LOW, Speed.SO_HIGH, CostOfUnits.LOW, null, null, false),
    LADDER_MEN(Color.RED, GunShot.ZERO, MilitaryUnitType.EUROPEAN, "Laddermen", Attack.ZERO, HitPoint.UNIT_SO_LOW, Speed.SO_HIGH, CostOfUnits.SO_LOW, null, null, false),
    ENGINEER(Color.RED, GunShot.ZERO, MilitaryUnitType.EUROPEAN, "Engineer", Attack.ZERO, HitPoint.UNIT_SO_LOW, Speed.MIDDLE, CostOfUnits.MIDDLE, null, null, false),
    BLACK_MONK(Color.RED, GunShot.ZERO, MilitaryUnitType.EUROPEAN, "Black Monk", Attack.MIDDLE, HitPoint.UNIT_MIDDLE, Speed.LOW, CostOfUnits.LOW, null, null, false),
    //Bow Units :
    ARCHER_BOW(Color.RED, GunShot.HIGH, MilitaryUnitType.BOW, "Archer Bow", Attack.LOW, HitPoint.UNIT_LOW, Speed.HIGH, CostOfUnits.LOW, null, null, false),
    SLAVES(Color.RED, GunShot.ZERO, MilitaryUnitType.BOW, "Slaves", Attack.VERY_LOW, HitPoint.UNIT_NOTHING, Speed.HIGH, CostOfUnits.SO_LOW, null, null, false),
    SLINGERS(Color.RED, GunShot.LOW, MilitaryUnitType.BOW, "Slingers", Attack.LOW, HitPoint.UNIT_SO_LOW, Speed.HIGH, CostOfUnits.NOTHING, null, null, false),
    ASSASSINS(Color.RED, GunShot.ZERO, MilitaryUnitType.BOW, "Assassins", Attack.MIDDLE, HitPoint.UNIT_MIDDLE, Speed.MIDDLE, CostOfUnits.MIDDLE, null, null, false),
    HORSE_ARCHER(Color.RED, GunShot.HIGH, MilitaryUnitType.BOW, "Horse Archers", Attack.LOW, HitPoint.UNIT_MIDDLE, Speed.SO_HIGH, CostOfUnits.HIGH, null, null, true),
    ARABIAN_SWORSMEN(Color.RED, GunShot.ZERO, MilitaryUnitType.BOW, "Arabian Swordsmen", Attack.HIGH, HitPoint.UNIT_HIGH, Speed.SO_HIGH, CostOfUnits.HIGH, null, null, false),
    FIRE_THROWERS(Color.RED, GunShot.LOW, MilitaryUnitType.BOW, "Fire Throwers", Attack.HIGH, HitPoint.UNIT_LOW, Speed.SO_HIGH, CostOfUnits.HIGH, null, null, false);


    private final Color color;
    private int gunshot;
    private final MilitaryUnitType type;
    private final String name;
    //TODO: should be set
    private MilitaryUnitState state;
    private final int attack;
    private int hitPoint;
    private final int speed;
    private final int cost;
    private final Material armour;
    private final Material armament;
    private final boolean isHavingHorse;

    MilitaryUnitName(Color color, GunShot gunShot, MilitaryUnitType type, String name, Attack attack, HitPoint hitPoint,
                     Speed speed, CostOfUnits costOfUnits, Material armour, Material armament, boolean isHavingHorse) {
        this.color = color;
        this.gunshot = gunShot.getGunShot();
        this.type = type;
        this.name = name;
        this.attack = attack.getAttack();
        this.hitPoint = hitPoint.getHitPoint();
        this.speed = speed.getSpeed();
        this.cost = costOfUnits.getCost();
        this.armour = armour;
        this.armament = armament;
        this.isHavingHorse = isHavingHorse;
    }

    public void reduceHitPoint(int hitPoint) {
        this.hitPoint -= hitPoint;
    }

    public MilitaryUnitType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getCost() {
        return cost;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getGunshot() {
        return gunshot;
    }

    public Material getArmour() {
        return armour;
    }

    public Material getArmament() {
        return armament;
    }

    public MilitaryUnitState getState() {
        return state;
    }

    public Color getColor() {
        return color;
    }

    public void increaseGunshot(int gunshot) {
        this.gunshot += gunshot;
    }

    public void reduceGunshot(int gunshot) {
        this.gunshot -= gunshot;
    }


}
