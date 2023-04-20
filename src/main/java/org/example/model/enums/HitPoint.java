package org.example.model.enums;

public enum HitPoint {
    UNIT_HIGH(300),
    UNIT_MIDDLE(120),
    UNIT_LOW(60),
    UNIT_SO_LOW(40),
    UNIT_NOTHING(20);

    private int hitPoint;

    HitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public int getHitPoint() {
        return hitPoint;
    }
}
