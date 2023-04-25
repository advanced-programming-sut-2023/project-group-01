package org.example.model.building.enums;

import org.example.model.building.enums.MaterialType;

public enum FirstProducerType {
    //Farm :
    APPLE_GARDEN(MaterialType.APPLE),
    BREAD_PRODUCER(MaterialType.BREAD),
    HUNTING_POST(MaterialType.MEAT),
    DAIRY_FARM(MaterialType.CHEESE),
    BARLEY_FARM(MaterialType.BARLEY),
    WHEAT_FIELD(MaterialType.WHEAT),

    //Mine :
    IRON_MINE(MaterialType.IRON),
    PITCH_RIG(MaterialType.OIL),
    QUARRY(MaterialType.STONE),
    WOODCUTTER(MaterialType.WOOD),

    //Different :
    STABLE(MaterialType.HORSE);

    private final MaterialType product;

    FirstProducerType(MaterialType product) {
        this.product = product;
    }

    public MaterialType getProduct() {
        return product;
    }
}
