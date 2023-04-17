package org.example.view.enums.commands.GameMenuCommands;

import java.util.regex.Matcher;

public enum ShopMenuCommands {
    Temp("das");
    private final String regex;

    ShopMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, ShopMenuCommands shopMenuCommands){
        return null;
    }
}
