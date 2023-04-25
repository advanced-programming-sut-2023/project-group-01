package org.example.model;

import org.example.model.building.Tile;

public class People {
    protected Tile position;
    protected User player;
    public People(Tile position, User player) {
        this.position = position;
        this.player = player;
    }

    public Tile getPosition() {
        return position;
    }

    public User getPlayer() {
        return player;
    }
}
