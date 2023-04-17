package org.example.model.unit.enums;

public enum Speed {
    SO_HIGH(30),
    HIGH(25),
    MIDDLE(20),
    LOW(15),
    VERY_LOW(10);
    private int speed;

    Speed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
