package org.example.model.enums;

public enum Range {
    FIRE_RANGE_SO_HIGH(8),
    FIRE_RANGE_HIGH(6),
    FIRE_RANGE_MIDDLE(4),
    FIRE_RANGE_LOW(2),
    FIRE_RANGE_SO_LOW(1),
    //DefendRange
    DEFEND_RANGE_SO_HIGH(7),
    DEFEND_RANGE_HIGH(5),
    DEFEND_RANGE_MIDDLE(3),
    DEFEND_RANGE_LOW(2),
    DEFEND_RANGE_SO_LOW(1);

    private final int range;

    Range(int range) {
        this.range = range;
    }

    public int getRange() {
        return range;
    }
}
