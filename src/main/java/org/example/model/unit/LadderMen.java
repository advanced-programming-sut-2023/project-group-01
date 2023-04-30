package org.example.model.unit;

import org.example.model.Empire;
import org.example.model.User;
import org.example.model.building.Tile;
import org.example.model.unit.enums.MilitaryUnitName;

public class LadderMen extends MilitaryUnit {
    private boolean havingLadder = true;
    
    public LadderMen(Tile position, User player, MilitaryUnitName militaryUnitName) {
        super(position, player, militaryUnitName);
    }

    public void setHavingLadder(boolean havingLadder) {
        this.havingLadder = havingLadder;
    }
    public boolean getHavingLadder(){
        return havingLadder;
    }
}