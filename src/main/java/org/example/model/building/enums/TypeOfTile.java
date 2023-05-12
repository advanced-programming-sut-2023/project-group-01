package org.example.model.building.enums;

public enum TypeOfTile {

    W_ROCK("wRock",false,false),
    E_ROCK("eRock",false,false),
    N_ROCK("nRock",false,false),
    S_ROCK("sRock",false,false),
    NORMAL_GROUND("normal", true, false),
    GRAVEL_GROUND("gravel", true, false),
    STONE_MINE("stoneMine",true, false),
    IRON_MINE("ironMine", true, false),
    GRASSLAND("grassland", true, false),
    MEADOW("meadow",true, false),
    FULL_MEADOW("fullMeadow",true, false),
    OIL_GROUND("oilGround",true, false),
    PLAIN("plain",true, false),
    SHALLOW_WATER("shallowWater",true, true),
    RIVER("river",false, true),
    SMALL_POND("smallPond",false, true),
    BIG_POND("bigPond",false, true),
    BEACH("beach",true, false),
    SEA("sea",false, true);
    private final boolean canCross;
    private final boolean isWater;
    private final String name;

    TypeOfTile(String name, boolean canCross, boolean isWater) {
        this.canCross = canCross;
        this.name = name;
        this.isWater = isWater;
    }

    public boolean getCanCross() {
        return canCross;
    }

    public boolean isWater() {
        return isWater;
    }

    public static TypeOfTile getTypeOfTileWithName(String name){
        for (int i = 0; i < TypeOfTile.values().length; i++)
            if(TypeOfTile.values()[i].name.equals(name))
                return TypeOfTile.values()[i];
        return null;
    }
}
