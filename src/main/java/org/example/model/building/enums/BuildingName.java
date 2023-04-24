package org.example.model.building.enums;

import org.example.model.enums.HitPoint;
import org.example.model.User;

public enum BuildingName {
    /*SMALL_STONE_GATEHOUSE(HitPoint.BUILDING_LOW, BuildingCategory.TYPES_AND_COMPONENTS_CASTLES, "", 0, 0, 0),
    BIG_STONE_GATEHOUSE(HitPoint.BUILDING_LOW, BuildingCategory.TYPES_AND_COMPONENTS_CASTLES, "", 0, 20, 0),
    DRAWBRIDGE(HitPoint.BUILDING_LOW, BuildingCategory.TYPES_AND_COMPONENTS_CASTLES, "", 0, 0, 10),
    LOOKOUT_TOWER(HitPoint.BUILDING_LOW, BuildingCategory.TYPES_AND_COMPONENTS_CASTLES, "", 0, 10, 0),
    PERIMETER_TOWER(HitPoint.BUILDING_LOW, BuildingCategory.TYPES_AND_COMPONENTS_CASTLES, "", 0, 10, 0),
    DEFENDER_TURRET(HitPoint.BUILDING_LOW, BuildingCategory.TYPES_AND_COMPONENTS_CASTLES, "", 0, 15, 0),
    CIRCLE_TOWER(HitPoint.BUILDING_LOW, BuildingCategory.TYPES_AND_COMPONENTS_CASTLES, "", 0, 40, 0),
    SQUARE_TOWER(HitPoint.BUILDING_LOW, BuildingCategory.TYPES_AND_COMPONENTS_CASTLES, "", 0, 35, 0),
    ARMOURY(HitPoint.BUILDING_LOW, BuildingCategory.TYPES_AND_COMPONENTS_CASTLES, "", 0, 0, 5),
    BARRACK(HitPoint.BUILDING_LOW, BuildingCategory.TYPES_AND_COMPONENTS_CASTLES, "", 0, 15, 0),
    MERCENARY_BARRACKS(HitPoint.BUILDING_LOW, BuildingCategory.TYPES_AND_COMPONENTS_CASTLES, "", 0, 0, 10),
    ENGINEER_GUILD(HitPoint.BUILDING_LOW, BuildingCategory.TYPES_AND_COMPONENTS_CASTLES, "", 100, 0, 10),
    KILLING_PIT(HitPoint.BUILDING_LOW, BuildingCategory.TYPES_AND_COMPONENTS_CASTLES, "", 0, 0, 6),
    INN(HitPoint.BUILDING_LOW, BuildingCategory.FOOD_PROCESSING, "", 100, 0, 20),
    MILL(HitPoint.BUILDING_LOW, BuildingCategory.FOOD_PROCESSING, "", 0, 0, 20),
    IRON_MINE(HitPoint.BUILDING_LOW, BuildingCategory.INDUSTRIAL, "", 0, 0, 20),
    MARKET(HitPoint.BUILDING_LOW, BuildingCategory.INDUSTRIAL, "", 0, 0, 5),
    OX_TETHER(HitPoint.BUILDING_LOW, BuildingCategory.INDUSTRIAL, "", 0, 0, 5),
    PITCH_RIG(HitPoint.BUILDING_LOW, BuildingCategory.INDUSTRIAL, "", 0, 0, 20),
    QUARRY(HitPoint.BUILDING_LOW, BuildingCategory.INDUSTRIAL, "", 0, 0, 20),
    STOCKPILE(HitPoint.BUILDING_LOW, BuildingCategory.INDUSTRIAL, "", 0, 0, 0),
    WOODCUTTER(HitPoint.BUILDING_LOW, BuildingCategory.INDUSTRIAL, "", 0, 0, 3),
    APOTHECARY(HitPoint.BUILDING_LOW, BuildingCategory.URBAN_STRUCTURES, "", 150, 0, 20),
    HOVEL(HitPoint.BUILDING_LOW, BuildingCategory.URBAN_STRUCTURES, "", 0, 0, 6),
    CHURCH(HitPoint.BUILDING_LOW, BuildingCategory.URBAN_STRUCTURES, "", 250, 0, 0),
    CATHEDRAL(HitPoint.BUILDING_LOW, BuildingCategory.URBAN_STRUCTURES, "", 1000, 0, 0),
    WELL(HitPoint.BUILDING_LOW, BuildingCategory.URBAN_STRUCTURES, "", 30, 0, 0),
    WATER_POT(HitPoint.BUILDING_LOW, BuildingCategory.URBAN_STRUCTURES, "", 60, 0, 0),
    ARMOURER(HitPoint.BUILDING_LOW, BuildingCategory.MANUFACTURE_AND_MAINTENANCE_OF_WEAPONS, "", 100, 0, 20),
    BLACKSMITH(HitPoint.BUILDING_LOW, BuildingCategory.MANUFACTURE_AND_MAINTENANCE_OF_WEAPONS, "", 100, 0, 20),
    FLETCHER(HitPoint.BUILDING_LOW, BuildingCategory.MANUFACTURE_AND_MAINTENANCE_OF_WEAPONS, "", 100, 0, 20),
    POLETURNER(HitPoint.BUILDING_LOW, BuildingCategory.MANUFACTURE_AND_MAINTENANCE_OF_WEAPONS, "", 100, 0, 10),
    OIL_SMELTER(HitPoint.BUILDING_LOW, BuildingCategory.MANUFACTURE_AND_MAINTENANCE_OF_WEAPONS, "", 100, 0, 0),
    PITCH_DITCH(HitPoint.BUILDING_LOW, BuildingCategory.TYPES_AND_COMPONENTS_CASTLES, "", 0, 0, 0),
    CAGED_WAR_DOGS(HitPoint.BUILDING_LOW, BuildingCategory.TYPES_AND_COMPONENTS_CASTLES, "", 100, 0, 10),
    TUNNELER(HitPoint.BUILDING_LOW, BuildingCategory.TYPES_AND_COMPONENTS_CASTLES, "", 0, 0, 0),
    STABLE(HitPoint.BUILDING_LOW, BuildingCategory.TYPES_AND_COMPONENTS_CASTLES, "", 400, 0, 20),
    APPLE_GARDEN(HitPoint.BUILDING_LOW, BuildingCategory.FARMS_AND_HUNTING_GROUNDS, "", 0, 0, 5),
    DAIRY_PRODUCTS(HitPoint.BUILDING_LOW, BuildingCategory.FARMS_AND_HUNTING_GROUNDS, "", 0, 0, 10),
    BARLEY_FARM(HitPoint.BUILDING_LOW, BuildingCategory.FARMS_AND_HUNTING_GROUNDS, "", 0, 0, 15),
    BAKERY(HitPoint.BUILDING_LOW, BuildingCategory.FOOD_PROCESSING, "", 0, 0, 10),
    ALE_PRODUCING(HitPoint.BUILDING_LOW, BuildingCategory.FOOD_PROCESSING, "", 0, 0, 10),*/
    FOOD_STOCKPILE(HitPoint.BUILDING_LOW, BuildingCategory.FOOD_PROCESSING, "", 0, 0, 5, TypeOfTile.NORMAL);

    private final BuildingCategory buildingCategory;
    private final String name;
    private int hitPoint;
    private final int goldCost;
    private final int stoneCost;
    private final int woodCost;

    private  TypeOfTile typeCanBuildBuilding;
    private User owner;
   private int numberOfWorkers;

//Player player, int numberOfWorkers,
    BuildingName(HitPoint hitPoint , BuildingCategory buildingCategory, String name,
                 int goldCost, int stoneCost, int woodCost, TypeOfTile typeCanBuildBuilding) {
        this.hitPoint = hitPoint.getHitPoint();
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

    public BuildingCategory getBuildingCategory(){
        return buildingCategory;
    }

    public TypeOfTile getTypeCanBuildBuilding() {
        return typeCanBuildBuilding;
    }

    public void increaseHitPoint(int hitPoint) {
        this.hitPoint += hitPoint;
    }

    public void reduceHitPoint() {
        this.hitPoint -= hitPoint;
    }
}
