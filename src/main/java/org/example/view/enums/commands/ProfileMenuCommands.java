package org.example.view.enums.commands;

import java.util.regex.Matcher;

public enum ProfileMenuCommands {
    Temp("das");
    private final String regex;

    ProfileMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, ProfileMenuCommands profileMenuCommands){
        return null;
    }
}
