package org.example.model.building.enums;

public enum TypeOfTile {
    //TODO check this items
    NORMAL_GROUND(true),
    GRAVEL_GROUND(true),
    STONE_MINE(true),
    IRON_MINE(true),
    GRASSLAND(true),
    MEADOW(true),
    FULL_MEADOW(true),
    OIL_GROUND(true),
    PLAIN(true),
    SHALLOW_WATER(true),
    RIVER(false),
    SMALL_POND(false),
    BIG_POND(false),
    BEACH(true),
    SEA(false);
    private final boolean canCross;

    TypeOfTile(boolean canCross) {
        this.canCross = canCross;
    }

    public boolean getCanCross() {
        return canCross;
    }


}
