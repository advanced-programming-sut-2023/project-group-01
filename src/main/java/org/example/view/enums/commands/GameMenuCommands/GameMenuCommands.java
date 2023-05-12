package org.example.view.enums.commands.GameMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {

    SHOW_MAP("show map(?: -x (?<xOfMap>\\d+)()| -y (?<yOfMap>\\d+)()){2}\\2\\4"),
    GO_TO_EMPIRE_MENU("^enter empire menu$"),
    GET_TURN_NUMBER("^show turn number$"),
    GET_PLAYER("^show player$"),
    TRADE_MENU("^enter trade menu$"),
    SHOP_MENU("^enter shop menu$"),
    BUILDING_MENU("^enter building menu$"),
    MILITARY_MENU("^enter military menu$"),
    NEXT_TURN("^next turn$");
    private final String regex;

    GameMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, GameMenuCommands gameMenuCommands){
        Pattern pattern = Pattern.compile(gameMenuCommands.regex);
        if (pattern.matcher(input).matches())
            return pattern.matcher(input);
        return null;
    }
}
