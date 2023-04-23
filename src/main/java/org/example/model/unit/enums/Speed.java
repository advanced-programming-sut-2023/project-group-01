package org.example.model.unit.enums;

public enum Speed {
    SO_HIGH(10),
    HIGH(9),
    MIDDLE(8),
    LOW(7),
    VERY_LOW(6);
    private int speed;

    Speed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
