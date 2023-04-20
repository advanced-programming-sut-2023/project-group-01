package org.example.view.mainMenu.gameMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameCommands {
    //Military regexes :
    SELECT_UNIT("select unit -x (?<x>[\\d]+) -y (?<y>[\\d]+)"),
    MOVE_UNIT("move unit to -x (?<x>[\\d]+) -y (?<y>[\\d]+)"),
    PATROL_UNIT("patrol unit -x1 (?<x1>[\\d]+) -y1 (?<y1>[\\d]+) -x2 (?<x2>[\\d]+) -y2 (?<y2>[\\d]+)"),
    SET_STATE("set -x (?<x>[\\d]+) -y (?<y>[\\d]+) -s (?<state>[\\S]+)"),
    ATTACK("attack ((-e (?<x>[\\d]+) (?<y>[\\d]+))|(-x (?<x>[\\d]+) -y (?<y>[\\d]+)))"),
    Pour_oil("pour oil -d (?<direction>[\\d]+)"),
    DIG_TUNNEL("dig tunnel -x (?<x>[\\d]+) -y (?<y>[\\d]+)"),
    BUILD_Q("build -q (?<equipmentName>[\\S]+)"),
    DISBAND_UNIT("disband unit"),


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
