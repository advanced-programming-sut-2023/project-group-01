package org.example.view.enums.commands.GameMenuCommands;

import org.example.view.enums.commands.RegisterMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MilitaryMenuCommands {

    SELECT_UNIT("select unit (?=.*-x (?<x>\\d+))(?=.*-y (?<y>\\d+))"),
    MOVE_UNIT_TO("move unit to(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))"),
    PATROL_UNIT("patrol unit(?=.*-x1 (?<x1>\\d+))(?=.*-x2 (?<x2>\\d+))(?=.*-y1 (?<y1>\\d+))(?=.*-y2 (?<y2>\\d+))"),
    CANCEL_PATROL_UNIT("cancel patrol unit"),
    SET_STATE("set (?=.*-x (?<x>\\d+))(?=.*-y (?<y>\\d+))(?=.*-s (?<state>\\S*))"),
    ATTACK("(attack -e (?=.* (?<x>\\d+))(?=.* (?<y>\\d+)))|(attack (?=.*-x (?<x>\\d+))(?=.*-y (?<y>\\d+)))"),
    POUR_OIL("pour oil -d (?<direction>\\(left|right|up|down))"),
    DIG_TUNNEL("dig tunnel (?=.*-x (?<x>\\d+))(?=.*-y (?<y>\\d+)))"),
    BUILD_Q("build -q (?<equipment name>\\S+)"),
    DISBAND_UNIT("disband unit"),
    ;
    private final String regex;

    MilitaryMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, MilitaryMenuCommands commands){
        Pattern pattern = Pattern.compile(commands.regex);
        return pattern.matcher(input);
    }
}