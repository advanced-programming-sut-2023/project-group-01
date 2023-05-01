package org.example.controller.mainMenuController.gameMenuController;

import org.example.model.Empire;
import org.example.model.building.Material;
import org.example.model.building.enums.MaterialType;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;
import org.example.view.enums.Outputs;
import org.example.view.mainMenu.gameMenu.BuildingMenu;

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
        } else if (!empire.getMap().getTileWhitXAndY(Integer.parseInt(x), Integer.parseInt(y)).getBuilding()
                .getCurrentUser().equals(buildingMenu.getPlayer())) {
            return Outputs.NOT_HAVING_BUILDING;
        } else {
            this.buildingMenu.setBuilding(empire.getMap().getTileWhitXAndY(Integer.parseInt(x), Integer.parseInt(y)).getBuilding());
            return Outputs.VALID_SELECT_BUILDING;
        }
    }

    public Outputs dropBuilding(int x, int y, String type) {
        //TODO this
        return null;
    }

    public Outputs createUnit(String type, String count) {
        MilitaryUnit militaryUnit = null;
        //TODO tunneler
        if (type == null) {
            return Outputs.EMPTY_TYPE;
        } else if (buildingMenu.getSelectedBuilding() == null) {
            return Outputs.EMPTY_SELECTED_BUILDING;
        } else if (count == null) {
            return Outputs.EMPTY_COUNT;
        } else if (!count.matches("\\d+")) {
            return Outputs.INVALID_COUNT;
        } else if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("Barrack")) {
            militaryUnit = findBarrackMilitary(type);
        } else if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("Mercenary Barrack")) {
            militaryUnit = findMercenaryBarrack(type);
        } else if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("Engineer Guild")) {
            militaryUnit = findEngineer(type);                                          //ببیم کدوم کلیسا ها را میگه TODO
        } else if (buildingMenu.getSelectedBuilding().getBuildingName().getName().equals("Cathedral")) {
            militaryUnit = findBlackMonk(type);
        } else if (empire.getPopulation() < Integer.parseInt(count)) {
            return Outputs.NOT_ENOUGH_POPULATION;
        }
        //TODO some checks
        if (militaryUnit == null) {
            return Outputs.INVALID_MILITARY_TYPE;
        } else if (empire.getGold() > militaryUnit.getMilitaryUnitName().getCost() * Integer.parseInt(count)) {
            return Outputs.NOT_ENOUGH_MONEY;
        } else if (militaryUnit.getMilitaryUnitName().getType().equals("european")) {
            Material material1 = militaryUnit.getMilitaryUnitName().getArmour();
            Material material2 = militaryUnit.getMilitaryUnitName().getArmament();
            if ((material1 != null && !empire.havingMaterial(material1, Integer.valueOf(count))) &&
                    (material2 != null && !empire.havingMaterial(material2, Integer.parseInt(count)))) {
                return Outputs.NOT_ENOUGH_EQUIPMENT;
            }
        }

        return Outputs.SUCCESSFUL_CREATE;
    }

    private MilitaryUnit findBarrackMilitary(String militaryUnitName) {
        //TODO complete position
        return switch (militaryUnitName) {
            case "Archer" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getPlayer(), MilitaryUnitName.ARCHER);
            case "Crossbowmen" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getPlayer(), MilitaryUnitName.CROSSBOW_MEN);
            case "Spearmen" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getPlayer(), MilitaryUnitName.SPEAR_MEN);
            case "Pikemen" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getPlayer(), MilitaryUnitName.PIKE_MEN);
            case "Macemen" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getPlayer(), MilitaryUnitName.MACE_MEN);
            case "Swordsmen" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getPlayer(), MilitaryUnitName.SWORDSMEN);
            case "Knight" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getPlayer(), MilitaryUnitName.KNIGHT);
            default -> null;
        };
    }

    private MilitaryUnit findMercenaryBarrack(String militaryUnitName) {
        //TODO about assassins
        //TODO complete position
        return switch (militaryUnitName) {
            case "Archer Bow" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getPlayer(), MilitaryUnitName.ARCHER_BOW);
            case "Slaves" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getPlayer(), MilitaryUnitName.SLAVES);
            case "Slingers" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getPlayer(), MilitaryUnitName.SLINGERS);
            case "Assassins" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getPlayer(), MilitaryUnitName.ASSASSINS);
            case "Horse Archers" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getPlayer(), MilitaryUnitName.HORSE_ARCHER);
            case "Arabian Swordsmen" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getPlayer(), MilitaryUnitName.ARABIAN_SWORSMEN);
            case "Fire Throwers" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getPlayer(), MilitaryUnitName.FIRE_THROWERS);
            default -> null;
        };
    }

    private MilitaryUnit findEngineer(String militaryUnitName) {
        return switch (militaryUnitName) {
            case "Engineer" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getPlayer(), MilitaryUnitName.ENGINEER);
            case "Laddermen" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getPlayer(), MilitaryUnitName.LADDER_MEN);
            default -> null;
        };
    }

    private MilitaryUnit findBlackMonk(String militaryUnitName) {
        return switch (militaryUnitName) {
            case "Black Monk" ->
                    new MilitaryUnit(empire.getMap().getTile(0, 0), buildingMenu.getPlayer(), MilitaryUnitName.BLACK_MONK);

            default -> null;
        };
    }

    public Outputs repair() {
        //TODO وقتی سربازا نزدیک یا رئی قلعه هستن
        if (buildingMenu.getSelectedBuilding() == null) {
            return Outputs.EMPTY_SELECTED_BUILDING;
        } else if (!empire.havingMaterial(new Material(MaterialType.STONE), checkRepair())) {
            return Outputs.NOT_ENOUGH_STONE;
        }// else if () {
//
//        }
        else {
           buildingMenu.getSelectedBuilding().getBuildingName().setHitPoint();
           empire.addMaterial(buildingMenu.getSelectedBuilding().getBuildingName().getName(), checkRepair());
            return Outputs.SUCCESSFUL_REPAIR;
        }
    }

    public int checkRepair() {
        int maxHitPoint = buildingMenu.getSelectedBuilding().getBuildingName().getMaxHitPoint();
        int hitPoint = buildingMenu.getSelectedBuilding().getBuildingName().getHitPoint();
        int cost = (maxHitPoint - hitPoint) * buildingMenu.getSelectedBuilding().getBuildingName().getStoneCost();

        return Math.abs(cost/maxHitPoint);
    }


    public void setEmpire(Empire empire) {
        this.empire = empire;
    }

    public void setBuildingMenu(BuildingMenu buildingMenu) {
        this.buildingMenu = buildingMenu;
    }
}
