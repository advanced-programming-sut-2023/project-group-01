package org.example.model.building.castleBuilding;

import org.example.model.Empire;
import org.example.model.building.Tile;
import org.example.model.enums.Color;

import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;

public enum EmpireBuilding {

    EMPIRE_1(10, 10, Color.RED),
    EMPIRE_2(10, getMap().getMap().length / 2 , Color.YELLOW),
    EMPIRE_3(10, getMap().getMap().length -10, Color.ORANGE),
    EMPIRE_4(getMap().getMap().length / 2, 10, Color.DARK_BLUE),
    EMPIRE_5(getMap().getMap().length / 2, getMap().getMap().length - 10, Color.GREEN),
    EMPIRE_6(getMap().getMap().length - 10, 10, Color.LIGHT_BLUE),
    EMPIRE_7(getMap().getMap().length - 10, getMap().getMap().length / 2, Color.PURPLE),
    EMPIRE_8(getMap().getMap().length - 10, getMap().getMap().length - 10, Color.BLACK);

    private final int x;
    private final int y;
    private final Tile tile;
    private final Color color;

    EmpireBuilding(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.tile = getMap().getTileWhitXAndY(x, y);
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
}
