package org.example.view.enums.commands.GameMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum BuildingMenuCommands {
    DROP_BUILDING("dropbuilding (?=.*-x (?<x>\\S*))(?=.*-y (?<y>\\S*))(?=.*-type (?<type>\\S*))"),
    SELECT_BUILDING("select building (?=.*-x (?<x>\\S*))(?=.*-x (?<y>\\S*))"),
    CREATE_UNIT("createunit (?=.*-t (?<type>\\S*))(?=.*-c (?<count>\\S*))"),
    REPAIR("repair"),

    ;
    private final String regex;

    BuildingMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, BuildingMenuCommands command) {
        Pattern pattern = Pattern.compile(command.regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}
