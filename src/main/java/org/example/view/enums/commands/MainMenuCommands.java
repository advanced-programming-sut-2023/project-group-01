package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {

    USER_LOGOUT("^user logout$"),
    ENTER_PROFILE_MENU("^enter profile menu$");
    private final String regex;

    MainMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, MainMenuCommands mainMenuCommands){
        Pattern pattern = Pattern.compile(mainMenuCommands.regex);
        return pattern.matcher(input);
    }
}
