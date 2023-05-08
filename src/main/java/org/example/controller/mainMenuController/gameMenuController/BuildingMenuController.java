package org.example.controller.mainMenuController.gameMenuController;

import org.example.model.Empire;
import org.example.model.building.Building;
import org.example.model.building.Material;
import org.example.model.building.enums.BuildingName;
import org.example.model.building.enums.MaterialType;
import org.example.model.building.enums.TypeOfTile;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;
import org.example.view.enums.Outputs;
import org.example.view.mainMenu.gameMenu.BuildingMenu;
import org.example.view.mainMenu.gameMenu.GameMenu;

import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;

public class BuildingMenuController {
    private Empire empire;
    private BuildingMenu buildingMenu;

    public BuildingMenuController(Empire empire, BuildingMenu buildingMenu) {
        this.empire = empire;
        this.buildingMenu = buildingMenu;
    }

    public  Outputs selectBuilding(String x, String y) {
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
        } else if (!getMap().getTile(Integer.parseInt(x), Integer.parseInt(y)).getBuilding().getEmpire()
                .equals(buildingMenu.getEmpire())) {
            return Outputs.NOT_HAVING_BUILDING;
        } else {
            this.buildingMenu.setBuilding(getMap().getTileWhitXAndY(Integer.parseInt(x), Integer.parseInt(y)).getBuilding());
            return Outputs.VALID_SELECT_BUILDING;
        }
    }

    public Outputs dropBuilding(String x, String y, String type) {
        if (x == null) {
            return Outputs.EMPTY_X;
        } else if (y == null) {
            return Outputs.EMPTY_Y;
        } else if (!x.matches("\\d+")) {
            return Outputs.INVALID_X;
        } else if (!y.matches("\\d+")) {
            return Outputs.INVALID_Y;
        }
        BuildingName buildingName = findBuildingNameByName(type);
        if (buildingName == null)
            return Outputs.INVALID_BUILDING_TYPE;
        int x0 = Integer.parseInt(x);
        int y0 = Integer.parseInt(y);
        int buildingSize = buildingName.getSize();
        int mapSize = getMap().getSize();

        if (x0 > mapSize || y0 > mapSize || x0 + buildingSize > mapSize || y0 + buildingSize > mapSize)
            return Outputs.OUT_OF_RANGE;
        else if (isPositionFull(buildingName, x0, y0))
            return Outputs.FULL_POSITION;
        else if (isGroundSuitable(buildingName, x0, y0))
            return Outputs.NOT_SUITABLE_GROUND;
        putBuilding(buildingName, x0, y0, empire);
        return Outputs.SUCCESSFUL_DROP_BUILDING;
    }

    public BuildingName findBuildingNameByName(String name) {
        for (BuildingName buildingName : BuildingName.values()) {
            if (buildingName.getName().equals(name)) {
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
                    if (getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.SEA) ||
                            getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.STONE_MINE) ||
                            getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.IRON_MINE) ||
                            getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.BEACH) ||
                            getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.OIL_GROUND) ||
                            getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.RIVER) ||
                            getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.SHALLOW_WATER) ||
                            getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.SMALL_POND) ||
                            getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.BIG_POND)) {
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
        Building building = new Building(empire, x, y, buildingName);
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                getMap().getTile(i, j).setBuilding(building);
            }
        }
        empire.addToBuildings(building);
    }

    public Outputs destroyBuilding() {
        if (buildingMenu.getSelectedBuilding() == null) {
            return Outputs.EMPTY_SELECTED_BUILDING;
        }

        int x1 = buildingMenu.getSelectedBuilding().getBeginX();
        int y1 = buildingMenu.getSelectedBuilding().getBeginY();
        int x2 = buildingMenu.getSelectedBuilding().getEndX();
        int y2 = buildingMenu.getSelectedBuilding().getEndY();

        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                //TODO check
                GameMenu.getMap().getTile(i, j).setBuilding(null);
            }
        }
        buildingMenu.getSelectedBuilding().getEmpire().removeFromBuildings(buildingMenu.getSelectedBuilding());
        return Outputs.SUCCESSFUL_DESTROY_BUILDING;
    }

    public Outputs createUnit(String type, String count) {

        boolean barrackBoolean = false;
        boolean mercenaryBoolean = false;
        boolean engineerGuildBoolean = false;
        boolean cathedralBoolean = false;
        boolean tunnelerBoolean = false;
        Material material1;
        Material material2;

        if (type == null)
            return Outputs.EMPTY_TYPE;
        else if (buildingMenu.getSelectedBuilding() == null)
            return Outputs.EMPTY_SELECTED_BUILDING;
        else if (count == null)
            return Outputs.EMPTY_COUNT;
        else if (!count.matches("\\d+"))
            return Outputs.INVALID_COUNT;
        else if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("Barrack"))
            barrackBoolean = true;
        else if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("Mercenary Barrack"))
            mercenaryBoolean = true;
        else if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("Engineer Guild"))
            engineerGuildBoolean = true;
        else if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("Cathedral"))
            cathedralBoolean = true;
        else if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("Tunneler"))
            tunnelerBoolean = true;
        else if (empire.getPopulation() < Integer.parseInt(count))
            return Outputs.NOT_ENOUGH_POPULATION;

        if (!barrackBoolean && !mercenaryBoolean && !engineerGuildBoolean && !cathedralBoolean && !tunnelerBoolean)
            return Outputs.INVALID_MILITARY_TYPE;
        else if (empire.getGold() > getPriceByName(type) * Integer.parseInt(count))
            return Outputs.NOT_ENOUGH_MONEY;
        else if (barrackBoolean) {
            material1 = getArmouryByName(type);
            material2 = getArmamentByName(type);
            if ((material1 != null && !empire.havingMaterial(material1, Integer.parseInt(count))) &&
                    (material2 != null && !empire.havingMaterial(material2, Integer.parseInt(count))))
                return Outputs.NOT_ENOUGH_EQUIPMENT;
        }
        doCreateUnit(type, barrackBoolean, mercenaryBoolean, engineerGuildBoolean, cathedralBoolean
                , tunnelerBoolean, Integer.parseInt(count));
        return Outputs.SUCCESSFUL_CREATE;
    }

    private void doCreateUnit(String type, boolean barrackBoolean, boolean mercenaryBoolean,
                              boolean engineerGuildBoolean, boolean cathedralBoolean, boolean tunnelerBoolean, int count) {
        if (barrackBoolean) BarrackMilitary(type, count);
        else if (mercenaryBoolean) MercenaryBarrack(type, count);
        else if (engineerGuildBoolean) Engineer(type, count);
        else if (cathedralBoolean) BlackMonk(type, count);
        else if (tunnelerBoolean) Tunneler(type, count);
    }

    public int getPriceByName(String name) {
        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values())
            if (militaryUnitName.getName().equals(name))
                return militaryUnitName.getCost();
        return -1;
    }

    public Material getArmouryByName(String name) {
        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values())
            if (militaryUnitName.getName().equals(name))
                return militaryUnitName.getArmour();
        return null;
    }

    public Material getArmamentByName(String name) {
        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values())
            if (militaryUnitName.getName().equals(name))
                return militaryUnitName.getArmament();
        return null;
    }

    private void BarrackMilitary(String militaryUnitName, int count) {
        int XY = findXY(BuildingName.BARRACK);
        int size = getMap().getSize();
        int x = XY / size;
        int y = XY % size;
        //System.out.println("x :  " + x + "| y : " + y);

        switch (militaryUnitName) {
            case "Archer" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.ARCHER, x, y);
                MilitaryUnitName.ARCHER.getVoice().playVoice(MilitaryUnitName.ARCHER.getVoice());
            }
            case "Crossbowmen" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.CROSSBOW_MEN, x, y);
                MilitaryUnitName.CROSSBOW_MEN.getVoice().playVoice(MilitaryUnitName.CROSSBOW_MEN.getVoice());
            }
            case "Spearmen" ->{
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.SPEAR_MEN, x, y);
                MilitaryUnitName.SPEAR_MEN.getVoice().playVoice(MilitaryUnitName.SPEAR_MEN.getVoice());
            }
            case "Pikemen" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.PIKE_MEN, x, y);
                MilitaryUnitName.PIKE_MEN.getVoice().playVoice(MilitaryUnitName.PIKE_MEN.getVoice());
            }
            case "Macemen" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.MACE_MEN, x, y);
                MilitaryUnitName.MACE_MEN.getVoice().playVoice(MilitaryUnitName.MACE_MEN.getVoice());
            }
            case "Swordsmen" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.SWORDSMEN, x, y);
                MilitaryUnitName.SWORDSMEN.getVoice().playVoice(MilitaryUnitName.SWORDSMEN.getVoice());
            }
            case "Knight" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.KNIGHT, x, y);
                MilitaryUnitName.KNIGHT.getVoice().playVoice(MilitaryUnitName.KNIGHT.getVoice());
            }
        }
    }

    public int findXY(BuildingName buildingName) {
        int buildingSize = buildingName.getSize();
        int x = buildingMenu.getSelectedBuilding().getBeginX();
        int y = buildingMenu.getSelectedBuilding().getBeginY();
//        System.out.println("x of building " + x);
//        System.out.println("y of building " + y);
//        System.out.println("size " + getMap().getSize());
//        System.out.println();

        int size = getMap().getSize();
        //TODO check
        for (int i = x; i < x + buildingSize; i++)
            if (y > 1 && i < size)
                return i * size + y - 1;
        for (int j = y; j < y + buildingSize; j++)
            if (x > 1 && j < size)
                return (x - 1) * size + j;
        for (int i = x; i < x + buildingSize; i++)
            if (y + buildingSize < size && i < size)
                return i * size + y + buildingSize;
        for (int j = y; j < y + buildingSize; j++)
            if (x + buildingSize < size && j < size)
                return (x + buildingSize) * size + j;
        return -1;
    }

    private void MercenaryBarrack(String militaryUnitName, int count) {
        int XY = findXY(BuildingName.MERCENARY_BARRACKS);
        int size = getMap().getSize();
        int x = XY / size;
        int y = XY % size;

        switch (militaryUnitName) {
            case "Archer Bow" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.ARCHER_BOW, x, y);
                MilitaryUnitName.ARCHER_BOW.getVoice().playVoice(MilitaryUnitName.ARCHER_BOW.getVoice());
            }
            case "Slingers" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.SLINGERS, x, y);
                MilitaryUnitName.SLINGERS.getVoice().playVoice(MilitaryUnitName.SLINGERS.getVoice());
            }
            case "Assassins" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.ASSASSINS, x, y);
                MilitaryUnitName.ASSASSINS.getVoice().playVoice(MilitaryUnitName.ASSASSINS.getVoice());
            }
            case "Horse Archers" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.HORSE_ARCHER, x, y);
                MilitaryUnitName.HORSE_ARCHER.getVoice().playVoice(MilitaryUnitName.HORSE_ARCHER.getVoice());
            }
            case "Arabian Swordsmen" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.ARABIAN_SWORSMEN, x, y);
                MilitaryUnitName.ARABIAN_SWORSMEN.getVoice().playVoice(MilitaryUnitName.ARABIAN_SWORSMEN.getVoice());
            }
            case "Fire Throwers" -> {
                for (int i = 0; i < count; i++)
                    new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.FIRE_THROWERS, x, y);
                MilitaryUnitName.FIRE_THROWERS.getVoice().playVoice(MilitaryUnitName.FIRE_THROWERS.getVoice());
            }
        }
    }

    private void Engineer(String militaryUnitName, int count) {
        int XY = findXY(BuildingName.ENGINEER_GUILD);
        int size = getMap().getSize();
        int x = XY / size;
        int y = XY % size;

        if (militaryUnitName.equals("Engineer")) {
            for (int i = 0; i < count; i++)
                new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.ENGINEER, x, y);
            MilitaryUnitName.ENGINEER.getVoice().playVoice(MilitaryUnitName.ENGINEER.getVoice());
        } else if (militaryUnitName.equals("Laddermen")) {
            for (int i = 0; i < count; i++)
                new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.LADDER_MEN, x, y);
            MilitaryUnitName.LADDER_MEN.getVoice().playVoice(MilitaryUnitName.LADDER_MEN.getVoice());
        }
    }

    private void Tunneler(String militaryName, int count) {
        int XY = findXY(BuildingName.TUNNELER_GUILD);
        int size = getMap().getSize();
        int x = XY / size;
        int y = XY % size;

        if (militaryName.equals("tunneler")) {
            for (int i = 0; i < count; i++)
                new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.TUNNELER, x, y);
            MilitaryUnitName.TUNNELER.getVoice().playVoice(MilitaryUnitName.TUNNELER.getVoice());
        }
    }

    private void BlackMonk(String militaryUnitName, int count) {
        int XY = findXY(BuildingName.CATHEDRAL);
        int size = getMap().getSize();
        int x = XY / size;
        int y = XY % size;
        if (militaryUnitName.equals("Black Monk")) {
            for (int i = 0; i < count; i++)
                new MilitaryUnit(getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.BLACK_MONK, x, y);
            MilitaryUnitName.BLACK_MONK.getVoice().playVoice(MilitaryUnitName.BLACK_MONK.getVoice());
        }
    }

    public Outputs repair() {
        if (buildingMenu.getSelectedBuilding() == null) {
            return Outputs.EMPTY_SELECTED_BUILDING;
        } else if (!empire.havingMaterial(new Material(MaterialType.STONE), checkRepair())) {
            return Outputs.NOT_ENOUGH_STONE;
        }
        int x = buildingMenu.getSelectedBuilding().getBeginX();
        int y = buildingMenu.getSelectedBuilding().getBeginY();
        if (findNearEnemy(x, y)) {
            return Outputs.NEAR_ENEMY;
        } else {
            int size = buildingMenu.getSelectedBuilding().getBuildingName().getSize();
            for (int i = x; i < x + size; i++) {
                for (int j = y; j < y + size; j++) {
                    buildingMenu.getSelectedBuilding().getBuildingName().setHitPoint();
                }
            }
            //TODO check
            empire.reduceMaterial(buildingMenu.getSelectedBuilding().getBuildingName().getName(), checkRepair());
            return Outputs.SUCCESSFUL_REPAIR;
        }
    }

    private boolean findNearEnemy(int x, int y) {
        for (int i = x - 5; i < x + 5; i++) {
            for (int j = y - 5; j < y + 5; y++) {
                if (getMap().getTile(x, y) != null && getMap().getTile(x, y).findNearEnemiesMilitaryUnit(empire).size() != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int checkRepair() {
        int maxHitPoint = buildingMenu.getSelectedBuilding().getBuildingName().getMaxHitPoint();
        int hitPoint = buildingMenu.getSelectedBuilding().getBuildingName().getHitPoint();
        int cost = (maxHitPoint - hitPoint) * buildingMenu.getSelectedBuilding().getBuildingName().getStoneCost();

        return Math.abs(cost / maxHitPoint);
    }


    public void setEmpire(Empire empire) {
        this.empire = empire;
    }

}
