package org.example.controller;

import org.example.BestPath;
import org.example.controller.mainMenuController.gameMenuController.MilitaryMenuController;
import org.example.model.Empire;
import org.example.model.building.Tile;
import org.example.model.building.castleBuilding.CagedDogs;
import org.example.model.unit.MilitaryUnit;
import org.example.view.mainMenu.gameMenu.GameMenu;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class NextTurn {
    private ArrayList<Empire> empires;
    private final GameMenu gameMenu;
    private Empire currentEmpire;
    private LinkedHashMap<Tile, Tile> archerAttackingTile;
    private int numberOfEmpires;
    private int turnNumber;

    public NextTurn(GameMenu gameMenu) {
        this.archerAttackingTile = new LinkedHashMap<Tile, Tile>();
        this.gameMenu = gameMenu;
        this.turnNumber = 0;
    }

    public void addEmpire(Empire empire) {
        this.empires.add(empire);
        numberOfEmpires++;
    }

    public void archerAddAttackingTile(Tile tile1, Tile tile2) {
        archerAttackingTile.put(tile1, tile2);
    }

    public void nextTurn() {
        doRates();
        doOnAllTiles();
        checkEmpireExist();
        if (turnNumber == numberOfEmpires - 1) {
            turnNumber = 0;
            currentEmpire = empires.get(0);
        } else {
            turnNumber++;
            currentEmpire = empires.get(turnNumber);
        }
    }

    public void doOnAllTiles() {
        //TODO check i , j in getTile
        for (int i = 0; i < currentEmpire.getMap().getSize(); i++) {
            for (int j = 0; j < currentEmpire.getMap().getSize(); j++) {
                doOnAllMilitaryUnit(currentEmpire.getMap().getTile(i, j), i, j);
                doCageDogs(i, j);
            }
        }
    }

    public void doOnAllMilitaryUnit(Tile tile, int x, int y) {
        for (MilitaryUnit militaryUnit : tile.findUnit(currentEmpire)) {
            doAttack(x, y);
            doScoreMove(tile, militaryUnit);
            doPatrol(tile, militaryUnit);
        }
    }

    public void doRates() {
        //TODO
    }

    public void doCageDogs(int x, int y) {
        if (currentEmpire.getMap().getTile(x, y).getBuilding() != null &&
                currentEmpire.getMap().getTile(x, y).getBuilding() instanceof CagedDogs) {
            //TODO check the out of range
            for (int i = x - 10; i < x + 10; i++) {
                for (int j = y - 10; j < y + 10; j++) {
                    //TODO with attack
                }
            }
        }
    }

    public void checkEmpireExist() {
        ArrayList<Empire> removingEmpires = new ArrayList<Empire>();
        for (Empire empire : empires) {
            if (empire.getLord().getMilitaryUnitName().getHitPoint() <= 0) {
                //TODO have finalize method
                removingEmpires.add(empire);
            }
        }
        if (removingEmpires.size() > 0) empires.removeAll(removingEmpires);
    }

    public void doScoreMove(Tile tile, MilitaryUnit militaryUnit) {
        if (militaryUnit.getXPos() < currentEmpire.getMap().getSize() && militaryUnit.getYPos() < currentEmpire.getMap().getSize()) {
            BestPath bestPath = new BestPath(currentEmpire);

            LinkedList<Integer> path = bestPath.input(currentEmpire.getMap().getMap(), militaryUnit.getXPos(),
                    militaryUnit.getYPos(), militaryUnit.getXDestination(), militaryUnit.getYDestination(), false);

            if (path != null && path.size() > 0) {
                movingProcess(path, militaryUnit);
            }
        }
    }

    public void movingProcess(LinkedList<Integer> move, MilitaryUnit unit) {
        if (move.size() <= unit.getMilitaryUnitName().getSpeed()) {
            unit.goToDestination(unit.getXDestination(), unit.getYDestination());
        } else {
            //TODO set the number that delete elements of path for each unit
            int xDest = move.get(unit.getMilitaryUnitName().getSpeed()) / currentEmpire.getMap().getSize();
            int yDest = move.get(unit.getMilitaryUnitName().getSpeed()) % currentEmpire.getMap().getSize();
            unit.setDestination(unit.getXDestination(), unit.getYDestination(), xDest, yDest);
        }
    }

    public void doPatrol(Tile tile, MilitaryUnit militaryUnit) {
        //TODO reverse the xPos and yPos with xDestination and yDestination
        if (militaryUnit.getXPos() < currentEmpire.getMap().getSize() && militaryUnit.getYPos() < currentEmpire.getMap().getSize()) {
            BestPath bestPath = new BestPath(currentEmpire);
            LinkedList<Integer> path = bestPath.input(currentEmpire.getMap().getMap(), militaryUnit.getXPos(),
                    militaryUnit.getYPos(), militaryUnit.getXPos(), militaryUnit.getYPos(), false);
            if (path != null && path.size() > 0) {
                movePatrol(path, militaryUnit);
            }
        }
    }

    private void movePatrol(LinkedList<Integer> move, MilitaryUnit militaryUnit) {
        if (move.size() <= militaryUnit.getMilitaryUnitName().getSpeed()) {
            militaryUnit.goToDestination(militaryUnit.getXDestination(), militaryUnit.getYDestination());
            militaryUnit.changePatrol();
        } else {
            int xDest = move.get(militaryUnit.getMilitaryUnitName().getSpeed()) / currentEmpire.getMap().getSize();
            int yDest = move.get(militaryUnit.getMilitaryUnitName().getSpeed()) % currentEmpire.getMap().getSize();
            militaryUnit.movePatrol(xDest, yDest);
        }
    }

        //TODO روی تایل ها اتک میزنیم
        //TODO سربازای داخل یه خونه میجنگن
        //TODO راندمان گزاشتن برای سربازها
        //TODO افزایش راندمان اتک
        //TODO سربازای کماندار موقع حرکت چقدر برن جلو؟
        //TODO some checks

    private void doArcherAttack() {

    }

    public void doAttack(int x, int y) {
        boolean[] fire = new boolean[empires.size()];
        ArrayList<MilitaryUnit> empire1;
        ArrayList<MilitaryUnit> empire2;

        for (int i = 0; i < empires.size(); i++) {
            empire1 = MilitaryMenuController.findMilitary(x, y, empires.get(i));
            if (empire1.size() > 0) {
                for (int j = 0; j < empires.size(); j++) {
                    empire2 = MilitaryMenuController.findMilitary(x, y, empires.get(j));
                    if (empire2.size() > 0 && !fire[j]) {
                        attackTwoTroop(empire1, empire2);
                    }
                }
            }
            fire[i] = true;
        }
    }

    private void attackBuildings() {

    }
    public void doOffensiveAttack(MilitaryUnit militaryUnit) {
        //TODO در اتک عادی اینا باید هندل کنی
    }

    private void attackTwoTroop(ArrayList<MilitaryUnit> militaryUnitEmpire1, ArrayList<MilitaryUnit> militaryUnitEmpire2) {
        int damageEmpire1 = 0;
        int damageEmpire2 = 0;

        for (MilitaryUnit militaryUnit : militaryUnitEmpire1)
            damageEmpire1 += militaryUnit.getMilitaryUnitName().getAttack();
        for (MilitaryUnit militaryUnit : militaryUnitEmpire2)
            damageEmpire2 += militaryUnit.getMilitaryUnitName().getAttack();

        damageEmpire1 /= militaryUnitEmpire2.size();
        damageEmpire2 /= militaryUnitEmpire1.size();

        for (MilitaryUnit militaryUnit : militaryUnitEmpire1) {
            if (militaryUnit.getMilitaryUnitName().getHitPoint() > damageEmpire2) {
                militaryUnit.getMilitaryUnitName().reduceHitPoint(damageEmpire2);
            } else {
                //TODO remove the troop
            }
        }
        for (MilitaryUnit militaryUnit : militaryUnitEmpire2) {
            if (militaryUnit.getMilitaryUnitName().getHitPoint() > damageEmpire1) {
                militaryUnit.getMilitaryUnitName().reduceHitPoint(damageEmpire1);
            } else {
                //TODO remove the troop
            }
        }
    }

//        public void doAttack(String x, String y) {
//        //TODO check some error
//        //TODO check two type of attack
//        //TODO catapult attack
//        ArrayList<Empire> empires = gameMenu.getEmpires();
//
//        boolean[] fire = new boolean[empires.size()];
//        ArrayList<MilitaryUnit> empire1;
//        ArrayList<MilitaryUnit> empire2;
//
//        for (int i = 0; i < empires.size(); i++) {
//            empire1 = findMilitary(Integer.parseInt(x), Integer.parseInt(y), empires.get(i));
//            if (empire1.size() > 0) {
//                for (int j = 0; j < empires.size(); j++) {
//                    empire2 = findMilitary(Integer.parseInt(x), Integer.parseInt(y), empires.get(j));
//                    if (empire2.size() > 0 && !fire[j]) {
//                        attackTwoTroop(empire1, empire2);
//                    }
//                }
//            }
//            fire[i] = true;
//        }
//    }

    public Empire getCurrentEmpire() {
        return currentEmpire;
    }
}
