package org.example.model.unit;

import org.example.model.User;
import org.example.model.unit.enums.MilitaryUnitName;

public class Engineer extends MilitaryUnit {
    private boolean isWorking = false;

    public Engineer(User player, MilitaryUnitName militaryUnitName) {
        super(player, militaryUnitName);
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public boolean getWorking() {
        return isWorking;
    }
}
