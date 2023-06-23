package org.example.model.building;

import org.example.model.Empire;
import org.example.model.People;
import org.example.model.building.enums.TypeOfTile;
import org.example.model.unit.MilitaryUnit;

import java.util.ArrayList;

public class Tile {
    private ArrayList<People> people = new ArrayList<>();
    private Building building;
    private TypeOfTile typeOfTile = TypeOfTile.NORMAL_GROUND;
    private boolean haveDisease;

    public void setTypeOfTile(TypeOfTile typeOfTile) {
        this.typeOfTile = typeOfTile;
    }

    public void addUnit(People person) {
        people.add(person);
    }

    public void removeUnit(People person) {
        people.remove(person);
    }

    public ArrayList<MilitaryUnit> findUnit(Empire empire) {
        ArrayList<MilitaryUnit> militaryUnits = new ArrayList<MilitaryUnit>();
        for (People person : people) {
            if (person.getEmpire().equals(empire) && person instanceof MilitaryUnit) {
                militaryUnits.add((MilitaryUnit) person);
            }
        }
        return militaryUnits;
    }

    public ArrayList<MilitaryUnit> findNearEnemiesMilitaryUnit(Empire empire) {
        ArrayList<MilitaryUnit> enemies = new ArrayList<MilitaryUnit>();
        for (People person : people) {
            if (!person.getEmpire().equals(empire) && person instanceof MilitaryUnit) {
                enemies.add((MilitaryUnit) person);
            }
        }
        return enemies;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }


    public Building getBuilding() {
        return building;
    }

    public TypeOfTile getTypeOfTile() {
        return typeOfTile;
    }

    public ArrayList<People> getPeople() {
        return people;
    }

    public void removeAllUnit() {
        people.clear();
    }

    public void setImage() {

    }

    public void setHaveDisease(boolean haveDisease) {
        this.haveDisease = haveDisease;
    }

    public boolean isHaveDisease() {
        return haveDisease;
    }
}

