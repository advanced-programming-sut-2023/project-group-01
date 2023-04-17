package org.example.view.enums.commands.GameMenuCommands;

import java.util.regex.Matcher;

public enum BuildingMenuCommands {
    Temp("das");
    private final String regex;

    BuildingMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, BuildingMenuCommands buildingMenuCommands){
        return null;
    }
}
