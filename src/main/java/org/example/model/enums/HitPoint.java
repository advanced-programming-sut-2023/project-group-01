package org.example.model.enums;

public enum HitPoint {
    LORD(500),
    HIGH(300),
    MIDDLE(120),
    LOW(60),
    SO_LOW(40),
    NOTHING(20);

    private int hitPoint;

    HitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public int getHitPoint() {
        return hitPoint;
    }
}
