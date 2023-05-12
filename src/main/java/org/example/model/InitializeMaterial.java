package org.example.model;

import org.example.model.building.enums.MaterialType;

public enum InitializeMaterial {

    LOW_SOURCE(5000, 0, 20, 0, 20, 0, 50, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    //TODO check the capacity of stockPile
    MIDDLE_SOURCE(20000, 10, 30, 0, 30, 0, 50, 50, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    HIGH_SOURCE(40000, 20, 50, 10, 40, 0, 50, 50, 50, 0, 50,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    private final int gold;
    private final int iron;
    private final int stone;
    private final int oil;
    private final int wood;
    private final int wheat;
    private final int bread;
    private final int apple;
    private final int meat;
    private final int flour;
    private final int cheese;
    private final int barley;
    private final int ale;
    private final int arc;
    private final int crossbow;
    private final int mace;
    private final int pike;
    private final int sword;
    private final int spear;
    private final int leatherArmour;
    private final int metalArmour;

    InitializeMaterial(int gold, int iron, int stone, int oil, int wood, int wheat, int bread, int apple, int meat,
                       int flour, int cheese, int barley, int ale, int arc, int crossbow, int mace, int pike,
                       int sword, int spear, int leatherArmour, int metalArmour) {
        this.gold = gold;
        this.iron = iron;
        this.stone = stone;
        this.oil = oil;
        this.wood = wood;
        this.wheat = wheat;
        this.bread = bread;
        this.apple = apple;
        this.meat = meat;
        this.flour = flour;
        this.cheese = cheese;
        this.barley = barley;
        this.ale = ale;
        this.arc = arc;
        this.crossbow = crossbow;
        this.mace = mace;
        this.pike = pike;
        this.sword = sword;
        this.spear = spear;
        this.leatherArmour = leatherArmour;
        this.metalArmour = metalArmour;
    }

    public static void setSources(Empire empire, InitializeMaterial initializeMaterial) {
        empire.setGold(initializeMaterial.gold);
        for (int i = 0; i < MaterialType.values().length; i++) {
            int amount = initializeMaterial.setAmount(MaterialType.values()[i].getName(), initializeMaterial);
            empire.addMaterial(MaterialType.values()[i].getName(), amount);
        }
    }

    private int setAmount(String name, InitializeMaterial initializeMaterial) {
        switch (name) {
            case "iron":
                return initializeMaterial.iron;
            case "stone":
                return initializeMaterial.stone;
            case "oil":
                return initializeMaterial.oil;
            case "wood":
                return initializeMaterial.wood;
            case "wheat":
                return initializeMaterial.wheat;
            case "bread":
                return initializeMaterial.bread;
            case "apple":
                return initializeMaterial.apple;
            case "meat":
                return initializeMaterial.meat;
            case "flour":
                return initializeMaterial.flour;
            case "cheese":
                return initializeMaterial.cheese;
            case "grape":
                return initializeMaterial.barley;
            case "ale":
                return initializeMaterial.ale;
            case "arc":
                return initializeMaterial.arc;
            case "crossbow":
                return initializeMaterial.crossbow;
            case "mace":
                return initializeMaterial.mace;
            case "axe":
                return initializeMaterial.pike;
            case "sword":
                return initializeMaterial.sword;
            case "spear":
                return initializeMaterial.spear;
            case "leatherArmour":
                return initializeMaterial.leatherArmour;
            case "metalArmour":
                return initializeMaterial.metalArmour;
            default:
                return 0;
        }
    }
}
