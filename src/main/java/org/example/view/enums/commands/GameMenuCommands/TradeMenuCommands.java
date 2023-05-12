package org.example.view.enums.commands.GameMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands {
    TRADE("trade(?: -t (?<type>\\S+)()| -a (?<amount>\\d+)()| -p (?<price>\\d+)()|" +
            " -m (?<message>([^\\\"\\s]+|(\\\"[^\\\"]+\\\")))()){4}\\2\\4\\6"),
    TRADE_ACCEPT("trade accept(?: -i (?<id>\\d+)()| -m (?<message>([^\\\"\\s]+|(\\\"[^\\\"]+\\\")))()){2}\\2"),
    TRADE_LIST("^trade list$"),
    TRADE_HISTORY("^trade history$");
    private final String regex;

    TradeMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, TradeMenuCommands tradeMenuCommands){
        Pattern pattern = Pattern.compile(tradeMenuCommands.regex);
        if (pattern.matcher(input).matches())
            return pattern.matcher(input);
        return null;
    }
}
