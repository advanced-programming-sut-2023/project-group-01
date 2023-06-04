package org.example.model.building;

import org.example.Main;
import org.example.model.building.enums.MaterialType;

public class Material {
    private MaterialType materialType;

    public Material(MaterialType materialType) {
        this.materialType = materialType;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

}
