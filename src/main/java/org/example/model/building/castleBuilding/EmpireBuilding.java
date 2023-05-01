package org.example.model.building.castleBuilding;

import org.example.model.Empire;
import org.example.model.building.Tile;
import org.example.model.enums.Color;

import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;

public enum EmpireBuilding {

    EMPIRE_1(getMap().getTileWhitXAndY(10, 10), Color.RED),
    Empire_2(getMap().getTileWhitXAndY(10, getMap().getMap().length / 2), Color.YELLOW),
    Empire_3(getMap().getTileWhitXAndY(10, getMap().getMap().length - 10), Color.ORANGE),
    Empire_4(getMap().getTileWhitXAndY(getMap().getMap().length / 2, 10), Color.DARK_BLUE),
    Empire_5(getMap().getTileWhitXAndY(getMap().getMap().length / 2,
            getMap().getMap().length - 10), Color.GREEN),
    Empire_6(getMap().getTileWhitXAndY(getMap().getMap().length - 10, 10), Color.LIGHT_BLUE),
    Empire_7(getMap().getTileWhitXAndY(getMap().getMap().length - 10,
            getMap().getMap().length / 2), Color.PURPLE),
    Empire_8(getMap().getTileWhitXAndY(getMap().getMap().length - 10,
            getMap().getMap().length - 10), Color.BLACK);

    private final Tile tile;
    private final Color color;

    EmpireBuilding(Tile tile, Color color) {
        this.tile = tile;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
