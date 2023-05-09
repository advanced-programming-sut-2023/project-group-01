package org.example.controller;

import org.example.BestPath;
import org.example.model.Empire;
import org.example.model.People;
import org.example.model.building.Building;
import org.example.model.building.Tile;
import org.example.model.building.castleBuilding.*;
import org.example.model.unit.Catapult;
import org.example.model.unit.CatapultName;
import org.example.model.unit.LadderMen;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;
import org.example.view.mainMenu.gameMenu.GameMenu;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;

public class NextTurn {
    private ArrayList<Empire> empires;
    private final GameMenu gameMenu;
    private Empire currentEmpire;
    private int numberOfEmpires;
    private int turnNumber;
    private int score = 0;

    public NextTurn(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
        this.turnNumber = 0;
    }

    public void addEmpire(Empire empire) {
        this.empires.add(empire);
        numberOfEmpires++;
    }

    public void nextTurn() {
        if (turnNumber == numberOfEmpires - 1) {
            turnNumber = 0;
            currentEmpire = empires.get(0);
            doRates();
            doOnAllTiles();
            checkEmpireExist();
            doFornextTurn();
        } else {
            turnNumber++;
            currentEmpire = empires.get(turnNumber);
        }
    }

    public void doOnAllTiles() {
        for (int i = 0; i < getMap().getSize(); i++) {
            for (int j = 0; j < getMap().getSize(); j++) {
                doCageDogs(i, j);
                doOnAllMilitaryUnit(getMap().getTile(i, j), i, j);
            }
        }
        for (int i = 0; i < getMap().getSize(); i++) {
            for (int j = 0; j < getMap().getSize(); j++) {
                attack(getMap().getTile(i, j), i, j);
            }
        }
    }

    private void doCageDogs(int x, int y) {
        Tile tile = getMap().getTile(x, y);
        if (tile.getBuilding() != null && tile.getBuilding() instanceof CagedDogs) {
            for (int k = 0; k < 5; k++) {
                if (findEnemyInDogsRange(x, y, k)) return;
            }
        }
    }

    private boolean findEnemyInDogsRange(int x, int y, int k) {
        ArrayList<MilitaryUnit> enemyMilitaryUnit;

        for (int i = x - k; i < x + k; i++) {
            if ((enemyMilitaryUnit = getMap().getTile(i, y - k).findNearEnemiesMilitaryUnit(currentEmpire)) != null) {
                enemyMilitaryUnit.get(0).getMilitaryUnitName().reduceHitPoint(160);
                if (enemyMilitaryUnit.get(0).getMilitaryUnitName().getHitPoint() < 0)
                    enemyMilitaryUnit.get(0).removeUnit();
                getMap().getTile(x, y).getBuilding().removeBuilding();
                return true;
            }
        }
        for (int j = y - k; j < y + k; j++) {
            if ((enemyMilitaryUnit = getMap().getTile(x - k, j).findNearEnemiesMilitaryUnit(currentEmpire)) != null) {
                enemyMilitaryUnit.get(0).getMilitaryUnitName().reduceHitPoint(160);
                if (enemyMilitaryUnit.get(0).getMilitaryUnitName().getHitPoint() < 0)
                    enemyMilitaryUnit.get(0).removeUnit();
                getMap().getTile(x, y).getBuilding().removeBuilding();
                return true;
            }
        }
        for (int i = x - k; i < x + k; i++) {
            if ((enemyMilitaryUnit = getMap().getTile(i, y + k).findNearEnemiesMilitaryUnit(currentEmpire)) != null) {
                enemyMilitaryUnit.get(0).getMilitaryUnitName().reduceHitPoint(160);
                if (enemyMilitaryUnit.get(0).getMilitaryUnitName().getHitPoint() < 0)
                    enemyMilitaryUnit.get(0).removeUnit();
                getMap().getTile(x, y).getBuilding().removeBuilding();
                return true;
            }
        }
        for (int j = y - k; j < y + k; j++) {
            if ((enemyMilitaryUnit = getMap().getTile(x + k, j).findNearEnemiesMilitaryUnit(currentEmpire)) != null) {
                enemyMilitaryUnit.get(0).getMilitaryUnitName().reduceHitPoint(160);
                if (enemyMilitaryUnit.get(0).getMilitaryUnitName().getHitPoint() < 0)
                    enemyMilitaryUnit.get(0).removeUnit();
                getMap().getTile(x, y).getBuilding().removeBuilding();
                return true;
            }
        }
        return false;
    }

    private void checkEmpireExist() {
        ArrayList<Empire> removingEmpires = new ArrayList<Empire>();
        for (Empire empire : empires)
            if (empire.getLord().getMilitaryUnitName().getHitPoint() <= 0) {
                removingEmpires.add(empire);
                empire.getPlayer().setHighScore( empire.getPlayer().getHighScore() + score);
                score += 200;
            }
        if (removingEmpires.size() > 0) empires.removeAll(removingEmpires);
    }

    public void doRates() {
        //TODO
    }


    public void doOnAllMilitaryUnit(Tile tile, int x, int y) {
        int xDest;
        int yDest;
        Building building;
        for (Empire empire : empires) {
            for (MilitaryUnit unit : tile.findUnit(empire)) {
                xDest = unit.getXDestination();
                yDest = unit.getYDestination();
                building = getMap().getTile(xDest, yDest).getBuilding();
                if (building == null && !unit.isMoved())
                    callMoveFunctions(unit, false);
                else if (building != null && !unit.isMoved() && unit.getMilitaryUnitName().equals(MilitaryUnitName.ASSASSINS))
                    callMoveFunctions(unit, true);

            }
        }
    }

