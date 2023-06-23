package org.example.controller.mainMenuController;

import org.example.model.Empire;
import org.example.model.Map;
import org.example.model.building.Building;
import org.example.model.building.castleBuilding.Wall;
import org.example.model.building.enums.BuildingName;
import org.example.model.unit.Catapult;
import org.example.model.unit.CatapultName;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;

import java.util.ArrayList;

import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;
import static org.example.view.mainMenu.gameMenu.GameMenu.setMap;

public class Test {
    public static void main(String[] args) {
        Empire empire1 = new Empire(null, null);
        Empire empire2 = new Empire(null, null);
        ArrayList<Empire> empires = new ArrayList<>();
        setMap(new Map(200));
        empires.add(empire1);
        empires.add(empire2);
        MilitaryUnit unit1 = new MilitaryUnit(getMap().getTile(20, 20), empire1, MilitaryUnitName.ENGINEER, 20, 20);
        MilitaryUnit unit2 = new MilitaryUnit(getMap().getTile(20, 20), empire2, MilitaryUnitName.ARCHER, 20, 20);
        System.out.println("unit1 : " + unit1.getHitPoint());
        System.out.println("unit2 : " + unit2.getHitPoint());
        System.out.println("attack1 : " + unit1.getMilitaryUnitName().getAttack());
        System.out.println("attack2 : " + unit2.getMilitaryUnitName().getAttack());

        Attack.attack(empires);
        System.out.println();
        System.out.println("unit1 : " + unit1.getHitPoint());
        System.out.println("unit2 : " + unit2.getHitPoint());

        Attack.attack(empires);
        System.out.println();
        System.out.println("unit1 : " + unit1.getHitPoint());
        System.out.println("unit2 : " + unit2.getHitPoint());


//        MilitaryUnit unit = new MilitaryUnit(getMap().getTile(19, 20), empire1, MilitaryUnitName.FIRE_THROWERS, 19, 20);
//        Building building = new Wall(empire2, 21, 20, BuildingName.WALL);
//        getMap().getTile(21, 20).setBuilding(building);
//        unit.setXAttack(21);
//        unit.setYAttack(20);
//        System.out.println("unit : " + unit.getMilitaryUnitName().getAttack());
//        System.out.println("building : " + building.getHitPoint());
//        System.out.println("building : " + building.isFiring());
//        Attack.attack(empires);
//        System.out.println("building : " + building.getHitPoint());
//        System.out.println("building : " + building.isFiring());

        //TODO : state -> done
        // attack catapult
        // attack catapult to building
        // attack fire
        // attack engineer
        // attack with brazier
        //TODO :
        // attack assassins
        // fire range and defend range
    }
}
