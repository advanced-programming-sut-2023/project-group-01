package org.example.view.mainMenu.gameMenu;

import org.example.model.Empire;
import org.example.model.People;

import java.util.regex.Matcher;

public class MilitaryMenu {

    private final Empire empire;

    private People selectedUnit;

    public MilitaryMenu(Empire empire){
        this.empire = empire;
    }

    public void setSelectedUnit(People selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public void run() {

    }

    public void subRun(){

    }

    public void selectUnitChecker(Matcher matcher){

    }

    public void moveUnitChecker(Matcher matcher){

    }

    public void patrolUnitChecker(Matcher matcher){

    }

    public void setUnitChecker(Matcher matcher){

    }

    public void attackChecker(Matcher matcher){

    }

    public void pourOilChecker(Matcher matcher){

    }

    public void digTunnelChecker(Matcher matcher){

    }

    public void disbandUnit(){

    }

    public void buildChecker(Matcher matcher){

    }

}