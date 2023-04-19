package org.example.view.enums;

public enum Outputs {

    NOT_ENOUGH_DATA("Your entered data is not enough for registering new user."),
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
    VALID_REGISTRATION_INPUT("valid");
    private String output;

    Outputs(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return this.output;
    }
}
