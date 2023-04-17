package org.example.view.mainMenu.gameMenu;

import org.example.model.building.Building;
import org.example.model.Empire;

import java.util.regex.Matcher;

public class BuildingMenu{

    private final Empire empire;

    private Building selectedBuilding;

    public BuildingMenu(Empire empire){
        this.empire = empire;
    }

    public void setBuilding(Building selectedBuilding) {
        this.selectedBuilding = selectedBuilding;
    }

    public void run() {

    }

    public void selectBuildingChecker(Matcher matcher) {

    }

    public void dropBuildingChecker(Matcher matcher) {

    }

    public void createUnitChecker(Matcher matcher) {

    }

    public void repairChecker() {

    }


}
