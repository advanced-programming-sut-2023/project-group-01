package org.example.model.building.castleBuilding.enums;

import org.example.model.enums.Range;

public enum TowerAndGateHouseType {

    ;

    private final int fireRang;
    private final int defendRange;

    TowerAndGateHouseType(Range fireRange, Range defendRange) {
        this.fireRang = fireRange.getRange();
        this.defendRange = defendRange.getRange();
    }
}
