package org.example.model.building.enums;

public enum TypeOfTile {
    //TODO check this items
    W_ROCK(false,false),
    E_ROCK(false,false),
    N_ROCK(false,false),
    S_ROCK(false,false),
    NORMAL_GROUND(true, false),
    GRAVEL_GROUND(true, false),
    STONE_MINE(true, false),
    IRON_MINE(true, false),
    GRASSLAND(true, false),
    MEADOW(true, false),
    FULL_MEADOW(true, false),
    OIL_GROUND(true, false),
    PLAIN(true, false),
    SHALLOW_WATER(true, true),
    RIVER(false, true),
    SMALL_POND(false, true),
    BIG_POND(false, true),
    BEACH(true, false),
    SEA(false, true);
    private final boolean canCross;
    private final boolean isWater;

    TypeOfTile(boolean canCross, boolean isWater) {
        this.canCross = canCross;
        this.isWater = isWater;
    }

    public boolean getCanCross() {
        return canCross;
    }

    public boolean isWater() {
        return isWater;
    }
}
