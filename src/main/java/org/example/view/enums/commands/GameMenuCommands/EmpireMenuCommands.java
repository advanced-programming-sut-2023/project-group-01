package org.example.view.enums.commands.GameMenuCommands;

import org.example.model.Empire;

import java.util.regex.Matcher;

public enum EmpireMenuCommands {
    SHOW_POPULARITY_FACTORS(""),
    SHOW_POPULARITY(""),
    SHOW_FOOD_LIST(""),
    CHANGE_FOOD_RATE(""),
    SHOW_FOOD_RATE(""),
    CHANGE_TAX_RATE(""),
    SHOW_TAX_RATE(""),
    CHANGE_FEAR_RATE("");
    private final String regex;


    EmpireMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, EmpireMenuCommands empireMenuCommands){
        return null;
    }
}
