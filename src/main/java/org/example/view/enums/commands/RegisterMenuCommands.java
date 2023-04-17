package org.example.view.enums.commands;

import java.util.regex.Matcher;

public enum RegisterMenuCommands {

    Temp("das");
    private final String regex;

    RegisterMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, RegisterMenuCommands registerMenuCommands){
        return null;
    }
}
