package org.example.model.building.enums;

import org.example.Main;

public enum MaterialType {
    //Sources :
    IRON("iron", 215, 115, "source"),
    STONE("stone", 70, 35, "source"),
    OIL("oil", 100, 50, "source"),
    WOOD("wood", 20, 5, "source"),

    //Foods :
    BREAD("bread", 40, 20, "food"),
    APPLE("apple", 40, 20, "food"),
    MEAT("meat", 40, 20, "food"),
    FLOUR("flour", 160, 50, "food"),
    CHEESE("cheese", 40, 20, "food"),
    BARLEY("barley", 75, 40, "food"),
    ALE("ale", 100, 40, "food"),
    //TODO check
    WHEAT("wheat", 115, 40, "food"),

    //Warfares :
    ARC("arc", 155, 75, "warfare"),
    CROSSBOW("crossbow", 290, 150, "warfare"),
    MACE("mace", 290, 150, "warfare"),
    PIKE("pike", 180, 90, "warfare"),
    SWORD("sword", 290, 150, "warfare"),
    SPEAR("spear", 100, 50, "warfare"),
    LEATHER_ARMOUR("leatherArmour", 125, 60, "warfare"),
    METAL_ARMOUR("metalArmour", 290, 150, "warfare"),

    //Different :
    HORSE("horse", 0, 0, "different"),
    MELTED_IRON("meltedIron", 0, 0, "different"),
    COW("cow", 0, 0, "different")
    ;
    private final String name;
    private final String typeOfProduct;
    private final int sellingPrice;
    private final int buyingPrice;


    MaterialType(String name, int buyingPrice, int sellingPrice, String typeOfProduct) {
        this.name = name;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.typeOfProduct = typeOfProduct;
    }

    public static MaterialType getMaterialTypeWithName(String resourceType) {
        for (MaterialType materialType: MaterialType.values())
            if(materialType.getName().equals(resourceType))
                return materialType;
        return null;
    }

    public String getName() {
        return name;
    }

    public String getTypeOfProduct() {
        return typeOfProduct;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public int getBuyingPrice() {
        return buyingPrice;
    }


    public String getPictureAddress() {
        return null;
    }
}
