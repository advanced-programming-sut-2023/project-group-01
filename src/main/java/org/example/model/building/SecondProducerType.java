package org.example.model.building;

import org.example.model.building.enums.MaterialType;

public enum SecondProducerType {

    //Food Processing :
    MILL(MaterialType.WHEAT, null, MaterialType.FLOUR, null),
    BAKERY(MaterialType.WHEAT, null, MaterialType.BREAD, null),
    BREWERY(MaterialType.BARLEY, null, MaterialType.ALE, null),

    //TODO: SOME CHECKS HERE IS NEEDED
    //Weapon :
    FLETCHER_WORKSHOP(MaterialType.WOOD, null, MaterialType.ARC, MaterialType.CROSSBOW),
    POLETURNER_WORKSHOP(MaterialType.WOOD, MaterialType.IRON, MaterialType.SPEAR, MaterialType.PIKE),
    BLACKSMITH_WORKSHOP(MaterialType.IRON, null, MaterialType.SWORD, MaterialType.MACE),
    TANNER_WORKSHOP(MaterialType.COW, null, MaterialType.LEATHER_ARMOUR, null),
    ARMOUR_WORKSHOP(MaterialType.IRON, null, MaterialType.METAL_ARMOUR, null),

    //Different :
    OIL_SMELTER(MaterialType.IRON, null, MaterialType.MELTED_IRON, MaterialType.OIL);

    private final MaterialType firstEnterance;
    private final MaterialType secondEnterance2;
    private MaterialType firstProduct;
    private MaterialType secondProduct;
    private MaterialType producingMaterial;

    SecondProducerType(MaterialType firstEnterance, MaterialType secondEnterance, MaterialType product1, MaterialType product2) {
        this.firstEnterance = firstEnterance;
        this.secondEnterance2 = secondEnterance;
        this.firstProduct = product1;
        this.secondProduct = product2;
        this.producingMaterial = product1;
    }

    public MaterialType getFirstEnterance() {
        return firstEnterance;
    }

    public MaterialType getSecondEnterance2() {
        return secondEnterance2;
    }

    public MaterialType getFirstProduct() {
        return firstProduct;
    }

    //TODO should be used for things that has two product
    public void changeFirstProduct() {
        MaterialType materialType = firstProduct;
        this.firstProduct = this.secondProduct;
        this.secondProduct = materialType;
    }
}
