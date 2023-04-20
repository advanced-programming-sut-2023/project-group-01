package org.example.model.unit;

import org.example.model.People;
import org.example.model.User;
import org.example.model.building.Tile;
import org.example.model.unit.enums.MilitaryUnitName;

public class MilitaryUnit extends People {

    protected MilitaryUnitName militaryUnitName;

    public MilitaryUnit(Tile position, User player, MilitaryUnitName militaryUnitName) {
        super(position, player);
        this.militaryUnitName = militaryUnitName;
    }
}