    private void callMoveFunctions(MilitaryUnit unit, boolean bool) {
        if (unit instanceof Catapult) {
            MoveToXYForCatapult((Catapult) unit, bool);
            return;
        }

        int xDest;
        int yDest;
        if (unit.getXDestination() < getMap().getSize() && unit.getYDestination() < getMap().getSize()) {
            xDest = unit.getXDestination();
            yDest = unit.getYDestination();
        } else if (unit.getPatrolX1() < getMap().getSize() && unit.getPatrolY1() < getMap().getSize()) {
            xDest = unit.getPatrolX2();
            yDest = unit.getPatrolY2();
        } else {
            return;
        }
        MoveToXY(unit, false, xDest, yDest);

    }

    private void MoveToXY(MilitaryUnit unit, boolean haveBuilding, int xDest, int yDest) {
        int xPos = unit.getXPos(), yPos = unit.getYPos();
        boolean isAssassins = unit.getMilitaryUnitName().equals(MilitaryUnitName.ASSASSINS);
        BestPath bestPath = new BestPath(unit.getEmpire());
        LinkedList<Integer> path;
        path = bestPath.input(getMap().getMap(), xPos, yPos, xDest, yDest, false, isAssassins);
        int XY = 0;
        int size = path.size();
        int mapSize = getMap().getSize();

        if (!isAssassins && haveBuilding)
            for (int i = size - 1; i > 0; i--)
                if (getMap().getTile(path.get(i) / mapSize, path.get(i) % mapSize).getBuilding() != null)
                    XY = path.remove();
        boolean havePatrol = unit.getPatrolX1() < mapSize;
        //TODO check
        if (path == null || path.size() == 0) return;
        int counter = 0;
        for (Integer integer : path) {
            if (isMoveFinished(unit, integer, counter, havePatrol, xDest, yDest)) return;
            counter++;
        }

        Building building = getMap().getTile(XY / mapSize, XY % mapSize).getBuilding();
        //TODO check
        if (unit instanceof LadderMen && unit.getXDestination() < mapSize && unit.getYDestination() < mapSize &&
                building != null && building instanceof Wall)
            ((Wall) building).setHaveLadder(true);
        unit.setMoved();
    }

    private boolean isMoveFinished(MilitaryUnit unit, int integer, int counter, boolean havePatrol, int xDest, int yDest) {
        int mapSize = getMap().getSize();
        unit.goToPos(integer / mapSize, integer % mapSize);
        Building building = getMap().getTile(integer / mapSize, integer % mapSize).getBuilding();

        if (building != null && building instanceof KillingPits) {
            if (unit.getMilitaryUnitName().getHitPoint() > ((KillingPits) building).getDamage()) {
                unit.getMilitaryUnitName().reduceHitPoint(((KillingPits) building).getDamage());
                ((KillingPits) building).setUsed();
            } else {
                unit.removeUnit();
                ((KillingPits) building).setUsed();
                return true;
            }
        }
        if (getMap().getTile(unit.getXPos(), unit.getYPos()).findNearEnemiesMilitaryUnit(unit.getEmpire()).size() != 0) {
            unit.goToPos(unit.getXPos(), unit.getYPos());
            return true;
        }
        if (unit.getXPos() == xDest && unit.getYPos() == yDest) {
            if (havePatrol) {
                unit.goToPos(xDest, yDest);
                unit.changePatrol();
            } else {
                unit.goToDestination(xDest, yDest);
            }
            return true;
        }
        if (counter >= unit.getMilitaryUnitName().getSpeed()) {
            unit.goToPos(unit.getXPos(), unit.getYPos());
            return true;
        }
        if (unit.getMilitaryUnitName().getGunshot() > 0 &&
                findEnemyInTheBoardOfArcher(unit).size() != 0) {
            unit.goToPos(unit.getXPos(), unit.getYPos());
            return true;
        }
        return false;
    }

    private ArrayList<MilitaryUnit> findEnemyInTheBoardOfArcher(MilitaryUnit unit) {
        //TODO check
        int gunShot = unit.getMilitaryUnitName().getGunshot();
        int x = unit.getXPos();
        int y = unit.getYPos();
        ArrayList<MilitaryUnit> enemy = new ArrayList<>();

        for (int k = 0; k < gunShot; k++) {
            for (int i = x - k; i < x + k; i++) {
                for (int j = y - k; j < y + k; j++) {
                    if ((enemy = getMap().getTile(i, j).findNearEnemiesMilitaryUnit(unit.getEmpire())).size() != 0) {
                        return enemy;
                    }
                }
            }
        }
        return enemy;
    }

    private void MoveToXYForCatapult(Catapult catapult, boolean haveBuilding) {
        if (!catapult.getCatapultName().getCanMove()) return;
        if (catapult.getCatapultName().getCanAttackUnit() && findEnemyInTheBoardOfCatapult(catapult).size() != 0)
            return;
        if (!catapult.getCatapultName().getCanAttackUnit() && findEnemyBuildingInTheBoardOfCatapult(catapult) != null)
            return;

        int xPos = catapult.getXPos(), yPos = catapult.getYPos();
        int xDest = catapult.getXDestination(), yDest = catapult.getYDestination();

        BestPath bestPath = new BestPath(catapult.getEmpire());
        LinkedList<Integer> path;
        if (catapult.getMilitaryUnitName().equals(MilitaryUnitName.ASSASSINS))
            path = bestPath.input(getMap().getMap(), xPos, yPos, xDest, yDest, false, true);
        else
            path = path = bestPath.input(getMap().getMap(), xPos, yPos, xDest, yDest, true, false);

        int XY = 0;
        int size = path.size();
        int mapSize = getMap().getSize();

        for (int i = size - 1; i > 0; i--)
            if (getMap().getTile(path.get(i) / mapSize, path.get(i) % mapSize).getBuilding() != null)
                XY = path.remove();

        if (path == null || path.size() == 0)
            return;

        int counter = 0;

        for (Integer integer : path) {
            if (isCatapultMoveFinished(catapult, integer, counter)) return;
            counter++;
        }
        int xFinal = XY / mapSize;
        int yFinal = XY % mapSize;
        Building building = getMap().getTile(xFinal, yFinal).getBuilding();
        //TODO check
        catapult.setMoved();
    }

