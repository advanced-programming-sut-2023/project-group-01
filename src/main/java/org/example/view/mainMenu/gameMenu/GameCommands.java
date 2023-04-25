package org.example.view.mainMenu.gameMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameCommands {
    //Military regexes :
    FIRST_SELECT_UNIT("select unit select unit ((((-x( (?<x>.*))?)) (-y( (?<y>.*))?))|(((-y( (?<y1>.*))?)) (-x( (?<x1>.*))?)))"),
    MOVE_UNIT(""),
    PATROL_UNIT("patrol unit -x1 (?<x1>[\\d]+) -y1 (?<y1>[\\d]+) -x2 (?<x2>[\\d]+) -y2 (?<y2>[\\d]+)"),
    SET_STATE("set -x (?<x>[\\d]+) -y (?<y>[\\d]+) -s (?<state>[\\S]+)"),
    ATTACK("attack ((-e (?<x>[\\d]+) (?<y>[\\d]+))|(-x (?<x>[\\d]+) -y (?<y>[\\d]+)))"),
    Pour_oil("pour oil -d (?<direction>[\\d]+)"),
    DIG_TUNNEL("dig tunnel -x (?<x>[\\d]+) -y (?<y>[\\d]+)"),
    BUILD_Q("build -q (?<equipmentName>[\\S]+)"),
    DISBAND_UNIT("disband unit"),
    //BUILDINGS :
    SELECT_BUILDING("select building ((((-x( (?<x>.*))?)) (-y( (?<y>.*))?))|(((-y( (?<y1>.*))?)) (-x( (?<x1>.*))?)))"),
    DROP_BUILDING("dropbuilding -x x -y y -type type"),
    CREATE_UNIT("createunit ((((-t( (?<type>.*))?)) (-c( (?<count>.*))?))|(((-c( (?<count1>.*))?)) (-t( (?<type1>.*))?)))"),
    REPAIR("repair"),

    ;

    private GameCommands(String regex) {
        this.regex = regex;
    }

    private String regex;

    public static Matcher getMatcher(String input, GameCommands command) {
        Pattern pattern = Pattern.compile(command.regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
