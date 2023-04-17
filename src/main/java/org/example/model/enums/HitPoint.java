package org.example.model.enums;

public enum HitPoint {
    UNIT_HIGH(300),
    UNIT_MIDDLE(120),
    UNIT_LOW(60),
    UNIT_SO_LOW(40),
    UNIT_NOTHING(20),
    //Model.building.Building HitPoint :
    BUILDING_HIGH(1500),
    BUILDING_MIDDLE(1000),
    BUILDING_LOW(500),
    BUILDING_NOTHING(100);
    private int hitPoint;

    HitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public int getHitPoint() {
        return hitPoint;
    }
}
