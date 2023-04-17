package org.example.model.unit.enums;

public enum Attack {
    SO_HIGH(100),
    HIGH(75),
    MIDDLE(50),
    LOW(25),
    VERY_LOW(5),
    ZERO(0);
    private int attack;

    Attack(int attack) {
        this.attack = attack;
    }

    public int getAttack() {
        return attack;
    }
}
