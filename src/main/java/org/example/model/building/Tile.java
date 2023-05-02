package org.example.model.building;

import org.example.model.Empire;
import org.example.model.People;
import org.example.model.building.enums.TypeOfTile;

import java.util.ArrayList;

public class Tile {
    private ArrayList<People> people = new ArrayList<>();
    private Building building;
    private TypeOfTile typeOfTile = TypeOfTile.NORMAL_GROUND;


    public void setTypeOfTile(TypeOfTile typeOfTile) {
        this.typeOfTile = typeOfTile;
    }

    public void addUnit(People person) {
        people.add(person);
    }

    public void removeUnit(People person) {
        people.remove(person);
    }
    public boolean findUnit(Empire empire) {
        for (People person : people) {
            if (!person.getEmpire().equals(empire)) {
                return true;
            }
        }
        return false;
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
}