    private boolean isCatapultMoveFinished(Catapult catapult, Integer integer, int counter) {
        int mapSize = getMap().getSize();
        catapult.goToPos(integer / mapSize, integer % mapSize);
        Building building = getMap().getTile(integer / mapSize, integer % mapSize).getBuilding();

        if (building != null && building instanceof KillingPits) {
            if (catapult.getMilitaryUnitName().getHitPoint() > ((KillingPits) building).getDamage()) {
                catapult.getMilitaryUnitName().reduceHitPoint(((KillingPits) building).getDamage());
                ((KillingPits) building).setUsed();
            } else {
                catapult.removeUnit();
                ((KillingPits) building).setUsed();
                return true;
            }
        }
        if (catapult.getCatapultName().getCanAttackUnit() && getMap().getTile(catapult.getXPos(), catapult.getYPos()).
                findNearEnemiesMilitaryUnit(catapult.getEmpire()).size() != 0) {
            catapult.goToDestination(catapult.getXPos(), catapult.getYPos());
            return true;
        }
        if (catapult.getXPos() == catapult.getXDestination() && catapult.getYPos() == catapult.getYDestination()) {
            catapult.goToDestination(catapult.getXDestination(), catapult.getYDestination());
            return true;
        }
        if (counter >= catapult.getCatapultName().getSpeed()) {
            catapult.goToDestination(catapult.getXPos(), catapult.getYPos());
            return true;
        }
        if (!catapult.getCatapultName().getCanAttackUnit() && catapult.getCatapultName().getFireRange() > 0 &&
                findEnemyBuildingInTheBoardOfCatapult(catapult) != null) {
            catapult.goToDestination(catapult.getXPos(), catapult.getYPos());
            return true;
        }

        return false;
    }

    private ArrayList<MilitaryUnit> findEnemyInTheBoardOfCatapult(Catapult catapult) {
        //TODO check
        int gunShot = catapult.getCatapultName().getFireRange();
        int x = catapult.getXPos();
        int y = catapult.getYPos();
        ArrayList<MilitaryUnit> enemy = new ArrayList<>();

        if (catapult.getCatapultName().getCanAttackUnit()) {

        }
        for (int k = 0; k < gunShot; k++) {
            for (int i = x - k; i < x + k; i++)
                if ((enemy = getMap().getTile(i, y - k).findNearEnemiesMilitaryUnit(catapult.getEmpire())).size() != 0)
                    return enemy;
            for (int j = y - k; j < y + k; j++)
                if ((enemy = getMap().getTile(x - k, j).findNearEnemiesMilitaryUnit(catapult.getEmpire())).size() != 0)
                    return enemy;
            for (int i = x - k; i < x + k; i++)
                if ((enemy = getMap().getTile(i, y + k).findNearEnemiesMilitaryUnit(catapult.getEmpire())).size() != 0)
                    return enemy;
            for (int j = y - k; j < y + k; j++)
                if ((enemy = getMap().getTile(x + k, j).findNearEnemiesMilitaryUnit(catapult.getEmpire())).size() != 0)
                    return enemy;
        }
        return enemy;
    }

    private Building findEnemyBuildingInTheBoardOfCatapult(Catapult catapult) {
        //TODO check
        int gunShot = catapult.getCatapultName().getFireRange();
        int x = catapult.getXPos();
        int y = catapult.getYPos();
        Building building;

        if (catapult.getCatapultName().getCanAttackUnit()) {

        }
        for (int k = 0; k < gunShot; k++) {
            for (int i = x - k; i < x + k; i++)
                if ((building = getMap().getTile(i, y - k).getBuilding()) != null)
                    return building;
            for (int j = y - k; j < y + k; j++)
                if ((building = getMap().getTile(x - k, j).getBuilding()) != null)
                    return building;
            for (int i = x - k; i < x + k; i++)
                if ((building = getMap().getTile(i, y + k).getBuilding()) != null)
                    return building;
            for (int j = y - k; j < y + k; j++)
                if ((building = getMap().getTile(x + k, j).getBuilding()) != null)
                    return building;
        }
        return null;
    }

    private void attack(Tile tile, int x, int y) {
        ArrayList<MilitaryUnit> enenmy1 = new ArrayList<>();
        ArrayList<MilitaryUnit> enemy2 = new ArrayList<>();
        boolean[] isAttack = new boolean[empires.size()];

        for (int i = 0; i < empires.size(); i++) {
            enenmy1 = tile.findUnit(empires.get(i));
            if (enenmy1.size() != 0) {
                for (int j = 0; j < empires.size(); j++) {
                    enemy2 = tile.findUnit(empires.get(j));
                    if (!isAttack[i] && enemy2.size() != 0) {
                        attackUnits(enenmy1, enemy2);
                    }
                }
                isAttack[i] = true;
            }
        }

        boolean bool = false;
        for (boolean booll : isAttack)
            if (booll) bool = true;

        if (!bool && enenmy1.size() != 0)
            for (MilitaryUnit unit : enenmy1)
                doAttacking(unit);
    }

