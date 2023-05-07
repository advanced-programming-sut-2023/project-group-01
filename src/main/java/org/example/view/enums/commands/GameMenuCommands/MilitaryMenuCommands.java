package org.example.view.enums.commands.GameMenuCommands;

import org.example.view.enums.commands.RegisterMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MilitaryMenuCommands {

    SELECT_UNIT("select unit(?: -x (?<x>\\S*)()| -y (?<y>\\S*)()){2}\\2\\4"),
    MOVE_UNIT_TO("move unit to(?: -x (?<x>\\S*)()| -y (?<y>\\S*)()){2}\\2\\4"),
    PATROL_UNIT("patrol unit(?: -x1 (?<x1>\\S*)()| -y1 (?<y1>\\S*)()| -y2 (?<y2>\\S*)()| -x2 (?<x2>\\S*)()){4}\\2\\4\\6\\8"),
    CANCEL_PATROL_UNIT("cancel patrol unit"),
    SET_STATE("set(?: -x (?<x>\\S*)()| -y (?<y>\\S*)()| -s (?<state>\\S*)()){3}\\2\\4\\6"),
    ATTACK_E("attack -e(?: (?<x>\\S*)()| (?<y>\\S*)()){2}\\2\\4"),
    ATTACK("attack(?: -x (?<x>\\S*)()| -y (?<y>\\S*)()){2}\\2\\4"),
    POUR_OIL("pour oil -d (?<direction>\\S*)"),
    DIG_TUNNEL("dig tunnel(?: -x (?<x>\\S*)()| -y (?<y>\\S*)()){2}\\2\\4"),
    BUILD_Q("build -q (?<equipmentName>\\S*)"),
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