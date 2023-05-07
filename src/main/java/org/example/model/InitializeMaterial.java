package org.example.model;

public enum InitializeMaterial {

    LOW_SOURCE(5000, 0, 20, 0, 20, 0, 50, 0, 0, 0, 0,
            0, 0 , 0 ,0, 0 , 0 , 0, 0, 0, 0),
    //TODO check the capacity of stockPile
    MIDDLE_SOURCE(20000, 10, 30, 0, 30, 0, 50, 50, 0, 0, 0,
            0, 0 , 0 ,0, 0 , 0 , 0, 0, 0, 0),
    HIGH_SOURCE(40000, 20, 50, 10, 40, 0, 50, 50, 50, 0, 50,
            0, 0 , 0 ,0, 0 , 0 , 0, 0, 0, 0);
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
    private final int cheesse;
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
        this.cheesse = cheese;
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
}