    private void attackUnits(ArrayList<MilitaryUnit> enemy1, ArrayList<MilitaryUnit> enemy2) {
        int damage1 = 0;
        int counter1 = 0;
        int damage2 = 0;
        int counetr2 = 0;
        for (MilitaryUnit unit : enemy1) {
            if (!(unit instanceof Catapult))
                damage1 += unit.getMilitaryUnitName().getAttack();
            if (unit instanceof Catapult && ((Catapult) unit).getCatapultName().equals(CatapultName.FIRE_BALLISTRA)) {
                damage1 += ((Catapult) unit).getCatapultName().getDamage();
                counter1++;
            }
        }
        for (MilitaryUnit unit : enemy2) {
            if (!(unit instanceof Catapult))
                damage1 += unit.getMilitaryUnitName().getAttack();
            if (unit instanceof Catapult && ((Catapult) unit).getCatapultName().equals(CatapultName.FIRE_BALLISTRA)) {
                damage2 += ((Catapult) unit).getCatapultName().getDamage();
                counetr2++;
            }
        }

        damage1 /= counter1;
        damage2 /= counetr2;

        for (MilitaryUnit unit : enemy1) {
            if (unit.getMilitaryUnitName().getHitPoint() > damage1)
                unit.getMilitaryUnitName().reduceHitPoint(damage1);
            else
                unit.removeUnit();
        }
        for (MilitaryUnit unit : enemy2) {
            if (unit.getMilitaryUnitName().getHitPoint() > damage2)
                unit.getMilitaryUnitName().reduceHitPoint(damage2);
            else
                unit.removeUnit();
        }

    }

    private void doAttacking(MilitaryUnit unit) {
        if (unit instanceof Catapult && ((Catapult) unit).getCatapultName().getFireRange() > 0)
            doCatapultAttack((Catapult) unit);
        else if (!(unit instanceof Catapult) && unit.getMilitaryUnitName().getGunshot() > 0)
            doArcherAttack(unit);
        else if ((!(unit instanceof Catapult) && unit.getMilitaryUnitName().getGunshot() == 0) ||
                (unit instanceof Catapult && ((Catapult) unit).getCatapultName().getFireRange() == 0))
            doAttack(unit);
    }

    private void doCatapultAttack(Catapult catapult) {
        ArrayList<MilitaryUnit> enemy = new ArrayList<>();
        if (catapult.getCatapultName().getCanAttackUnit()) {
            if (catapult.getXAttack() > getMap().getSize() && catapult.getYAttack() > getMap().getSize())
                enemy = findEnemyInTheBoardOfCatapult(catapult);
            else
                enemy = getMap().getTile(catapult.getXAttack(), catapult.getYAttack()).findNearEnemiesMilitaryUnit(catapult.getEmpire());
            if (enemy.size() != 0) {
                int damage = catapult.getCatapultName().getDamage() / enemy.size();
                for (MilitaryUnit unit : enemy) {
                    if (unit.getMilitaryUnitName().getHitPoint() > damage)
                        unit.getMilitaryUnitName().reduceHitPoint(damage);
                    else
                        unit.removeUnit();
                }
            }
        } else {
            Building building;
            if (catapult.getXAttack() < getMap().getSize() && catapult.getYAttack() < getMap().getSize())
                building = getMap().getTile(catapult.getXAttack(), catapult.getYAttack()).getBuilding();
            else
                building = findEnemyBuildingInTheBoardOfCatapult(catapult);

            int damage = catapult.getCatapultName().getDamage();
            if (building != null) {
                if (building.getBuildingName().getHitPoint() > damage)
                    building.getBuildingName().reduceHitPoint(damage);
                else
                    building.removeBuilding();
            }
        }
    }

    private void doArcherAttack(MilitaryUnit unit) {
        ArrayList<MilitaryUnit> enemy;
        if (unit.getXAttack() > getMap().getSize() && unit.getYAttack() > getMap().getSize())
            enemy = findEnemyInTheBoardOfArcher(unit);
        else
            enemy = getMap().getTile(unit.getXAttack(), unit.getYAttack()).findNearEnemiesMilitaryUnit(unit.getEmpire());

        if (enemy.size() != 0) {
            int damage = unit.getMilitaryUnitName().getAttack() / enemy.size();
            for (MilitaryUnit militaryUnit : enemy) {
                if (militaryUnit.getMilitaryUnitName().getHitPoint() > damage)
                    militaryUnit.getMilitaryUnitName().reduceHitPoint(damage);
                else
                    militaryUnit.removeUnit();
            }
        }
    }

    private void doAttack(MilitaryUnit unit) {
        //Attack be building
        if (!(unit instanceof Catapult))
            doNormalAttack(unit);
        else if (unit instanceof Catapult && ((Catapult) unit).getCatapultName().getFireRange() == 0)
            CatapultAttack((Catapult) unit);
    }

    private void CatapultAttack(Catapult catapult) {
        int xPos = catapult.getXPos();
        int yPos = catapult.getYPos();
        int x = catapult.getXAttack();
        int y = catapult.getYAttack();
        int distance = Math.abs((x + y) - (xPos + yPos));
        int damage = catapult.getCatapultName().getDamage();
        Building building = getMap().getTile(x, y).getBuilding();
        if (catapult.getCatapultName().equals(CatapultName.BATTERNING_RAM) && distance == 1 && building != null) {
            if (building.getBuildingName().getHitPoint() > damage)
                building.getBuildingName().reduceHitPoint(damage);
            else
                building.removeBuilding();
        }
    }

    private void doNormalAttack(MilitaryUnit unit) {
        int xPos = unit.getXPos();
        int yPos = unit.getYPos();
        int x = unit.getXAttack();
        int y = unit.getYAttack();
        int distance = Math.abs((x + y) - (xPos + yPos));
        int damage = unit.getMilitaryUnitName().getAttack();
        Building building = getMap().getTile(x, y).getBuilding();
        if (distance == 1 && building != null) {
            if (building.getBuildingName().getHitPoint() > damage)
                building.getBuildingName().reduceHitPoint(damage);
            else
                building.removeBuilding();
        }

    }

