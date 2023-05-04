package org.example.view.enums.commands.GameMenuCommands;

import java.util.regex.Matcher;

public enum GameMenuCommands {

    SHOW_MAP(""),
    GO_TO_EMPIRE_MENU(""),
    GET_TURN_NUMBER(""),
    GET_PLAYER(""),
    NEXT_TURN("");
    private final String regex;

    GameMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, GameMenuCommands gameMenuCommands){
        return null;
    }
}
