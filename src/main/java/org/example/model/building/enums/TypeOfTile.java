package org.example.model.building.enums;

import org.example.Main;

public enum TypeOfTile {

    W_ROCK("wRock",false,false, ""),
    E_ROCK("eRock",false,false, ""),
    N_ROCK("nRock",false,false, ""),
    S_ROCK("sRock",false,false, ""),
    NORMAL_GROUND("normal", true, false, Main.class.getResource("/Images/tiles/normalGround2.png").toString()),
    GRAVEL_GROUND("gravel", true, false, Main.class.getResource("/Images/tiles/zaminbasangrize.png").toString()),
    STONE_MINE("stoneMine",true, false, Main.class.getResource("/Images/tiles/madansang2.png").toString()),
    IRON_MINE("ironMine", true, false, Main.class.getResource("/Images/tiles/madanahan3.png").toString()),
    GRASSLAND("grassland", true, false,
            Main.class.getResource("/Images/tiles/grass_tile.jpg").toString()),
    MEADOW("meadow",true, false, Main.class.getResource("/Images/tiles/alafzarkamtarakom2.png").toString()),
    FULL_MEADOW("fullMeadow",true, false, Main.class.getResource("/Images/tiles/alafzarportarakom.png").toString()),
    OIL_GROUND("oilGround",true, false,  Main.class.getResource("/Images/tiles/madanghir.png").toString()),
    PLAIN("plain",true, false,  Main.class.getResource("/Images/tiles/chamanzar.png").toString()),
    SHALLOW_WATER("shallowWater",true, true, Main.class.getResource("/Images/tiles/abkamomgh.png").toString()),
    RIVER("river",false, true,  Main.class.getResource("/Images/tiles/rood.png").toString()),
    SMALL_POND("smallPond",false, true,  Main.class.getResource("/Images/tiles/abkamomgh.png").toString()),
    BIG_POND("bigPond",false, true,  Main.class.getResource("/Images/tiles/abporomgh.png").toString()),
    BEACH("beach",true, false, Main.class.getResource("/Images/tiles/mase.png").toString()),
    SEA("sea",false, true,  Main.class.getResource("/Images/tiles/sea_tile.jpg").toString());
    private final boolean canCross;
    private final boolean isWater;
    private final String name;
    private final String pictureAddress;

    TypeOfTile(String name, boolean canCross, boolean isWater, String pictureAddress) {
        this.canCross = canCross;
        this.name = name;
        this.isWater = isWater;
        this.pictureAddress = pictureAddress;
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

    public String getName() {
        return name;
    }

    public String getPictureAddress() {
        return pictureAddress;
    }
}
