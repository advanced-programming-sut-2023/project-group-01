package org.example.controller.mainMenuController.gameMenuController;

import org.example.model.Empire;
import org.example.model.People;
import org.example.model.User;
import org.example.model.building.Tile;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitState;
import org.example.view.enums.Outputs;
import org.example.view.mainMenu.gameMenu.MilitaryMenu;

public class MilitaryMenuController {
    private MilitaryMenu militaryMenu;
    private final Empire empire;
    private User player;

    public MilitaryMenuController(Empire empire, User player) {
        this.empire = empire;
        this.player = player;
    }

    public Outputs selectUnit(String x, String y) {
        Outputs outputs = commonOutPuts(x, y);
        if (!outputs.equals(Outputs.VALID_X_Y)) {
            militaryMenu.setSelectPeople(findMilitary(Integer.parseInt(x), Integer.parseInt(y)));
        }
        return outputs;
    }

    public Outputs moveUnit(String x, String y) {
        //TODO اگه پاترول داره نالش کن
        Outputs outputs = commonOutPuts(x, y);

        return null;
    }

    public Outputs patrolUnit(String x1, String y1, String x2, String y2) {
        if (x1 == null) {
            return Outputs.EMPTY_X1;
        } else if (x2 == null) {
            return Outputs.EMPTY_X2;
        } else if (y1 == null) {
            return Outputs.EMPTY_Y1;
        } else if (y2 == null) {
            return Outputs.EMPTY_Y2;
        } else if (!x1.matches("\\d+")) {
            return Outputs.INVALID_X1;
        } else if (!x2.matches("\\d+")) {
            return Outputs.INVALID_X2;
        } else if (!y1.matches("\\d+")) {
            return Outputs.INVALID_Y1;
        } else if (!y2.matches("\\d+")) {
            return Outputs.INVALID_Y2;
        } else {
            ((MilitaryUnit)(militaryMenu.getSelectedUnit())).setPatrolXY(Integer.parseInt(x1),Integer.parseInt(y1)
                    ,Integer.parseInt(x2),Integer.parseInt(y2));
            return Outputs.SUCCESSFUL_PATROL;
        }
    }

    public Outputs cancelPatrol() {
        if (militaryMenu.getSelectedUnit() == null) {
            return Outputs.EMPTY_SELECTED_UNIT;
        } else {
            return Outputs.SUCCESSFUL_CANCEL_PATROL;
        }
    }

    public Outputs setUnit(String x, String y, MilitaryUnit militaryUnit, String set) {
        //TODO خطا ها برسی شوند زمین بد
        // set -x [x] -y [y] -s [standing|defensive|offensive]
        if (militaryUnit == null) {
            return Outputs.NOT_HAVING_TROOP;
        } else if (!set.equals("standing") && !set.equals("defensive") && !set.equals("offensive")) {
            return Outputs.INVALID_UNIT_STATE;
        } else {
            if (set.equals("standing")) {
                militaryUnit.getMilitaryUnitName().setState(MilitaryUnitState.STANDING);
            } else if (set.equals("defensive")) {
                militaryUnit.getMilitaryUnitName().setState(MilitaryUnitState.DEFENSIVE);
            } else {
                militaryUnit.getMilitaryUnitName().setState(MilitaryUnitState.OFFENSIVE);
            }
            return Outputs.SUCCESSFUL_SET_STATE;
        }
    }

    public Outputs attack(Tile tile) {

        return null;
    }

    public Outputs pourOil(String direction) {

        return null;
    }

    public Outputs digTunnel(Tile tile) {

        return null;
    }

    public Outputs build(String equipmentName) {

        return null;
    }

    public Outputs commonOutPuts(String x, String y) {
        if (x == null) {
            return Outputs.EMPTY_X;
        } else if (y == null) {
            return Outputs.EMPTY_Y;
        } else if (!x.matches("\\d+")) {
            return Outputs.INVALID_X;
        } else if (!y.matches("\\d+")) {
            return Outputs.INVALID_Y;
        } else if (Integer.parseInt(x) > empire.getMap().getSize() || Integer.parseInt(y) > empire.getMap().getSize()) {
            return Outputs.OUT_OF_RANGE;
        } else if (findMilitary(Integer.parseInt(x), Integer.parseInt(y)) == null) {
            return Outputs.NOT_HAVING_TROOP;
        } else {
            return Outputs.VALID_X_Y;
        }
    }

    public MilitaryUnit findMilitary(int x, int y) {
        for (People people : empire.getMap().getTileWhitXAndY(x, y).getPeople()) {
            if (people instanceof MilitaryUnit && player.equals(people.getPlayer())) {
                return (MilitaryUnit) people;
            }
        }
        return null;
    }

    public void setMilitaryMenu(MilitaryMenu militaryMenu) {
        this.militaryMenu = militaryMenu;
    }
}