    private void doNormalWildMove(MilitaryUnit unit) {
        int xPos = unit.getXPos();
        int yPos = unit.getYPos();
        int mapSize = getMap().getSize();

        if (!(unit instanceof Catapult)) {
            if (getMap().getTile(xPos, yPos).findNearEnemiesMilitaryUnit(unit.getEmpire()).size() != 0 && !unit.isMoved() &&
                    unit.getXPos() > mapSize && unit.getPatrolX1() > mapSize) {
                ArrayList<MilitaryUnit> enemy = findEnemyForWildUnit(xPos, yPos, unit, unit.getMilitaryUnitName().getGunshot());
                if (findEnemyForWildUnit(xPos, yPos, unit, unit.getMilitaryUnitName().getGunshot()).size() != 0) {
                    int x = enemy.get(0).getXPos();
                    int y = enemy.get(0).getYPos();
                    if (unit.getMilitaryUnitName().getGunshot() == 0)
                        unit.goToDestination(x, y);
                    else
                        unit.goToDestination(XYfinderForWildArcher(x, y, unit) / mapSize,
                                XYfinderForWildArcher(x, y, unit) / mapSize);
                }

            }
        }
    }

    private int XYfinderForWildArcher(int xDest, int yDest, MilitaryUnit unit) {
        int xPos = unit.getXPos();
        int yPos = unit.getYPos();

        if (xDest >= xPos && yDest >= yPos) return (xDest - 1) * getMap().getSize() + yDest - 1;
        else if (xDest >= xPos && yDest < yPos) return (xDest - 1) * getMap().getSize() + yDest + 1;
        else if (xDest < xPos && yDest > yPos) return (xDest + 1) * getMap().getSize() + yDest - 1;
        else return (xDest + 1) * getMap().getSize() + yDest + 1;

    }

    private ArrayList<MilitaryUnit> findEnemyForWildUnit(int x, int y, MilitaryUnit unit, int gunShot) {
        boolean[][] visit = new boolean[20][20];
        ArrayList<MilitaryUnit> enemy = new ArrayList<>();
        for (int k = 0; k < gunShot + 10; k++) {
            for (int i = x - k; i < x + k; i++) {
                for (int j = y - k; j < y + k; j++) {
                    if (!visit[i][j] && (enemy = getMap().getTile(i, j).findNearEnemiesMilitaryUnit(unit.getEmpire())).size() != 0) {
                        return enemy;
                    }
                    visit[i][j] = true;
                }
            }
        }
        return enemy;
    }

