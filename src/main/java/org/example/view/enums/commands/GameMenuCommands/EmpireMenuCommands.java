package org.example.view.enums.commands.GameMenuCommands;

import org.example.model.Empire;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum EmpireMenuCommands {
    SHOW_POPULARITY_FACTORS("^show popularity factors$"),
    SHOW_POPULARITY("^show popularity$"),
    SHOW_FOOD_LIST("^show food list$"),
    CHANGE_FOOD_RATE("^change food rate -r (?<foodRate>.+)"),
    SHOW_FOOD_RATE("^show food rate$"),
    CHANGE_TAX_RATE("^change tax rate -r (?<taxRate>.+)"),
    SHOW_TAX_RATE("^show tax rate$"),
    CHANGE_FEAR_RATE("^change fear rate -r (?<fearRate>.+)");
    private final String regex;


    EmpireMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, EmpireMenuCommands empireMenuCommands){
        Pattern pattern = Pattern.compile(empireMenuCommands.regex);
        if (pattern.matcher(input).find())
            return pattern.matcher(input);
        return null;
    }
}
