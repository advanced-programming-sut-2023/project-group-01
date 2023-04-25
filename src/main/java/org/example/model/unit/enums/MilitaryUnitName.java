package org.example.model.unit.enums;

import org.example.model.enums.Color;
import org.example.model.enums.HitPoint;
import org.example.model.building.Material;

public enum MilitaryUnitName {
    LORD(Color.RED, GunShot.ZERO, "lord", "lord", Attack.LORD_ATTACK, HitPoint.LORD, Speed.MIDDLE, 0, null, null, false),
    ARCHER(Color.RED, GunShot.HIGH, "european", "Archer", Attack.LOW, HitPoint.LOW, Speed.HIGH, 12, null, null, false),
    CROSSBOW_MEN(Color.RED, GunShot.MIDDLE, "european", "Crossbowmen", Attack.LOW, HitPoint.MIDDLE, Speed.LOW, 20, null, null, false),
    SPEAR_MEN(Color.RED, GunShot.ZERO, "european", "Spearmen", Attack.MIDDLE, HitPoint.SO_LOW, Speed.MIDDLE, 8, null, null, false),
    PIKE_MEN(Color.RED, GunShot.ZERO, "european", "Pikemen", Attack.MIDDLE, HitPoint.HIGH, Speed.LOW, 20, null, null, false),
    MACE_MEN(Color.RED, GunShot.ZERO, "european", "Macemen", Attack.HIGH, HitPoint.MIDDLE, Speed.MIDDLE, 20, null, null, false),
    SWORDSMEN(Color.RED, GunShot.ZERO, "european", "Swordsmen", Attack.SO_HIGH, HitPoint.SO_LOW, Speed.VERY_LOW, 40, null, null, false),
    KNIGHT(Color.RED, GunShot.ZERO, "european", "Knight", Attack.SO_HIGH, HitPoint.HIGH, Speed.SO_HIGH, 40, null, null, true),
    TUNNELER(Color.RED, GunShot.ZERO, "european", "Tunneler", Attack.MIDDLE, HitPoint.SO_LOW, Speed.SO_HIGH, 30, null, null, false),
    LADDER_MEN(Color.RED, GunShot.ZERO, "european", "Laddermen", Attack.ZERO, HitPoint.SO_LOW, Speed.SO_HIGH, 4, null, null, false),
    ENGINEER(Color.RED, GunShot.ZERO, "european", "Engineer", Attack.ZERO, HitPoint.SO_LOW, Speed.MIDDLE, 30, null, null, false),
    BLACK_MONK(Color.RED, GunShot.ZERO, "european", "Black Monk", Attack.MIDDLE, HitPoint.MIDDLE, Speed.LOW, 10, null, null, false),
    //Bow Units :
    ARCHER_BOW(Color.RED, GunShot.HIGH, "bow", "Archer Bow", Attack.LOW, HitPoint.LOW, Speed.HIGH, 75, null, null, false),
    SLAVES(Color.RED, GunShot.ZERO, "bow", "Slaves", Attack.VERY_LOW, HitPoint.NOTHING, Speed.HIGH, 5, null, null, false),
    SLINGERS(Color.RED, GunShot.LOW, "bow", "Slingers", Attack.LOW, HitPoint.SO_LOW, Speed.HIGH, 12, null, null, false),
    ASSASSINS(Color.RED, GunShot.ZERO, "bow", "Assassins", Attack.MIDDLE, HitPoint.MIDDLE, Speed.MIDDLE, 60, null, null, false),
    HORSE_ARCHER(Color.RED, GunShot.HIGH, "bow", "Horse Archers", Attack.LOW, HitPoint.MIDDLE, Speed.SO_HIGH, 80, null, null, true),
    ARABIAN_SWORSMEN(Color.RED, GunShot.ZERO, "bow", "Arabian Swordsmen", Attack.HIGH, HitPoint.HIGH, Speed.SO_HIGH, 80, null, null, false),
    FIRE_THROWERS(Color.RED, GunShot.LOW, "bow", "Fire Throwers", Attack.HIGH, HitPoint.LOW, Speed.SO_HIGH, 100, null, null, false);


    private final Color color;
    private int gunshot;
    private final String type;
    private final String name;
    private MilitaryUnitState state;
    private final int attack;
    private int hitPoint;
    private final int speed;
    private final int cost;
    private final Material armour;
    private final Material armament;
    private final boolean isHavingHorse;

    MilitaryUnitName(Color color, GunShot gunShot, String type, String name, Attack attack, HitPoint hitPoint,
                     Speed speed, int price, Material armour, Material armament, boolean isHavingHorse) {
        this.state = MilitaryUnitState.STANDING;
        this.color = color;
        this.gunshot = gunShot.getGunShot();
        this.type = type;
        this.name = name;
        this.attack = attack.getAttack();
        this.hitPoint = hitPoint.getHitPoint();
        this.speed = speed.getSpeed();
        this.cost = price;
        this.armour = armour;
        this.armament = armament;
        this.isHavingHorse = isHavingHorse;
    }

    public void setState(MilitaryUnitState state) {
        this.state = state;
    }

    public void reduceHitPoint(int hitPoint) {
        this.hitPoint -= hitPoint;
    }

    public void increaseGunshot(int gunshot) {
        this.gunshot += gunshot;
    }

    public void reduceGunshot(int gunshot) {
        this.gunshot -= gunshot;
    }

    public String getType() {
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

}
