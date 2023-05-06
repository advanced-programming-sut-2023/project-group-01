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
        if (findMilitary(Integer.parseInt(x), Integer.parseInt(y), empire).size() == 0)
            return Outputs.NOT_HAVING_TROOP;
        else if (outputs.equals(Outputs.VALID_X_Y))
            militaryMenu.setSelectedUnit(findMilitary(Integer.parseInt(x), Integer.parseInt(y), empire));
        return outputs;
    }

    public Outputs moveUnit(String x, String y) {
        Outputs outputs = commonOutPuts(x, y);
        if (!outputs.equals(Outputs.VALID_X_Y)) return outputs;

        int xStart = militaryMenu.getSelectedUnit().get(0).getXPos();
        int yStart = militaryMenu.getSelectedUnit().get(0).getYPos();
        int xDestination = Integer.parseInt(x);
        int yDestination = Integer.parseInt(y);

//        System.out.println("xStart : " + xStart);
//        System.out.println("yStart : " + yStart);
//        System.out.println("xDestination : " + xDestination);
//        System.out.println("yDestination : " + yDestination);
//        System.out.println();

        BestPath bestPath = new BestPath(empire);

        LinkedList<Integer> move = bestPath.input(getMap().getMap(), xStart, yStart, xDestination, yDestination, false);
        if ((move == null || move.size() == 0) && !(xStart == xDestination && yStart == yDestination)) return Outputs.NO_WAY_TO_MOVE;

        moving(xStart, yStart, xDestination, yDestination, empire);
        //TODO
//        System.out.println("x : " + militaryMenu.getSelectedUnit().get(0).getXPos());
//        System.out.println("y : " + militaryMenu.getSelectedUnit().get(0).getYPos());
//        System.out.println();
        return Outputs.SUCCESSFUL_MOVE;
    }

    public static void moving(int xStart, int yStart, int xDestination, int yDestination, Empire empire) {
        BestPath bestPath = new BestPath(empire);
        LinkedList<Integer> move = bestPath.input(getMap().getMap(), xStart, yStart, xDestination, yDestination, false);
        int maxLength = 0;
        if (move == null || move.size() == 0) return;
        int dx = xDestination - xStart;
        int dy = yDestination - yStart;
        int distance = dx + dy;
    //(int) Math.floor(Math.sqrt(dx * dx + dy * dy))
        for (MilitaryUnit militaryUnit : getMap().getTile(xStart, yStart).findUnit(empire)) {
            if (move.size() <= militaryUnit.getMilitaryUnitName().getSpeed()) {
                if (militaryUnit.getMilitaryUnitName().getGunshot() > 0) {     //TODO check
                    if (distance > militaryUnit.getMilitaryUnitName().getGunshot())
                        militaryUnit.goToDestination(xDestination, yDestination);
                } else militaryUnit.goToDestination(xDestination, yDestination);
            } else {
                int xDest = move.get(militaryUnit.getMilitaryUnitName().getSpeed()) / getMap().getSize();
                int yDest = move.get(militaryUnit.getMilitaryUnitName().getSpeed()) % getMap().getSize();
                if (militaryUnit.getMilitaryUnitName().getGunshot() > 0) {
                    if (distance > militaryUnit.getMilitaryUnitName().getGunshot())
                        militaryUnit.setDestination(xDest, yDest, xDestination, yDestination);
                } else militaryUnit.setDestination(xDest, yDest, xDestination, yDestination);
            }
            if (militaryUnit.getMilitaryUnitName().getSpeed() > maxLength && militaryUnit.getMilitaryUnitName().getSpeed() <= move.size())
                maxLength = militaryUnit.getMilitaryUnitName().getSpeed();
        }

        gateHouse(move, maxLength, empire);
    }

    private static void gateHouse(LinkedList<Integer> move, int length, Empire empire) {
        //TODO وقتی می خوای از اری لیست ادرس بگیری باید آی را را ضربدر سایز کنی
        int x;
        int y;
        Building building;
        for (int i = 0; i < length; i++) {
            x = move.get(i) / getMap().getSize();
            y = move.get(i) % getMap().getSize();
            building = getMap().getTile(x, y).getBuilding();
            if (getMap().getTile(x, y).getBuilding() != null && building instanceof Gatehouse) {
                building.setEmpire(empire);
            }
        }
    }

    public Outputs patrolUnit(String x1, String y1, String x2, String y2) {
        if (x1 == null) return Outputs.EMPTY_X1;
        else if (x2 == null) return Outputs.EMPTY_X2;
        else if (y1 == null) return Outputs.EMPTY_Y1;
        else if (y2 == null) return Outputs.EMPTY_Y2;
        else if (!x1.matches("\\d+")) return Outputs.INVALID_X1;
        else if (!x2.matches("\\d+")) return Outputs.INVALID_X2;
        else if (!y1.matches("\\d+")) return Outputs.INVALID_Y1;
        else if (!y2.matches("\\d+")) return Outputs.INVALID_Y2;

        int x1Patrol = Integer.parseInt(x1);
        int y1Patrol = Integer.parseInt(y1);
        int x2Patrol = Integer.parseInt(x2);
        int y2Patrol = Integer.parseInt(y2);

        BestPath bestPath = new BestPath(empire);
        LinkedList<Integer> patrol = bestPath.input(getMap().getMap(), x1Patrol, y1Patrol, x2Patrol, y2Patrol, false);
        if (patrol.size() == 0) return Outputs.NO_WAY_FOR_PATROL;

        for (MilitaryUnit militaryUnit : militaryMenu.getSelectedUnit())
            if (militaryUnit.getEmpire().equals(empire)) {
                militaryUnit.goToPos(x1Patrol, y1Patrol);
                militaryUnit.setPatrolXY(x1Patrol, y1Patrol, x2Patrol, y2Patrol);
            }
        return Outputs.SUCCESSFUL_PATROL;
    }

    public Outputs cancelPatrol() {
        if (militaryMenu.getSelectedUnit() == null)
            return Outputs.EMPTY_SELECTED_UNIT;
        for (MilitaryUnit militaryUnit : militaryMenu.getSelectedUnit())
            militaryUnit.cancelPatrol();
        return Outputs.SUCCESSFUL_CANCEL_PATROL;

    }

    public Outputs setUnit(String x, String y, String set) {
        if (!commonOutPuts(x, y).equals(Outputs.VALID_X_Y)) return commonOutPuts(x, y);

        int xPos = Integer.parseInt(x);
        int yPos = Integer.parseInt(y);

        if (getMap().getTile(xPos, yPos).findUnit(empire).size() == 0)
            return Outputs.NOT_HAVING_TROOP;
        else if (!set.equals("standing") && !set.equals("defensive") && !set.equals("offensive"))
            return Outputs.INVALID_UNIT_STATE;

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

        }

        int x = militaryMenu.getSelectedUnit().get(0).getXPos();
        int y = militaryMenu.getSelectedUnit().get(0).getYPos();
        //Set the position for each troop
        if (direction.equals("left") && y == 0) {
            return Outputs.OUT_OF_RANGE_POUR_OIL;
        } else if (direction.equals("left")) {
            getMap().getTile(x, y - 1).removeAllUnit();
        } else if (direction.equals("right") && y == getMap().getSize() - 1) {
            return Outputs.OUT_OF_RANGE_POUR_OIL;
        } else if (direction.equals("right")) {
            getMap().getTile(x, y + 1).removeAllUnit();
        } else if (direction.equals("up") && x == 0) {
            return Outputs.OUT_OF_RANGE_POUR_OIL;
        } else if (direction.equals("up")) {
            getMap().getTile(x - 1, y).removeAllUnit();
        } else if (direction.equals("down") && x == getMap().getSize() - 1) {
            return Outputs.OUT_OF_RANGE_POUR_OIL;
        } else if (direction.equals("down")) {
            getMap().getTile(x + 1, y).removeAllUnit();
        }
        return Outputs.SUCCESSFUL_POUR_OIL;
    }

    public ArrayList<MilitaryUnit> findPourOilers() {
        ArrayList<MilitaryUnit> pourOilers = new ArrayList<>();

        for (MilitaryUnit militaryUnit : militaryMenu.getSelectedUnit())
            if (militaryUnit instanceof Engineer)
                pourOilers.add(militaryUnit);
        return pourOilers;
    }

    public Outputs digTunnel(String x, String y) {
        if (!commonOutPuts(x, y).equals(Outputs.VALID_X_Y)) return commonOutPuts(x, y);

        int xPos = Integer.parseInt(x);
        int yPos = Integer.parseInt(y);
        Building building = getMap().getTile(xPos, yPos).getBuilding();

        ArrayList<MilitaryUnit> Tunneler = tunneler();

        if (Tunneler.size() == 0) return Outputs.NOT_HAVING_TUNNELER;

        int damage = Tunneler.size() * MilitaryUnitName.TUNNELER.getAttack();

        if (building.getBuildingName().getHitPoint() > damage)
            building.getBuildingName().reduceHitPoint(damage);
        else getMap().getTile(xPos, yPos).setBuilding(null);

        return null;
    }

    public ArrayList<MilitaryUnit> tunneler() {
        ArrayList<MilitaryUnit> Tunneler = new ArrayList<MilitaryUnit>();
        for (MilitaryUnit militaryUnit : militaryMenu.getSelectedUnit())
            if (militaryUnit.getMilitaryUnitName().getName().equals("Tunneler"))
                Tunneler.add(militaryUnit);
        return Tunneler;
    }

    public Outputs build(String equipmentName) {
        ArrayList<Engineer> engineers = findEngineer();
        CatapultName catapultName = findCatapultName(equipmentName);

        if (catapultName == null)
            return Outputs.INVALID_CATAPULT_NAME;
        else if (catapultName.getCapacity() > engineers.size())
            return Outputs.NOT_ENOUGH_ENGINEER;

        int x = militaryMenu.getSelectedUnit().get(0).getXPos();
        int y = militaryMenu.getSelectedUnit().get(0).getYPos();

        for (int i = 0; i < catapultName.getCapacity(); i++) {
            getMap().getTile(x, y).removeUnit(engineers.get(i));
            catapultName.addEngineer(engineers.get(i));
        }
        Catapult catapult = new Catapult(getMap().getTile(x, y), empire, x, y, catapultName);
        getMap().getTile(x, y).addUnit(catapult);

        return Outputs.SUCCESSFUL_CATAPULT;

    }


    public CatapultName findCatapultName(String name) {
        for (CatapultName catapultName : CatapultName.values())
            if (catapultName.getName().equals(name))
                return catapultName;
        return null;
    }

    public ArrayList<Engineer> findEngineer() {
        ArrayList<Engineer> engineers = new ArrayList<Engineer>();
        for (MilitaryUnit militaryUnit : militaryMenu.getSelectedUnit())
            if (militaryUnit instanceof Engineer)
                engineers.add((Engineer) militaryUnit);
        return engineers;
    }

    public Outputs commonOutPuts(String x, String y) {
        if (x == null) return Outputs.EMPTY_X;
        else if (y == null) return Outputs.EMPTY_Y;
        else if (!x.matches("\\d+")) return Outputs.INVALID_X;
        else if (!y.matches("\\d+")) return Outputs.INVALID_Y;
        else if (Integer.parseInt(x) > getMap().getSize() || Integer.parseInt(y) > getMap().getSize())
            return Outputs.OUT_OF_RANGE;
        else return Outputs.VALID_X_Y;
    }

    public Outputs attack(String x, String y) {
        Outputs outputs = commonOutPuts(x, y);
        if (!outputs.equals(Outputs.VALID_X_Y)) return outputs;
        else if (militaryMenu.getSelectedUnit() == null) return Outputs.EMPTY_SELECTED_UNIT;
        else if (!checkEnemyExistance(Integer.parseInt(x), Integer.parseInt(y))) return Outputs.NO_EXISTANCE_FOR_ENEMY;

        int xAttack = Integer.parseInt(x);
        int yAttack = Integer.parseInt(y);
        int xStart = militaryMenu.getSelectedUnit().get(0).getXPos();
        int yStart = militaryMenu.getSelectedUnit().get(0).getYPos();
        moving(xStart, yStart, xAttack, yAttack, empire);
        return Outputs.SUCCESSFUL_ATTACK;
    }

    private boolean checkEnemyExistance(int x, int y) {
        for (People person : getMap().getTile(x, y).getPeople()) {
            if (person instanceof MilitaryUnit && !person.getEmpire().equals(empire))
                return true;
        }
        return false;
    }

    public static ArrayList<MilitaryUnit> findMilitary(int x, int y, Empire empire) {
        ArrayList<MilitaryUnit> militaryUnits = new ArrayList<>();
        for (People people : getMap().getTile(x, y).getPeople())
            if (people instanceof MilitaryUnit && empire.equals(people.getEmpire()))
                militaryUnits.add((MilitaryUnit) people);
        return militaryUnits;
    }

    public Outputs disbandUnit(int destX, int destY) {
        if (militaryMenu.getSelectedUnit() == null) return Outputs.EMPTY_SELECTED_UNIT;

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