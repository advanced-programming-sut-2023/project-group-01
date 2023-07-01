package org.example.controller.mainMenuController.gameMenuController;

import org.example.model.Empire;
import org.example.model.building.*;
import org.example.model.building.castleBuilding.*;
import org.example.model.building.castleBuilding.enums.TowerType;
import org.example.model.building.enums.BuildingCategory;
import org.example.model.building.enums.BuildingName;
import org.example.model.building.enums.MaterialType;
import org.example.model.building.enums.TypeOfTile;
import org.example.model.unit.Engineer;
import org.example.model.unit.LadderMen;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;
import org.example.view.enums.Outputs;
import org.example.view.graphicView.GameMenuApp;
import org.example.view.mainMenu.gameMenu.BuildingMenu;

import static org.example.view.enums.Outputs.*;
import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;
import static org.example.view.mainMenu.gameMenu.GameMenu.getThisEmpire;

public class BuildingMenuController {
    private Empire empire;
    private BuildingMenu buildingMenu;

    public BuildingMenuController(Empire empire, BuildingMenu buildingMenu) {
        this.empire = empire;
        this.buildingMenu = buildingMenu;
    }

    public Outputs selectBuilding(String x, String y) {
        if (x == null) {
            return Outputs.EMPTY_X;
        } else if (y == null) {
            return Outputs.EMPTY_Y;
        } else if (!x.matches("\\d+")) {
            return Outputs.INVALID_X;
        } else if (!y.matches("\\d+")) {
            return Outputs.INVALID_Y;
        } else if (Integer.parseInt(x) > getMap().getSize() || Integer.parseInt(y) > getMap().getSize()) {
            return Outputs.OUT_OF_RANGE;
        } else if (getMap().getTile(Integer.parseInt(x), Integer.parseInt(y)).getBuilding() == null) {
            return Outputs.NOT_HAVING_BUILDING;
        } else if (!getMap().getTile(Integer.parseInt(x), Integer.parseInt(y)).getBuilding().getEmpire().equals(buildingMenu.getEmpire())) {
            return Outputs.NOT_HAVING_BUILDING;
        } else {
            this.buildingMenu.setBuilding(getMap().getTileWhitXAndY(Integer.parseInt(x), Integer.parseInt(y)).getBuilding());
            return Outputs.VALID_SELECT_BUILDING;
        }
    }

    public static Outputs dropBuilding(String x, String y, String type) {

        BuildingName buildingName = findBuildingNameByName(type);
        int x0 = Integer.parseInt(x);
        int y0 = Integer.parseInt(y);
        int buildingSize = buildingName.getSize();
        int mapSize = getMap().getSize();

        if (x0 > mapSize || y0 > mapSize || x0 + buildingSize > mapSize || y0 + buildingSize > mapSize)
            return Outputs.OUT_OF_RANGE;
        else if (isPositionFull(buildingName, x0, y0)) return Outputs.FULL_POSITION;
        else if (isGroundSuitable(buildingName, x0, y0)) return Outputs.NOT_SUITABLE_GROUND;
        else if (getThisEmpire().getNormalPopulation() < buildingName.getNumberOfWorkers())
            return Outputs.NOT_ENOUGH_POPULATION;
        else if (buildingName.equals(BuildingName.STOCKPILE) || buildingName.equals(BuildingName.GRANARY)) {
            if (dropNearBuilding(Integer.parseInt(x), Integer.parseInt(y), buildingName).equals(Outputs.NOT_NEAR_BUILDING))
                return Outputs.NOT_NEAR_BUILDING;
        }
        if (buildingName.equals(BuildingName.DRAWBRIDGE)) {
            if (dropNearBuilding(Integer.parseInt(x), Integer.parseInt(y), BuildingName.BIG_STONE_GATEHOUSE).
                    equals(Outputs.NOT_NEAR_BUILDING))
                return Outputs.NOT_NEAR_BUILDING;
            if (dropNearBuilding(Integer.parseInt(x), Integer.parseInt(y), BuildingName.SMALL_STONE_GATEHOUSE).
                    equals(Outputs.NOT_NEAR_BUILDING))
                return Outputs.NOT_NEAR_BUILDING;
        }
        if (!getThisEmpire().havingMaterial(MaterialType.WOOD, buildingName.getWoodCost()))
            return Outputs.NOT_ENOUGH_WOOD;
        if (!getThisEmpire().havingMaterial(MaterialType.STONE, buildingName.getStoneCost()))
            return Outputs.NOT_ENOUGH_STONE;
        putBuilding(buildingName, x0, y0, getThisEmpire());
        if (buildingName.equals(BuildingName.STABLE)) getThisEmpire().addMaterial("horse", 4);
        return SUCCESSFUL_DROP_BUILDING;
    }