    private void doFornextTurn() {
        for (Empire empire : empires) {
            for (People person : empire.getPeople()) {
                if (person instanceof MilitaryUnit) {
                    ((MilitaryUnit) person).setNotMoved();
                    if (((MilitaryUnit) person).getMilitaryUnitName().getHitPoint() <= 0) {
                        ((MilitaryUnit) person).removeUnit();
                    }
                }
            }
            for (Building building : empire.getBuildings()) {
                if (building.getBuildingName().getHitPoint() <= 0 || (building instanceof KillingPits && ((KillingPits)building).isUsed()))
                    building.removeBuilding();
            }
        }
    }

//    public void doOnCatapults(Catapult catapult) {
//
//    }
//
//    public void doOnLadderMen(LadderMen ladderMen) {
//
//    }
//
//    public void moveLadderMen(LadderMen ladderMen) {
//        if (getMap().getTile(ladderMen.getXDestination(), ladderMen.getYDestination()).getBuilding() == null) {
//            LinkedList<Integer> path = new BestPath(ladderMen.getEmpire()).input(getMap().getMap(), ladderMen.getXPos(),
//                    ladderMen.getYPos(), ladderMen.getXDestination(), ladderMen.getYDestination(), false, false);
//            doGeneralMove(ladderMen, path);
//        } else {
//            //TODO
//        }
//    }
//
//    public void putLadder(LadderMen ladderMen) {
//
//    }
//
//    //TODO State
//    public void doOnAssassins(MilitaryUnit unit) {
//        if (getMap().getTile(unit.getXDestination(), unit.getYDestination()).getBuilding() == null) {
//            LinkedList<Integer> path = new BestPath(unit.getEmpire()).input(getMap().getMap(), unit.getXPos(),
//                    unit.getYPos(), unit.getXDestination(), unit.getYDestination(), false, false);
//            doGeneralMove(unit, path);
//        } else {
//            //TODO
//        }
//    }
//
//    public void doOnArcher(MilitaryUnit unit) {
//        if (getMap().getTile(unit.getXDestination(), unit.getYDestination()).getBuilding() == null) {
//            LinkedList<Integer> path = new BestPath(unit.getEmpire()).input(getMap().getMap(), unit.getXPos(),
//                    unit.getYPos(), unit.getXDestination(), unit.getYDestination(), false, false);
//            doGeneralMoveForArcher(unit, path);
//        } else {
//            //TODO
//        }
//    }
//
//    public void doOnNormalUnit(MilitaryUnit unit) {
//        if (getMap().getTile(unit.getXDestination(), unit.getYDestination()).getBuilding() == null) {
//            LinkedList<Integer> path = new BestPath(unit.getEmpire()).input(getMap().getMap(), unit.getXPos(),
//                    unit.getYPos(), unit.getXDestination(), unit.getYDestination(), false, false);
//            doGeneralMove(unit, path);
//        } else {
//            //TODO
//        }
//    }
//
//
//    private void doGeneralMove(MilitaryUnit unit, LinkedList<Integer> path) {
//
//    }
//
//    private void doGeneralMoveForArcher(MilitaryUnit unit, LinkedList<Integer> path) {
//
//    }
//
//    public void doMovePatrol(MilitaryUnit unit) {
//
//    }
//
//    public void States() {
//
//    }
//
//
//    private void doScoreMove(Tile tile, MilitaryUnit militaryUnit, int x, int y) {
//
//        if (militaryUnit.getXPos() > getMap().getSize()) return;
//
//        BestPath bestPath = new BestPath(currentEmpire);
//        boolean assassinsBool = false;
//        LinkedList<Integer> path;
//        if (militaryUnit.getMilitaryUnitName().equals(MilitaryUnitName.ASSASSINS)) assassinsBool = true;
//
//        path = bestPath.input(currentEmpire.getMap().getMap(), militaryUnit.getXPos(),
//                militaryUnit.getYPos(), militaryUnit.getXDestination(), militaryUnit.getYDestination(), false, assassinsBool);
//        if (path != null && path.size() > 0) movingProcess(path, militaryUnit);
//    }
//
//    private void gateHouse(LinkedList<Integer> move, int length, Empire empire) {
//        //TODO وقتی می خوای از اری لیست ادرس بگیری باید آی را را ضربدر سایز کنی
//        int x;
//        int y;
//        Building building;
//        for (int i = 0; i < length; i++) {
//            x = move.get(i) / getMap().getSize();
//            y = move.get(i) % getMap().getSize();
//            building = getMap().getTile(x, y).getBuilding();
//            if (getMap().getTile(x, y).getBuilding() != null && building instanceof Gatehouse) {
//                building.setEmpire(empire);
//            }
//        }
//    }
//
//    private void movingProcess(LinkedList<Integer> move, MilitaryUnit unit) {
//        if (move.size() <= unit.getMilitaryUnitName().getSpeed()) {
//            unit.goToDestination(unit.getXDestination(), unit.getYDestination());
//        } else {
//            //TODO set the number that delete elements of path for each unit
//            int xDest = move.get(unit.getMilitaryUnitName().getSpeed()) / currentEmpire.getMap().getSize();
//            int yDest = move.get(unit.getMilitaryUnitName().getSpeed()) % currentEmpire.getMap().getSize();
//            //unit.setDestination(unit.getXDestination(), unit.getYDestination(), xDest, yDest);
//        }
//    }
//
//    public void doPatrol(Tile tile, MilitaryUnit militaryUnit, int x, int y) {
//        //TODO reverse the xPos and yPos with xDestination and yDestination // TODO check
//        int size = getMap().getSize();
//        if (militaryUnit.getXPos() > size || militaryUnit.getPatrolX2() > size) return;
//        boolean assassinsBool = false;
//        if (militaryUnit.getMilitaryUnitName().equals(MilitaryUnitName.ASSASSINS)) assassinsBool = true;
//
//        BestPath bestPath = new BestPath(currentEmpire);
//        LinkedList<Integer> path = bestPath.input(currentEmpire.getMap().getMap(), x, y,
//                militaryUnit.getPatrolX2(), militaryUnit.getPatrolY2(), false, assassinsBool);
//
//        if (path != null && path.size() > 0) movePatrol(path, militaryUnit);
//    }
//
//    private void movePatrol(LinkedList<Integer> move, MilitaryUnit militaryUnit) {
//        if (move.size() <= militaryUnit.getMilitaryUnitName().getSpeed()) {
//            militaryUnit.goToDestination(militaryUnit.getXDestination(), militaryUnit.getYDestination());
//            militaryUnit.changePatrol();
//        } else {
//            int xDest = move.get(militaryUnit.getMilitaryUnitName().getSpeed()) / currentEmpire.getMap().getSize();
//            int yDest = move.get(militaryUnit.getMilitaryUnitName().getSpeed()) % currentEmpire.getMap().getSize();
//            militaryUnit.movePatrol(xDest, yDest);
//        }
//    }
//
//    public void doAttack(int x, int y) {
//        boolean[] fire = new boolean[empires.size()];
//        ArrayList<MilitaryUnit> empire1;
//        ArrayList<MilitaryUnit> empire2;
//
//        for (int i = 0; i < empires.size(); i++) {
//            empire1 = MilitaryMenuController.findMilitary(x, y, empires.get(i));
//            if (empire1.size() > 0) {
//                for (int j = 0; j < empires.size(); j++) {
//                    empire2 = MilitaryMenuController.findMilitary(x, y, empires.get(j));
//                    if (empire2.size() > 0 && !fire[j]) {
//                        attackTwoTroop(empire1, empire2);
//                    }
//                }
//            }
//            fire[i] = true;
//        }
//    }
//
//    private void doArcherAttack(int x, int y) {
//        //TODO منجنیق سنگ انداز شیلد ها هندل شود
//        ArrayList<MilitaryUnit> archers = new ArrayList<MilitaryUnit>();
//        for (People person : getMap().getTile(x, y).getPeople())
//            if (person instanceof MilitaryUnit && ((MilitaryUnit) person).getMilitaryUnitName().getGunshot() > 0)
//                archers.add((MilitaryUnit) person);
//        for (MilitaryUnit militaryUnit : archers) shooting(militaryUnit);
//    }
//
//    private void shooting(MilitaryUnit militaryUnit) {
//        //TODO consider defend range
//        //TODO check the range of x, y
//        int x = militaryUnit.getXPos();
//        int y = militaryUnit.getYPos();
//        int gunShot = militaryUnit.getMilitaryUnitName().getGunshot();
//        int damage = militaryUnit.getMilitaryUnitName().getAttack();
//        Building building = getMap().getTile(x, y).getBuilding();
//        MilitaryUnit unit;
//
//        if (building != null && building instanceof Tower) gunShot += ((Tower) building).getTowerType().getFireRang();
//
//        for (int i = x; i < x + gunShot; i++) {
//            for (int j = y; j < y + gunShot; j++) {
//                unit = getMap().getTile(i, j).findEnemyMilitaryUnitForArcher(currentEmpire);
//                if (unit != null) {
//                    unit.getMilitaryUnitName().reduceHitPoint(damage);
//                    return;
//                }
//            }
//        }
//
//        for (int i = x; i > x - gunShot; i--) {
//            for (int j = y; j > y - gunShot; j--) {
//                unit = getMap().getTile(i, j).findEnemyMilitaryUnit(currentEmpire);
//                if (unit != null) {
//                    unit.getMilitaryUnitName().reduceHitPoint(damage);
//                    return;
//                }
//            }
//        }
//
//    }
//
//
//    private void attackBuildings(MilitaryUnit militaryUnit) {
//        int xPos = militaryUnit.getXPos();
//        int yPos = militaryUnit.getYPos();
//        int xDestination = militaryUnit.getXDestination();
//        int yDestination = militaryUnit.getYDestination();
//        Building building = getMap().getTile(xDestination, yDestination).getBuilding();
//
//        if (militaryUnit.getMilitaryUnitName().getGunshot() == 0 || militaryUnit instanceof Catapult
//                && ((Catapult) militaryUnit).getCatapultName().getFireRange() == 0 &&
//                !((Catapult) militaryUnit).getCatapultName().getCanAttackUnit()) {
//            if (Math.abs((xPos + yPos - xDestination - yDestination)) == 1 && building != null) {
//                building.getBuildingName().reduceHitPoint(militaryUnit.getMilitaryUnitName().getAttack());
//            }
//        } else if (militaryUnit instanceof Catapult && !((Catapult) militaryUnit).getCatapultName().getCanAttackUnit()) {
//            int range = ((Catapult) militaryUnit).getCatapultName().getFireRange();
//            for (int k = 0; k < range; k++) {
//                if (findEnemyBuilding(xPos, yPos, k, militaryUnit.getEmpire()) != null) {
//                    building.getBuildingName().reduceHitPoint(((Catapult) militaryUnit).getCatapultName().getDamage());
//                    return;
//                }
//            }
//        }
//    }
//
//    private Building findEnemyBuilding(int x, int y, int k, Empire empire) {
//        int size = getMap().getSize();
//        Building building;
//        for (int i = x; i < x + k; i++) {
//            building = getMap().getTile(i, y + x + k - i).getBuilding();
//            if (building != null && !building.getEmpire().equals(empire))
//                return building;
//        }
//        for (int i = x; i > x - k; i--) {
//            building = getMap().getTile(i, y + x + k - i).getBuilding();
//            if (building != null && !building.getEmpire().equals(empire))
//                return building;
//        }
//        return null;
//    }
//
//    public void doOffensiveAttack(MilitaryUnit militaryUnit, int x, int y) {
//        LinkedList<Integer> enemyPosition;
//        int xEnemyPosition;
//        int yEnemyPosition;
//        int size = getMap().getSize();
//        for (int k = 0; k < 20; k++) {
//            if ((enemyPosition = wildEnemy(x, y, k, militaryUnit.getEmpire())) != null) {
//                xEnemyPosition = enemyPosition.get(enemyPosition.size() - 1) / size;
//                yEnemyPosition = enemyPosition.get(enemyPosition.size() - 1) % size;
//                militaryUnit.goToPos(xEnemyPosition, yEnemyPosition);
//                return;
//            }
//        }
//    }
//
//    private void laddermenAttack() {
//
//    }
//
//    private LinkedList<Integer> wildEnemy(int x, int y, int k, Empire empire) {
//        BestPath bestPath = new BestPath(empire);
//        int size = getMap().getSize();
//
//
//        for (int i = x; i < x + k; i++) {
//            if (getMap().getTile(i, y + x + k - i).findEnemyMilitaryUnit(empire) != null) {
//                LinkedList<Integer> path = bestPath.input(getMap().getMap(), x, y, i, y + k - i, false, false);
//                if (path.size() <= size) return path;
//            }
//        }
//        for (int i = x; i > x - k; i--) {
//            if (getMap().getTile(i, y + k + i - x).findEnemyMilitaryUnit(empire) != null) {
//                LinkedList<Integer> path = bestPath.input(getMap().getMap(), x, y, i, y + k + i - x, false, false);
//                if (path.size() <= size) return path;
//            }
//        }
//        return null;
//    }
//
//
//    private void attackTwoTroop(ArrayList<MilitaryUnit> militaryUnitEmpire1, ArrayList<MilitaryUnit> militaryUnitEmpire2) {
//        int damageEmpire1 = 0;
//        int damageEmpire2 = 0;
//
//        damageEmpire1 = getDamageOfEmpire(militaryUnitEmpire1, damageEmpire1);
//        damageEmpire2 = getDamageOfEmpire(militaryUnitEmpire2, damageEmpire2);
//        damageEmpire1 /= militaryUnitEmpire2.size();
//        damageEmpire2 /= militaryUnitEmpire1.size();
//
//        enforceDamage(militaryUnitEmpire1, damageEmpire2);
//        enforceDamage(militaryUnitEmpire2, damageEmpire1);
//    }
//
//    private void enforceDamage(ArrayList<MilitaryUnit> militaryUnitEmpire2, int damageEmpire1) {
//        for (MilitaryUnit militaryUnit : militaryUnitEmpire2) {
//            if (!(militaryUnit instanceof Catapult)) {
//                if (militaryUnit.getMilitaryUnitName().getHitPoint() > damageEmpire1)
//                    militaryUnit.getMilitaryUnitName().reduceHitPoint(damageEmpire1);
//                //else
//                //TODO remove troop
//            } else if (militaryUnit instanceof Catapult) {
//                if (((Catapult) militaryUnit).getCatapultName().getHitPoint() > damageEmpire1)
//                    ((Catapult) militaryUnit).getCatapultName().reduceHitPoint(damageEmpire1);
//                //else
//                //TODO remove the troop
//            }
//
//        }
//    }
//
//    private int getDamageOfEmpire(ArrayList<MilitaryUnit> militaryUnitEmpire, int damageEmpire) {
//        for (MilitaryUnit militaryUnit : militaryUnitEmpire) {
//            if (!(militaryUnit instanceof Catapult) && militaryUnit.getMilitaryUnitName().getGunshot() > 0)
//                damageEmpire += militaryUnit.getMilitaryUnitName().getAttack();
//            if (militaryUnit instanceof Catapult && ((Catapult) militaryUnit).getCatapultName().getFireRange() > 0)
//                damageEmpire += ((Catapult) militaryUnit).getCatapultName().getDamage();
//        }
//        return damageEmpire;
//    }
//
//    public Empire getCurrentEmpire() {
//        return currentEmpire;
//    }

//        public void doAttack(String x, String y) {

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

