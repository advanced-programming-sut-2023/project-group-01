package org.example.view.enums.commands;

import java.util.regex.Matcher;

public enum LoginMenuCommands {
    Temp("das");
    private final String regex;

    LoginMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, LoginMenuCommands loginMenuCommands){
        return null;
    }
}