    public static Outputs dropNearBuilding(int x, int y, BuildingName buildingName) {
        if (!haveStorage(buildingName)) return SUCCESSFUL_DROP_BUILDING;

        for (int i = x - 1; i <= x + buildingName.getSize(); i++)
            for (int j = y - 1; j <= y + buildingName.getSize(); j++)
                if (getMap().getTile(i, j).getBuilding() != null && buildingName.equals(getMap().getTile(i, j).getBuilding().getBuildingName()))
                    return SUCCESSFUL_DROP_BUILDING;
        return Outputs.NOT_NEAR_BUILDING;
    }

    public static boolean haveStorage(BuildingName buildingName) {
        for (Building building : getThisEmpire().getBuildings())
            if (building.getBuildingName().equals(buildingName)) return true;
        return false;
    }

    public static BuildingName findBuildingNameByName(String name) {
        for (BuildingName buildingName : BuildingName.values()) {
            if (buildingName.getName().equals(name) &&
                    !buildingName.getBuildingCategory().equals(BuildingCategory.TREES)) {
                return buildingName;
            }
        }
        return null;
    }

    public static boolean isGroundSuitable(BuildingName buildingName, int x, int y) {
        int size = buildingName.getSize();

        if (buildingName.getTypeCanBuildBuilding().equals(TypeOfTile.NORMAL_GROUND)) {
            for (int i = x; i < x + size; i++) {
                for (int j = y; j < y + size; j++) {
                    if (getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.SEA) || getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.STONE_MINE) || getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.IRON_MINE) || getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.BEACH) || getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.OIL_GROUND) || getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.RIVER) || getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.SHALLOW_WATER) || getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.SMALL_POND) || getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.BIG_POND)) {
                        return true;
                    }
                }
            }
            return false;
        } else if (buildingName.getTypeCanBuildBuilding().equals(TypeOfTile.OIL_GROUND))
            return suitability(TypeOfTile.OIL_GROUND, x, y, size);
        else if (buildingName.getTypeCanBuildBuilding().equals(TypeOfTile.IRON_MINE))
            return suitability(TypeOfTile.IRON_MINE, x, y, size);
        else if (buildingName.getTypeCanBuildBuilding().equals(TypeOfTile.STONE_MINE))
            return suitability(TypeOfTile.STONE_MINE, x, y, size);
        else if (buildingName.getTypeCanBuildBuilding().equals(TypeOfTile.MEADOW))
            return suitability(TypeOfTile.MEADOW, x, y, size);
        return true;
    }

    public static boolean suitability(TypeOfTile typeOfTile, int x, int y, int size) {
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                if (getMap().getTile(i, j).getTypeOfTile().equals(typeOfTile)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isPositionFull(BuildingName buildingName, int x, int y) {
        int size = buildingName.getSize();
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                if (getMap().getTile(i, j).getBuilding() != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void putBuilding(BuildingName buildingName, int x, int y, Empire empire) {
        int size = buildingName.getSize();
        Building building = getBuilding(buildingName, empire, x, y);
        for (int i = x; i < x + size; i++)
            for (int j = y; j < y + size; j++)
                GameMenuApp.map.getTile(i, j).setBuilding(building);
        if (empire != null)
            empire.addToBuildings(building);
    }

    public static Building getBuilding(BuildingName buildingName, Empire empire, int x, int y) {

        return switch (buildingName.getType()) {
            case "building" -> new Building(empire, x, y, buildingName);
            case "gateHouse" -> new Gatehouse(empire, x, y, buildingName);
            case "tower" -> new Tower(empire, x, y, buildingName, getTypeOfTowerTypeByName(buildingName));
            case "stairs" -> new CastleBuilding(empire, x, y, buildingName);
            case "storage" -> new Storage(empire, x, y, buildingName);
            case "killingPit" -> new KillingPits(empire, x, y, buildingName);
            case "firstProducer" -> new FirstProducer(empire, x, y, buildingName);
            case "secondProducer" -> new SecondProducer(empire, x, y, buildingName);
            case "pitchDitch" -> new PitchDitch(empire, x, y, buildingName);
            case "cagedWarDogs" -> new CagedDogs(empire, x, y, buildingName);
            case "oilSmelter" -> new OilSmelter(empire, x, y, buildingName);
            case "wall" -> new Wall(empire, x, y, buildingName);
            default -> null;
        };
    }

    private static TowerType getTypeOfTowerTypeByName(BuildingName buildingName) {
        return switch (buildingName.getName()) {
            case "LookoutTower" -> TowerType.LOOKOUT_TOWER;
            case "PerimeterTurret" -> TowerType.PERIMETER_TOWER;
            case "DefenceTurret" -> TowerType.DEFENSE_TURRET;
            case "RoundTower" -> TowerType.ROUND_TOWER;
            default -> TowerType.SQUARE_TOWER;
        };
    }

    public static Outputs destroyBuilding(Building building) {
        if (building == null) return Outputs.EMPTY_SELECTED_BUILDING;

        int x1 = building.getBeginX();
        int y1 = building.getBeginY();
        int x2 = building.getEndX();
        int y2 = building.getEndY();

        for (int i = x1; i < x2; i++)
            for (int j = y1; j < y2; j++)
                getMap().getTile(i, j).setBuilding(null);

        building.getEmpire().removeFromBuildings(building);
        if (building.getBuildingName().equals(BuildingName.STABLE))
            building.getEmpire().reduceHorseForDestroy((Stable) building);
        for (int i = 0; i < building.getBuildingName().getNumberOfWorkers(); i++)
            building.removeBuilding();
        return Outputs.SUCCESSFUL_DESTROY_BUILDING;
    }

    public Outputs createUnit(String type, String count) {

        boolean barrackBoolean = false;
        boolean mercenaryBoolean = false;
        boolean engineerGuildBoolean = false;
        boolean cathedralBoolean = false;
        boolean tunnelerBoolean = false;
        MaterialType material1 = null;
        MaterialType material2 = null;

        if (type == null) return Outputs.EMPTY_TYPE;
        if (buildingMenu.getSelectedBuilding() == null) return Outputs.EMPTY_SELECTED_BUILDING;
        if (count == null) return Outputs.EMPTY_COUNT;
        if (!count.matches("\\d+")) return Outputs.INVALID_COUNT;
        if (!checkValidMilitaryName(type)) return WRONG_UNIT_TYPE;
        if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("Barrack")) barrackBoolean = true;
        if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("MercenaryBarrack"))
            mercenaryBoolean = true;
        if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("EngineerGuild"))
            engineerGuildBoolean = true;
        if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("Cathedral")) cathedralBoolean = true;
        if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("TunnelerGuild"))
            tunnelerBoolean = true;
        if (!barrackBoolean && !mercenaryBoolean && !engineerGuildBoolean && !cathedralBoolean && !tunnelerBoolean)
            return Outputs.WRONG_UNIT_FOR_SELECTED_BUILDING;
        if (empire.getNormalPopulation() < Integer.parseInt(count)) return Outputs.NOT_ENOUGH_POPULATION;
        if (empire.getGold() < getPriceByName(type) * Integer.parseInt(count)) return Outputs.NOT_ENOUGH_MONEY;
        if (barrackBoolean) {
            material1 = getArmouryByName(type);
            material2 = getArmamentByName(type);
            if ((material1 != null && !empire.havingMaterial(material1, Integer.parseInt(count))) || (material2 != null && !empire.havingMaterial(material2, Integer.parseInt(count))) || (type.equals("knight") && !empire.havingMaterial(MaterialType.HORSE, Integer.parseInt(count))))
                return Outputs.NOT_ENOUGH_EQUIPMENT;
        }
        doCreateUnit(type, barrackBoolean, mercenaryBoolean, engineerGuildBoolean, cathedralBoolean, tunnelerBoolean, Integer.parseInt(count));
        if (material1 != null) empire.reduceMaterial(material1.getName(), Integer.parseInt(count));
        if (material2 != null) empire.reduceMaterial(material2.getName(), Integer.parseInt(count));
        empire.decreaseGold(getPriceByName(type) * Integer.parseInt(count));
        return Outputs.SUCCESSFUL_CREATE;
    }

    private boolean checkValidMilitaryName(String name) {
        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values())
            if (militaryUnitName.getName().equals(name)) return true;
        return false;
    }

    private boolean doCreateUnit(String type, boolean barrackBoolean, boolean mercenaryBoolean, boolean engineerGuildBoolean, boolean cathedralBoolean, boolean tunnelerBoolean, int count) {
        if (barrackBoolean) return BarrackMilitary(type, count);
        else if (mercenaryBoolean) return MercenaryBarrack(type, count);
        else if (engineerGuildBoolean) return Engineer(type, count);
        else if (cathedralBoolean) return BlackMonk(type, count);
        else if (tunnelerBoolean) return Tunneler(type, count);
        return false;
    }

    public int getPriceByName(String name) {
        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values())
            if (militaryUnitName.getName().equals(name)) return militaryUnitName.getCost();
        return -1;
    }

    public MaterialType getArmouryByName(String name) {
        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values())
            if (militaryUnitName.getName().equals(name)) return militaryUnitName.getArmour();
        return null;
    }

    public MaterialType getArmamentByName(String name) {
        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values())
            if (militaryUnitName.getName().equals(name)) return militaryUnitName.getArmament();
        return null;
    }

    private boolean BarrackMilitary(String militaryUnitName, int count) {
        int XY = findXY(BuildingName.BARRACK);
        int size = getMap().getSize();
        int x = XY / size;
        int y = XY % size;

        switch (militaryUnitName) {
            case "archer" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.ARCHER, x, y);
                MilitaryUnitName.ARCHER.getVoice().playVoice(MilitaryUnitName.ARCHER.getVoice());
                return true;
            }
            case "crossbowmen" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.CROSSBOW_MEN, x, y);
                MilitaryUnitName.CROSSBOW_MEN.getVoice().playVoice(MilitaryUnitName.CROSSBOW_MEN.getVoice());
                return true;
            }
            case "spearmen" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.SPEAR_MEN, x, y);
                MilitaryUnitName.SPEAR_MEN.getVoice().playVoice(MilitaryUnitName.SPEAR_MEN.getVoice());
                return true;
            }
            case "pikemen" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.PIKE_MEN, x, y);
                MilitaryUnitName.PIKE_MEN.getVoice().playVoice(MilitaryUnitName.PIKE_MEN.getVoice());
                return true;
            }
            case "macemen" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.MACE_MEN, x, y);
                MilitaryUnitName.MACE_MEN.getVoice().playVoice(MilitaryUnitName.MACE_MEN.getVoice());
                return true;
            }
            case "swordsmen" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.SWORDSMEN, x, y);
                MilitaryUnitName.SWORDSMEN.getVoice().playVoice(MilitaryUnitName.SWORDSMEN.getVoice());
                return true;
            }
            case "knight" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.KNIGHT, x, y);
                MilitaryUnitName.KNIGHT.getVoice().playVoice(MilitaryUnitName.KNIGHT.getVoice());
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public int findXY(BuildingName buildingName) {
        int buildingSize = buildingName.getSize();
        int x = buildingMenu.getSelectedBuilding().getBeginX();
        int y = buildingMenu.getSelectedBuilding().getBeginY();

        int size = getMap().getSize();

        for (int i = x; i < x + buildingSize; i++)
            if (y > 1 && i < size && getMap().getTile(i, y - 1) != null) return i * size + y - 1;
        for (int j = y; j < y + buildingSize; j++)
            if (x > 1 && j < size && getMap().getTile(x - 1, j) != null) return (x - 1) * size + j;
        for (int i = x; i < x + buildingSize; i++)
            if (y + buildingSize < size && i < size && getMap().getTile(i, y + buildingSize) != null)
                return i * size + y + buildingSize;
        for (int j = y; j < y + buildingSize; j++)
            if (x + buildingSize < size && j < size && getMap().getTile(x, j) != null)
                return (x + buildingSize) * size + j;
        return -1;
    }

    private boolean MercenaryBarrack(String militaryUnitName, int count) {
        int XY = findXY(BuildingName.MERCENARY_BARRACKS);
        int size = getMap().getSize();
        int x = XY / size;
        int y = XY % size;

        switch (militaryUnitName) {
            case "archerBow" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.ARCHER_BOW, x, y);
                MilitaryUnitName.ARCHER_BOW.getVoice().playVoice(MilitaryUnitName.ARCHER_BOW.getVoice());
                return true;
            }
            case "slingers" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.SLINGERS, x, y);
                MilitaryUnitName.SLINGERS.getVoice().playVoice(MilitaryUnitName.SLINGERS.getVoice());
                return true;
            }
            case "assassins" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.ASSASSINS, x, y);
                MilitaryUnitName.ASSASSINS.getVoice().playVoice(MilitaryUnitName.ASSASSINS.getVoice());
                return true;
            }
            case "horseArchers" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.HORSE_ARCHER, x, y);
                MilitaryUnitName.HORSE_ARCHER.getVoice().playVoice(MilitaryUnitName.HORSE_ARCHER.getVoice());
                return true;
            }
            case "arabianSwordsmen" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.ARABIAN_SWORSMEN, x, y);
                MilitaryUnitName.ARABIAN_SWORSMEN.getVoice().playVoice(MilitaryUnitName.ARABIAN_SWORSMEN.getVoice());
                return true;
            }
            case "fireThrowers" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.FIRE_THROWERS, x, y);
                MilitaryUnitName.FIRE_THROWERS.getVoice().playVoice(MilitaryUnitName.FIRE_THROWERS.getVoice());
                return true;
            }
            case "slaves" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.SLAVES, x, y);
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    private boolean Engineer(String militaryUnitName, int count) {
        int XY = findXY(BuildingName.ENGINEER_GUILD), size = getMap().getSize(), x = XY / size, y = XY % size;
        boolean haveOilSmelter = findOilSmelter();

        if (militaryUnitName.equals("engineer")) {
            for (int i = 0; i < count; i++) {
                Engineer engineer = new Engineer(getMap().getTile(x, y), empire, MilitaryUnitName.ENGINEER, x, y);
                if (haveOilSmelter) engineer.addOil();
            }
            MilitaryUnitName.ENGINEER.getVoice().playVoice(MilitaryUnitName.ENGINEER.getVoice());
            return true;
        } else if (militaryUnitName.equals("laddermen")) {
            for (int i = 0; i < count; i++)
                new LadderMen(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.LADDER_MEN, x, y);
            MilitaryUnitName.LADDER_MEN.getVoice().playVoice(MilitaryUnitName.LADDER_MEN.getVoice());
            return true;
        }
        return false;
    }

    private boolean findOilSmelter() {
        for (Building building : empire.getBuildings())
            if (building.getBuildingName().equals(BuildingName.OIL_SMELTER)) return true;
        return false;
    }

    private boolean Tunneler(String militaryName, int count) {
        int XY = findXY(BuildingName.TUNNELER_GUILD);
        int size = getMap().getSize();
        int x = XY / size;
        int y = XY % size;

        if (militaryName.equals("tunneler")) {
            for (int i = 0; i < count; i++)
                new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.TUNNELER, x, y);
            MilitaryUnitName.TUNNELER.getVoice().playVoice(MilitaryUnitName.TUNNELER.getVoice());
            return true;
        }
        return false;
    }

    private boolean BlackMonk(String militaryUnitName, int count) {
        int XY = findXY(BuildingName.CATHEDRAL);
        int size = getMap().getSize();
        int x = XY / size;
        int y = XY % size;
        if (militaryUnitName.equals("Black Monk")) {
            for (int i = 0; i < count; i++)
                new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.BLACK_MONK, x, y);
            MilitaryUnitName.BLACK_MONK.getVoice().playVoice(MilitaryUnitName.BLACK_MONK.getVoice());
            return true;
        }
        return false;
    }

    public static Outputs repair(Building building) {
        if (!getThisEmpire().havingMaterial(MaterialType.STONE, checkRepair(building)))
            return Outputs.NOT_ENOUGH_STONE;
        int x = building.getBeginX();
        int y = building.getBeginY();
        if (findNearEnemy(x, y))
            return Outputs.NEAR_ENEMY;
        int size = building.getBuildingName().getSize();
        building.getBuildingName().setHitPoint();
        getThisEmpire().reduceMaterial(building.getBuildingName().getName(), checkRepair(building));
        return Outputs.SUCCESSFUL_REPAIR;
    }

    public static boolean findNearEnemy(int x, int y) {
        for (int i = x - 5; i < x + 5; i++) {
            for (int j = y - 5; j < y + 5; y++) {
                if (getMap().getTile(x, y) != null && getMap().getTile(x, y).findNearEnemiesMilitaryUnit(getThisEmpire()).size() != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int checkRepair(Building building) {
        int maxHitPoint = building.getBuildingName().getMaxHitPoint();
        int hitPoint = building.getBuildingName().getHitPoint();
        int cost = (maxHitPoint - hitPoint) * building.getBuildingName().getStoneCost();
        return Math.abs(cost / maxHitPoint);
    }


    public void setEmpire(Empire empire) {
        this.empire = empire;
    }

}
