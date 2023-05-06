package org.example.view.mainMenu.gameMenu;

import org.example.controller.mainMenuController.gameMenuController.EmpireMenuController;
import org.example.model.Empire;
import org.example.model.enums.FoodType;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.GameMenuCommands.EmpireMenuCommands;

import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class EmpireMenu {

    private final Empire empire;

    public EmpireMenu(Empire empire) {
        this.empire = empire;
    }

    public void run(Scanner scanner) {
        while (true) {
            String command = scanner.nextLine();
            Matcher matcher;
            if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.SHOW_POPULARITY_FACTORS)) != null)
                showPopularityFactors();
            else if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.SHOW_POPULARITY)) != null)
                showPopularity();
            else if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.SHOW_FOOD_LIST)) != null)
                showFoodList();
            else if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.CHANGE_FOOD_RATE)) != null)
                foodRateChecker(matcher);
            else if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.SHOW_FOOD_RATE)) != null)
                foodRateShow();
            else if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.CHANGE_TAX_RATE)) != null)
                taxRateChecker(matcher);
            else if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.SHOW_TAX_RATE)) != null)
                taxRateShow();
            else if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.CHANGE_FEAR_RATE)) != null)
                fearRateChecker(matcher);
            else System.out.println("Invalid command!");
        }
    }

    public void foodRateChecker(Matcher matcher) {
        try {
            int foodRate = Integer.valueOf(matcher.group("foodRate"));
            Outputs outputs = new EmpireMenuController().foodRate(foodRate);
            System.out.println(outputs.toString());
        } catch (NumberFormatException e) {
            System.out.println("Your food rate is not a valid number please try again.");
        }
    }

    public void taxRateChecker(Matcher matcher) {
        try {
            int taxRate = Integer.valueOf(matcher.group("taxRate"));
            Outputs outputs = new EmpireMenuController().taxRate(taxRate);
            System.out.println(outputs.toString());
        } catch (NumberFormatException e) {
            System.out.println("Your tax rate not number please try again.");
        }
    }

    public void fearRateChecker(Matcher matcher) {
        try {
            int fearRate = Integer.valueOf(matcher.group("fearRate"));
            Outputs outputs = new EmpireMenuController().fearRate(fearRate);
            System.out.println(outputs.toString());
        } catch (NumberFormatException e) {
            System.out.println("Your fear rate not number please try again.");
        }
    }

    public void showPopularityFactors() {
        //TODO check detail
        System.out.println("food");
        System.out.println("tax");
        System.out.println("religion");
        System.out.println("fear");
    }

    public void showPopularity() {
        System.out.println("Your popularity is " + empire.getPopularity());
    }

    public void showFoodList() {
        LinkedHashMap<FoodType, Integer> foodList = empire.getFoods();
        for (FoodType type : FoodType.values()) {
            System.out.println(type.toString() + ": " + foodList.get(type));
        }
    }

    public void foodRateShow() {
        System.out.println("Your food rate is " + empire.getFoodRate());
    }

    public void taxRateShow() {
        System.out.println("Your tax rate is " + empire.getTaxRate());
    }


}
