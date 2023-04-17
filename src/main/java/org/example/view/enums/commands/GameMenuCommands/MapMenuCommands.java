package org.example.view.enums.commands.GameMenuCommands;

import java.util.regex.Matcher;

public enum MapMenuCommands {

    Temp("das");
    private final String regex;

    MapMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, MapMenuCommands mapMenuCommands){
        return null;
    }
}
