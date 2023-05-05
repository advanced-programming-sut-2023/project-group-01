package org.example.model.building.castleBuilding.enums;

public enum TowerType {
    //TODO some checks
    LOOKOUT_TOWER("lookout tower", 10, 10, 15),
    ROUND_TOWER("round tower", 8, 8, 12),
    SQUARE_TOWER("square tower", 8, 8, 12),
    PERIMETER_TOWER("perimeter tower", 6, 6, 10),
    DEFENSE_TURRET("defence turret", 5, 5, 8),

    ;
    private final String name;
    private final int fireRang;
    private final int defendRange;
    private final int height;

    TowerType(String name, int fireRange, int defendRange, int height) {
        this.name = name;
        this.fireRang = fireRange;
        this.defendRange = defendRange;
        this.height = height;
    }

    public int getFireRang() {
        return fireRang;
    }

    public int getDefendRange() {
        return defendRange;
    }

    public int getHeight() {
        return height;
    }
}
