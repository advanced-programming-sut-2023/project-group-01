package org.example.view.enums;

public enum BackgroundColor {
    ANSI_YELLOW_BACKGROUND("\u001B[43m"),
    ANSI_BLUE_BACKGROUND("\u001B[44m"),
    ANSI_BLACK_BACKGROUND("\u001B[40m"),
    ANSI_PURPLE_BACKGROUND("\u001B[45m"),
    ANSI_CYAN_BACKGROUND("\u001B[46m"),
    ANSI_GREEN_BACKGROUND("\u001B[42m"),
    ANSI_WHITE_BACKGROUND("\u001B[47m");

    private final String color;

    private BackgroundColor(String color){
        this.color = color;
    }
    public static void changeColor(BackgroundColor backgroundColor){
        System.out.println(backgroundColor.color);
    }

}
