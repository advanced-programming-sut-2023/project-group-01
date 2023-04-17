package org.example.view.enums.commands;

import java.util.regex.Matcher;

public enum MainMenuCommands {

    Temp("das");
    private final String regex;

    MainMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, MainMenuCommands mainMenuCommands){
        return null;
    }
}
