package org.example.controller;

import org.example.BestPath;
import org.example.controller.mainMenuController.gameMenuController.MilitaryMenuController;
import org.example.model.Empire;
import org.example.model.People;
import org.example.model.building.Building;
import org.example.model.building.Tile;
import org.example.model.building.castleBuilding.CagedDogs;
import org.example.model.building.castleBuilding.Tower;
import org.example.model.unit.Catapult;
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
        } else {
            turnNumber++;
            currentEmpire = empires.get(turnNumber);
        }
    }

    public void doOnAllTiles() {
        for (int i = 0; i < currentEmpire.getMap().getSize(); i++) {
            for (int j = 0; j < currentEmpire.getMap().getSize(); j++) {
                doOnAllMilitaryUnit(currentEmpire.getMap().getTile(i, j), i, j);
                doCageDogs(i, j);
            }
        }
    }

    public void doOnAllMilitaryUnit(Tile tile, int x, int y) {
        MilitaryUnit unit = getMap().getTile(x, y).findEnemyMilitaryUnit(currentEmpire);

        for (MilitaryUnit militaryUnit : tile.findUnit(currentEmpire)) {
            if (unit == null) {
                doArcherAttack(x, y);
                doOffensiveAttack(militaryUnit, x, y);
                attackBuildings(militaryUnit);
            }
            doAttack(x, y);
            doScoreMove(tile, militaryUnit, x, y);
            doPatrol(tile, militaryUnit, x, y);
        }
    }

    public void doRates() {
        //TODO
    }

    public void doCageDogs(int x, int y) {
        if (currentEmpire.getMap().getTile(x, y).getBuilding() != null &&
                currentEmpire.getMap().getTile(x, y).getBuilding() instanceof CagedDogs) {
            for (int i = x; i > x - 10; i--) {
                for (int j = y; j > y - 10; j--) {
                    //TODO with attack
                    if (doDogAttack(getMap().getTile(x, y), getMap().getTile(i, j))) return;
                }
            }
            for (int i = x; i < x + 10; i++) {
                for (int j = y; j < y + 10; j++) {
                    //TODO with attack
                    if (doDogAttack(getMap().getTile(x, y), getMap().getTile(i, j))) return;
                }
            }
        }
    }

    private boolean doDogAttack(Tile startTile, Tile attackTile) {
        ArrayList<MilitaryUnit> enemies = attackTile.findNearEnemiesMilitaryUnit(currentEmpire);
        if (enemies.size() != 0) {
            for (MilitaryUnit unit : enemies) {
                unit.getMilitaryUnitName().reduceHitPoint(((CagedDogs) startTile.getBuilding()).getDamage());
            }
            return true;
        }
        return false;
    }

    public void checkEmpireExist() {
        ArrayList<Empire> removingEmpires = new ArrayList<Empire>();
        for (Empire empire : empires) {
            if (empire.getLord().getMilitaryUnitName().getHitPoint() <= 0) {
                removingEmpires.add(empire);
            }
        }
        if (removingEmpires.size() > 0) empires.removeAll(removingEmpires);
    }

    public void doScoreMove(Tile tile, MilitaryUnit militaryUnit, int x, int y) {
        if (militaryUnit.getXPos() > getMap().getSize()) return;

        BestPath bestPath = new BestPath(currentEmpire);
        boolean assassinsBool = false;
        LinkedList<Integer> path;
        if (militaryUnit.getMilitaryUnitName().equals(MilitaryUnitName.ASSASSINS)) assassinsBool = true;

        path = bestPath.input(currentEmpire.getMap().getMap(), militaryUnit.getXPos(),
                militaryUnit.getYPos(), militaryUnit.getXDestination(), militaryUnit.getYDestination(), false, assassinsBool);
        if (path != null && path.size() > 0) movingProcess(path, militaryUnit);
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

    public void doPatrol(Tile tile, MilitaryUnit militaryUnit, int x, int y) {
        //TODO reverse the xPos and yPos with xDestination and yDestination // TODO check
        int size = getMap().getSize();
        if (militaryUnit.getXPos() > size || militaryUnit.getPatrolX2() > size) return;
        boolean assassinsBool = false;
        if (militaryUnit.getMilitaryUnitName().equals(MilitaryUnitName.ASSASSINS)) assassinsBool = true;

        BestPath bestPath = new BestPath(currentEmpire);
        LinkedList<Integer> path = bestPath.input(currentEmpire.getMap().getMap(), x, y,
                militaryUnit.getPatrolX2(), militaryUnit.getPatrolY2(), false, assassinsBool);

        if (path != null && path.size() > 0) movePatrol(path, militaryUnit);
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

    private void doArcherAttack(int x, int y) {
        //TODO منجنیق سنگ انداز شیلد ها هندل شود
        ArrayList<MilitaryUnit> archers = new ArrayList<MilitaryUnit>();
        for (People person : getMap().getTile(x, y).getPeople())
            if (person instanceof MilitaryUnit && ((MilitaryUnit) person).getMilitaryUnitName().getGunshot() > 0)
                archers.add((MilitaryUnit) person);
        for (MilitaryUnit militaryUnit : archers) shooting(militaryUnit);
    }

    private void shooting(MilitaryUnit militaryUnit) {
        //TODO consider defend range
        //TODO check the range of x, y
        int x = militaryUnit.getXPos();
        int y = militaryUnit.getYPos();
        int gunShot = militaryUnit.getMilitaryUnitName().getGunshot();
        int damage = militaryUnit.getMilitaryUnitName().getAttack();
        Building building = getMap().getTile(x, y).getBuilding();
        MilitaryUnit unit;

        if (building != null && building instanceof Tower) gunShot += ((Tower) building).getTowerType().getFireRang();

        for (int i = x; i < x + gunShot; i++) {
            for (int j = y; j < y + gunShot; j++) {
                unit = getMap().getTile(i, j).findEnemyMilitaryUnitForArcher(currentEmpire);
                if (unit != null) {
                    unit.getMilitaryUnitName().reduceHitPoint(damage);
                    return;
                }
            }
        }

        for (int i = x; i > x - gunShot; i--) {
            for (int j = y; j > y - gunShot; j--) {
                unit = getMap().getTile(i, j).findEnemyMilitaryUnit(currentEmpire);
                if (unit != null) {
                    unit.getMilitaryUnitName().reduceHitPoint(damage);
                    return;
                }
            }
        }

    }

    private void attackBuildings(MilitaryUnit militaryUnit) {
        int xPos = militaryUnit.getXPos();
        int yPos = militaryUnit.getYPos();
        int xDestination = militaryUnit.getXDestination();
        int yDestination = militaryUnit.getYDestination();
        Building building = getMap().getTile(xDestination, yDestination).getBuilding();

        if (militaryUnit.getMilitaryUnitName().getGunshot() == 0 || militaryUnit instanceof Catapult
                && ((Catapult) militaryUnit).getCatapultName().getFireRange() == 0 &&
                !((Catapult) militaryUnit).getCatapultName().getCanAttackUnit()) {
            if (Math.abs((xPos + yPos - xDestination - yDestination)) == 1 && building != null) {
                building.getBuildingName().reduceHitPoint(militaryUnit.getMilitaryUnitName().getAttack());
            }
        } else if (militaryUnit instanceof Catapult && !((Catapult) militaryUnit).getCatapultName().getCanAttackUnit()) {
            int range = ((Catapult) militaryUnit).getCatapultName().getFireRange();
            for (int k = 0; k < range; k++) {
                if (findEnemyBuilding(xPos, yPos, k, militaryUnit.getEmpire()) != null) {
                    building.getBuildingName().reduceHitPoint(((Catapult) militaryUnit).getCatapultName().getDamage());
                    return;
                }
            }
        }
    }

    private Building findEnemyBuilding(int x, int y, int k, Empire empire) {
        int size = getMap().getSize();
        Building building;
        for (int i = x; i < x + k; i++) {
            building = getMap().getTile(i, y + x + k - i).getBuilding();
            if (building != null && !building.getEmpire().equals(empire))
                return building;
        }
        for (int i = x; i > x - k; i--) {
            building = getMap().getTile(i, y + x + k - i).getBuilding();
            if (building != null && !building.getEmpire().equals(empire))
                return building;
        }
        return null;
    }

    public void doOffensiveAttack(MilitaryUnit militaryUnit, int x, int y) {
        LinkedList<Integer> enemyPosition;
        int xEnemyPosition;
        int yEnemyPosition;
        int size = getMap().getSize();
        for (int k = 0; k < 20; k++) {
            if ((enemyPosition = wildEnemy(x, y, k, militaryUnit.getEmpire())) != null) {
                xEnemyPosition = enemyPosition.get(enemyPosition.size() - 1) / size;
                yEnemyPosition = enemyPosition.get(enemyPosition.size() - 1) % size;
                militaryUnit.goToPos(xEnemyPosition, yEnemyPosition);
                return;
            }
        }
    }

    private void laddermenAttack() {

    }

    private LinkedList<Integer> wildEnemy(int x, int y, int k, Empire empire) {
        BestPath bestPath = new BestPath(empire);
        int size = getMap().getSize();


        for (int i = x; i < x + k; i++) {
            if (getMap().getTile(i, y + x + k - i).findEnemyMilitaryUnit(empire) != null) {
                LinkedList<Integer> path = bestPath.input(getMap().getMap(), x, y, i, y + k - i, false, false);
                if (path.size() <= size) return path;
            }
        }
        for (int i = x; i > x - k; i--) {
            if (getMap().getTile(i, y + k + i - x).findEnemyMilitaryUnit(empire) != null) {
                LinkedList<Integer> path = bestPath.input(getMap().getMap(), x, y, i, y + k + i - x, false, false);
                if (path.size() <= size) return path;
            }
        }
        return null;
    }


    private void attackTwoTroop(ArrayList<MilitaryUnit> militaryUnitEmpire1, ArrayList<MilitaryUnit> militaryUnitEmpire2) {
        int damageEmpire1 = 0;
        int damageEmpire2 = 0;

        damageEmpire1 = getDamageOfEmpire(militaryUnitEmpire1, damageEmpire1);
        damageEmpire2 = getDamageOfEmpire(militaryUnitEmpire2, damageEmpire2);
        damageEmpire1 /= militaryUnitEmpire2.size();
        damageEmpire2 /= militaryUnitEmpire1.size();

        enforceDamage(militaryUnitEmpire1, damageEmpire2);
        enforceDamage(militaryUnitEmpire2, damageEmpire1);
    }

    private void enforceDamage(ArrayList<MilitaryUnit> militaryUnitEmpire2, int damageEmpire1) {
        for (MilitaryUnit militaryUnit : militaryUnitEmpire2) {
            if (!(militaryUnit instanceof Catapult)) {
                if (militaryUnit.getMilitaryUnitName().getHitPoint() > damageEmpire1)
                    militaryUnit.getMilitaryUnitName().reduceHitPoint(damageEmpire1);
                //else
                //TODO remove troop
            } else if (militaryUnit instanceof Catapult) {
                if (((Catapult) militaryUnit).getCatapultName().getHitPoint() > damageEmpire1)
                    ((Catapult) militaryUnit).getCatapultName().reduceHitPoint(damageEmpire1);
                //else
                //TODO remove the troop
            }

        }
    }

    private int getDamageOfEmpire(ArrayList<MilitaryUnit> militaryUnitEmpire, int damageEmpire) {
        for (MilitaryUnit militaryUnit : militaryUnitEmpire) {
            if (!(militaryUnit instanceof Catapult) && militaryUnit.getMilitaryUnitName().getGunshot() > 0)
                damageEmpire += militaryUnit.getMilitaryUnitName().getAttack();
            if (militaryUnit instanceof Catapult && ((Catapult) militaryUnit).getCatapultName().getFireRange() > 0)
                damageEmpire += ((Catapult) militaryUnit).getCatapultName().getDamage();
        }
        return damageEmpire;
    }

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
     */


    public Empire getCurrentEmpire() {
        return currentEmpire;
    }
}
