package org.example.view.enums.commands.GameMenuCommands;

import org.example.model.Empire;

import java.util.regex.Matcher;

public enum MilitaryMenuCommands {

    Temp("das");
    private final String regex;

    MilitaryMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, MilitaryMenuCommands militaryMenuCommands){
        return null;
    }
}