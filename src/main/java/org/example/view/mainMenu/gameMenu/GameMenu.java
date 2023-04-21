package org.example.view.mainMenu.gameMenu;

import org.example.model.Empire;
import org.example.model.Map;

import java.util.ArrayList;
import java.util.Scanner;

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

    public void run(Scanner scanner) {
        map =  new CreateMapMenu().run(scanner);
    }

    public void nextTurn() {

    }

}
