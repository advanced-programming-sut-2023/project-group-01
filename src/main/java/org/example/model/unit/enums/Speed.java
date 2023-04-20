package org.example.model.unit.enums;

public enum Speed {
    SO_HIGH(18),
    HIGH(16),
    MIDDLE(14),
    LOW(12),
    VERY_LOW(10);
    private int speed;

    Speed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
