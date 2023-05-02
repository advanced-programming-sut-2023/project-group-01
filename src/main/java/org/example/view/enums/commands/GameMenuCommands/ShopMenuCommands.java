package org.example.view.enums.commands.GameMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ShopMenuCommands {
    SHOW_PRICE_LIST("^show price list$"),
    BUY("buy (?=.*-i (?<name>\\S+))(?=.*-a (?<count>\\d+))"),
    SELL("sell (?=.*-i (?<name>\\S+))(?=.*-a (?<count>\\d+))");
    private final String regex;

    ShopMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, ShopMenuCommands shopMenuCommands){
        Pattern pattern = Pattern.compile(shopMenuCommands.regex);
        return pattern.matcher(input);
    }
}
