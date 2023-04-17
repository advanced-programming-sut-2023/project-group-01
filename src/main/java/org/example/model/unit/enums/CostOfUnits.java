package org.example.model.unit.enums;

public enum CostOfUnits {
    SO_HIGH(100),
    HIGH(80),
    MIDDLE(60),
    LOW(40),
    SO_LOW(20),
    NOTHING(5);
    private int cost;

    CostOfUnits(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}
