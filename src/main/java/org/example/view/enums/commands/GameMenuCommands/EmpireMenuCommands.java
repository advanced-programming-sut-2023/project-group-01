package org.example.view.enums.commands.GameMenuCommands;

import org.example.model.Empire;

import java.util.regex.Matcher;

public enum EmpireMenuCommands {
    Temp("das");
    private final String regex;


    EmpireMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, EmpireMenuCommands empireMenuCommands){
        return null;
    }
}
