package org.example.view.enums;

import com.sun.net.httpserver.Authenticator;

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
    VALID_REGISTRATION_INPUT("valid"),
    INVALID_COORDINATES("Invalid coordinates."),
    INVALID_TYPE_OF_TILE("The type of tile is invalid."),
    TILE_NOT_EMPTY("The tile have a building."),
    INVALID_DIRECTION("The entered direction is invalid"),
    INAPPROPRIATE_TYPE_OF_TILE("The type of tile not appropriate for this building or tree"),
    SUCCESS("The command was executed successfully"),
    INVALID_TYPE_OF_TREE("The type of tree is invalid."),
    INVALID_TYPE_OF_BUILDING("The type of building is invalid."),
    INVALID_COUNT("The entered count for unit is invalid please enter a number greater than 0"),
    INVALID_TYPE_OF_UNIT("The type of unit is invalid.");


    private String output;

    Outputs(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return this.output;
    }
}
