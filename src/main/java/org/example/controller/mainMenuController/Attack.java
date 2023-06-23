package org.example.controller.mainMenuController;

import org.example.model.Disease;
import org.example.model.Empire;
import org.example.model.People;
import org.example.model.building.Building;
import org.example.model.building.castleBuilding.Wall;
import org.example.model.building.enums.BuildingName;
import org.example.model.unit.Catapult;
import org.example.model.unit.CatapultName;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;

import java.util.ArrayList;
import java.util.Random;

import static org.example.view.mainMenu.gameMenu.GameMenu.*;

public class Attack {
    //TODO set moving for attack building
    //TODO check null pointer
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
        if (catapult.getXAttack() != -1 && catapult.getYAttack() != -1){
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



    public static void moveForAttack(ArrayList<Empire> empires) {
        //TODO
        for (Empire empire : empires) {
            for (MilitaryUnit unit : empire.getUnits()) {
                if (unit instanceof Catapult) {
                    Building building = getMap().getTile(unit.getXAttack(), unit.getYAttack()).getBuilding();
                    if (building != null && building.getEmpire().equals(unit.getEmpire()))
                        moveForAttackWithRangeForEnemyBuilding(unit, ((Catapult) unit).getCatapultName().getFireRange());
                    else if (building == null)
                        moveForAttackWithRangeForEnemyUnit(unit, ((Catapult) unit).getCatapultName().getFireRange());
                } else {
                    Building building = getMap().getTile(unit.getXAttack(), unit.getYAttack()).getBuilding();
                    if (building != null && building.getEmpire().equals(unit.getEmpire()))
                        moveForAttackWithRangeForEnemyBuilding(unit, ( unit.getMilitaryUnitName().getGunshot()));
                    else if (building == null)
                        moveForAttackWithRangeForEnemyUnit(unit, unit.getMilitaryUnitName().getGunshot());
                }
            }
        }
    }

    public static void moveForAttackWithRangeForEnemyUnit(MilitaryUnit unit, int range) {

    }

    public static void moveForAttackWithRangeForEnemyBuilding(MilitaryUnit unit, int range) {
        int mapSize = getMap().getSize();
        int xStart = Math.max(getMap().getTile(unit.getXAttack(), unit.getYAttack()).getBuilding().getBeginX() - 1, 0);
        int yStart = Math.max(getMap().getTile(unit.getXAttack(), unit.getYAttack()).getBuilding().getBeginY() - 1, 0);
        int xEnd = Math.min(getMap().getTile(unit.getXAttack(), unit.getYAttack()).getBuilding().getEndX() + 1, mapSize);
        int yEnd = Math.min(getMap().getTile(unit.getXAttack(), unit.getYAttack()).getBuilding().getEndY() + 1, mapSize);

        ArrayList<Integer> path = null;

        for (int i = xStart; i < xEnd; i++) {
            for (int j = yStart; j < yEnd; j++) {
                //TODO set path from bestPath
            }
        }
        if (path == null) return;
        //TODO else move

    }

    public static void setBrazierForArcher(int x, int y) {
        ArrayList<MilitaryUnit> archerForSetBrazier = new ArrayList<>();
        for (People people : getMap().getTile(x, y).findUnit(getThisEmpire())) {
            if (!(people instanceof Catapult)) {
                MilitaryUnitName type = ((MilitaryUnit)people).getMilitaryUnitName();
                if (type.equals(MilitaryUnitName.ARCHER) || type.equals(MilitaryUnitName.ARCHER_BOW) ||
                type.equals(MilitaryUnitName.HORSE_ARCHER) || type.equals(MilitaryUnitName.CROSSBOW_MEN))
                    ((MilitaryUnit) people).setHaveFire(true);
            }
        }
    }

    public static void diseaseLogic(ArrayList<Empire> empires) {
        Random random = new Random();
        int diseasedEmpire = random.nextInt(empires.size() + 1);
        int diseasedBuilding = random.nextInt(empires.get(diseasedEmpire).getBuildings().size() + 1);
        Building building = empires.get(diseasedEmpire).getBuildings().get(diseasedBuilding);
        int mapSize = getMap().getSize();
        int xStart = Math.max(building.getBeginX() - 5, 0);
        int yStart = Math.max(building.getBeginY() - 5, 0);
        int xEnd = Math.min(building.getEndX() + 5, mapSize);
        int yEnd = Math.min(building.getEndY() + 5, mapSize);
        Disease disease = new Disease(xStart, yStart, xEnd, yEnd);
        Empire badBakht = empires.get(diseasedEmpire);
        badBakht.getDiseases().add(disease);
        badBakht.setPopularity();
        boolean haveApothecary = false;
        outer:
        for (Building building1 : badBakht.getBuildings()) {
            if (building1.getBuildingName().equals(BuildingName.APOTHECARY)) {
                haveApothecary = true;
                break outer;
            }
        }
        //TODO update man

        if (haveApothecary) {
            badBakht.getDiseases().remove(disease);
            badBakht.setPopularity();
        }
    }

    public static void diseaseLogicForEachTurn(Empire empire) {
        //TODO call this after each next turn for thisEmpire
        empire.setPopularity();
    }

    public static void fireLogicAfterEachLogic(Empire empire) {
        //TODO call after each next turn for thisEmpire
        ArrayList<Building> destroyedBuildingByFire = new ArrayList<>();
        for (Building building : empire.getBuildings()) {
            if (building.isFiring()) {
                building.reduceFireNumber();
            }
            if (building.getHitPoint() <= 0)
                destroyedBuildingByFire.add(building);
        }
        empire.getBuildings().removeAll(destroyedBuildingByFire);
    }
}
