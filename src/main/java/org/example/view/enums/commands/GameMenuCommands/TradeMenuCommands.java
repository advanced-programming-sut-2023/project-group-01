package org.example.view.enums.commands.GameMenuCommands;

import java.util.regex.Matcher;

public enum TradeMenuCommands {
    TRADE("trade(?: -t (?<type>\\S+)()| -a (?<amount>\\d+)()| -p (?<price>\\d+)()| -m (?<message>\\S+)()){4}\\2\\4\\6\\8"),
    TRADE_ACCEPT("^trade list$"),
    TRADE_LIST("trade accept(?: -i (?<id>\\d+)()| -m (?<message>\\S+)()){2}\\2\\4"),
    TRADE_HISTORY("^trade history$");
    private final String regex;

    TradeMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, TradeMenuCommands tradeMenuCommands){
        return null;
    }
}
