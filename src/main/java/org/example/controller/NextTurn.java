package org.example.controller;

import org.example.controller.mainMenuController.gameMenuController.BestPath;
import org.example.model.Empire;
import org.example.model.People;
import org.example.model.Worker;
import org.example.model.building.Building;
import org.example.model.building.Tile;
import org.example.model.building.castleBuilding.CagedDogs;
import org.example.model.building.castleBuilding.KillingPits;
import org.example.model.building.castleBuilding.Tower;
import org.example.model.building.castleBuilding.Wall;
import org.example.model.building.enums.BuildingName;
import org.example.model.enums.FoodType;
import org.example.model.unit.Catapult;
import org.example.model.unit.CatapultName;
import org.example.model.unit.LadderMen;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;
import org.example.view.animations.MoveAnimation;
import org.example.view.graphicView.GameMenuApp;
import org.example.view.mainMenu.gameMenu.GameMenu;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.example.view.mainMenu.gameMenu.GameMenu.*;

public class NextTurn {
    private ArrayList<Empire> empires;
    public GameMenuApp gameMenu;
    private Empire currentEmpire;
    private int numberOfEmpires;
    private int turnNumber;
    private int score = 0;

    public NextTurn(GameMenuApp gameMenu) {
        this.gameMenu = gameMenu;
        this.turnNumber = 0;
        this.empires = getEmpires();
        this.numberOfEmpires = empires.size();
    }

