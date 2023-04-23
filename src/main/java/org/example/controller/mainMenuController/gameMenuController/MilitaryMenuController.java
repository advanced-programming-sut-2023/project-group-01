package org.example.controller.mainMenuController.gameMenuController;

import org.example.model.Empire;
import org.example.model.People;
import org.example.model.User;
import org.example.model.building.Tile;
import org.example.model.unit.MilitaryUnit;
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

    public Outputs moveUnit(int x, int y, MilitaryUnit militaryUnit) {


        return null;
    }

    public Outputs patrolUnit(int x1, int y1, int x2, int y2) {

        return null;
    }

    public Outputs setUnit(String set) {

        return null;
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
        for (People people : empire.getMap().getTile(x, y).getPeople()) {
            if (people instanceof MilitaryUnit && player.equals(people.getPlayer())) {
                return (MilitaryUnit) people;
            }
        }
        return null;
    }
}