     /*

        private void attackCatapult(MilitaryUnit militaryUnit, int x, int y, Empire empire) {
        ArrayList<Catapult> catapults = new ArrayList<Catapult>();
        for (People person : getMap().getTile(x, y).getPeople())
            if (person instanceof Catapult && ((Catapult)person).getCatapultName().getFireRange() > 0)
                catapults.add((Catapult) person);
        for (Catapult catapult : catapults)  shootStone(catapult);
    }
    public void shootStone(Catapult catapult) {

    }

            for (int i = x - k; i < x + k; i++)
            if (getMap().getTile(i, y - k).findEnemyMilitaryUnit(empire) != null) {
                LinkedList<Integer> path = bestPath.input(getMap().getMap(), x, y, i, y - k, false);
                if (path.size() <= 20) return path;
            }
        for (int j = y - k; j < y + k; j++)
            if (getMap().getTile(x - k, j).findEnemyMilitaryUnit(empire) != null){
                LinkedList<Integer> path = bestPath.input(getMap().getMap(), x, y, x - k, j, false);
                if (path.size() <= 20) return path;
            }
        for (int i = x + k; x > x - k; i--)
            if (getMap().getTile(i, y + k).findEnemyMilitaryUnit(empire) != null){
                LinkedList<Integer> path = bestPath.input(getMap().getMap(), x, y, i, y + k, false);
                if (path.size() <= 20) return path;
            }
        for (int j = y + k; y > y - k; y--)
            if (getMap().getTile(x + k, j).findEnemyMilitaryUnit(empire) != null){
                LinkedList<Integer> path = bestPath.input(getMap().getMap(), x, y, x + k, j, false);
                if (path.size() <= 20)
                    return path;
            }

    if (!findSomeUnitExceptArcher(getMap().getTile(x, y), militaryUnit.getEmpire())) return;
    public boolean findSomeUnitExceptArcher(Tile tile, Empire empire) {
        for (People person : tile.getPeople())
            if (person instanceof MilitaryUnit && person.getEmpire().equals(empire) &&
                    ((MilitaryUnit) person).getMilitaryUnitName().getGunshot() > 0)
                return true;
        return false;
    }
    /        BestPath bestPath = new BestPath(empire);
//        //TODO check
//        LinkedList<Integer> move = bestPath.input(getMap().getMap(), xStart, yStart, xDestination, yDestination, false, false);
//        int maxLength = 0;
//        if (move == null || move.size() == 0) return;
//        int dx = xDestination - xStart;
//        int dy = yDestination - yStart;
//        int distance = dx + dy;
//        int maxLength = 0;
        //(int) Math.floor(Math.sqrt(dx * dx + dy * dy))
//                for (MilitaryUnit militaryUnit : getMap().getTile(xStart, yStart).findUnit(empire)) {
//            if (move.size() <= militaryUnit.getMilitaryUnitName().getSpeed()) {
//                if (militaryUnit.getMilitaryUnitName().getGunshot() > 0) {     //TODO check
//                    if (distance > militaryUnit.getMilitaryUnitName().getGunshot())
//                        militaryUnit.goToDestination(xDestination, yDestination);
//                } else militaryUnit.goToDestination(xDestination, yDestination);
//            } else {
//                int xDest = move.get(militaryUnit.getMilitaryUnitName().getSpeed()) / getMap().getSize();
//                int yDest = move.get(militaryUnit.getMilitaryUnitName().getSpeed()) % getMap().getSize();
//                if (militaryUnit.getMilitaryUnitName().getGunshot() > 0) {
//                    if (distance > militaryUnit.getMilitaryUnitName().getGunshot())
//                        militaryUnit.setDestination(xDest, yDest, xDestination, yDestination);
//                } else militaryUnit.setDestination(xDest, yDest, xDestination, yDestination);
//            }
//            if (militaryUnit.getMilitaryUnitName().getSpeed() > maxLength && militaryUnit.getMilitaryUnitName().getSpeed() <= move.size())
//                maxLength = militaryUnit.getMilitaryUnitName().getSpeed();
//        }
//        gateHouse(move, maxLength, empire);





int xPosition = ladderMen.getXPos();
        int yPosition = ladderMen.getYPos();
        int xDestination = ladderMen.getXDestination();
        int ydestination = ladderMen.getYDestination();
        int mapSize = getMap().getSize();

        if (getMap().getTile(xDestination, ydestination).getBuilding() != null) {
            putLadder(ladderMen);
            return;
        }

        BestPath bestPath = new BestPath(ladderMen.getEmpire());
        LinkedList<Integer> path = bestPath.input(getMap().getMap(), xPosition, yPosition, xDestination, ydestination, false, false);
        if (path == null || path.size() == 0) return;
        int x;
        int y;
        for (Integer integer : path) {
            x = integer / mapSize;
            y = integer % mapSize;
            Building building = getMap().getTile(x, y).getBuilding();
            if (building != null && building instanceof KillingPits) {
                ((KillingPits) building).setUsed();
                if (((KillingPits) building).getDamage() >= ladderMen.getMilitaryUnitName().getHitPoint())
                    ladderMen.removeUnit();
                else
                    ladderMen.getMilitaryUnitName().reduceHitPoint(((KillingPits) building).getDamage());
            }
            if (getMap().getTile(x, y).findEnemyMilitaryUnit(ladderMen.getEmpire()) != null) {
                ladderMen.setMoved();
                ladderMen.goToDestination(x, y);
            } else {
                ladderMen.goToPos(x, y);
            }
        }
     */
}
