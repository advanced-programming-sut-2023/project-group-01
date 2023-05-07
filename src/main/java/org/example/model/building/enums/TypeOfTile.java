package org.example.model.building.enums;

public enum TypeOfTile {
    //TODO check this items
    W_ROCK("W rock",false,false),
    E_ROCK("E rock",false,false),
    N_ROCK("N rock",false,false),
    S_ROCK("S rock",false,false),
    NORMAL_GROUND("normal", true, false),
    GRAVEL_GROUND("gravel", true, false),
    STONE_MINE("stone mine",true, false),
    IRON_MINE("iron mine", true, false),
    GRASSLAND("grassland", true, false),
    MEADOW("meadow",true, false),
    FULL_MEADOW("full meadow",true, false),
    OIL_GROUND("oil ground",true, false),
    PLAIN("plain",true, false),
    SHALLOW_WATER("shallow water",true, true),
    RIVER("river",false, true),
    SMALL_POND("small pond",false, true),
    BIG_POND("big pond",false, true),
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
