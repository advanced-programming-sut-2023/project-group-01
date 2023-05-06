package org.example.view.enums.commands.GameMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CreateMapMenuCommands {
    SET_TEXTURE_FOR_A_TILE("set texture(?: -x (?<xOfMap>\\d+)()| -y (?<yOfMap>\\d+)()| -t (?<type>\\S+)()){3}\\2\\4\\6"),
    SET_TEXTURE_FOR_A_RECTANGLE("set texture(?: -x1 (?<x1OfMap>\\S*)()| -y1 (?<y1OfMap>\\S*)()| -y2 (?<y2OfMap>\\S*)()| -x2 (?<x2OfMap>\\S*)()| -t (?<type>\\S+)()){5}\\2\\4\\6\\8\\10"),
    CLEAR("clear(?: -x (?<xOfMap>\\d+)()| -y (?<yOfMap>\\d+)()){2}\\2\\4"),
    DROP_ROCK("drop rock(?: -x (?<xOfMap>\\d+)()| -y (?<yOfMap>\\d+)()| -d (?<direction>\\S+)()){3}\\2\\4\\6"),
    DROP_TREE("drop tree(?: -x (?<xOfMap>\\d+)()| -y (?<yOfMap>\\d+)()| -t (?<type>\\S+)()){3}\\2\\4\\6"),
    DROP_BUILDING("drop building(?: -x (?<xOfMap>\\d+)()| -y (?<yOfMap>\\d+)()| -t (?<type>\\S+)()){3}\\2\\4\\6"),
    DROP_UNIT("drop unit(?: -x (?<xOfMap>\\d+)()| -y (?<yOfMap>\\d+)()| -t (?<type>\\S+)()| -c (?<count>\\d+)()){4}\\2\\4\\6\\8");
    private final String regex;

    CreateMapMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, CreateMapMenuCommands createMapMenuCommands){
        Pattern pattern = Pattern.compile(createMapMenuCommands.regex);
        if (pattern.matcher(input).find())
            return pattern.matcher(input);
        return null;
    }

}
