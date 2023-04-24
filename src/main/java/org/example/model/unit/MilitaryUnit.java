package org.example.model.unit;

import org.example.model.Empire;
import org.example.model.People;
import org.example.model.User;
import org.example.model.unit.enums.MilitaryUnitName;

public class MilitaryUnit extends People {

    protected Empire player;
    protected MilitaryUnitName militaryUnitName;

    public MilitaryUnit(Empire player,MilitaryUnitName militaryUnitName) {
        this.player = player;
        this.militaryUnitName = militaryUnitName;
    }


}
