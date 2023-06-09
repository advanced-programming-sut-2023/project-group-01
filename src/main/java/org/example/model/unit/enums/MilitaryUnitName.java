package org.example.model.unit.enums;

import org.example.Main;
import org.example.model.Voice;
import org.example.model.building.enums.MaterialType;
import org.example.model.enums.Color;
import org.example.model.enums.HitPoint;
import org.example.model.building.Material;
import org.example.model.unit.MilitaryUnit;

import static org.example.model.building.enums.MaterialType.*;

public enum MilitaryUnitName {
    LORD(Color.RED, GunShot.ZERO, "lord", "lord", Attack.LORD_ATTACK, HitPoint.LORD, Speed.MIDDLE, 0, null, null, false, null,
            Main.class.getResource("/Images/GameTabs/Barrack/Picture1.png").toString()),
    ARCHER(Color.RED, GunShot.HIGH, "european", "archer", Attack.LOW, HitPoint.LOW, Speed.HIGH, 12, null, ARC, false, Voice.ARCHER,
            Main.class.getResource("/Images/GameTabs/Barrack/armys1.tgx1.png").toString()),
    CROSSBOW_MEN(Color.RED, GunShot.MIDDLE, "european", "crossbowmen", Attack.LOW, HitPoint.MIDDLE, Speed.LOW,
            20, LEATHER_ARMOUR, CROSSBOW, false, Voice.CROSSBOW_MEN,
            Main.class.getResource("/Images/GameTabs/Barrack/armys4.tgx.png").toString()),
    SPEAR_MEN(Color.RED, GunShot.ZERO, "european", "spearmen", Attack.MIDDLE, HitPoint.SO_LOW, Speed.MIDDLE,
            8, null, SPEAR, false, Voice.SPEAR_MEN,
            Main.class.getResource("/Images/GameTabs/Barrack/armys2.tgx.png").toString()),
    PIKE_MEN(Color.RED, GunShot.ZERO, "european", "pikemen", Attack.MIDDLE, HitPoint.HIGH,
            Speed.LOW, 20, METAL_ARMOUR, PIKE, false, Voice.PIKE_MEN,
            Main.class.getResource("/Images/GameTabs/Barrack/armys5.tgx.png").toString()),
    MACE_MEN(Color.RED, GunShot.ZERO, "european", "macemen", Attack.HIGH, HitPoint.MIDDLE, Speed.MIDDLE,
            20, LEATHER_ARMOUR, MACE, false, Voice.MACE_MEN,
            Main.class.getResource("/Images/GameTabs/Barrack/armys3.tgx.png").toString()),
    SWORDSMEN(Color.RED, GunShot.ZERO, "european", "swordsmen", Attack.SO_HIGH, HitPoint.SO_LOW,
            Speed.VERY_LOW, 40, METAL_ARMOUR, SWORD, false, Voice.SWORDSMEN,
            Main.class.getResource("/Images/GameTabs/Barrack/armys6.tgx.png").toString()),
    KNIGHT(Color.RED, GunShot.ZERO, "european", "knight", Attack.SO_HIGH, HitPoint.HIGH,
            Speed.SO_HIGH, 40, METAL_ARMOUR, SWORD, true, Voice.KNIGHT,
            Main.class.getResource("/Images/GameTabs/Barrack/armys7.tgx.png").toString()),
    //TODO set tunneler
    TUNNELER(Color.RED, GunShot.ZERO, "european", "tunneler", Attack.MIDDLE, HitPoint.SO_LOW,
            Speed.SO_HIGH, 30, null, null, false, Voice.TUNNELER,
            ""),
    LADDER_MEN(Color.RED, GunShot.ZERO, "european", "laddermen", Attack.ZERO, HitPoint.SO_LOW, Speed.SO_HIGH, 4, null, null, false, Voice.LADDER_MEN, "pictureAddress"),
    ENGINEER(Color.RED, GunShot.ZERO, "european", "engineer", Attack.ZERO, HitPoint.SO_LOW, Speed.MIDDLE, 30, null, null, false, Voice.ENGINEER, "pictureAddress"),
    BLACK_MONK(Color.RED, GunShot.ZERO, "european", "blackMonk", Attack.MIDDLE, HitPoint.MIDDLE, Speed.LOW, 10, null, null, false, Voice.BLACK_MONK, "pictureAddress"),
    //Bow Units :

