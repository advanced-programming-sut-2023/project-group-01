package org.example.model.building.enums;

import org.example.view.graphicView.BuildingMenuApp;

import java.net.URL;

public enum MaterialType {
    //Sources :
    IRON("iron", 215, 115, "source",
            MaterialType.class.getResource("/Images/ShopMenu/industryShop/iron.png")),
    STONE("stone", 70, 35, "source",
            MaterialType.class.getResource("/Images/ShopMenu/industryShop/stone.png")),
    OIL("oil", 100, 50, "source",
            MaterialType.class.getResource("/Images/ShopMenu/industryShop/oil.png")),
    WOOD("wood", 20, 5, "source",
            MaterialType.class.getResource("/Images/ShopMenu/industryShop/wood.png")),

    //Foods :
    BREAD("bread", 40, 20, "food",
            MaterialType.class.getResource("/Images/ShopMenu/foodShop/bread.png")),
    APPLE("apple", 40, 20, "food",
            MaterialType.class.getResource("/Images/ShopMenu/foodShop/apple.png")),
    MEAT("meat", 40, 20, "food",
            MaterialType.class.getResource("/Images/ShopMenu/foodShop/meat.png")),
    FLOUR("flour", 160, 50, "food",
            MaterialType.class.getResource("/Images/ShopMenu/foodShop/floor.png")),
    CHEESE("cheese", 40, 20, "food",
            MaterialType.class.getResource("/Images/ShopMenu/foodShop/cheese.png")),
    BARLEY("barley", 75, 40, "food",
            MaterialType.class.getResource("/Images/ShopMenu/foodShop/barley.png")),
    ALE("ale", 100, 40, "food",
            MaterialType.class.getResource("/Images/ShopMenu/foodShop/ale.png")),
    //TODO check
    WHEAT("wheat", 115, 40, "food",
            MaterialType.class.getResource("/Images/ShopMenu/foodShop/wheat.png")),

    //Warfares :
    ARC("arc", 155, 75, "warfare",
            MaterialType.class.getResource("/Images/ShopMenu/weapon/arc.png")),
    CROSSBOW("crossbow", 290, 150, "warfare",
            MaterialType.class.getResource("/Images/ShopMenu/weapon/crossbow.png")),
    MACE("mace", 290, 150, "warfare",
            MaterialType.class.getResource("/Images/ShopMenu/weapon/mace.png")),
    PIKE("pike", 180, 90, "warfare",
            MaterialType.class.getResource("/Images/ShopMenu/weapon/pike.png")),
    SWORD("sword", 290, 150, "warfare",
            MaterialType.class.getResource("/Images/ShopMenu/weapon/sword.png")),
    SPEAR("spear", 100, 50, "warfare",
            MaterialType.class.getResource("/Images/ShopMenu/weapon/spear.png")),
    LEATHER_ARMOUR("leatherArmour", 125, 60, "warfare",
            MaterialType.class.getResource("/Images/ShopMenu/weapon/leatherArmour.png")),
    METAL_ARMOUR("metalArmour", 290, 150, "warfare",
            MaterialType.class.getResource("/Images/ShopMenu/weapon/metalarmour.png")),

    //Different :
    HORSE("horse", 0, 0, "different", null),
    MELTED_IRON("meltedIron", 0, 0, "different", null),
    COW("cow", 0, 0, "different", null);
    private final String name;
    private final String typeOfProduct;
    private final int sellingPrice;
    private final int buyingPrice;
    private final URL pictureAddress;


    MaterialType(String name, int buyingPrice, int sellingPrice, String typeOfProduct, URL pictureAddress) {
        this.name = name;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.typeOfProduct = typeOfProduct;
        this.pictureAddress = pictureAddress;
    }

    public static MaterialType getMaterialTypeWithName(String resourceType) {
        for (MaterialType materialType : MaterialType.values())
            if (materialType.getName().equals(resourceType))
                return materialType;
        return null;
    }

    public static MaterialType getMaterialWithImage(URL url){
        for (MaterialType materialType : MaterialType.values())
            if (materialType.getPictureAddress().equals(url))
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

    public URL getPictureAddress() {
        return pictureAddress;
    }
}
