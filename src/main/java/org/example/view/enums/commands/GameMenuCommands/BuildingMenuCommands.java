package org.example.view.enums.commands.GameMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum BuildingMenuCommands {
    DROP_BUILDING("drop building(?: -x (?<x>\\S*)()| -y (?<y>\\S*)()| -t (?<type>\\S*)()){3}\\2\\4\\6"),
    SELECT_BUILDING("select building(?: -x (?<x>\\S*)()| -y (?<y>\\S*)()){2}\\2\\4"),
    CREATE_UNIT("create unit(?: -t (?<t>\\S*)()| -c (?<count>\\S*)()){2}\\2\\4"),
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
