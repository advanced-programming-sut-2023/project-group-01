package org.example.model.building.enums;

public enum BuildingName {
    SMALL_STONE_GATEHOUSE(1000, 1000, BuildingCategory.CASTLE_BUILDING, "Small Stone Gatehouse", 0, 10, 0, 0),
    BIG_STONE_GATEHOUSE(2000, 2000, BuildingCategory.CASTLE_BUILDING, "Big Stone Gatehouse", 0, 20, 0, 0),
    DRAWBRIDGE(0, 0, BuildingCategory.CASTLE_BUILDING, "Drawbridge", 0, 0, 10, 0),
    LOOKOUT_TOWER(250, 250, BuildingCategory.CASTLE_BUILDING, "Lookout Tower", 0, 10, 0, 0),
    PERIMETER_TURRET(1000, 1000, BuildingCategory.CASTLE_BUILDING, "Perimeter Tower", 0, 10, 0, 0),
    DEFENSE_TURRET(1200, 1200, BuildingCategory.CASTLE_BUILDING, "Defence Tower", 0, 15, 0, 0),
    ROUND_TOWER(2000, 2000, BuildingCategory.CASTLE_BUILDING, "Round Tower", 0, 40, 0, 0),
    SQUARE_TOWER(1600, 1600, BuildingCategory.CASTLE_BUILDING, "Square Tower", 0, 35, 0, 0),
    ARMOURY(280, 280, BuildingCategory.CASTLE_BUILDING, "Armoury", 0, 0, 5, 0),
    BARRACK(350, 350, BuildingCategory.CASTLE_BUILDING, "Barrack", 0, 15, 0, 0),
    MERCENARY_BARRACKS(350, 350, BuildingCategory.CASTLE_BUILDING, "Mercenary Barrack", 0, 0, 10, 0),
    ENGINEER_GUILD(300, 300, BuildingCategory.CASTLE_BUILDING, "Engineer Guid", 100, 0, 10, 0),
    KILLING_PIT(0, 0, BuildingCategory.CASTLE_BUILDING, "Killing Pit", 0, 0, 6, 0),
    INN(250, 250, BuildingCategory.FOOD_PROCESSING_BUILDING, "Inn", 100, 0, 20, 1),
    MILL(270, 270, BuildingCategory.FOOD_PROCESSING_BUILDING, "Mill", 0, 0, 20, 3),
    IRON_MINE(320, 320, BuildingCategory.INDUSTRIAL_BUILDING, "Iron Mine", 0, 0, 20, 2),
    MARKET(330, 330, BuildingCategory.INDUSTRIAL_BUILDING, "Market", 0, 0, 5, 1),
    OX_TETHER(100, 100, BuildingCategory.INDUSTRIAL_BUILDING, "Ox Tether", 0, 0, 5, 1),
    PITCH_RIG(240, 240, BuildingCategory.INDUSTRIAL_BUILDING, "Pitch Rig", 0, 0, 20, 1),
    QUARRY(300, 300, BuildingCategory.INDUSTRIAL_BUILDING, "Quarry", 0, 0, 20, 3),
    STOCKPILE(0, 0, BuildingCategory.INDUSTRIAL_BUILDING, "Stockpile", 0, 0, 0, 0),
    WOODCUTTER(180, 180, BuildingCategory.INDUSTRIAL_BUILDING, "Woodcutter", 0, 0, 3, 1),
    //TODO
    APOTHECARY(300, 300, BuildingCategory.TOWN_BUILDING, "Apothecary", 150, 0, 20, 0),
    HOVEL(280, 280, BuildingCategory.TOWN_BUILDING, "Hovel", 0, 0, 6, 0),
    CHURCH(800, 800, BuildingCategory.TOWN_BUILDING, "Church", 250, 0, 0, 0),
    CATHEDRAL(1000, 1000, BuildingCategory.TOWN_BUILDING, "Cathedral", 1000, 0, 0, 0),
    WELL(500, 500, BuildingCategory.TOWN_BUILDING, "Well", 30, 0, 0, 0),
    //TODO
    WATER_POT(200, 200, BuildingCategory.TOWN_BUILDING, "Water Pot", 60, 0, 0, 3),
    ARMOURER(340, 340, BuildingCategory.WEAPON_BUILDING, "Armourer Workshop", 100, 0, 20, 1),
    BLACKSMITH(340, 340, BuildingCategory.WEAPON_BUILDING, "Blacksmith Workshop", 100, 0, 20, 1),
    FLETCHER(340, 340, BuildingCategory.WEAPON_BUILDING, "Fletcher Workshop", 100, 0, 20, 1),
    POLETURNER(340, 340, BuildingCategory.WEAPON_BUILDING, "Poleturner Workshop", 100, 0, 10, 1),
    //TODO
    OIL_SMELTER(200, 200, BuildingCategory.WEAPON_BUILDING, "Oil Smelter", 100, 0, 0, 1),
    PITCH_DITCH(0, 0, BuildingCategory.CASTLE_BUILDING, "Pitch Ditch", 0, 0, 0, 0),
    CAGED_WAR_DOGS(0, 0, BuildingCategory.CASTLE_BUILDING, "Caged War Dogs", 100, 0, 10, 0),
    //TODO
    TUNNELER_GUILD(220, 220, BuildingCategory.CASTLE_BUILDING, "Tunneler", 0, 0, 0, 1),
    //چادر محاصره
    STABLE(220, 220, BuildingCategory.CASTLE_BUILDING, "Stable", 400, 0, 20, 0),
    APPLE_GARDEN(310, 310, BuildingCategory.FARM_BUILDING, "Apple Garden", 0, 0, 5, 1),
    DAIRY_FARM(310, 310, BuildingCategory.FARM_BUILDING, "Dairy Farm", 0, 0, 10, 1),
    BARLEY_FARM(310, 310, BuildingCategory.FARM_BUILDING, "Barley Farm", 0, 0, 15, 1),
    BAKERY(310, 310, BuildingCategory.FOOD_PROCESSING_BUILDING, "Bakery", 0, 0, 10, 1),
    ALE_PRODUCING(300, 300, BuildingCategory.FOOD_PROCESSING_BUILDING, "Ale Producing", 0, 0, 10, 1),
    FOOD_STOCKPILE(330, 330, BuildingCategory.FOOD_PROCESSING_BUILDING, "Food Stockpile", 0, 0, 5, 0);


    private int hitPoint;
    private final int maxHitPoint;
    private final BuildingCategory buildingCategory;
    private final String name;
    private final int goldCost;
    private final int stoneCost;
    private final int woodCost;


    //TODO : set this
    //TODO چادر محاصره
    private TypeOfTile typeCanBuildBuilding;
    private int numberOfWorkers;

    BuildingName(int hitPoint, int maxHitPoint, BuildingCategory buildingCategory, String name, int goldCost, int stoneCost, int woodCost, int numberOfWorkers) {
        this.hitPoint = hitPoint;
        this.maxHitPoint = maxHitPoint;
        this.buildingCategory = buildingCategory;
        this.name = name;
        this.goldCost = goldCost;
        this.stoneCost = stoneCost;
        this.woodCost = woodCost;
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

    public void increaseHitPoint(int hitPoint) {
        this.hitPoint += hitPoint;
    }

    public void reduceHitPoint() {
        this.hitPoint -= hitPoint;
    }
}
