package org.example.model;

import org.example.model.building.Tile;

public class People {
    protected Tile position;
    protected Empire empire;
    public People(Tile position, Empire empire) {
        this.position = position;
        this.empire = empire;
    }

    public Tile getPosition() {
        return position;
    }

    public Empire getEmpire() {
        return empire;
    }

}
