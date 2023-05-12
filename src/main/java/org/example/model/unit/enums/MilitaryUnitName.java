package org.example.model.unit.enums;

import org.example.model.Voice;
import org.example.model.building.enums.MaterialType;
import org.example.model.enums.Color;
import org.example.model.enums.HitPoint;
import org.example.model.building.Material;
import org.example.model.unit.MilitaryUnit;

import static org.example.model.building.enums.MaterialType.*;

public enum MilitaryUnitName {
    LORD(Color.RED, GunShot.ZERO, "lord", "lord", Attack.LORD_ATTACK, HitPoint.LORD, Speed.MIDDLE, 0, null, null, false, null),
    ARCHER(Color.RED, GunShot.HIGH, "european", "archer", Attack.LOW, HitPoint.LOW, Speed.HIGH, 12, null, ARC, false, Voice.ARCHER),
    CROSSBOW_MEN(Color.RED, GunShot.MIDDLE, "european", "crossbowmen", Attack.LOW, HitPoint.MIDDLE, Speed.LOW, 20, LEATHER_ARMOUR, CROSSBOW, false, Voice.CROSSBOW_MEN),
    SPEAR_MEN(Color.RED, GunShot.ZERO, "european", "spearmen", Attack.MIDDLE, HitPoint.SO_LOW, Speed.MIDDLE, 8, null, SPEAR, false, Voice.SPEAR_MEN),
    PIKE_MEN(Color.RED, GunShot.ZERO, "european", "pikemen", Attack.MIDDLE, HitPoint.HIGH, Speed.LOW, 20, METAL_ARMOUR, PIKE, false, Voice.PIKE_MEN),
    MACE_MEN(Color.RED, GunShot.ZERO, "european", "macemen", Attack.HIGH, HitPoint.MIDDLE, Speed.MIDDLE, 20, LEATHER_ARMOUR, MACE, false, Voice.MACE_MEN),
    SWORDSMEN(Color.RED, GunShot.ZERO, "european", "swordsmen", Attack.SO_HIGH, HitPoint.SO_LOW, Speed.VERY_LOW, 40, METAL_ARMOUR, SWORD, false, Voice.SWORDSMEN),
    KNIGHT(Color.RED, GunShot.ZERO, "european", "knight", Attack.SO_HIGH, HitPoint.HIGH, Speed.SO_HIGH, 40, METAL_ARMOUR, SWORD, true, Voice.KNIGHT),
    TUNNELER(Color.RED, GunShot.ZERO, "european", "tunneler", Attack.MIDDLE, HitPoint.SO_LOW, Speed.SO_HIGH, 30, null, null, false, Voice.TUNNELER),
    LADDER_MEN(Color.RED, GunShot.ZERO, "european", "laddermen", Attack.ZERO, HitPoint.SO_LOW, Speed.SO_HIGH, 4, null, null, false, Voice.LADDER_MEN),
    ENGINEER(Color.RED, GunShot.ZERO, "european", "engineer", Attack.ZERO, HitPoint.SO_LOW, Speed.MIDDLE, 30, null, null, false, Voice.ENGINEER),
    BLACK_MONK(Color.RED, GunShot.ZERO, "european", "blackMonk", Attack.MIDDLE, HitPoint.MIDDLE, Speed.LOW, 10, null, null, false, Voice.BLACK_MONK),
    //Bow Units :
    ARCHER_BOW(Color.RED, GunShot.HIGH, "bow", "archerBow", Attack.LOW, HitPoint.LOW, Speed.HIGH, 75, null, null, false, Voice.ARCHER_BOW),
    SLAVES(Color.RED, GunShot.ZERO, "bow", "slaves", Attack.VERY_LOW, HitPoint.NOTHING, Speed.HIGH, 5, null, null, false, Voice.SLAVES),
    SLINGERS(Color.RED, GunShot.LOW, "bow", "slingers", Attack.LOW, HitPoint.SO_LOW, Speed.HIGH, 12, null, null, false, Voice.SLINGERS),
    ASSASSINS(Color.RED, GunShot.ZERO, "bow", "assassins", Attack.MIDDLE, HitPoint.MIDDLE, Speed.MIDDLE, 60, null, null, false, Voice.ASSASSINS),
    HORSE_ARCHER(Color.RED, GunShot.HIGH, "bow", "horseArchers", Attack.LOW, HitPoint.MIDDLE, Speed.SO_HIGH, 80, null, null, true, Voice.HORSE_ARCHER),
    ARABIAN_SWORSMEN(Color.RED, GunShot.ZERO, "bow", "arabianSwordsmen", Attack.HIGH, HitPoint.HIGH, Speed.SO_HIGH, 80, null, null, false, Voice.ARABIAN_SWORSMEN),
    FIRE_THROWERS(Color.RED, GunShot.LOW, "bow", "fireThrowers", Attack.HIGH, HitPoint.LOW, Speed.SO_HIGH, 100, null, null, false, Voice.FIRE_THROWERS);


    private final Color color;
    private int gunshot;
    private final String type;
    private final String name;
    private MilitaryUnitState state;
    private int attack;
    private int hitPoint;
    private final int speed;
    private final int cost;
    private final MaterialType armour;
    private final MaterialType armament;
    private final boolean isHavingHorse;
    private final Voice voice;

    MilitaryUnitName(Color color, GunShot gunShot, String type, String name, Attack attack, HitPoint hitPoint,
                     Speed speed, int price, MaterialType armour, MaterialType armament, boolean isHavingHorse, Voice voice) {
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
        this.voice = voice;
    }

    public void setState(MilitaryUnitState state) {
        this.state = state;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public void setGunshot(int gunshot) {
        this.gunshot = gunshot;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void reduceHitPoint(int hitPoint) {
        this.hitPoint -= hitPoint;
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

    public MaterialType getArmament() {
        return armament;
    }

    public MaterialType getArmour() {
        return armour;
    }

    public MilitaryUnitState getState() {
        return state;
    }

    public Color getColor() {
        return color;
    }

    public Voice getVoice() {
        return voice;
    }

//    public MilitaryUnit getBarrackMilitaryUnitByName(String name, Empire empire) {
//        // if (name.equals())
//        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values()) {
//            if (militaryUnitName.getName().equals(name)) {
//                return new MilitaryUnit(null, empire, militaryUnitName, );
//            }
//        }
//        return null;
//    }
    public static MilitaryUnitName getMilitaryUnitWhitName(String name){
        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values())
            if(militaryUnitName.getName().equals(name))
                return militaryUnitName;
        return null;
    }


}
