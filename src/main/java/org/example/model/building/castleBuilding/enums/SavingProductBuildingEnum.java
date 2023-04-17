package org.example.model.building.castleBuilding.enums;

public enum SavingProductBuildingEnum {
    ;
    private final int MaxCapcity;
    private final String type;

    SavingProductBuildingEnum(int maxCapacity, String type) {
        this.MaxCapcity = maxCapacity;
        this.type = type;
    }
}
