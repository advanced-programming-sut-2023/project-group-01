package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    CHANGE_USERNAME("^profile change -u (?<username>.+)"),
    CHANGE_NICKNAME("^profile change -n (?<nickname>.+)"),
    CHANGE_PASSWORD("^profile change password " +
            "(?=.*\\-o (?<oldPassword>(\\\"[^\\\"]+\\\"|[^\\s\\\"]+(?=(?:[^'\\\"]*(?:'|\\\")[^'\\\"]*(?:'|\\\"))*[^'\\\"]*$))))?" +
            "(?=.*\\-n (?<newPassword>(\\\"[^\\\"]+\\\"|[^\\s\\\"]+(?=(?:[^'\\\"]*(?:'|\\\")[^'\\\"]*(?:'|\\\"))*[^'\\\"]*$))))?"),
    CHANGE_EMAIL("^profile change -e (?<email>.+)"),
    CHANGE_SLOGAN("^profile change -s (?<slogan>.+)"),
    REMOVE_SLOGAN("^profile remove slogan$"),
    DISPLAY_HIGHSCORE("^profile display highscore$"),
    DISPLAY_RANK("^profile display rank$"),
    DISPLAY_SLOGAN("^profile display slogan$"),
    FULL_DISPLAY("^profile display$"),
    BACK_TO_MAIN_MENU("^back to main menu$");
    private final String regex;

    ProfileMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, ProfileMenuCommands profileMenuCommands){
        Pattern pattern = Pattern.compile(profileMenuCommands.regex);
        return pattern.matcher(input);
    }
}
