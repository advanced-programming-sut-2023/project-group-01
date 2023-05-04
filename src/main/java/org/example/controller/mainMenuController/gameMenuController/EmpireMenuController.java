package org.example.controller.mainMenuController.gameMenuController;

import org.example.view.enums.Outputs;

import java.util.regex.Matcher;

import static org.example.view.mainMenu.gameMenu.GameMenu.getThisEmpire;

public class EmpireMenuController {



    public Outputs foodRate(int rateNumber){
        if(rateNumber <= 2 && rateNumber >= -2) getThisEmpire().setFoodRate(rateNumber);
        else return Outputs.INVALID_RATE_FOR_FOOD;
        return Outputs.SUCCESS;
    }

    public Outputs taxRate(int taxNumber){
        if(taxNumber <= 2 && taxNumber >= -2) getThisEmpire().setTaxRate(taxNumber);
        else return Outputs.INVALID_RATE_FOR_FOOD;
        return Outputs.SUCCESS;
    }

    public Outputs fearRate(int fearNumber){
        if(fearNumber <= 2 && fearNumber >= -2) getThisEmpire().setFoodRate(fearNumber);
        else return Outputs.INVALID_RATE_FOR_FOOD;
        return Outputs.SUCCESS;
    }

}
