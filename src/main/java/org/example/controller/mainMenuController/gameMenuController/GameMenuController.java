package org.example.controller.mainMenuController.gameMenuController;


import org.example.model.building.Tile;
import org.example.view.enums.Outputs;

public class GameMenuController {

    public Outputs showMap(Tile tile){
        if(tile == null)
            return Outputs.INVALID_COORDINATES;
        return Outputs.SUCCESS;
    }

    public void nextTurn(){

    }
}