    public NextTurn(GameMenu gameMenu) {
        //this.gameMenu = gameMenu;
        this.turnNumber = 0;
        this.empires = getEmpires();
        this.numberOfEmpires = empires.size();
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void addEmpire(Empire empire) {
        this.empires.add(empire);
        numberOfEmpires++;
    }

    public void nextTurn() {
        //if (turnNumber == numberOfEmpires - 1) {
        turnNumber = 0;
        currentEmpire = empires.get(0);
        doRates();
        doOnAllTiles();
        checkEmpireExist();
        doCatapult();
        doForNextTurn();
        //    } else {
        //  turnNumber++;
        //    currentEmpire = empires.get(turnNumber);
        //  }
//        GameMenu.setThisEmpire(currentEmpire);
    }

    public void doOnAllTiles() {
        for (int i = 0; i < getMap().getSize(); i++) {
            for (int j = 0; j < getMap().getSize(); j++) {
                doCageDogs(i, j);
                doOnAllMilitaryUnit(getMap().getTile(i, j), i, j);
            }
        }
//        for (int i = 0; i < getMap().getSize(); i++) {
//            for (int j = 0; j < getMap().getSize(); j++) {
//                attack(getMap().getTile(i, j), i, j);
//            }
//        }


    }

    private void doCageDogs(int x, int y) {
        Tile tile = getMap().getTile(x, y);
        if (tile.getBuilding() != null && tile.getBuilding() instanceof CagedDogs) {
            for (int k = 0; k < 5; k++) {
                if (findEnemyInDogsRange(x, y, k)) return;
            }
        }
    }

    public void doRates() {
        for (int i = 0; i < empires.size(); i++) {
            for (Building building : empires.get(i).getBuildings()) {
                if (building.getBuildingName().equals(BuildingName.CHURCH) || building.getBuildingName().equals(BuildingName.CATHEDRAL))
                    empires.get(i).addToReligionPopularity(2);
                if (building.getBuildingName().equals(BuildingName.INN)) empires.get(i).addToFoodPopularity(5);
            }
            int variety = empires.get(i).getVarietyFood();
            getTaxCheck(empires.get(i));
            getFoodCheck(empires.get(i));
            empires.get(i).setFearPopularity();
            empires.get(i).setTaxPopularity();
            empires.get(i).setFoodPopularity(variety);
            empires.get(i).setPopularity();
            changePopulation(empires.get(i));
            empires.get(i).setAttackOfUnits();
            empires.get(i).createMaterial();
        }
    }

    private void changePopulation(Empire empire) {
        float foodRatio = (float) ((empire.getFoodRate() + 2) * 0.5);
        float remainFood = empire.getTotalAmountOfFoods() - foodRatio * empire.getPopularity();
        if (remainFood > 0 && empire.getPopularity() > 0) for (int i = 0; i < remainFood / 2; i++)
            if (empire.getPopulation() < empire.getMaxPopulation()) {
                empire.addPeople(new People(getMap().getTile(empire.getEmpireBuilding().getX(), empire.getEmpireBuilding().getY()), empire));
            } else if (empire.getPopularity() < 0) {
                int amount = -empire.getPopularity();
                for (People people : empire.getPeople()) {
                    if (!(people instanceof Worker) && !(people instanceof MilitaryUnit)) {
                        empire.removePeople(people);
                        people.getPosition().removeUnit(people);
                        amount--;
                        if (amount == 0) break;
                    }
                }
            }

    }

    private void getFoodCheck(Empire empire) {
        float foodRatio = (float) ((empire.getFoodRate() + 2) * 0.5);
        if (empire.getTotalAmountOfFoods() < foodRatio * empire.getPopularity()) empire.setFoodRate(-2);
        else {
            float amountOfFood = foodRatio * empire.getPopularity();
            for (FoodType foodType : FoodType.values()) {
                if (empire.getFoods().get(foodType) >= amountOfFood) {
                    empire.getFoods().replace(foodType, empire.getFoods().get(foodType) - amountOfFood);
                    break;
                } else {
                    amountOfFood -= empire.getFoods().get(foodType);
                    empire.getFoods().replace(foodType, (float) 0);
                }
            }
        }
    }

    private void getTaxCheck(Empire empire) {
        float taxRatio = 0;
        if (empire.getTaxRate() > 0) taxRatio = (float) (0.6 + Math.abs(empire.getTaxRate() - 1) * 0.2000);
        else if (empire.getTaxRate() < 0) taxRatio = (float) (-0.6 + Math.abs(empire.getTaxRate() - 1) * 0.2);
        if (taxRatio < 0 && Math.abs(taxRatio) * empire.getPopulation() > empire.getGold()) {
            empire.setTaxRate(0);
            return;
        }
        empire.addGold(taxRatio * empire.getPopulation());
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
//        ArrayList<Empire> removingEmpires = new ArrayList<Empire>();
//        for (Empire empire : empires)
//            if (empire.getLord().getMilitaryUnitName().getHitPoint() <= 0) {
//                removingEmpires.add(empire);
//                empire.getPlayer().setHighScore(empire.getPlayer().getHighScore() + score);
//                try {
//                    UsersDatabaseJSON.saveUsersInJSON();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                score += 200;
//            }
//        if (removingEmpires.size() > 0) empires.removeAll(removingEmpires);
    }

    private void doCatapult() {
        for (Empire empire : empires)
            for (People person : empire.getPeople())
                if (person instanceof Catapult)
                    if (((Catapult) person).getCatapultName().getFireRange() > 0) doCataAttack((Catapult) person);
                    else doCatAttack((Catapult) person);
    }

    private void doCataAttack(Catapult catapult) {
        int xAttack = catapult.getXAttack();
        int yAttack = catapult.getYAttack();
        if (xAttack == 0 && yAttack == 0) return;
        if (Math.abs(xAttack - catapult.getXPos()) + Math.abs(yAttack - catapult.getYPos()) > catapult.getCatapultName().getFireRange())
            return;

        int damage = catapult.getCatapultName().getDamage();
        ArrayList<MilitaryUnit> enemy = getMap().getTile(xAttack, yAttack).findNearEnemiesMilitaryUnit(catapult.getEmpire());
        if (catapult.getCatapultName().getCanAttackUnit()) {
            if (getMap().getTile(xAttack, yAttack) != null && enemy != null && enemy.size() != 0) {
                for (People person : enemy) {
                    int hitPoint;
                    if (person instanceof MilitaryUnit && !(person instanceof Catapult)) {
                        hitPoint = ((MilitaryUnit) person).getMilitaryUnitName().getHitPoint();
                    } else {
                        hitPoint = ((Catapult) person).getCatapultName().getHitPoint();
                    }

                    if (damage > hitPoint) ((MilitaryUnit) person).removeUnit();
                    else if (!(person instanceof Catapult) && damage < hitPoint && person instanceof MilitaryUnit)
                        ((MilitaryUnit) person).getMilitaryUnitName().reduceHitPoint(damage);
                    else if (person instanceof Catapult && damage < hitPoint && person instanceof MilitaryUnit)
                        ((Catapult) person).getCatapultName().reduceHitPoint(damage);
                }
            }
        } else {
            Building building = getMap().getTile(xAttack, yAttack).getBuilding();
            if (building == null) return;
            if (building.getBuildingName().getHitPoint() < damage)
                building.removeBuilding();
            else
                building.getBuildingName().reduceHitPoint(damage);
        }
    }

    private void doCatAttack(Catapult catapult) {
        int damge = catapult.getCatapultName().getDamage();
        if (Math.abs(catapult.getXPos() - catapult.getXAttack()) + Math.abs(catapult.getYPos() - catapult.getYAttack()) > 1)
            return;
        Building building = getMap().getTile(catapult.getXAttack(), catapult.getYAttack()).getBuilding();
        if (building == null) return;
        if (building.getBuildingName().getHitPoint() > damge)
            building.getBuildingName().reduceHitPoint(damge);
        else
            building.removeBuilding();
    }

    private void doOnAllMilitaryUnit(Tile tile, int x, int y) {
        int xDest = -1;
        int yDest = -1;
        Building building;
        //Empire empire = empires.get(0);
        for (Empire empire : empires) {
            for (MilitaryUnit unit : tile.findUnit(empire)) {
                if (unit.getXAttack() != -1 && unit.getYAttack() != -1) {
                    moveForAttack(unit);
                } else {
                    if (unit.getMilitaryUnitName() != null)
                        if (unit.getMilitaryUnitName().equals(MilitaryUnitName.LORD)) continue;
                    if (unit.getXDestination() < getMap().getSize()) {
                        xDest = unit.getXDestination();
                        yDest = unit.getYDestination();
//                        System.out.println(unit.isMoved());
                    } else if (unit.getPatrolX1() < getMap().getSize()) {
                        xDest = unit.getPatrolX2();
                        yDest = unit.getPatrolY2();
                    }
                    if (xDest == -1 && yDest == -1) continue;
                    building = getMap().getTile(xDest, yDest).getBuilding();
                    if (building == null && !unit.isMoved()) {
                        callMoveFunctions(unit, false);
                    } else if (building != null && !unit.isMoved() && (!(unit instanceof Catapult)) &&
                            unit.getMilitaryUnitName().equals(MilitaryUnitName.ASSASSINS))
                        callMoveFunctions(unit, true);
                    else if (!unit.isMoved() && unit instanceof Catapult)
                        callMoveFunctions(unit, false);
                }
            }
        }
    }

    private void callMoveFunctions(MilitaryUnit unit, boolean bool) {
        if (unit.getXAttack() != -1 && unit.getYAttack() != -1) {
            moveForAttack(unit);
            return;
        }
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

    public void MoveToXY(MilitaryUnit unit, boolean haveBuilding, int xDest, int yDest) {
        int xPos = unit.getXPos(), yPos = unit.getYPos();
        boolean isAssassins = unit.getMilitaryUnitName().equals(MilitaryUnitName.ASSASSINS);
        BestPath bestPath = new BestPath(unit.getEmpire());
        LinkedList<Integer> path;
        if (xDest == -1 || yDest == -1) return;
        path = bestPath.input(getMap().getMap(), xPos, yPos, xDest, yDest, false, isAssassins);
        if (unit.getMilitaryUnitName().equals(MilitaryUnitName.ASSASSINS))
            path = bestPath.input(getMap().getMap(), xPos, yPos, xDest, yDest, false, true);
        else path = path = bestPath.input(getMap().getMap(), xPos, yPos, xDest, yDest, true, false);
        int XY = 0;
        int size = path.size();
        int mapSize = getMap().getSize();
        if (!isAssassins && haveBuilding)
            for (int i = size - 1; i > 0; i--)
                if (getMap().getTile(path.get(i) / mapSize, path.get(i) % mapSize).getBuilding() != null
                        && !getMap().getTile(path.get(i) / mapSize, path.get(i) % mapSize)
                        .getBuilding().equals(BuildingName.EMPIRE_CASTLE))
                    XY = path.remove();
        boolean havePatrol = unit.getPatrolX1() < mapSize;

        if (path == null || path.size() == 0) return;
        MoveAnimation moveAnimation = new MoveAnimation(this, unit, havePatrol, xDest, yDest, path);
        moveAnimation.play();
        Building building = getMap().getTile(XY / mapSize, XY % mapSize).getBuilding();
        if (unit instanceof LadderMen && unit.getXDestination() < mapSize && unit.getYDestination() < mapSize && building != null && building instanceof Wall)
            ((Wall) building).setHaveLadder(true);
        unit.setMoved();
    }

    public boolean isMoveFinished(MilitaryUnit unit, int integer, int counter, boolean havePatrol, int xDest, int yDest) {
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
            unit.goToDestination(unit.getXPos(), unit.getYPos());
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
        return false;
    }

    private ArrayList<MilitaryUnit> findEnemyInTheBoardOfArcher(MilitaryUnit unit) {
        int gunShot = unit.getMilitaryUnitName().getGunshot();
        int x = unit.getXPos();
        int y = unit.getYPos();
        ArrayList<MilitaryUnit> enemy = new ArrayList<>();
        boolean patrolBool = false;
        if (unit.getPatrolX1() < getMap().getSize()) patrolBool = true;

        for (int k = 0; k < gunShot; k++) {
            for (int i = x - k; i < x + k; i++) {
                for (int j = y - k; j < y + k; j++) {
                    if (patrolBool && (i == x) && (j == y)) continue;
                    if (getMap().getTile(i, j) != null && (enemy = getMap().getTile(i, j).findNearEnemiesMilitaryUnit(unit.getEmpire())).size() != 0)
                        return enemy;
                }
            }
        }
        return enemy;
    }

    private void MoveToXYForCatapult(Catapult catapult, boolean haveBuilding) {
        if (!catapult.getCatapultName().getCanMove()) return;
        if (catapult.getCatapultName().getCanAttackUnit() && findEnemyInTheBoardOfCatapult(catapult).size() != 0
                && getMap().getTile(catapult.getXDestination(), catapult.getYDestination()) == null)
            return;
        //TODO
        if (!catapult.getCatapultName().getCanAttackUnit() && findEnemyBuildingInTheBoardOfCatapult(catapult) != null
                && getMap().getTile(catapult.getXDestination(), catapult.getYDestination()) == null)
            return;

        int xPos = catapult.getXPos(), yPos = catapult.getYPos();
        int xDest = catapult.getXDestination(), yDest = catapult.getYDestination();
        boolean bool = getMap().getTile(xDest, yDest) == null && getMap().getTile(catapult.getXAttack(), catapult.getYAttack()) != null;
        if (bool) {
            xDest = catapult.getXAttack();
            yDest = catapult.getYAttack();
        }

        BestPath bestPath = new BestPath(catapult.getEmpire());
        LinkedList<Integer> path = bestPath.input(getMap().getMap(), xPos, yPos, xDest, yDest, false, false);

        int XY = 0;
        int size = path.size();
        int mapSize = getMap().getSize();


        if (bool) {
            for (int i = size - 1; i > 0; i--)
                if (getMap().getTile(path.get(i) / mapSize, path.get(i) % mapSize).getBuilding() != null)
                    XY = path.remove();
        }

        if (path == null || path.size() == 0) return;

        int counter = 0;

        for (Integer integer : path) {
            if (isCatapultMoveFinished(catapult, integer, counter)) return;
            counter++;
        }
        int xFinal = XY / mapSize;
        int yFinal = XY % mapSize;
        Building building = getMap().getTile(xFinal, yFinal).getBuilding();
        catapult.setMoved();
    }

    private boolean isCatapultMoveFinished(Catapult catapult, Integer integer, int counter) {
        int mapSize = getMap().getSize();
        catapult.goToPos(integer / mapSize, integer % mapSize);
        Building building = getMap().getTile(integer / mapSize, integer % mapSize).getBuilding();

        if (building != null && building instanceof KillingPits && !building.getEmpire().equals(catapult.getEmpire())) {
            if (catapult.getMilitaryUnitName().getHitPoint() > ((KillingPits) building).getDamage()) {
                catapult.getMilitaryUnitName().reduceHitPoint(((KillingPits) building).getDamage());
                ((KillingPits) building).setUsed();
            } else {
                catapult.removeUnit();
                ((KillingPits) building).setUsed();
                return true;
            }
        }
        if (catapult.getXPos() == catapult.getXDestination() && catapult.getYPos() == catapult.getYDestination()) {
            catapult.goToDestination(catapult.getXDestination(), catapult.getYDestination());
            return true;
        }
        if (counter >= catapult.getCatapultName().getSpeed()) {
            return true;
        }
        return false;
    }

    private ArrayList<MilitaryUnit> findEnemyInTheBoardOfCatapult(Catapult catapult) {
        int gunShot = catapult.getCatapultName().getFireRange();
        int x = catapult.getXPos();
        int y = catapult.getYPos();
        ArrayList<MilitaryUnit> enemy = new ArrayList<>();

        for (int k = 0; k < gunShot; k++) {
            for (int i = x - k; i < x + k; i++)
                if (getMap().getTile(i, y - k) != null)
                    if ((enemy = getMap().getTile(i, y - k).findNearEnemiesMilitaryUnit(catapult.getEmpire())).size() != 0)
                        return enemy;
            for (int j = y - k; j < y + k; j++)
                if (getMap().getTile(x - k, j) != null)
                    if ((enemy = getMap().getTile(x - k, j).findNearEnemiesMilitaryUnit(catapult.getEmpire())).size() != 0)
                        return enemy;
            for (int i = x - k; i < x + k; i++)
                if (getMap().getTile(i, y + k) != null)
                    if ((enemy = getMap().getTile(i, y + k).findNearEnemiesMilitaryUnit(catapult.getEmpire())).size() != 0)
                        return enemy;
            for (int j = y - k; j < y + k; j++)
                if (getMap().getTile(x + k, j) != null)
                    if ((enemy = getMap().getTile(x + k, j).findNearEnemiesMilitaryUnit(catapult.getEmpire())).size() != 0)
                        return enemy;
        }
        return enemy;
    }

    private Building findEnemyBuildingInTheBoardOfCatapult(Catapult catapult) {

        int gunShot = catapult.getCatapultName().getFireRange();
        int x = catapult.getXPos();
        int y = catapult.getYPos();
        Building building;

        for (int k = 0; k < gunShot; k++) {
            for (int i = x - k; i < x + k; i++)
                if (getMap().getTile(i, y - k) != null)
                    if ((building = getMap().getTile(i, y - k).getBuilding()) != null)
                        if (!building.getEmpire().equals(catapult.getEmpire())) return building;
            for (int j = y - k; j < y + k; j++)
                if (getMap().getTile(x - k, j) != null)
                    if ((building = getMap().getTile(x - k, j).getBuilding()) != null)
                        if (!building.getEmpire().equals(catapult.getEmpire())) return building;
            for (int i = x - k; i < x + k; i++)
                if (getMap().getTile(i, y + k) != null)
                    if ((building = getMap().getTile(i, y + k).getBuilding()) != null)
                        if (!building.getEmpire().equals(catapult.getEmpire())) return building;
            for (int j = y - k; j < y + k; j++)
                if (getMap().getTile(x + k, j) != null)
                    if ((building = getMap().getTile(x + k, j).getBuilding()) != null)
                        if (!building.getEmpire().equals(catapult.getEmpire())) return building;
        }
        return null;
    }


    public static void attack(ArrayList<Empire> empires) {
        for (Empire empire : empires) {
            for (MilitaryUnit unit : empire.getUnits()) {
                if (unit instanceof Catapult) {
                    if (((Catapult) unit).getCatapultName().equals(CatapultName.FIRE_BALLISTRA)) {
                        attackUnitForBallista((Catapult) unit);
                    } else {
                        attackEnemyBuildingForCatapult((Catapult) unit);
                    }
                } else {
                    MilitaryUnitName type = unit.getMilitaryUnitName();
                    if (type.equals(MilitaryUnitName.LADDER_MEN)) {
                        attackEnemyForLadderMen(unit);
                    } else if (type.getGunshot() > 0 && !type.equals(MilitaryUnitName.FIRE_THROWERS)) {
                        if (unit.getXAttack() != -1 && unit.getYAttack() != -1) {
                            attackEnemyUnitForArcherWithAttackPoint(unit.getXAttack(), unit.getYAttack(), unit);
                        } else {
                            attackEnemyUnitForArcherWithoutAttackPoint(unit);
                        }
                    } else if (type.equals(MilitaryUnitName.FIRE_THROWERS)) {
                        attackEnemyForFireThrowers(unit);
                    } else if (type.equals(MilitaryUnitName.SLAVES)) {
                        attackEnemyForSalve(unit);
                    } else if (type.equals(MilitaryUnitName.ENGINEER)) {
                        attackEnemyForEngineer(unit);
                    } else {
                        //TODO check why to attack is done
                        faceToFaceAttackEnemyUnit(unit, unit.getXAttack(), unit.getYAttack(), type.getAttack());
                    }
                }
            }
        }
    }

    //TODO enemy can be catapult
    private static void attackUnitForBallista(Catapult catapult) {
        if (catapult.getXAttack() == -1 && catapult.getYAttack() == -1) {
            int mapSize = getMap().getSize();
            int xStart = Math.max(catapult.getXPos() - catapult.getCatapultName().getFireRange(), 0);
            int yStart = Math.max(catapult.getYPos() - catapult.getCatapultName().getFireRange(), 0);
            int xEnd = Math.min(catapult.getXPos() + catapult.getCatapultName().getFireRange(), mapSize - 1);
            int yEnd = Math.min(catapult.getYPos() + catapult.getCatapultName().getFireRange(), mapSize - 1);
            ArrayList<MilitaryUnit> enemy = new ArrayList<>();
            for (int i = xStart; i < xEnd; i++) {
                for (int j = yStart; j < yEnd; j++) {
                    ArrayList<MilitaryUnit> oneTileEnemy = getMap().getTile(i, j).findNearEnemiesMilitaryUnit(catapult.getEmpire());
                    if (oneTileEnemy != null)
                        enemy.addAll(oneTileEnemy);
                }
            }
            if (enemy.size() == 0) return;
            double attackPower = catapult.getCatapultName().getDamage();
            killEnemies(enemy, attackPower);
        } else {
            ArrayList<MilitaryUnit> enemy = getMap().getTile(catapult.getXAttack(),
                    catapult.getYAttack()).findNearEnemiesMilitaryUnit(catapult.getEmpire());
            if (enemy == null) return;
            double attackPower = catapult.getCatapultName().getDamage();
            killEnemies(enemy, attackPower);
        }
    }

    private static synchronized void killEnemies(ArrayList<MilitaryUnit> enemy, double attackPower) {
        int damage = (int) Math.ceil(attackPower / enemy.size());
        for (MilitaryUnit unit : enemy) {
            if (unit instanceof Catapult) {
                unit.reduceHitPoint(damage);
                if (unit.getHitPoint() <= 0)
                    unit.removeUnit();
            } else {
                unit.reduceHitPoint(damage);
                if (unit.getHitPoint() <= 0) {
                    unit.removeUnit();
                }
            }
        }
    }

    private static void attackEnemyBuildingForCatapult(Catapult catapult) {
        if (catapult.getXAttack() != -1 && catapult.getYAttack() != -1) {
            Building enemyBuilding = getMap().getTile(catapult.getXAttack(), catapult.getYAttack()).getBuilding();
            int dx = Math.abs(catapult.getXAttack() - catapult.getXPos());
            int dy = Math.abs(catapult.getYAttack() - catapult.getYPos());
            if (dx + dy > catapult.getCatapultName().getFireRange() + 1) return;
            if (enemyBuilding != null && !enemyBuilding.getEmpire().equals(catapult.getEmpire())) {
                int deltaX = Math.abs(catapult.getXAttack() - catapult.getXPos());
                int deltaY = Math.abs(catapult.getYAttack() - catapult.getYPos());
                if (deltaX + deltaY == 1) {
                    enemyBuilding.reduceHitPoint(catapult.getCatapultName().getDamage());
                    if (enemyBuilding.getHitPoint() <= 0)
                        enemyBuilding.removeBuilding();
                }
            }
        }
    }

    //TODO check attack building fire range and defend rage
    //TODO reduce fire number

    public static void attackEnemyUnitForArcherWithAttackPoint(int xAttack, int yAttack, MilitaryUnit unit) {
        //TODO
        int dx = Math.abs(unit.getXAttack() - unit.getXPos());
        int dy = Math.abs(unit.getYAttack() - unit.getYPos());
        if (dx + dy > unit.getMilitaryUnitName().getGunshot()) return;

        ArrayList<MilitaryUnit> enemy = getMap().getTile(xAttack, yAttack).findNearEnemiesMilitaryUnit(unit.getEmpire());
        if (enemy == null) {
            Building building = getMap().getTile(xAttack, yAttack).getBuilding();
            if (building != null && !building.getEmpire().equals(unit.getEmpire()) &&
                    !unit.getMilitaryUnitName().equals(MilitaryUnitName.FIRE_THROWERS)) {
                if (unit.isHaveFire()) {
                    building.setFiring(true);
                } else {
                    building.reduceHitPoint(unit.getMilitaryUnitName().getAttack());
                    if (building.getHitPoint() <= 0)
                        building.removeBuilding();
                    return;
                }
            }
        }
        double attackPower = unit.getMilitaryUnitName().getAttack();
        killEnemies(enemy, attackPower);
    }

    public static void attackEnemyUnitForArcherWithoutAttackPoint(MilitaryUnit unit) {
        //Archer normally do not attack a building
        int mapSize = getMap().getSize();
        int xStart = Math.max(unit.getXPos() - unit.getMilitaryUnitName().getGunshot(), 0);
        int yStart = Math.max(unit.getYPos() - unit.getMilitaryUnitName().getGunshot(), 0);
        int xEnd = Math.min(unit.getXPos() + unit.getMilitaryUnitName().getGunshot(), mapSize - 1);
        int yEnd = Math.min(unit.getYPos() + unit.getMilitaryUnitName().getGunshot(), mapSize - 1);
        ArrayList<MilitaryUnit> enemy = new ArrayList<>();
        for (int i = xStart; i < xEnd; i++) {
            for (int j = yStart; j < yEnd; j++) {
                ArrayList<MilitaryUnit> oneTileEnemy = getMap().getTile(i, j).findNearEnemiesMilitaryUnit(unit.getEmpire());
                if (oneTileEnemy != null)
                    enemy.addAll(oneTileEnemy);
            }
        }
        if (enemy.size() == 0) return;
        double attackPower = unit.getMilitaryUnitName().getAttack();
        killEnemies(enemy, attackPower);
    }

    public static void attackEnemyForFireThrowers(MilitaryUnit unit) {
        //FireThrowers do not attack building normally you should order it
        //if no attack position attack as an archer with no point attack
        // else attack as archer with point attack and set building fireBoolean true
        if (unit.getXAttack() == -1 && unit.getYAttack() == -1) {
            attackEnemyUnitForArcherWithoutAttackPoint(unit);
        } else {
            attackEnemyUnitForArcherWithAttackPoint(unit.getXPos(), unit.getYPos(), unit);
            Building enemyBuilding = getMap().getTile(unit.getXAttack(), unit.getYAttack()).getBuilding();
            if (enemyBuilding != null && !enemyBuilding.getEmpire().equals(unit.getEmpire())) {
                enemyBuilding.setFiring(true);
            }
        }
    }

    public static void attackEnemyForSalve(MilitaryUnit unit) {
        if (unit.getXAttack() == -1 && unit.getXAttack() == -1) return;
        int deltaX = Math.abs(unit.getXAttack() - unit.getXPos());
        int deltaY = Math.abs(unit.getYAttack() - unit.getYPos());
        Building enemyBuilding = getMap().getTile(unit.getXAttack(), unit.getYAttack()).getBuilding();
        if (deltaX + deltaY == 1) {
            //TODO set fire animation
            //set a fire boolean for building and reduce this hitPoint
            if (enemyBuilding != null && !enemyBuilding.getEmpire().equals(unit.getEmpire()))
                enemyBuilding.setFiring(true);
        } else {
            faceToFaceAttackEnemyUnit(unit, unit.getXPos(), unit.getYPos(), unit.getMilitaryUnitName().getAttack());
        }
    }

    public static void faceToFaceAttackEnemyUnit(MilitaryUnit unit, int x, int y, int attackStrength) {
        ArrayList<MilitaryUnit> enemy = getMap().getTile(x, y).findNearEnemiesMilitaryUnit(unit.getEmpire());
        if (enemy.size() == 0) {
            Building enemyBuilding = getMap().getTile(x, y).getBuilding();
            if (enemyBuilding != null && !enemyBuilding.getEmpire().equals(unit.getEmpire()) &&
                    !unit.getMilitaryUnitName().equals(MilitaryUnitName.SLAVES)) {
                if (isEnemyBuildingNearUnit(unit)) {
                    enemyBuilding.reduceHitPoint(attackStrength);
                    if (enemyBuilding.getBuildingName().getHitPoint() <= 0)
                        enemyBuilding.removeBuilding();
                }
            }
            return;
        }
        double attackPower = attackStrength;
        killEnemies(enemy, attackPower);
    }

    public static boolean isEnemyBuildingNearUnit(MilitaryUnit unit) {
        Building building = getMap().getTile(unit.getXAttack(), unit.getYAttack()).getBuilding();
        int mapSize = getMap().getSize();
        int xStart = Math.max(building.getBeginX() - 1, 0);
        int yStart = Math.max(building.getBeginY() - 1, 0);
        int xEnd = Math.min(building.getEndX() + 1, mapSize - 1);
        int yEnd = Math.min(building.getEndY() + 1, mapSize - 1);
        for (int i = xStart; i < xEnd; i++) {
            for (int j = yStart; j < yEnd; j++) {
                if (i == unit.getXPos() && j == unit.getYPos())
                    return true;
            }
        }
        return false;
    }

    public static void attackEnemyForLadderMen(MilitaryUnit unit) {
        Building enemyBuilding = getMap().getTile(unit.getXAttack(), unit.getYAttack()).getBuilding();
        if (enemyBuilding == null || !(enemyBuilding instanceof Wall)) return;
        ((Wall) enemyBuilding).setHaveLadder(true);
    }

    public static void attackEnemyForEngineer(MilitaryUnit unit) {
        int x = -1, y = -1;
        outer:
        for (int i = unit.getXPos() - 1; i < unit.getXPos() + 1; i++) {
            for (int j = unit.getYPos() - 1; j < unit.getYPos() + 1; j++) {
                if (getMap().getTile(i, j).findNearEnemiesMilitaryUnit(unit.getEmpire()).size() != 0) {
                    x = i;
                    y = j;
                    break outer;
                }
            }
        }
        if (x == -1 && y == -1) return;
        if (x == -1 && y == -1) return;
        getMap().getTile(x, y).removeAllUnit();
        Building building = getMap().getTile(x, y).getBuilding();
        if (building != null)
            building.setFiring(true);
    }


    public void moveForAttack(MilitaryUnit unit) {
        //TODO
        //ArrayList<Empire> empires
//        for (Empire empire : empires) {
//            for (MilitaryUnit unit : empire.getUnits()) {
        if (unit instanceof Catapult) {
            Building building = getMap().getTile(unit.getXAttack(), unit.getYAttack()).getBuilding();
            if (building != null && building.getEmpire().equals(unit.getEmpire()))
                moveForAttackWithRangeForEnemyBuilding(unit, ((Catapult) unit).getCatapultName().getFireRange());
            else if (building == null)
                moveForAttackWithRangeForEnemyUnit(unit, ((Catapult) unit).getCatapultName().getFireRange());
        } else {
            Building building = getMap().getTile(unit.getXAttack(), unit.getYAttack()).getBuilding();
            if (building != null && building.getEmpire().equals(unit.getEmpire()))
                moveForAttackWithRangeForEnemyBuilding(unit, (unit.getMilitaryUnitName().getGunshot()));
            else if (building == null)
                moveForAttackWithRangeForEnemyUnit(unit, unit.getMilitaryUnitName().getGunshot());


        }
//            }
//        }
    }

    public void moveForAttackWithRangeForEnemyUnit(MilitaryUnit unit, int range) {
        MoveToXY(unit, false, unit.getXAttack(), unit.getYAttack());
    }

    public void moveForAttackWithRangeForEnemyBuilding(MilitaryUnit unit, int range) {
        int mapSize = getMap().getSize();
        int xStart = Math.max(getMap().getTile(unit.getXAttack(), unit.getYAttack()).getBuilding().getBeginX() - 1, 0);
        int yStart = Math.max(getMap().getTile(unit.getXAttack(), unit.getYAttack()).getBuilding().getBeginY() - 1, 0);
        int xEnd = Math.min(getMap().getTile(unit.getXAttack(), unit.getYAttack()).getBuilding().getEndX() + 1, mapSize);
        int yEnd = Math.min(getMap().getTile(unit.getXAttack(), unit.getYAttack()).getBuilding().getEndY() + 1, mapSize);

        int x = -1;
        int y = -1;
        boolean tunneler = unit.getMilitaryUnitName().equals(MilitaryUnitName.TUNNELER);
        boolean assassins = unit.getMilitaryUnitName().equals(MilitaryUnitName.ASSASSINS);
        outer:
        for (int i = xStart; i < xEnd; i++) {
            for (int j = yStart; j < yEnd; j++) {
                LinkedList<Integer> bestPath =
                        new BestPath(unit.getEmpire()).input(getMap().getMap(), unit.getXPos(), unit.getYPos(), i, j,
                                tunneler, assassins);
                if (bestPath.size() != 0) {
                    x = i;
                    y = j;
                    break outer;
                }
            }
        }
        if (x == -1 && y == -1) return;
        MoveToXY(unit, false, x, y);
    }

    public static void setBrazierForArcher(int x, int y) {
        ArrayList<MilitaryUnit> archerForSetBrazier = new ArrayList<>();
        for (People people : getMap().getTile(x, y).findUnit(getThisEmpire())) {
            if (!(people instanceof Catapult)) {
                MilitaryUnitName type = ((MilitaryUnit) people).getMilitaryUnitName();
                if (type.equals(MilitaryUnitName.ARCHER) || type.equals(MilitaryUnitName.ARCHER_BOW) ||
                        type.equals(MilitaryUnitName.HORSE_ARCHER) || type.equals(MilitaryUnitName.CROSSBOW_MEN))
                    ((MilitaryUnit) people).setHaveFire(true);
            }
        }
    }


    private void doNormalWildMove(MilitaryUnit unit) {
        int xPos = unit.getXPos();
        int yPos = unit.getYPos();
        int mapSize = getMap().getSize();

        if (!(unit instanceof Catapult)) {
            if (getMap().getTile(xPos, yPos).findNearEnemiesMilitaryUnit(unit.getEmpire()).size() != 0 && !unit.isMoved() && unit.getXPos() > mapSize && unit.getPatrolX1() > mapSize) {
                ArrayList<MilitaryUnit> enemy = findEnemyForWildUnit(xPos, yPos, unit, unit.getMilitaryUnitName().getGunshot());
                if (findEnemyForWildUnit(xPos, yPos, unit, unit.getMilitaryUnitName().getGunshot()).size() != 0) {
                    int x = enemy.get(0).getXPos();
                    int y = enemy.get(0).getYPos();
                    if (unit.getMilitaryUnitName().getGunshot() == 0) unit.goToDestination(x, y);
                    else
                        unit.goToDestination(XYfinderForWildArcher(x, y, unit) / mapSize, XYfinderForWildArcher(x, y, unit) / mapSize);
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

    private void doForNextTurn() {
        for (Empire empire : empires) {
            for (People person : empire.getPeople()) {
                if (person instanceof MilitaryUnit) {
                    ((MilitaryUnit) person).setNotMoved();
                    if (!(person instanceof Catapult) && ((MilitaryUnit) person).getMilitaryUnitName().getHitPoint() <= 0) {
                        ((MilitaryUnit) person).removeUnit();
                    } else if (person instanceof Catapult && ((Catapult) person).getCatapultName().getHitPoint() <= 0) {
                        ((MilitaryUnit) person).removeUnit();
                    } else {
                        ((MilitaryUnit) person).setNotMoved();
                    }
                }
            }
            ArrayList<Building> buildings = new ArrayList<>();
            for (Building building : empire.getBuildings()) {
                if (building.getBuildingName().getHitPoint() <= 0 || (building instanceof KillingPits &&
                        ((KillingPits) building).isUsed()))
                    buildings.add(building);
            }
            empire.getBuildings().removeAll(buildings);
        }
    }

    private void updateAfterNextTurn() {
        //TODO :
        // 1.man
        // 2.reval bazi
        // 3.to null building for undo
        // 4.isOnDestroyState 5.
    }

    private void attack(Tile tile, int x, int y) {
//        ArrayList<MilitaryUnit> enemy1 = new ArrayList<>();
//        ArrayList<MilitaryUnit> enemy2 = new ArrayList<>();
//        boolean[] isAttack = new boolean[empires.size()];
//        for (int i = 0; i < empires.size() - 1; i++) {
//            enemy1 = tile.findUnit(empires.get(i));
//            if (enemy1.size() != 0) {
//                for (int j = i + 1; j < empires.size(); j++) {
//                    enemy2 = tile.findUnit(empires.get(j));
//                    if (!isAttack[i] && enemy2.size() != 0)
//                        attackUnits(enemy1, enemy2);
//                }
//            }
//            isAttack[i] = true;
//        }
//
//        if (enemy1.size() != 0)
//            for (MilitaryUnit unit : enemy1)
//                doAttacking(unit);
//    }
//
//    private void attackUnits(ArrayList<MilitaryUnit> enemy1, ArrayList<MilitaryUnit> enemy2) {
//        int damage1 = 0;
//        int counter1 = 0;
//        int damage2 = 0;
//        int counter2 = 0;
//        for (MilitaryUnit unit : enemy1) {
//            if (!(unit instanceof Catapult)) damage1 += unit.getMilitaryUnitName().getAttack();
//            if (unit instanceof Catapult && ((Catapult) unit).getCatapultName().equals(CatapultName.FIRE_BALLISTRA)) {
//                damage1 += ((Catapult) unit).getCatapultName().getDamage();
//                counter1++;
//            }
//        }
//        for (MilitaryUnit unit : enemy2) {
//            if (!(unit instanceof Catapult)) damage1 += unit.getMilitaryUnitName().getAttack();
//            if (unit instanceof Catapult && ((Catapult) unit).getCatapultName().equals(CatapultName.FIRE_BALLISTRA)) {
//                damage2 += ((Catapult) unit).getCatapultName().getDamage();
//                counter2++;
//            }
//        }
//
//        damage1 /= counter1;
//        damage2 /= counter2;
//
//        for (MilitaryUnit unit : enemy1) {
//            if (!(unit instanceof Catapult) && unit.getMilitaryUnitName().getHitPoint() > damage1)
//                unit.getMilitaryUnitName().reduceHitPoint(damage1);
//            else if (unit instanceof Catapult && ((Catapult) unit).getCatapultName().getHitPoint() > damage1)
//                unit.getMilitaryUnitName().reduceHitPoint(damage1);
//            else unit.removeUnit();
//        }
//        for (MilitaryUnit unit : enemy2) {
//            if (!(unit instanceof Catapult) && unit.getMilitaryUnitName().getHitPoint() > damage2)
//                unit.getMilitaryUnitName().reduceHitPoint(damage2);
//            else if (unit instanceof Catapult && ((Catapult) unit).getCatapultName().getHitPoint() > damage2)
//                unit.getMilitaryUnitName().reduceHitPoint(damage2);
//            else unit.removeUnit();
//        }
//        for (MilitaryUnit militaryUnit : enemy1) {
//            System.out.println(militaryUnit.getMilitaryUnitName().getHitPoint());
//        }
//        for (MilitaryUnit militaryUnit : enemy2) {
//            System.out.println(militaryUnit.getMilitaryUnitName().getHitPoint());
//        }
//
//
//    }
//
//    private void doAttacking(MilitaryUnit unit) {
//        if (unit instanceof Catapult && ((Catapult) unit).getCatapultName().getFireRange() > 0) {
//            doCatapultAttack((Catapult) unit);
//        } else if (!(unit instanceof Catapult) && unit.getMilitaryUnitName().getGunshot() > 0) doArcherAttack(unit);
//        else if ((!(unit instanceof Catapult) && unit.getMilitaryUnitName().getGunshot() == 0) ||
//                (unit instanceof Catapult && ((Catapult) unit).getCatapultName().getFireRange() == 0))
//            doAttack(unit);
//    }
//
//    private void doCatapultAttack(Catapult catapult) {
//        System.out.println("\n\ngoh to sharif\n\n");
//
//        ArrayList<MilitaryUnit> enemy = new ArrayList<>();
//        if (catapult.getCatapultName().getCanAttackUnit()) {
//            if (catapult.getXAttack() > getMap().getSize() && catapult.getYAttack() > getMap().getSize())
//                enemy = findEnemyInTheBoardOfCatapult(catapult);
//            else
//                enemy = getMap().getTile(catapult.getXAttack(), catapult.getYAttack()).findNearEnemiesMilitaryUnit(catapult.getEmpire());
//            if (enemy.size() != 0) {
//                int damage = catapult.getCatapultName().getDamage() / enemy.size();
//                for (MilitaryUnit unit : enemy) {
//                    if (unit.getMilitaryUnitName().getHitPoint() > damage)
//                        unit.getMilitaryUnitName().reduceHitPoint(damage);
//                    else unit.removeUnit();
//                }
//            }
//        } else {
//            Building building;
//            if (catapult.getXAttack() < getMap().getSize() && catapult.getYAttack() < getMap().getSize())
//                building = getMap().getTile(catapult.getXAttack(), catapult.getYAttack()).getBuilding();
//            else building = findEnemyBuildingInTheBoardOfCatapult(catapult);
//
//            int damage = catapult.getCatapultName().getDamage();
//            if (building != null) {
//                if (building.getBuildingName().getHitPoint() > damage)
//                    building.getBuildingName().reduceHitPoint(damage);
//                else building.removeBuilding();
//            }
//        }
//    }
//
//    private void doArcherAttack(MilitaryUnit unit) {
//        ArrayList<MilitaryUnit> enemy = new ArrayList<>();
//        if (unit.getXAttack() > getMap().getSize() && unit.getYAttack() > getMap().getSize())
//            enemy = findEnemyInTheBoardOfArcher(unit);
//        else if (unit.getXAttack() == -1 || unit.getYAttack() == -1)
//            enemy = getMap().getTile(unit.getXAttack(), unit.getYAttack()).findNearEnemiesMilitaryUnit(unit.getEmpire());
//
//        if (enemy.size() != 0) {
//
//            Building building1 = getMap().getTile(enemy.get(0).getXPos(), enemy.get(0).getYPos()).getBuilding();
//            int damage = unit.getMilitaryUnitName().getAttack();
//            if (building1 != null && building1.getBuildingName().getType().equals("tower"))
//                damage -= ((Tower) building1).getTowerType().getDefendRange();
//            damage /= enemy.size();
//            for (MilitaryUnit militaryUnit : enemy) {
//                if (militaryUnit.getMilitaryUnitName().getHitPoint() > damage)
//                    militaryUnit.getMilitaryUnitName().reduceHitPoint(damage);
//                else militaryUnit.removeUnit();
//            }
//        }
//    }
//
//    private static void doAttack(MilitaryUnit unit) {
//        if (!(unit instanceof Catapult)) doNormalAttack(unit);
//        else if (unit instanceof Catapult && ((Catapult) unit).getCatapultName().getFireRange() == 0)
//            CatapultAttack((Catapult) unit);
//    }
//
//    private static void CatapultAttack(Catapult catapult) {
//        int xPos = catapult.getXPos();
//        int yPos = catapult.getYPos();
//        int x = catapult.getXAttack();
//        int y = catapult.getYAttack();
//        int distance = Math.abs((x + y) - (xPos + yPos));
//        int damage = catapult.getCatapultName().getDamage();
//        Building building = getMap().getTile(x, y).getBuilding();
//        if (catapult.getCatapultName().equals(CatapultName.BATTERNING_RAM) && distance == 1 && building != null) {
//            if (building.getBuildingName().getHitPoint() > damage) building.getBuildingName().reduceHitPoint(damage);
//            else building.removeBuilding();
//        }
//    }
//
//    private static void doNormalAttack(MilitaryUnit unit) {
//        int xPos = unit.getXPos();
//        int yPos = unit.getYPos();
//        int x = unit.getXAttack();
//        int y = unit.getYAttack();
//        int distance = Math.abs((x + y) - (xPos + yPos));
//        int damage = unit.getMilitaryUnitName().getAttack();
//        Building building = getMap().getTile(x, y).getBuilding();
//        if (distance == 1 && building != null) {
//            if (building.getBuildingName().getHitPoint() > damage) building.getBuildingName().reduceHitPoint(damage);
//            else building.removeBuilding();
//        }
//
//    }
//
//
//
    }
}