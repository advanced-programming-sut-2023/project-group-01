package org.example.view.mainMenu.gameMenu;

import org.example.model.Empire;

import java.util.ArrayList;
import java.util.Map;

public class GameMenu {
    private final ArrayList<Empire> empires;

    private Map map;

    private Empire thisEmpire;

    private int turnNumber = 1;

    public GameMenu(ArrayList<Empire> empires) {
        this.empires = empires;
        thisEmpire = empires.get(0);
    }

    public void setThisUser(Empire thisEmpire) {
        this.thisEmpire = thisEmpire;
    }

    public void setGameMap(Map map) {
        this.map = map;
    }

    public Empire getThisEmpire() {
        return thisEmpire;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void run() {

    }

    public void nextTurn() {

    }

}
