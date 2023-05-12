package org.example.model.building.enums;

import org.example.model.Empire;
import org.example.model.building.Building;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;

public enum BuildingName {
    EMPIRE_CASTLE("empireBuilding", Integer.MAX_VALUE, Integer.MAX_VALUE, BuildingCategory.CASTLE_BUILDING, "EmpireBuilding", 0, 0, 0, 0, 1, TypeOfTile.NORMAL_GROUND),
    SMALL_STONE_GATEHOUSE("gateHouse", 1000, 1000, BuildingCategory.CASTLE_BUILDING, "SmallStoneGatehouse", 0, 10, 0, 0, 4, TypeOfTile.NORMAL_GROUND),
    BIG_STONE_GATEHOUSE("gateHouse", 2000, 2000, BuildingCategory.CASTLE_BUILDING, "BigStoneGatehouse", 0, 20, 0, 0, 5, TypeOfTile.NORMAL_GROUND),
    DRAWBRIDGE("gateHouse", 0, 0, BuildingCategory.CASTLE_BUILDING, "Drawbridge", 0, 0, 10, 0, 3, TypeOfTile.NORMAL_GROUND),
    LOOKOUT_TOWER("tower", 250, 250, BuildingCategory.CASTLE_BUILDING, "LookoutTower", 0, 10, 0, 0, 2, TypeOfTile.NORMAL_GROUND),
    PERIMETER_TURRET("tower", 1000, 1000, BuildingCategory.CASTLE_BUILDING, "PerimeterTurret", 0, 10, 0, 0, 3, TypeOfTile.NORMAL_GROUND),
    DEFENSE_TURRET("tower", 1200, 1200, BuildingCategory.CASTLE_BUILDING, "DefenceTurret", 0, 15, 0, 0, 3, TypeOfTile.NORMAL_GROUND),
    ROUND_TOWER("tower", 2000, 2000, BuildingCategory.CASTLE_BUILDING, "RoundTower", 0, 40, 0, 0, 4, TypeOfTile.NORMAL_GROUND),
    SQUARE_TOWER("tower", 1600, 1600, BuildingCategory.CASTLE_BUILDING, "SquareTower", 0, 35, 0, 0, 4, TypeOfTile.NORMAL_GROUND),
    WALL("wall", 700, 700, BuildingCategory.CASTLE_BUILDING, "Wall", 0, 1, 0, 0, 1, TypeOfTile.NORMAL_GROUND),
    STAIRS("building", 400, 400, BuildingCategory.CASTLE_BUILDING, "Stairs", 0, 4, 0, 0, 1, TypeOfTile.NORMAL_GROUND),
    ARMOURY("storage", 280, 280, BuildingCategory.CASTLE_BUILDING, "Armoury", 0, 0, 5, 0, 2, TypeOfTile.NORMAL_GROUND),
    BARRACK("building", 350, 350, BuildingCategory.CASTLE_BUILDING, "Barrack", 0, 15, 0, 0, 5, TypeOfTile.NORMAL_GROUND),
    MERCENARY_BARRACKS("building", 350, 350, BuildingCategory.CASTLE_BUILDING, "MercenaryBarrack", 0, 0, 10, 0, 5, TypeOfTile.NORMAL_GROUND),
    ENGINEER_GUILD("building", 300, 300, BuildingCategory.CASTLE_BUILDING, "EngineerGuild", 100, 0, 10, 0, 2, TypeOfTile.NORMAL_GROUND),
    KILLING_PIT("killingPit", 0, 0, BuildingCategory.CASTLE_BUILDING, "KillingPit", 0, 0, 6, 0, 1, TypeOfTile.NORMAL_GROUND),
    INN("firstProducer", 250, 250, BuildingCategory.FOOD_PROCESSING_BUILDING, "Inn", 100, 0, 20, 1, 3, TypeOfTile.NORMAL_GROUND),
    MILL("building", 270, 270, BuildingCategory.FOOD_PROCESSING_BUILDING, "Mill", 0, 0, 20, 3, 2, TypeOfTile.NORMAL_GROUND),
    IRON_MINE("firstProducer", 320, 320, BuildingCategory.INDUSTRIAL_BUILDING, "IronMine", 0, 0, 20, 2, 3, TypeOfTile.IRON_MINE),
    MARKET("building", 330, 330, BuildingCategory.INDUSTRIAL_BUILDING, "Market", 0, 0, 5, 1, 3, TypeOfTile.NORMAL_GROUND),
    OX_TETHER("building", 100, 100, BuildingCategory.INDUSTRIAL_BUILDING, "OxTether", 0, 0, 5, 1, 1, TypeOfTile.NORMAL_GROUND),
    PITCH_RIG("firstProducer", 240, 240, BuildingCategory.INDUSTRIAL_BUILDING, "PitchRig", 0, 0, 20, 1, 2, TypeOfTile.OIL_GROUND),
    QUARRY("firstProducer", 300, 300, BuildingCategory.INDUSTRIAL_BUILDING, "Quarry", 0, 0, 20, 3, 4, TypeOfTile.STONE_MINE),
    STOCKPILE("storage", 0, 0, BuildingCategory.INDUSTRIAL_BUILDING, "Stockpile", 0, 0, 0, 0, 3, TypeOfTile.NORMAL_GROUND),
    WOODCUTTER("firstProducer", 180, 180, BuildingCategory.INDUSTRIAL_BUILDING, "Woodcutter", 0, 0, 3, 1, 2, TypeOfTile.NORMAL_GROUND),
    APOTHECARY("building", 300, 300, BuildingCategory.TOWN_BUILDING, "Apothecary", 150, 0, 20, 0, 3, TypeOfTile.NORMAL_GROUND),
    HOVEL("building", 280, 280, BuildingCategory.TOWN_BUILDING, "Hovel", 0, 0, 6, 0, 2, TypeOfTile.NORMAL_GROUND),
    CHURCH("building", 800, 800, BuildingCategory.TOWN_BUILDING, "Church", 250, 0, 0, 0, 6, TypeOfTile.NORMAL_GROUND),
    CATHEDRAL("building", 1000, 1000, BuildingCategory.TOWN_BUILDING, "Cathedral", 1000, 0, 0, 0, 9, TypeOfTile.NORMAL_GROUND),
    WELL("building", 500, 500, BuildingCategory.TOWN_BUILDING, "Well", 30, 0, 0, 0, 2, TypeOfTile.NORMAL_GROUND),
    WATER_POT("building", 200, 200, BuildingCategory.TOWN_BUILDING, "WaterPot", 60, 0, 0, 3, 2, TypeOfTile.NORMAL_GROUND),
    ARMOURER("secondProducer", 340, 340, BuildingCategory.WEAPON_BUILDING, "ArmourerWorkshop", 100, 0, 20, 1, 3, TypeOfTile.NORMAL_GROUND),
    BLACKSMITH("secondProducer", 340, 340, BuildingCategory.WEAPON_BUILDING, "BlacksmithWorkshop", 100, 0, 20, 1, 3, TypeOfTile.NORMAL_GROUND),
    FLETCHER("secondProducer", 340, 340, BuildingCategory.WEAPON_BUILDING, "FletcherWorkshop", 100, 0, 20, 1, 3, TypeOfTile.NORMAL_GROUND),
    POLETURNER("secondProducer", 340, 340, BuildingCategory.WEAPON_BUILDING, "PoleturnerWorkshop", 100, 0, 10, 1, 3, TypeOfTile.NORMAL_GROUND),
    OIL_SMELTER("oilSmelter", 200, 200, BuildingCategory.WEAPON_BUILDING, "OilSmelter", 100, 0, 0, 1, 2, TypeOfTile.NORMAL_GROUND),
    PITCH_DITCH("pitchDitch", 0, 0, BuildingCategory.CASTLE_BUILDING, "PitchDitch", 0, 0, 0, 0, 1, TypeOfTile.NORMAL_GROUND),
    CAGED_WAR_DOGS("cagedWarDogs", 0, 0, BuildingCategory.CASTLE_BUILDING, "CagedWarDogs", 100, 0, 10, 0, 1, TypeOfTile.NORMAL_GROUND),
    TUNNELER_GUILD("building", 220, 220, BuildingCategory.CASTLE_BUILDING, "TunnelerGuild", 0, 0, 0, 1, 3, TypeOfTile.NORMAL_GROUND),
    STABLE("firstProducer", 220, 220, BuildingCategory.CASTLE_BUILDING, "Stable", 400, 0, 20, 0, 3, TypeOfTile.NORMAL_GROUND),
    APPLE_GARDEN("firstProducer", 310, 310, BuildingCategory.FARM_BUILDING, "AppleGarden", 0, 0, 5, 1, 4, TypeOfTile.MEADOW),
    DAIRY_FARM("firstProducer", 310, 310, BuildingCategory.FARM_BUILDING, "DairyFarm", 0, 0, 10, 1, 5, TypeOfTile.MEADOW),
    BARLEY_FARM("firstProducer", 310, 310, BuildingCategory.FARM_BUILDING, "BarleyFarm", 0, 0, 15, 1, 5, TypeOfTile.MEADOW),
    BAKERY("secondProducer", 310, 310, BuildingCategory.FOOD_PROCESSING_BUILDING, "Bakery", 0, 0, 10, 1, 2, TypeOfTile.NORMAL_GROUND),
    ALE_PRODUCING("secondProducer", 300, 300, BuildingCategory.FOOD_PROCESSING_BUILDING, "AleProducing", 0, 0, 10, 1, 2, TypeOfTile.NORMAL_GROUND),
    GRANARY("storage", 330, 330, BuildingCategory.FOOD_PROCESSING_BUILDING, "FoodStockpile", 0, 0, 5, 0, 2, TypeOfTile.NORMAL_GROUND),
    desertShrub("", Integer.MAX_VALUE, Integer.MAX_VALUE, BuildingCategory.TREES, "desertShrub", 0, 0, 0, 0, 1, TypeOfTile.NORMAL_GROUND),
    cherryPalm("", Integer.MAX_VALUE, Integer.MAX_VALUE, BuildingCategory.TREES, "CherryPalm", 0, 0, 0, 0, 1, TypeOfTile.NORMAL_GROUND),
    oliveTree("", Integer.MAX_VALUE, Integer.MAX_VALUE, BuildingCategory.TREES, "oliveTree", 0, 0, 0, 0, 1, TypeOfTile.NORMAL_GROUND),
    coconutPalm("", Integer.MAX_VALUE, Integer.MAX_VALUE, BuildingCategory.TREES, "coconutPalm", 0, 0, 0, 0, 1, TypeOfTile.NORMAL_GROUND),
    palmTree("", Integer.MAX_VALUE, Integer.MAX_VALUE, BuildingCategory.TREES, "palmTree", 0, 0, 0, 0, 1, TypeOfTile.NORMAL_GROUND),
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

    BuildingName(String type, int hitPoint, int maxHitPoint, BuildingCategory buildingCategory, String name, int goldCost, int stoneCost,
                 int woodCost, int numberOfWorkers, int size, TypeOfTile typeCanBuildBuilding) {
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

    public static BuildingName getBuildingNameWithName(String name) {
        for (int i = 0; i < BuildingName.values().length; i++)
            if (BuildingName.values()[i].name.equals(name))
                return BuildingName.values()[i];
        return null;
    }
}
