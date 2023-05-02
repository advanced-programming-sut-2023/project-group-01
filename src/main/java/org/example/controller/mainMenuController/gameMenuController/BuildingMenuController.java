package org.example.controller.mainMenuController.gameMenuController;

import org.example.Main;
import org.example.Voice;
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

import javax.sound.sampled.*;
import java.io.IOException;

public class BuildingMenuController {
    private Empire empire;
    private BuildingMenu buildingMenu;

    public Outputs selectBuilding(String x, String y) {
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
        } else if (!empire.getMap().getTile(Integer.parseInt(x), Integer.parseInt(y)).getBuilding()
                .getEmpire().equals(buildingMenu.getEmpire())) {
            return Outputs.NOT_HAVING_BUILDING;
        } else {
            this.buildingMenu.setBuilding(empire.getMap().getTile(Integer.parseInt(x), Integer.parseInt(y)).getBuilding());
            return Outputs.VALID_SELECT_BUILDING;
        }
    }

    public Outputs dropBuilding(String x, String y, String type) {
        //TODO اگر ساختمون ساخته نشد باید پاک شود
        if (x == null) {
            return Outputs.EMPTY_X;
        } else if (y == null) {
            return Outputs.EMPTY_Y;
        } else if (!x.matches("\\d+")) {
            return Outputs.INVALID_X;
        } else if (!y.matches("\\d+")) {
            return Outputs.INVALID_Y;
        }
        Building building = findBuildingByName(type, Integer.parseInt(x), Integer.parseInt(y));
        int x0 = Integer.parseInt(x);
        int y0 = Integer.parseInt(y);

        if (building == null) {
            return Outputs.INVALID_BUILDING_TYPE;
        } else if (isOutOfRange(building, x0, y0)) {
            return Outputs.OUT_OF_RANGE;
        } else if (isPositionFull(building, x0, y0)) {
            return Outputs.FULL_POSITION;
        } else if (isGroundSuitable(building, x0, y0)) {
            return Outputs.NOT_SUITABLE_GROUND;
        } else {
            putBuilding(building, x0, y0);
            return Outputs.SUCCESSFUL_DROP_BUILDING;
        }
    }

    public Building findBuildingByName(String name, int x1, int y1) {
        //TODO set the position of units
        for (BuildingName buildingName : BuildingName.values()) {
            if (buildingName.getName().equals(name)) {
                return new Building(empire, x1, y1, x1 + buildingName.getSize(), y1 + buildingName.getSize(), buildingName);
            }
        }
        return null;
    }

    public boolean isGroundSuitable(Building building, int x, int y) {
        int size = building.getBuildingName().getSize();

        if (building.getBuildingName().getTypeCanBuildBuilding().equals(TypeOfTile.NORMAL_GROUND)) {
            for (int i = x; i < x + size; i++) {
                for (int j = y; j < y + size; j++) {
                    //TODO Should be checked errors
                }
            }
        }
        return false;
    }

    //TODO duplicating code

    //TODO can brief is outOf Range
    public boolean isOutOfRange(Building building, int x, int y) {
        int size = building.getBuildingName().getSize();
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                if (i > empire.getMap().getSize() || j > empire.getMap().getSize()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPositionFull(Building building, int x, int y) {
        int size = building.getBuildingName().getSize();
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                if (empire.getMap().getTile(i, j).getBuilding() != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public void putBuilding(Building building, int x, int y) {
        int size = building.getBuildingName().getSize();
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                empire.getMap().getTile(i, j).setBuilding(building);
            }
        }
    }


    public Outputs destroyBuilding() {
        if (buildingMenu.getSelectedBuilding() == null) {
            return Outputs.EMPTY_SELECTED_BUILDING;
        } else {
            removeBuilding();
            return Outputs.SUCCESSFUL_DESTROY_BUILDING;
        }
    }

    public void removeBuilding() {
        int size = buildingMenu.getSelectedBuilding().getBuildingName().getSize();
        int x1 = buildingMenu.getSelectedBuilding().getBeginX();
        int y1 = buildingMenu.getSelectedBuilding().getBeginY();
        int x2 = buildingMenu.getSelectedBuilding().getEndX();
        int y2 = buildingMenu.getSelectedBuilding().getEndY();


        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                buildingMenu.getEmpire().getMap().getTile(x1, y1).setBuilding(null);
            }
        }
    }

    public Outputs createUnit(String type, String count) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        boolean barrackBoolean = false;
        boolean mercenaryBoolean = false;
        boolean engineerGuildBoolean = false;
        boolean cathedralBoolean = false;
        boolean tunnelerBoolean = false;
        Material material1 = null;
        Material material2 = null;

        //TODO number of troops
        if (type == null) {
            return Outputs.EMPTY_TYPE;
        } else if (buildingMenu.getSelectedBuilding() == null) {
            return Outputs.EMPTY_SELECTED_BUILDING;
        } else if (count == null) {
            return Outputs.EMPTY_COUNT;
        } else if (!count.matches("\\d+")) {
            return Outputs.INVALID_COUNT;
        } else if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("Barrack")) {
            barrackBoolean = true;
        } else if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("Mercenary Barrack")) {
            mercenaryBoolean = true;
        } else if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("Engineer Guild")) {
            engineerGuildBoolean = true;                                          //ببیم کدوم کلیسا ها را میگه TODO
        } else if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("Cathedral")) {
            cathedralBoolean = true;//todo tunneler guild
        } else if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("Tunneler")) {
            tunnelerBoolean = true;
        } else if (empire.getPopulation() < Integer.parseInt(count)) {
            return Outputs.NOT_ENOUGH_POPULATION;
        }
        //todo باید جای سرباز ها را ست کنیم

        if (!barrackBoolean && !mercenaryBoolean && !engineerGuildBoolean && !cathedralBoolean && !tunnelerBoolean) {
            return Outputs.INVALID_MILITARY_TYPE;
                                                    //TODO check this
        } else if (empire.getGold() > getPriceByName(type) * Integer.parseInt(count)) {
            return Outputs.NOT_ENOUGH_MONEY;
        } else if (barrackBoolean) {
            //TODO some checks
            material1 = getArmouryByName(type);
            material2 = getArmamentByName(type);
            if ((material1 != null && !empire.havingMaterial(material1, Integer.valueOf(count))) &&
                    (material2 != null && !empire.havingMaterial(material2, Integer.parseInt(count)))) {
                return Outputs.NOT_ENOUGH_EQUIPMENT;
            }
        }
        doCreateUnit(type, barrackBoolean, mercenaryBoolean, engineerGuildBoolean, cathedralBoolean, tunnelerBoolean, Integer.parseInt(count));
        return Outputs.SUCCESSFUL_CREATE;
    }

    private void doCreateUnit(String type,boolean barrackBoolean, boolean mercenaryBoolean, boolean engineerGuildBoolean, boolean cathedralBoolean, boolean tunnelerBoolean, int count) {

        //TODO پاک کردن مردم عادی و چک کردن اندازه جمعیت

        if (barrackBoolean) {
            for (int i = 0; i < count; i++) {
                findBarrackMilitary(type);
            }
        } else if (mercenaryBoolean) {
            for (int i = 0; i < count; i++) {
                findMercenaryBarrack(type);
            }
        } else if (engineerGuildBoolean) {
            for (int i = 0; i < count; i++) {
                findEngineer(type);
            }
        } else if (cathedralBoolean) {
            for (int i = 0; i < count; i++) {
                findBlackMonk(type);
            }
        } else if (tunnelerBoolean){
            for (int i = 0; i < count; i++) {
                findTunneler(type);
            }
        }
    }

    public int getPriceByName(String name) {
        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values()) {
            if (militaryUnitName.getName().equals(name)) {
                return militaryUnitName.getCost();
            }
        }
        return -1;
    }

    public Material getArmouryByName(String name) {
        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values()) {
            if (militaryUnitName.getName().equals(name)) {
                return militaryUnitName.getArmour();
            }
        }
        return null;
    }

    public Material getArmamentByName(String name) {
        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values()) {
            if (militaryUnitName.getName().equals(name)) {
                return militaryUnitName.getArmament();
            }
        }
        return null;
    }

    private MilitaryUnit findBarrackMilitary(String militaryUnitName) {
        //TODO complete position
        return switch (militaryUnitName) {
            case "Archer" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getEmpire(), MilitaryUnitName.ARCHER);
            case "Crossbowmen" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getEmpire(), MilitaryUnitName.CROSSBOW_MEN);
            case "Spearmen" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getEmpire(), MilitaryUnitName.SPEAR_MEN);
            case "Pikemen" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getEmpire(), MilitaryUnitName.PIKE_MEN);
            case "Macemen" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getEmpire(), MilitaryUnitName.MACE_MEN);
            case "Swordsmen" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getEmpire(), MilitaryUnitName.SWORDSMEN);
            case "Knight" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getEmpire(), MilitaryUnitName.KNIGHT);
            default -> null;
        };
    }

    private MilitaryUnit findMercenaryBarrack(String militaryUnitName) {
        //TODO about assassins
        //TODO complete position
        return switch (militaryUnitName) {
            case "Archer Bow" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getEmpire(), MilitaryUnitName.ARCHER_BOW);
            case "Slaves" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getEmpire(), MilitaryUnitName.SLAVES);
            case "Slingers" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getEmpire(), MilitaryUnitName.SLINGERS);
            case "Assassins" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getEmpire(), MilitaryUnitName.ASSASSINS);
            case "Horse Archers" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getEmpire(), MilitaryUnitName.HORSE_ARCHER);
            case "Arabian Swordsmen" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getEmpire(), MilitaryUnitName.ARABIAN_SWORSMEN);
            case "Fire Throwers" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getEmpire(), MilitaryUnitName.FIRE_THROWERS);
            default -> null;
        };
    }

    private MilitaryUnit findEngineer(String militaryUnitName) {
        return switch (militaryUnitName) {
            case "Engineer" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getEmpire(), MilitaryUnitName.ENGINEER);
            case "Laddermen" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getEmpire(), MilitaryUnitName.LADDER_MEN);
            default -> null;
        };
    }

    private MilitaryUnit findTunneler(String militaryName) {
        return switch (militaryName) {
            case "tunneler" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getEmpire(), MilitaryUnitName.TUNNELER);
            default -> null;
        };
    }

    private MilitaryUnit findBlackMonk(String militaryUnitName) {
        return switch (militaryUnitName) {
            case "Black Monk" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getEmpire(), MilitaryUnitName.BLACK_MONK);

            default -> null;
        };
    }

    public Outputs repair() {
        if (buildingMenu.getSelectedBuilding() == null) {
            return Outputs.EMPTY_SELECTED_BUILDING;
        } else if (!empire.havingMaterial(new Material(MaterialType.STONE), checkRepair())) {
            return Outputs.NOT_ENOUGH_STONE;
        }
        int x = buildingMenu.getSelectedBuilding().getBeginX();
        int y = buildingMenu.getSelectedBuilding().getBeginY();
        if (empire.getMap().getTile(x, y).findUnit(this.empire) || empire.getMap().getTile(x, y).findUnit(this.empire) ||
                empire.getMap().getTile(x, y).findUnit(this.empire) || empire.getMap().getTile(x, y).findUnit(this.empire)) {
            return Outputs.NEAR_ENEMY;
        } else {
            buildingMenu.getSelectedBuilding().getBuildingName().setHitPoint();
            empire.addMaterial(buildingMenu.getSelectedBuilding().getBuildingName().getName(), checkRepair());
            return Outputs.SUCCESSFUL_REPAIR;
        }
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

    public void setBuildingMenu(BuildingMenu buildingMenu) {
        this.buildingMenu = buildingMenu;
    }
}
