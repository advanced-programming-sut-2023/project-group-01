package org.example.view.enums.commands.GameMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MapMenuCommands {

    MOVE_MAP("move map(?=.*(?<up> up )(?<upCount>\\d+))?(?=.*(?<right> right )(?<rightCount>\\d+))?" +
            "(?=.*(?<left> left )(?<leftCount>\\d+))?(?=.*(?<down> down )(?<downCount>\\d+))?"),
    SHOW_DETAIL("show detail(?: -x (?<xOfMap>\\d+)()| -y (?<yOfMap>\\d+)()){2}\\2\\4");
    private final String regex;

    MapMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, MapMenuCommands mapMenuCommands){
        Pattern pattern = Pattern.compile(mapMenuCommands.regex);
        if (pattern.matcher(input).find())
            return pattern.matcher(input);
        return null;
    }
}
