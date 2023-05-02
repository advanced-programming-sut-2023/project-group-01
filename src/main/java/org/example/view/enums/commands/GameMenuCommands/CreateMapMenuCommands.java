package org.example.view.enums.commands.GameMenuCommands;

import java.util.regex.Matcher;

public enum CreateMapMenuCommands {
    SET_TEXTURE_FOR_A_TILE(""),
    SET_TEXTURE_FOR_A_RECTANGLE(""),
    CLEAR(""),
    DROP_ROCK(""),
    DROP_TREE(""),
    DROP_BUILDING(""),
    DROP_UNIT("");
    private final String regex;

    CreateMapMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, CreateMapMenuCommands createMapMenuCommands){
        return null;
    }

}
