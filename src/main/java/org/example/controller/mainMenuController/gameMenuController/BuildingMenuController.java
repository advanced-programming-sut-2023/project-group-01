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

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

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
        } else if (Integer.parseInt(x) > empire.getMap().getSize() || Integer.parseInt(y) > empire.getMap().getSize()) {
            return Outputs.OUT_OF_RANGE;
        } else if (!empire.getMap().getTile(Integer.parseInt(x), Integer.parseInt(y)).getBuilding().getEmpire()
                .equals(buildingMenu.getEmpire())) {
            return Outputs.NOT_HAVING_BUILDING;
        } else {
            this.buildingMenu.setBuilding(empire.getMap().getTileWhitXAndY(Integer.parseInt(x), Integer.parseInt(y)).getBuilding());
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
        int buildingSize = building.getBuildingName().getSize();
        int mapSize = empire.getMap().getSize();

        if (building == null) {
            return Outputs.INVALID_BUILDING_TYPE;
        } else if (x0 > mapSize || y0 > mapSize || x0 + buildingSize > mapSize || y0 + buildingSize > mapSize) {
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
                return new Building(empire, x1, y1, buildingName);
            }
        }
        return null;
    }

    public boolean isGroundSuitable(Building building, int x, int y) {
        int size = building.getBuildingName().getSize();

        TypeOfTile typeOfTile;

        if ((typeOfTile = building.getBuildingName().getTypeCanBuildBuilding()).equals(TypeOfTile.NORMAL_GROUND)) {
            for (int i = x; i < x + size; i++) {
                for (int j = y; j < y + size; j++) {
                    if (empire.getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.SEA) ||
                            empire.getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.STONE_MINE) ||
                            empire.getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.IRON_MINE) ||
                            empire.getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.BEACH) ||
                            empire.getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.OIL_GROUND) ||
                            empire.getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.RIVER) ||
                            empire.getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.SHALLOW_WATER) ||
                            empire.getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.SMALL_POND) ||
                            empire.getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.BIG_POND)) {
                        return false;
                    }
                }
            }
            return true;
        }

        if ((typeOfTile = building.getBuildingName().getTypeCanBuildBuilding()).equals(TypeOfTile.OIL_GROUND)) {
            for (int i = x; i < x + size; i++) {
                for (int j = y; j < y + size; j++) {
                    if (!empire.getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.OIL_GROUND)) {
                        return false;
                    }
                }
            }
            return true;
        }

        if ((typeOfTile = building.getBuildingName().getTypeCanBuildBuilding()).equals(TypeOfTile.IRON_MINE)) {
            for (int i = x; i < x + size; i++) {
                for (int j = y; j < y + size; j++) {
                    if (!empire.getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.IRON_MINE)) {
                        return false;
                    }
                }
            }
            return true;
        }

        if ((typeOfTile = building.getBuildingName().getTypeCanBuildBuilding()).equals(TypeOfTile.STONE_MINE)) {
            for (int i = x; i < x + size; i++) {
                for (int j = y; j < y + size; j++) {
                    if (!empire.getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.STONE_MINE)) {
                        return false;
                    }
                }
            }
            return true;
        }

        if ((typeOfTile = building.getBuildingName().getTypeCanBuildBuilding()).equals(TypeOfTile.MEADOW)) {
            for (int i = x; i < x + size; i++) {
                for (int j = y; j < y + size; j++) {
                    if (!empire.getMap().getTile(i, j).getTypeOfTile().equals(TypeOfTile.MEADOW)) {
                        return false;
                    }
                }
            }
            return true;
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
        }

        int x1 = buildingMenu.getSelectedBuilding().getBeginX();
        int y1 = buildingMenu.getSelectedBuilding().getBeginY();
        int x2 = buildingMenu.getSelectedBuilding().getEndX();
        int y2 = buildingMenu.getSelectedBuilding().getEndY();

        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                //TODO check
                buildingMenu.getEmpire().getMap().getTile(x1, y1).setBuilding(null);
            }
        }
        return Outputs.SUCCESSFUL_DESTROY_BUILDING;
    }

    public Outputs createUnit(String type, String count) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        boolean barrackBoolean = false;
        boolean mercenaryBoolean = false;
        boolean engineerGuildBoolean = false;
        boolean cathedralBoolean = false;
        boolean tunnelerBoolean = false;
        Material material1 = null;
        Material material2 = null;

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
            engineerGuildBoolean = true;
        } else if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("Cathedral")) {
            cathedralBoolean = true;
        } else if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("Tunneler")) {
            tunnelerBoolean = true;
        } else if (empire.getPopulation() < Integer.parseInt(count)) {
            return Outputs.NOT_ENOUGH_POPULATION;
        }

        if (!barrackBoolean && !mercenaryBoolean && !engineerGuildBoolean && !cathedralBoolean && !tunnelerBoolean) {
            return Outputs.INVALID_MILITARY_TYPE;
        } else if (empire.getGold() > getPriceByName(type) * Integer.parseInt(count)) {
            return Outputs.NOT_ENOUGH_MONEY;
        } else if (barrackBoolean) {
            material1 = getArmouryByName(type);
            material2 = getArmamentByName(type);
            if ((material1 != null && !empire.havingMaterial(material1, Integer.parseInt(count))) &&
                    (material2 != null && !empire.havingMaterial(material2, Integer.parseInt(count)))) {
                return Outputs.NOT_ENOUGH_EQUIPMENT;
            }
        }
        doCreateUnit(type, barrackBoolean, mercenaryBoolean, engineerGuildBoolean, cathedralBoolean, tunnelerBoolean, Integer.parseInt(count));
        return Outputs.SUCCESSFUL_CREATE;
    }

    private void doCreateUnit(String type, boolean barrackBoolean, boolean mercenaryBoolean,
                              boolean engineerGuildBoolean, boolean cathedralBoolean, boolean tunnelerBoolean, int count) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        //TODO پاک کردن مردم عادی و چک کردن اندازه جمعیت
        if (barrackBoolean) {
            BarrackMilitary(type, count);
        } else if (mercenaryBoolean) {
            MercenaryBarrack(type, count);
        } else if (engineerGuildBoolean) {
            Engineer(type, count);
        } else if (cathedralBoolean) {
            BlackMonk(type, count);
        } else if (tunnelerBoolean) {
            Tunneler(type, count);
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

    private void BarrackMilitary(String militaryUnitName, int count) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        int x = buildingMenu.getSelectedBuilding().getBeginX();
        int y = buildingMenu.getSelectedBuilding().getBeginY();

        if (militaryUnitName.equals("Archer")) {
            for (int i = 0; i < count; i++) {
                new MilitaryUnit(empire.getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.ARCHER, x, y);
            }
            MilitaryUnitName.ARCHER.getVoice().playVoice(MilitaryUnitName.ARCHER.getVoice());
        } else if (militaryUnitName.equals("Crossbowmen")) {
            for (int i = 0; i < count; i++) {
                new MilitaryUnit(empire.getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.CROSSBOW_MEN, x, y);
            }
            MilitaryUnitName.CROSSBOW_MEN.getVoice().playVoice(MilitaryUnitName.CROSSBOW_MEN.getVoice());
        } else if (militaryUnitName.equals("Spearmen")) {
            for (int i = 0; i < count; i++) {
                new MilitaryUnit(empire.getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.SPEAR_MEN, x, y);
            }
            MilitaryUnitName.SPEAR_MEN.getVoice().playVoice(MilitaryUnitName.SPEAR_MEN.getVoice());
        } else if (militaryUnitName.equals("Pikemen")) {
            for (int i = 0; i < count; i++) {
                new MilitaryUnit(empire.getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.PIKE_MEN, x, y);
            }
            MilitaryUnitName.PIKE_MEN.getVoice().playVoice(MilitaryUnitName.PIKE_MEN.getVoice());
        } else if (militaryUnitName.equals("Macemen")) {
            for (int i = 0; i < count; i++) {
                new MilitaryUnit(empire.getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.MACE_MEN, x, y);
            }
            MilitaryUnitName.MACE_MEN.getVoice().playVoice(MilitaryUnitName.MACE_MEN.getVoice());
        } else if (militaryUnitName.equals("Swordsmen")) {
            for (int i = 0; i < count; i++) {
                new MilitaryUnit(empire.getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.SWORDSMEN, x, y);
            }
            MilitaryUnitName.SWORDSMEN.getVoice().playVoice(MilitaryUnitName.SWORDSMEN.getVoice());
        } else if (militaryUnitName.equals("Knight")) {
            for (int i = 0; i < count; i++) {
                new MilitaryUnit(empire.getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.KNIGHT, x, y);
            }
            MilitaryUnitName.KNIGHT.getVoice().playVoice(MilitaryUnitName.KNIGHT.getVoice());
        }
    }

    private void MercenaryBarrack(String militaryUnitName, int count) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        //TODO about assassins
        int x = buildingMenu.getSelectedBuilding().getBeginX();
        int y = buildingMenu.getSelectedBuilding().getBeginY();

        if (militaryUnitName.equals("Archer Bow")) {
            for (int i = 0; i < count; i++)
                new MilitaryUnit(empire.getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.ARCHER_BOW, x, y);
            MilitaryUnitName.ARCHER_BOW.getVoice().playVoice(MilitaryUnitName.ARCHER_BOW.getVoice());
        } else if (militaryUnitName.equals("Slingers")) {
            for (int i = 0; i < count; i++)
                new MilitaryUnit(empire.getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.SLINGERS, x, y);
            MilitaryUnitName.SLINGERS.getVoice().playVoice(MilitaryUnitName.SLINGERS.getVoice());
        } else if (militaryUnitName.equals("Assassins")) {
            for (int i = 0; i < count; i++)
                new MilitaryUnit(empire.getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.ASSASSINS, x, y);
            MilitaryUnitName.ASSASSINS.getVoice().playVoice(MilitaryUnitName.ASSASSINS.getVoice());
        } else if (militaryUnitName.equals("Horse Archers")) {
            for (int i = 0; i < count; i++)
                new MilitaryUnit(empire.getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.HORSE_ARCHER, x, y);
            MilitaryUnitName.HORSE_ARCHER.getVoice().playVoice(MilitaryUnitName.HORSE_ARCHER.getVoice());
        } else if (militaryUnitName.equals("Arabian Swordsmen")) {
            for (int i = 0; i < count; i++)
                new MilitaryUnit(empire.getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.ARABIAN_SWORSMEN, x, y);
            MilitaryUnitName.ARABIAN_SWORSMEN.getVoice().playVoice(MilitaryUnitName.ARABIAN_SWORSMEN.getVoice());
        } else if (militaryUnitName.equals("Fire Throwers")) {
            for (int i = 0; i < count; i++)
                new MilitaryUnit(empire.getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.FIRE_THROWERS, x, y);
            MilitaryUnitName.FIRE_THROWERS.getVoice().playVoice(MilitaryUnitName.FIRE_THROWERS.getVoice());
        }
    }

    private void Engineer(String militaryUnitName, int count) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        int x = buildingMenu.getSelectedBuilding().getBeginX();
        int y = buildingMenu.getSelectedBuilding().getBeginY();

        if (militaryUnitName.equals("Engineer")) {
            for (int i = 0; i < count; i++)
                new MilitaryUnit(empire.getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.ENGINEER, x, y);
            MilitaryUnitName.ENGINEER.getVoice().playVoice(MilitaryUnitName.ENGINEER.getVoice());
        } else if (militaryUnitName.equals("Laddermen")) {
            for (int i = 0; i < count; i++)
                new MilitaryUnit(empire.getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.LADDER_MEN, x, y);
            MilitaryUnitName.LADDER_MEN.getVoice().playVoice(MilitaryUnitName.LADDER_MEN.getVoice());
        }
    }

    private void Tunneler(String militaryName, int count) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        int x = buildingMenu.getSelectedBuilding().getBeginX();
        int y = buildingMenu.getSelectedBuilding().getBeginY();

        if (militaryName.equals("tunneler")) {
            for (int i = 0; i < count; i++)
                new MilitaryUnit(empire.getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.TUNNELER, x, y);
            MilitaryUnitName.TUNNELER.getVoice().playVoice(MilitaryUnitName.TUNNELER.getVoice());
        }
    }

    private void BlackMonk(String militaryUnitName, int count) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        int x = buildingMenu.getSelectedBuilding().getBeginX();
        int y = buildingMenu.getSelectedBuilding().getBeginY();

        if (militaryUnitName.equals("Black Monk")) {
            for (int i = 0; i < count; i++)
                new MilitaryUnit(empire.getMap().getTile(x, y), buildingMenu.getEmpire(), MilitaryUnitName.BLACK_MONK, x, y);
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
        if (empire.getMap().getTile(x, y).findUnit(this.empire) || empire.getMap().getTile(x, y).findUnit(this.empire) ||
                empire.getMap().getTile(x, y).findUnit(this.empire) || empire.getMap().getTile(x, y).findUnit(this.empire)) {
            return Outputs.NEAR_ENEMY;
        } else {
            int size = buildingMenu.getSelectedBuilding().getBuildingName().getSize();
            for (int i = x; i < x + size; i++) {
                for (int j = y; j < y + size; j++) {
                    buildingMenu.getSelectedBuilding().getBuildingName().setHitPoint();
                }
            }
            //TODO check
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

}
