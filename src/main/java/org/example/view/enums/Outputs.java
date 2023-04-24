package org.example.view.enums;

public enum Outputs {

    NOT_ENOUGH_DATA("Your entered data is not enough for this command."),
    INVALID_USERNAME("Your entered username is invalid."),
    USER_EXISTS("There is a user existing with your entered username."),
    SHORT_PASSWORD("Your entered password is short."),
    PASSWORD_WITHOUT_NUMBER("Your entered password must include at least one number"),
    PASSWORD_WITHOUT_SMALL_LETTER("Your entered password must include at least one small letter."),
    PASSWORD_WITHOUT_CAPITAL_LETTER("Your entered password must include at least one capital letter."),
    PASSWORD_WITHOUT_SPECIAL_CHARACTER("Your entered password must include at least one special character."),
    SECURE_PASSWORD("Secure"),
    WRONG_PASSWORD_CONFIRM("Your password confirmation does not match with password."),
    EMAIL_EXISTS("There is a user existing with your entered email."),
    INVALID_EMAIL("Your entered email is invalid."),
    VALID_REGISTRATION_INPUT("valid"),
    INVALID_REGISTRATION_INPUT("Invalid input for registration."),
    RANDOM_PASSWORD_CONFIRMATION("Your random password is : "),
    INVALID_QUESTION_NUMBER("Your entered question number is invalid."),
    WRONG_ANSWER_CONFIRM("Your entered answer doesn't match with answer confirm."),
    SUCCESS("success"),
    RANDOM_SLOGAN("Your random slogan is : "),
    VALID_LOGIN_INPUT("valid"),
    INVALID_LOGIN_INPUT("Invalid input for login."),
    NOT_EXISTING_USERNAME("Your entered username doesn't exist."),
    WRONG_PASSWORD("Your entered password is wrong."),
    WRONG_ANSWER("Your security answer is wrong."),
    INVALID_FORGET_PASSWORD_INPUT("invalid input for password forget menu."),
    PASSWORD_CHANGE_SUCCESSFUL("Your password changed successfully.");
    private String output;

    Outputs(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return this.output;
    }
}
