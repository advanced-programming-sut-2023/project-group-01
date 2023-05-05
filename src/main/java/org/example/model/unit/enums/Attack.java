package org.example.model.unit.enums;

public enum Attack {
    LORD_ATTACK(200),
    SO_HIGH(100),
    HIGH(75),
    MIDDLE(50),
    LOW(25),
    VERY_LOW(5),
    ZERO(0),
    WILD_SO_HIGH_ATTACK(150),
    WILD_HIGH_ATTACK(110),
    WILD_MIDDLE_ATTACK(80),
    WILD_LOW_ATTACK(40),
    WILD_VREY_LOW_ATTACK(10);
    private int attack;

    Attack(int attack) {
        this.attack = attack;
    }

    public int getAttack() {
        return attack;
    }
}
