package org.example.controller.mainMenuController.gameMenuController;

import org.example.model.building.Tile;
import org.example.model.building.enums.TypeOfTile;
import org.example.view.enums.Outputs;

import java.util.regex.Matcher;

import static org.example.view.mainMenu.gameMenu.CreateMapMenu.gameMap;

public class CreateMapMenuController {

    public Outputs setTextureForATile(Tile tile, String type) {
        if(tile == null)
            return Outputs.INVALID_COORDINATES;
        if(tile.getBuilding() != null)
            return Outputs.TILE_NOT_EMPTY;
        try{
            tile.setTypeOfTile(TypeOfTile.valueOf(type));
            return Outputs.SUCCESS;
        }
        catch (IllegalArgumentException e){
            return Outputs.INVALID_TYPE_OF_TILE;
        }
    }

    public Outputs setTextureForARectangle(int x1, int y1, int x2, int y2, String type) {
        if(x1>x2 || y1<y2 ||
                gameMap.getTileWhitXAndY(x1,y1) == null ||
                gameMap.getTileWhitXAndY(x2,y2) == null)
            return Outputs.INVALID_COORDINATES;
        try{
            TypeOfTile typeOfTile = TypeOfTile.valueOf("type");
            for (int i=x1; i<=x2; i++)
                for(int j=y2; j<= y1; j++)
                    if(gameMap.getTileWhitXAndY(i,j).getBuilding() != null)
                        return Outputs.TILE_NOT_EMPTY;
            for (int i=x1; i<=x2; i++)
                for(int j=y2; j<= y1; j++)
                    gameMap.getTileWhitXAndY(i,j).setTypeOfTile(typeOfTile);
            return Outputs.SUCCESS;
        }
        catch (IllegalArgumentException e){
            return Outputs.INVALID_TYPE_OF_TILE;
        }

    }

    public Outputs clear(Tile tile) {
        return null;
    }

    public Outputs dropRock(Tile tile, String type) {
        return null;
    }

    public Outputs dropTree(Tile tile, String type) {
        return null;
    }

    public Outputs dropBuilding(int xOfBuilding, int yOfBuilding, String type) {
        return null;
    }

    public Outputs dropUnit(Tile tile, String type, int count) {
        return null;
    }


}
