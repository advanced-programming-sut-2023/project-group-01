package org.example.model.building.enums;

import javafx.scene.image.Image;
import org.example.Main;
import org.example.model.Empire;
import org.example.model.building.Building;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;

public enum BuildingName {
    EMPIRE_CASTLE("empireBuilding", Integer.MAX_VALUE, Integer.MAX_VALUE, BuildingCategory.CASTLE_BUILDING, "EmpireBuilding", 0, 0, 0, 0, 1, TypeOfTile.NORMAL_GROUND, Main.class.getResource("/Images/tower/castle2.png").toString()),
    SMALL_STONE_GATEHOUSE("gateHouse", 1000, 1000, BuildingCategory.CASTLE_BUILDING, "SmallStoneGatehouse", 0, 10, 0, 0, 3, TypeOfTile.NORMAL_GROUND,  Main.class.getResource("/Images/carehouse/smallgatehouse2.png").toString()),
    BIG_STONE_GATEHOUSE("gateHouse", 2000, 2000, BuildingCategory.CASTLE_BUILDING, "BigStoneGatehouse", 0, 20, 0, 0, 3, TypeOfTile.NORMAL_GROUND,  Main.class.getResource("/Images/carehouse/biggatehouse2.png").toString()),
    DRAWBRIDGE("gateHouse", 0, 0, BuildingCategory.CASTLE_BUILDING, "Drawbridge", 0, 0, 10, 0, 3, TypeOfTile.NORMAL_GROUND,  Main.class.getResource("/Images/carehouse/drawbridge2.png").toString()),
    LOOKOUT_TOWER("tower", 250, 250, BuildingCategory.CASTLE_BUILDING, "LookoutTower", 0, 10, 0, 0, 2, TypeOfTile.NORMAL_GROUND,  Main.class.getResource("/Images/tower/lookouttower2.png").toString()),
    PERIMETER_TURRET("tower", 1000, 1000, BuildingCategory.CASTLE_BUILDING, "PerimeterTurret", 0, 10, 0, 0, 3, TypeOfTile.NORMAL_GROUND,  Main.class.getResource("/Images/tower/perimeterturret2.png").toString()),
    DEFENSE_TURRET("tower", 1200, 1200, BuildingCategory.CASTLE_BUILDING, "DefenceTurret",
            0, 15, 0, 0, 3, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/tower/defenseturret2.png").toString()),
    ROUND_TOWER("tower", 2000, 2000, BuildingCategory.CASTLE_BUILDING, "RoundTower", 0,
            40, 0, 0, 3, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/tower/roundtower2.png").toString()),
    SQUARE_TOWER("tower", 1600, 1600, BuildingCategory.CASTLE_BUILDING, "SquareTower", 0,
            35, 0, 0, 3, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/tower/squaretower2.png").toString()),
    WALL("wall", 700, 700, BuildingCategory.CASTLE_BUILDING, "Wall", 0, 1,
            0, 0, 1, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/castleBuilding/wall2.png").toString()),
    STAIRS("building", 400, 400, BuildingCategory.CASTLE_BUILDING, "Stairs", 0,
            4, 0, 0, 1, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/castleBuilding/stairs2.png").toString()),
    ARMOURY("storage", 280, 280, BuildingCategory.CASTLE_BUILDING, "Armoury", 0,
            0, 5, 0, 2, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/castleBuilding/armoury2.png").toString()),
    BARRACK("building", 350, 350, BuildingCategory.CASTLE_BUILDING, "Barrack", 0,
            15, 0, 0, 3, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/castleBuilding/barrack2.png").toString()),
    MERCENARY_BARRACKS("building", 350, 350, BuildingCategory.CASTLE_BUILDING,
            "MercenaryBarrack", 0, 0, 10, 0, 3,
            TypeOfTile.NORMAL_GROUND, Main.class.getResource("/Images/castleBuilding/mercenarybarrack2.png").toString()),
    ENGINEER_GUILD("building", 300, 300, BuildingCategory.CASTLE_BUILDING, "EngineerGuild",
            100, 0, 10, 0, 2, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/militaryBuilding/engineerguild2.png").toString()),
    KILLING_PIT("killingPit", 0, 0, BuildingCategory.CASTLE_BUILDING, "KillingPit",
            0, 0, 6, 0, 1, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/carehouse/killingpit2.png").toString()),
    INN("firstProducer", 250, 250, BuildingCategory.FOOD_PROCESSING_BUILDING, "Inn", 100, 0, 20, 1, 3, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/foodProcessing/inn2.png").toString()),
    MILL("building", 270, 270, BuildingCategory.FOOD_PROCESSING_BUILDING, "Mill", 0,
            0, 20, 3, 2, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/foodProcessing/mill2.png").toString()),
    IRON_MINE("firstProducer", 320, 320, BuildingCategory.INDUSTRIAL_BUILDING, "IronMine",
            0, 0, 20, 2, 3, TypeOfTile.IRON_MINE, Main.class.getResource("/Images/industry/ironMine2.png").toString()),
    MARKET("building", 330, 330, BuildingCategory.INDUSTRIAL_BUILDING, "Market",
            0, 0, 5, 1, 3, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/industry/shop2.png").toString()),

    OX_TETHER("building", 100, 100, BuildingCategory.INDUSTRIAL_BUILDING, "OxTether", 0, 0, 5, 1, 1, TypeOfTile.NORMAL_GROUND, Main.class.getResource("/Images/industry/oxTether.png").toString()),
    PITCH_RIG("firstProducer", 240, 240, BuildingCategory.INDUSTRIAL_BUILDING, "PitchRig", 0, 0, 20, 1, 2, TypeOfTile.OIL_GROUND, Main.class.getResource("/Images/industry/pitchrig2.gif").toString()),
    QUARRY("firstProducer", 300, 300, BuildingCategory.INDUSTRIAL_BUILDING, "Quarry", 0, 0, 20, 3, 3, TypeOfTile.STONE_MINE, Main.class.getResource("/Images/industry/quarry2.gif").toString()),
    STOCKPILE("storage", 1, 1, BuildingCategory.INDUSTRIAL_BUILDING, "Stockpile", 0, 0, 0, 0, 3, TypeOfTile.NORMAL_GROUND, Main.class.getResource("/Images/industry/stockpile2.gif").toString()),
    WOODCUTTER("firstProducer", 180, 180, BuildingCategory.INDUSTRIAL_BUILDING, "Woodcutter", 0, 0, 3, 1, 2, TypeOfTile.NORMAL_GROUND, Main.class.getResource("/Images/industry/woodCutter2.png").toString()),
    APOTHECARY("building", 300, 300, BuildingCategory.TOWN_BUILDING, "Apothecary",
            150, 0, 20, 0, 3, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/townBuildings/apothecary2.png").toString()),
    HOVEL("building", 280, 280, BuildingCategory.TOWN_BUILDING, "Hovel", 0, 0, 6, 0, 2, TypeOfTile.NORMAL_GROUND, Main.class.getResource("/Images/townBuildings/hovel2.png").toString()),
    CHURCH("building", 800, 800, BuildingCategory.TOWN_BUILDING, "Church", 250, 0, 0, 0, 3, TypeOfTile.NORMAL_GROUND, Main.class.getResource("/Images/townBuildings/church2.png").toString()),
    CATHEDRAL("building", 1000, 1000, BuildingCategory.TOWN_BUILDING, "Cathedral", 1000, 0, 0, 0, 3, TypeOfTile.NORMAL_GROUND, Main.class.getResource("/Images/townBuildings/cathedral2.png").toString()),
    WELL("building", 500, 500, BuildingCategory.TOWN_BUILDING, "Well", 30, 0, 0, 0, 2, TypeOfTile.NORMAL_GROUND, Main.class.getResource("/Images/townBuildings/well2.png").toString()),
    WATER_POT("building", 200, 200, BuildingCategory.TOWN_BUILDING, "WaterPot", 60, 0, 0, 3, 2, TypeOfTile.NORMAL_GROUND, Main.class.getResource("/Images/townBuildings/waterpot2.png").toString()),
    ARMOURER("secondProducer", 340, 340, BuildingCategory.WEAPON_BUILDING, "ArmourerWorkshop", 100, 0, 20, 1, 3, TypeOfTile.NORMAL_GROUND, Main.class.getResource("/Images/weaponBuilding/armourer2.png").toString()),
    BLACKSMITH("secondProducer", 340, 340, BuildingCategory.WEAPON_BUILDING, "BlacksmithWorkshop", 100, 0, 20, 1, 3, TypeOfTile.NORMAL_GROUND,  Main.class.getResource("/Images/weaponBuilding/sword2.png").toString()),
    FLETCHER("secondProducer", 340, 340, BuildingCategory.WEAPON_BUILDING, "FletcherWorkshop", 100, 0, 20, 1, 3, TypeOfTile.NORMAL_GROUND,  Main.class.getResource("/Images/weaponBuilding/arc2.png").toString()),
    POLETURNER("secondProducer", 340, 340, BuildingCategory.WEAPON_BUILDING, "PoleturnerWorkshop", 100, 0, 10, 1, 3, TypeOfTile.NORMAL_GROUND,  Main.class.getResource("/Images/weaponBuilding/pike2.png").toString()),
    OIL_SMELTER("oilSmelter", 200, 200, BuildingCategory.WEAPON_BUILDING,
            "OilSmelter", 100, 0, 0, 1, 2, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/militaryBuilding/oilsmelter2.png").toString()),
    PITCH_DITCH("pitchDitch", 0, 0, BuildingCategory.CASTLE_BUILDING, "PitchDitch", 0,
            0, 0, 0, 1, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/carehouse/pitchditch2.png").toString()),
    CAGED_WAR_DOGS("cagedWarDogs", 0, 0, BuildingCategory.CASTLE_BUILDING, "CagedWarDogs",
            100, 0, 10, 0, 1, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/carehouse/cagedwardogs2.png").toString()),

    TUNNELER_GUILD("building", 220, 220, BuildingCategory.CASTLE_BUILDING, "TunnelerGuild",
            0, 0, 0, 1, 3, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/militaryBuilding/tunnelerguild2.png").toString()),
    STABLE("firstProducer", 220, 220, BuildingCategory.CASTLE_BUILDING, "Stable",
            400, 0, 20, 0, 3, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/militaryBuilding/stable2.png").toString()),
    APPLE_GARDEN("firstProducer", 310, 310, BuildingCategory.FARM_BUILDING,
            "AppleGarden", 0, 0, 5, 1, 3, TypeOfTile.MEADOW,
            Main.class.getResource("/Images/dairyFarm/applefarm2.png").toString()),
    DAIRY_FARM("firstProducer", 310, 310, BuildingCategory.FARM_BUILDING, "DairyFarm",
            0, 0, 10, 1, 5, TypeOfTile.MEADOW,
            Main.class.getResource("/Images/dairyFarm/dairyfarm2.png").toString()),
    BARLEY_FARM("firstProducer", 310, 310, BuildingCategory.FARM_BUILDING, "BarleyFarm",
            0, 0, 15, 1, 3, TypeOfTile.MEADOW,
            Main.class.getResource("/Images/dairyFarm/hopsfarm2.png").toString()),
    BAKERY("secondProducer", 310, 310, BuildingCategory.FOOD_PROCESSING_BUILDING, "Bakery",
            0, 0, 10, 1, 2, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/foodProcessing/bakery2.png").toString()),
    ALE_PRODUCING("secondProducer", 300, 300, BuildingCategory.FOOD_PROCESSING_BUILDING,
            "AleProducing", 0, 0, 10, 1, 2, TypeOfTile.NORMAL_GROUND,
            Main.class.getResource("/Images/foodProcessing/brewery2.png").toString()),
    GRANARY("storage", 330, 330, BuildingCategory.FOOD_PROCESSING_BUILDING, "FoodStockpile",
            0, 0, 5, 0, 2, TypeOfTile.NORMAL_GROUND,  Main.class.getResource("/Images/foodProcessing/granary2.png").toString()),
    desertShrub("", Integer.MAX_VALUE, Integer.MAX_VALUE, BuildingCategory.TREES, "desertShrub", 0,
            0, 0, 0, 1, TypeOfTile.NORMAL_GROUND, Main.class.getResource("/Images/tiles/desertShorbpng.png").toString()),
    cherryPalm("", Integer.MAX_VALUE, Integer.MAX_VALUE, BuildingCategory.TREES, "CherryPalm", 0,
            0, 0, 0, 1, TypeOfTile.NORMAL_GROUND, Main.class.getResource("/Images/tiles/cherryPalm.png").toString()),
    oliveTree("", Integer.MAX_VALUE, Integer.MAX_VALUE, BuildingCategory.TREES, "oliveTree", 0,
            0, 0, 0, 1, TypeOfTile.NORMAL_GROUND, Main.class.getResource("/Images/tiles/oliveTreepng.png").toString()),
    coconutPalm("", Integer.MAX_VALUE, Integer.MAX_VALUE, BuildingCategory.TREES, "coconutPalm", 0,
            0, 0, 0, 1, TypeOfTile.NORMAL_GROUND, Main.class.getResource("/Images/tiles/coconutPalmpng.png").toString()),
    palmTree("", Integer.MAX_VALUE, Integer.MAX_VALUE, BuildingCategory.TREES, "palmTree", 0,
            0, 0, 0, 1, TypeOfTile.NORMAL_GROUND, Main.class.getResource("/Images/tiles/datePalmpng.png").toString()),
    ;

    private final String type;
    private int hitPoint;
    private final int maxHitPoint;
    private final BuildingCategory buildingCategory;
    private final String name;
    private final int goldCost;
    private final int stoneCost;
    private final int woodCost;
    private final int size;
    private final TypeOfTile typeCanBuildBuilding;
    private int numberOfWorkers;
    private final String pictureAddress;

    BuildingName(String type, int hitPoint, int maxHitPoint, BuildingCategory buildingCategory, String name, int goldCost, int stoneCost,
                 int woodCost, int numberOfWorkers, int size, TypeOfTile typeCanBuildBuilding, String pictureAddress) {
        this.type = type;
        this.hitPoint = hitPoint;
        this.maxHitPoint = maxHitPoint;
        this.buildingCategory = buildingCategory;
        this.name = name;
        this.goldCost = goldCost;
        this.stoneCost = stoneCost;
        this.woodCost = woodCost;
        this.numberOfWorkers = numberOfWorkers;
        this.size = size;
        this.typeCanBuildBuilding = typeCanBuildBuilding;
        this.pictureAddress = pictureAddress;
    }


    public static BuildingName getByAddres(String pictureAddress) {
        if(pictureAddress == null) return null;
        for (BuildingName value : BuildingName.values()) {
            if(value.getPictureAddress().replace("2", "").equals(pictureAddress.replace
                    ("2", "")))
                return value;
        }
        return null;
    }

    public int getGoldCost() {
        return goldCost;
    }

    public int getStoneCost() {
        return stoneCost;
    }

    public int getWoodCost() {
        return woodCost;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public BuildingCategory getBuildingCategory() {
        return buildingCategory;
    }

    public void reduceHitPoint(int hitPoint) {
        this.hitPoint -= hitPoint;
    }

    public void setHitPoint() {
        this.hitPoint = this.maxHitPoint;
    }

    public int getMaxHitPoint() {
        return maxHitPoint;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getNumberOfWorkers() {
        return numberOfWorkers;
    }

    public TypeOfTile getTypeCanBuildBuilding() {
        return typeCanBuildBuilding;
    }


    public String getType() {
        return type;
    }

    public String getPictureAddress() {
        return pictureAddress;
    }

    public static BuildingName getBuildingNameWithName(String name) {
        for (int i = 0; i < BuildingName.values().length; i++)
            if (BuildingName.values()[i].name.equals(name))
                return BuildingName.values()[i];
        return null;
    }

    public static BuildingName getByAddress(String pictureAddress) {
        for (BuildingName buildingName : BuildingName.values())
            if (buildingName.getPictureAddress().equals(pictureAddress))
                return buildingName;
        return null;
    }
}
