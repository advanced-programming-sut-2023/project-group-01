package org.example.controller.mainMenuController.gameMenuController;

import org.example.view.enums.Outputs;

import static org.example.view.mainMenu.gameMenu.GameMenu.getThisEmpire;

public class EmpireMenuController {

    public Outputs foodRate(int rateNumber) {
        if (rateNumber <= 2 && rateNumber >= -2) getThisEmpire().setFoodRate(rateNumber);
        else return Outputs.INVALID_RATE_FOR_FOOD;
        return Outputs.SUCCESS;
    }

    public Outputs taxRate(int taxNumber) {
        if (!getThisEmpire().havingSmallGate()) return Outputs.NOT_HAVING_SMALL_GATEHOUSE;
        if (taxNumber <= 8 && taxNumber >= -3) getThisEmpire().setTaxRate(taxNumber);
        else return Outputs.INVALID_RATE_FOR_TAX;
        return Outputs.SUCCESS;
    }

    public Outputs fearRate(int fearNumber) {
        if (fearNumber <= 5 && fearNumber >= -5) getThisEmpire().setFearRate(fearNumber);
        else return Outputs.INVALID_RATE_FOR_FEAR;
        return Outputs.SUCCESS;
    }

}
