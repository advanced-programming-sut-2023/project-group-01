package org.example.model.unit;

import org.example.model.People;
import org.example.model.User;
import org.example.model.unit.enums.MilitaryUnitName;

public class MilitaryUnit extends People {

    protected User player;
    protected MilitaryUnitName militaryUnitName;

    public MilitaryUnit(User player,MilitaryUnitName militaryUnitName) {
        this.player = player;
        this.militaryUnitName = militaryUnitName;
    }


}
