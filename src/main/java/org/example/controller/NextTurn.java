package org.example.controller;

import org.example.model.Empire;
import org.example.model.building.Tile;
import org.example.model.unit.MilitaryUnit;
import org.example.view.mainMenu.gameMenu.GameMenu;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.example.controller.mainMenuController.gameMenuController.MilitaryMenuController.findMilitary;

public class NextTurn {
    private ArrayList<Empire> empires;
    private final GameMenu gameMenu;
    private Empire currentEmpire;
    private ArrayList<Tile> attackingTiles;
    private LinkedHashMap<Tile, Tile> archerAttackingTile;
    private int numberOfEmpires;
    private int turnNumber;

    public NextTurn(GameMenu gameMenu) {
        this.attackingTiles = new ArrayList<>();
        this.archerAttackingTile = new LinkedHashMap<Tile, Tile>();
        this.gameMenu = gameMenu;
        this.turnNumber = 0;
    }

    public void addEmpire(Empire empire) {
        this.empires.add(empire);
        numberOfEmpires++;
    }

    public void addAttackingTile(Tile tile) {
        attackingTiles.add(tile);
    }

    public void archerAddAttackingTile(Tile tile1, Tile tile2) {
        archerAttackingTile.put(tile1, tile2);
    }

    public void nextTurn() {
        //TODO بعد هر دست بازم اتک ها باقی می مونه یا نه؟
        doScoreMove();


    }

    public void doRates() {

    }
    public void checkEmpireExist() {

    }

    public void doScoreMove() {

    }

    public void doPatrol() {

    }

    public void offensiveAttack() {

    }

    public void doArcherAttack() {
        //TODO روی تایل ها اتک میزنیم
        //TODO سربازای داخل یه خونه میجنگن

    }
    public void doAttack(String x, String y) {
        //TODO check some error
        //TODO check two type of attack
        //TODO catapult attack
        ArrayList<Empire> empires = gameMenu.getEmpires();

        boolean[] fire = new boolean[empires.size()];
        ArrayList<MilitaryUnit> empire1;
        ArrayList<MilitaryUnit> empire2;

        for (int i = 0; i < empires.size(); i++) {
            empire1 = findMilitary(Integer.parseInt(x), Integer.parseInt(y), empires.get(i));
            if (empire1.size() > 0) {
                for (int j = 0; j < empires.size(); j++) {
                    empire2 = findMilitary(Integer.parseInt(x), Integer.parseInt(y), empires.get(j));
                    if (empire2.size() > 0 && !fire[j]) {
                        attackTwoTroop(empire1, empire2);
                    }
                }
            }
            fire[i] = true;
        }
    }

    private void attackTwoTroop(ArrayList<MilitaryUnit> militaryUnitEmpire1, ArrayList<MilitaryUnit> militaryUnitEmpire2) {
        int damageEmpire1 = 0;
        int damageEmpire2 = 0;

        for (MilitaryUnit militaryUnit : militaryUnitEmpire1)
            damageEmpire1 += militaryUnit.getMilitaryUnitName().getAttack();
        for (MilitaryUnit militaryUnit : militaryUnitEmpire2)
            damageEmpire2 += militaryUnit.getMilitaryUnitName().getAttack();

        damageEmpire1 /= militaryUnitEmpire2.size();
        damageEmpire2 /= militaryUnitEmpire1.size();

        for (MilitaryUnit militaryUnit : militaryUnitEmpire1) {
            if (militaryUnit.getMilitaryUnitName().getHitPoint() > damageEmpire2) {
                militaryUnit.getMilitaryUnitName().reduceHitPoint(damageEmpire2);
            } else {
                //TODO remove the troop
            }
        }
        for (MilitaryUnit militaryUnit : militaryUnitEmpire2) {
            if (militaryUnit.getMilitaryUnitName().getHitPoint() > damageEmpire1) {
                militaryUnit.getMilitaryUnitName().reduceHitPoint(damageEmpire1);
            } else {
                //TODO remove the troop
            }
        }
    }

    public Empire getCurrentEmpire() {
        return currentEmpire;
    }
}
