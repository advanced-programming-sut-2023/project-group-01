package org.example.view.enums.commands.GameMenuCommands;

import java.util.regex.Matcher;

public enum TradeMenuCommands {
    TRADE(""),
    TRADE_ACCEPT(""),
    TRADE_LIST(""),
    TRADE_HISTORY("");
    private final String regex;

    TradeMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, TradeMenuCommands tradeMenuCommands){
        return null;
    }
}