    ARCHER_BOW(Color.RED, GunShot.HIGH, "bow", "archerBow", Attack.LOW, HitPoint.LOW, Speed.HIGH,
            75, null, null, false, Voice.ARCHER_BOW,
            Main.class.getResource("/Images/GameTabs/MercenaryBarrack/armys19.tgx.png").toString()),
    SLAVES(Color.RED, GunShot.ZERO, "bow", "slaves", Attack.VERY_LOW, HitPoint.NOTHING, Speed.HIGH,
            5, null, null, false, Voice.SLAVES,
            Main.class.getResource("/Images/GameTabs/MercenaryBarrack/armys20.tgx.png").toString()),

    SLINGERS(Color.RED, GunShot.LOW, "bow", "slingers", Attack.LOW, HitPoint.SO_LOW,
            Speed.HIGH, 12, null, null, false, Voice.SLINGERS,
            Main.class.getResource("/Images/GameTabs/MercenaryBarrack/armys21.tgx.png").toString()),
    ASSASSINS(Color.RED, GunShot.ZERO, "bow", "assassins", Attack.MIDDLE,
            HitPoint.MIDDLE, Speed.MIDDLE, 60, null, null, false, Voice.ASSASSINS,
            Main.class.getResource("/Images/GameTabs/MercenaryBarrack/assassins.png").toString()),
    HORSE_ARCHER(Color.RED, GunShot.HIGH, "bow", "horseArchers", Attack.LOW, HitPoint.MIDDLE,
            Speed.SO_HIGH, 80, null, null, true, Voice.HORSE_ARCHER,
            Main.class.getResource("/Images/GameTabs/MercenaryBarrack/armys23.tgx.png").toString()),
    ARABIAN_SWORSMEN(Color.RED, GunShot.ZERO, "bow", "arabianSwordsmen", Attack.HIGH, HitPoint.HIGH,
            Speed.SO_HIGH, 80, null, null, false, Voice.ARABIAN_SWORSMEN,
            Main.class.getResource("/Images/GameTabs/MercenaryBarrack/swordsman.png").toString()),

    FIRE_THROWERS(Color.RED, GunShot.LOW, "bow", "fireThrowers", Attack.HIGH, HitPoint.LOW,
            Speed.SO_HIGH, 100, null, null, false, Voice.FIRE_THROWERS,
            Main.class.getResource("/Images/GameTabs/MercenaryBarrack/armys25.tgx.png").toString());


    private final Color color;
    private final int maxAttack;
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
    private final String pictureAddress;


    MilitaryUnitName(Color color, GunShot gunShot, String type, String name, Attack attack, HitPoint hitPoint,
                     Speed speed, int price, MaterialType armour, MaterialType armament, boolean isHavingHorse, Voice voice, String pictureAddress) {
        this.pictureAddress = pictureAddress;
        this.state = MilitaryUnitState.STANDING;
        this.color = color;
        this.gunshot = gunShot.getGunShot();
        this.type = type;
        this.name = name;
        this.attack = attack.getAttack();
        this.maxAttack = attack.getAttack();
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

    public int getMaxAttack() {
        return maxAttack;
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

    public String getPictureAddress() {
        return pictureAddress;
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

    public static MilitaryUnitName getMilitaryUnitWhitName(String name){
        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values())
            if(militaryUnitName.getName().equals(name))
                return militaryUnitName;
        return null;
    }

    public static MilitaryUnitName getUnitByPictureAddress(String address) {
        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values())
            if (militaryUnitName.getPictureAddress().equals(address))
                return militaryUnitName;
        return null;
    }
}
