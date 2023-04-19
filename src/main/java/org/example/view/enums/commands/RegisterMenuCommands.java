package org.example.view.enums.commands;

import java.util.regex.Matcher;

public enum RegisterMenuCommands {

    REGISTER_REGEX("user create (?=.*\\-u (?<username>(\\\"[^\\\"]+\\\"|[^\\s\\\"]+(?=(?:[^'\\\"]*(?:'|\\\")[^'\\\"]*(?:'|\\\"))*[^'\\\"]*$))))?" +
            "(?=.*\\-p (?<password>(\\\"[^\\\"]+\\\"|[^\\s\\\"]+(?=(?:[^'\\\"]*(?:'|\\\")[^'\\\"]*(?:'|\\\"))*[^'\\\"]*$)))? " +
            "(?<passwordConfirm>(\\\"[^\\\"]+\\\"|[^\\s\\\"]+(?=(?:[^'\\\"]*(?:'|\\\")[^'\\\"]*(?:'|\\\"))*[^'\\\"]*$)))?)?" +
            "(?=.*\\-email (?<email>(\\\"[^\\\"]+\\\"|[^\\s\\\"]+(?=(?:[^'\\\"]*(?:'|\\\")[^'\\\"]*(?:'|\\\"))*[^'\\\"]*$))))?" +
            "(?=(?<sloganSwitch>.*\\-s) (?<slogan>(\\\"[^\\\"]+\\\"|[^\\s\\\"]+(?=(?:[^'\\\"]*(?:'|\\\")[^'\\\"]*(?:'|\\\"))*[^'\\\"]*$))?))?" +
            "(?=.*\\-n (?<nickname>(\\\"[^\\\"]+\\\"|[^\\s\\\"]+(?=(?:[^'\\\"]*(?:'|\\\")[^'\\\"]*(?:'|\\\"))*[^'\\\"]*$))))?"),
    SECURE_PASSWORD("^(?=(?<number>.*[0-9]))?(?=(?<smallLetter>.*[a-z]))?(?=(?<capitalLetter>.*[A-Z]))?(?=(?<specialCharacter>.*[^a-zA-Z0-9]))?.{6,}$"),
    VALID_USERNAME("^[a-zA-Z0-9\\_]+$"),
    VALID_EMAIL("^[a-zA-Z0-9\\.\\_]+@[a-zA-Z0-9\\.\\_]+\\.[a-zA-Z0-9\\.\\_]+$");
    private final String regex;

    RegisterMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, RegisterMenuCommands registerMenuCommands){
        return null;
    }
}
