package org.example.view.enums.commands.GameMenuCommands;

import java.util.regex.Matcher;

public enum GameMenuCommands {

    Temp("das");
    private final String regex;

    GameMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, GameMenuCommands gameMenuCommands){
        return null;
    }
}
