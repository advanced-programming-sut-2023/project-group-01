package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegisterMenuCommands {

    REGISTER_REGEX("user create (?=.*\\-u (?<username>(\\\"[^\\\"]+\\\"|[^\\s\\\"]+(?=(?:[^'\\\"]*(?:'|\\\")[^'\\\"]*(?:'|\\\"))*[^'\\\"]*$))))?" +
            "(?=.*\\-p (?<password>(\\\"[^\\\"]+\\\"|[^\\s\\\"]+(?=(?:[^'\\\"]*(?:'|\\\")[^'\\\"]*(?:'|\\\"))*[^'\\\"]*$)))? " +
            "(?<passwordConfirm>(\\\"[^\\\"]+\\\"|[^\\s\\\"\\-]+(?=(?:[^'\\\"]*(?:'|\\\")[^'\\\"]*(?:'|\\\"))*[^'\\\"]*$)))?)?" +
            "(?=.*\\-email (?<email>(\\\"[^\\\"]+\\\"|[^\\s\\\"]+(?=(?:[^'\\\"]*(?:'|\\\")[^'\\\"]*(?:'|\\\"))*[^'\\\"]*$))))?" +
            "(?=(?<sloganSwitch>.*\\-s) (?<slogan>(\\\"[^\\\"]+\\\"|[^\\s\\\"]+(?=(?:[^'\\\"]*(?:'|\\\")[^'\\\"]*(?:'|\\\"))*[^'\\\"]*$))?))?" +
            "(?=.*\\-n (?<nickname>(\\\"[^\\\"]+\\\"|[^\\s\\\"]+(?=(?:[^'\\\"]*(?:'|\\\")[^'\\\"]*(?:'|\\\"))*[^'\\\"]*$))))?"),
    ENTER_LOGIN_MENU("^enter login menu$"),
    PICK_QUESTION_REGEX("question pick (?=.*\\-q (?<number>[\\d]+))?" +
            "(?=.*\\-a (?<answer>(\\\"[^\\\"]+\\\"|[^\\s\\\"]+(?=(?:[^'\\\"]*(?:'|\\\")[^'\\\"]*(?:'|\\\"))*[^'\\\"]*$))))?" +
            "(?=.*\\-c (?<answerConfirm>(\\\"[^\\\"]+\\\"|[^\\s\\\"]+(?=(?:[^'\\\"]*(?:'|\\\")[^'\\\"]*(?:'|\\\"))*[^'\\\"]*$))))?"),
    SECURE_PASSWORD("^(?=(?<number>.*[0-9]))?(?=(?<smallLetter>.*[a-z]))?(?=(?<capitalLetter>.*[A-Z]))?(?=(?<specialCharacter>.*[^a-zA-Z0-9]))?.{6,}$"),
    VALID_USERNAME("^[a-zA-Z0-9\\_]+$"),
    VALID_EMAIL("^[a-zA-Z0-9\\.\\_]+@[a-zA-Z0-9\\.\\_]+\\.[a-zA-Z0-9\\.\\_]+$"),
    RANDOM_PASSWORD_CONFIRMATION_REGEX("^(?<passwordConfirm>[\\S]+)$"),
    EXIT("^exit$"),
    WRONG_RANDOM_PASS_CONFIRM("Password confirmation doesn't equal generated random password.Please re enter generated password:");
    private final String regex;

    RegisterMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, RegisterMenuCommands registerMenuCommands){

        Pattern pattern = Pattern.compile(registerMenuCommands.regex);
        return pattern.matcher(input);
    }
}
