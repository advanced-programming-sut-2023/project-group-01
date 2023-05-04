package org.example.controller.mainMenuController.gameMenuController;

import org.example.BestPath;
import org.example.model.Empire;
import org.example.model.People;
import org.example.model.building.Building;
import org.example.model.building.Gatehouse;
import org.example.model.unit.Catapult;
import org.example.model.unit.CatapultName;
import org.example.model.unit.Engineer;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;
import org.example.model.unit.enums.MilitaryUnitState;
import org.example.view.enums.Outputs;
import org.example.view.mainMenu.gameMenu.GameMenu;
import org.example.view.mainMenu.gameMenu.MilitaryMenu;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;

public class MilitaryMenuController {
    private final GameMenu gameMenu;
    private MilitaryMenu militaryMenu;
    private final Empire empire;

    public MilitaryMenuController(Empire empire, MilitaryMenu militaryMenu, GameMenu gameMenu) {
        this.empire = empire;
        this.militaryMenu = militaryMenu;
        this.gameMenu = gameMenu;
    }

    public Outputs selectUnit(String x, String y) {
        Outputs outputs = commonOutPuts(x, y);
        if (outputs.equals(Outputs.VALID_X_Y)) {
            militaryMenu.setSelectedUnit(findMilitary(Integer.parseInt(x), Integer.parseInt(y), empire));
        }
        return outputs;
    }

    public Outputs moveUnit(String x, String y) {
        //TODO روی خونه ی تله برود
        Outputs outputs = commonOutPuts(x, y);
        if (outputs.equals(Outputs.VALID_X_Y)) {
            int xStart = militaryMenu.getSelectedUnit().get(0).getXPos();
            int yStart = militaryMenu.getSelectedUnit().get(0).getYPos();
            int xDestination = Integer.parseInt(x);
            int yDestination = Integer.parseInt(y);

            BestPath bestPath = new BestPath(empire);
            LinkedList<Integer> move = bestPath.input(empire.getMap().getMap(), xStart, yStart, xDestination, yDestination, false);

            if (move.size() == 0) {
                return Outputs.NO_WAY_TO_MOVE;
            }

            int length = 0;

            for (MilitaryUnit militaryUnit : militaryMenu.getSelectedUnit()) {
                if (move.size() <= militaryUnit.getMilitaryUnitName().getSpeed()) {
                    militaryUnit.goToDestination(xDestination, yDestination);
                } else {
                    int xDest = move.get(militaryUnit.getMilitaryUnitName().getSpeed()) / empire.getMap().getSize();
                    int yDest = move.get(militaryUnit.getMilitaryUnitName().getSpeed()) % empire.getMap().getSize();
                    militaryUnit.setDestination(xDestination, yDestination, xDest, yDest);
                }
                if (militaryUnit.getMilitaryUnitName().getSpeed() > length)
                    length = militaryUnit.getMilitaryUnitName().getSpeed();
            }
            gateHouse(move, length);
            return Outputs.SUCCESSFUL_MOVE;
        }
        return outputs;
    }

