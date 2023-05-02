package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    LOGIN_REGEX("user login (?=.*\\-u (?<username>(\\\"[^\\\"]+\\\"|[^\\s\\\"]+(?=(?:[^'\\\"]*(?:'|\\\")[^'\\\"]*(?:'|\\\"))*[^'\\\"]*$))))?" +
            "(?=.*\\-p (?<password>(\\\"[^\\\"]+\\\"|[^\\s\\\"]+(?=(?:[^'\\\"]*(?:'|\\\")[^'\\\"]*(?:'|\\\"))*[^'\\\"]*$))))?"+
            "((?<stayLoggedIn>--stay-logged-in))?"),
    FORGET_PASSWORD("forgot my password -u (?<username>.+)"),
    ENTER_REGISTER_MENU("^enter register menu$");
    private final String regex;

    LoginMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, LoginMenuCommands loginMenuCommands){
        Pattern pattern = Pattern.compile(loginMenuCommands.regex);
        return pattern.matcher(input);
    }
}


