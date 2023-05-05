package org.example.model.unit.enums;

public enum Speed {
    SO_HIGH(10),
    HIGH(9),
    MIDDLE(8),
    LOW(7),
    VERY_LOW(6),

    WILD_SO_HIGH(16),
    WILD_HIGH(14),
    WILD_MIDDLE(12),
    WILD_LOW(10),
    WILD_VERY_LOW(8);
    private int speed;

    Speed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