    public void gateHouse(LinkedList<Integer> move, int length) {
        //TODO وقتی می خوای از اری لیست ادرس بگیری باید آی را را ضربدر سایز کنی
        int x;
        int y;
        Building building;
        for (int i = 0; i < move.size(); i++) {
            x = move.get(i) / empire.getMap().getSize();
            y = move.get(i) % empire.getMap().getSize();
            building = empire.getMap().getTile(x, y).getBuilding();
            if (empire.getMap().getTile(x, y).getBuilding() != null && building instanceof Gatehouse) {
                ((Gatehouse) building).setEmpire(empire);
            }
        }
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
            int x1Patrol = Integer.parseInt(x1);
            int y1Patrol = Integer.parseInt(y1);
            int x2Patrol = Integer.parseInt(x2);
            int y2Patrol = Integer.parseInt(y2);

            BestPath bestPath = new BestPath(empire);
            LinkedList<Integer> patrol = bestPath.input(empire.getMap().getMap(), x1Patrol, y1Patrol, x2Patrol, y2Patrol, false);

            if (patrol.size() == 0) {
                return Outputs.NO_WAY_FOR_PATROL;
            }

            for (MilitaryUnit militaryUnit : militaryMenu.getSelectedUnit()) {
                if (militaryUnit.getEmpire().equals(empire)){
                    //TODO check for out of range
                    militaryUnit.goToPos(x1Patrol, y1Patrol);
                    militaryUnit.setPatrolXY(x1Patrol, y1Patrol, x2Patrol, y2Patrol);
                }
            }
            return Outputs.SUCCESSFUL_PATROL;
        }
    }

    public Outputs cancelPatrol() {
        if (militaryMenu.getSelectedUnit() == null) {
            return Outputs.EMPTY_SELECTED_UNIT;
        } else {
            for (MilitaryUnit militaryUnit : militaryMenu.getSelectedUnit())
                militaryUnit.cancelPatrol();
            return Outputs.SUCCESSFUL_CANCEL_PATROL;
        }
    }

    public Outputs setUnit(String x, String y, String set) {
        if (!commonOutPuts(x, y).equals(Outputs.VALID_X_Y)) {
            return commonOutPuts(x, y);
        }

        int xPos = Integer.parseInt(x);
        int yPos = Integer.parseInt(y);

        if (empire.getMap().getTile(xPos, yPos).findUnit(empire).size() == 0) {
            return Outputs.NOT_HAVING_TROOP;
        } else if (!set.equals("standing") && !set.equals("defensive") && !set.equals("offensive")) {
            return Outputs.INVALID_UNIT_STATE;
        } else {
            if (set.equals("standing")) {
                for (MilitaryUnit militaryUnit : militaryMenu.getSelectedUnit()) {
                    if (militaryUnit.getEmpire().equals(empire))
                        militaryUnit.getMilitaryUnitName().setState(MilitaryUnitState.STANDING);
                }
            } else if (set.equals("defensive")) {
                for (MilitaryUnit militaryUnit : militaryMenu.getSelectedUnit()) {
                    if (militaryUnit.getEmpire().equals(empire))
                        militaryUnit.getMilitaryUnitName().setState(MilitaryUnitState.DEFENSIVE);
                }
            } else {
                for (MilitaryUnit militaryUnit : militaryMenu.getSelectedUnit()) {
                    if (militaryUnit.getEmpire().equals(empire))
                        militaryUnit.getMilitaryUnitName().setState(MilitaryUnitState.OFFENSIVE);
                }
            }
            return Outputs.SUCCESSFUL_SET_STATE;
        }
    }

    public Outputs pourOil(String direction) {
        if (!(direction.equals("left") || direction.equals("right") || direction.equals("up") || direction.equals("down"))) {
            return Outputs.WRONG_POUR_OIL_DIRECTION;
        } else if (findPourOilers().size() == 0) {
            return Outputs.NO_ONE_TO_POUR_OIL;
        } else {
            return doPourOil(direction);
        }
    }

    private Outputs doPourOil(String direction) {

        for (MilitaryUnit militaryUnit : findPourOilers()) {
            //خالی کردن مخزن اب داغشون
        }

        int x = militaryMenu.getSelectedUnit().get(0).getXPos();
        int y = militaryMenu.getSelectedUnit().get(0).getYPos();
        //Set the position for each troop
        if (direction.equals("left") && y == 0) {
            return Outputs.OUT_OF_RANGE_POUR_OIL;
        } else if (direction.equals("left")) {
            empire.getMap().getTile(x, y - 1).removeAllUnit();
        } else if (direction.equals("right") && y == empire.getMap().getSize() - 1) {
            return Outputs.OUT_OF_RANGE_POUR_OIL;
        } else if (direction.equals("right")) {
            empire.getMap().getTile(x, y + 1).removeAllUnit();
        } else if (direction.equals("up") && x == 0) {
            return Outputs.OUT_OF_RANGE_POUR_OIL;
        } else if (direction.equals("up")) {
            empire.getMap().getTile(x - 1, y).removeAllUnit();
        } else if (direction.equals("down") && x == empire.getMap().getSize() - 1) {
            return Outputs.OUT_OF_RANGE_POUR_OIL;
        } else if (direction.equals("down")) {
            empire.getMap().getTile(x + 1, y).removeAllUnit();
        }
        return Outputs.SUCCESSFUL_POUR_OIL;
    }

    public ArrayList<MilitaryUnit> findPourOilers() {
        ArrayList<MilitaryUnit> pourOilers = new ArrayList<>();

        for (MilitaryUnit militaryUnit : militaryMenu.getSelectedUnit()) {
            //TODO this having oil
            if (militaryUnit instanceof Engineer) {
                pourOilers.add(militaryUnit);
            }
        }
        return pourOilers;
    }

    public Outputs digTunnel(String x, String y) {
        if (!commonOutPuts(x, y).equals(Outputs.VALID_X_Y)) {
            return commonOutPuts(x, y);
        }
        int xPos = Integer.parseInt(x);
        int yPos = Integer.parseInt(y);
        Building building = empire.getMap().getTile(xPos, yPos).getBuilding();

        ArrayList<MilitaryUnit> Tunneler = tunneler();

        if (Tunneler.size() == 0) {
            return Outputs.NOT_HAVING_TUNNELER;
        }

        int damage = Tunneler.size() * MilitaryUnitName.TUNNELER.getAttack();
        //TODO زنده ماندن تونلر ها

        if (building.getBuildingName().getHitPoint() > damage) {
            building.getBuildingName().reduceHitPoint(damage);
        } else {
            //TODO checks
            empire.getMap().getTile(xPos, yPos).setBuilding(null);
        }

        return null;
    }

    public ArrayList<MilitaryUnit> tunneler() {
        ArrayList<MilitaryUnit> Tunneler = new ArrayList<MilitaryUnit>();
        for (MilitaryUnit militaryUnit : militaryMenu.getSelectedUnit()) {
            if (militaryUnit.getMilitaryUnitName().getName().equals("Tunneler")) {
                Tunneler.add(militaryUnit);
            }
        }
        return Tunneler;
    }

    public Outputs build(String equipmentName) {
        ArrayList<Engineer> engineers = findEngineer();
        CatapultName catapultName = findCatapultName(equipmentName);

        if (catapultName == null) {
            return Outputs.INVALID_CATAPULT_NAME;
        } else if (catapultName.getCapacity() > engineers.size()) {
            return Outputs.NOT_ENOUGH_ENGINEER;
        } else {
            int x = militaryMenu.getSelectedUnit().get(0).getXPos();
            int y = militaryMenu.getSelectedUnit().get(0).getYPos();

            for (int i = 0; i < catapultName.getCapacity(); i++) {
                empire.getMap().getTile(x, y).removeUnit(engineers.get(i));
                catapultName.addEngineer(engineers.get(i));
            }
            Catapult catapult = new Catapult(empire.getMap().getTile(x, y), empire, x, y, catapultName);
            empire.getMap().getTile(x, y).addUnit(catapult);

            return Outputs.SUCCESSFUL_CATAPULT;
        }
    }


    public CatapultName findCatapultName(String name) {
        for (CatapultName catapultName : CatapultName.values()) {
            if (catapultName.getName().equals(name)) {
                return catapultName;
            }
        }
        return null;
    }

    public ArrayList<Engineer> findEngineer() {
        ArrayList<Engineer> engineers = new ArrayList<Engineer>();
        for (MilitaryUnit militaryUnit : militaryMenu.getSelectedUnit()) {
            if (militaryUnit instanceof Engineer) {
                engineers.add((Engineer) militaryUnit);
            }
        }
        return engineers;
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
        } else if (findMilitary(Integer.parseInt(x), Integer.parseInt(y), empire) == null) {
            return Outputs.NOT_HAVING_TROOP;
        } else {
            return Outputs.VALID_X_Y;
        }
    }

    public Outputs attack(String x, String y) {
        //TODO errors

        return null;
    }

    public static ArrayList<MilitaryUnit> findMilitary(int x, int y, Empire empire) {
        ArrayList<MilitaryUnit> militaryUnits = new ArrayList<>();
        for (People people : empire.getMap().getTile(x, y).getPeople()) {
            if (people instanceof MilitaryUnit && empire.equals(people.getEmpire())) {
                militaryUnits.add((MilitaryUnit) people);

            }
        }
        return militaryUnits;
    }

    public Outputs disbandUnit(int destX, int destY) {
        if (militaryMenu.getSelectedUnit() == null) {
            return Outputs.EMPTY_SELECTED_UNIT;
        }
        BestPath bestPath = new BestPath(empire);
        int x = militaryMenu.getSelectedUnit().get(0).getXPos();
        int y = militaryMenu.getSelectedUnit().get(0).getYPos();
        LinkedList<Integer> path = bestPath.input(getMap().getMap(), x, y, destX, destY, false);
        doDisband(x, y, path);

        return null;
    }

    public void doDisband(int destX, int destY, LinkedList<Integer> path) {
        for (MilitaryUnit militaryUnit : militaryMenu.getSelectedUnit()) {
            if (militaryUnit.getMilitaryUnitName().getSpeed() < path.size()) {
                militaryUnit.goToPos(destX, destY);
            } else {
                //TODO
            }
        }
    }
}