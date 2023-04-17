package org.example.view.enums.commands.GameMenuCommands;

import java.util.regex.Matcher;

public enum CreateMapMenuCommands {
    Temp("das");
    private final String regex;

    CreateMapMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, CreateMapMenuCommands createMapMenuCommands){
        return null;
    }

}
