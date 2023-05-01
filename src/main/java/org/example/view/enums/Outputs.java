package org.example.view.enums;

import com.sun.net.httpserver.Authenticator;

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
    INVALID_COORDINATES("Invalid coordinates."),
    INVALID_TYPE_OF_TILE("The type of tile is invalid."),
    TILE_NOT_EMPTY("The tile have a building."),
    INVALID_DIRECTION("The entered direction is invalid"),
    INAPPROPRIATE_TYPE_OF_TILE("The type of tile not appropriate for this building or tree"),
    SUCCESS("The command was executed successfully"),
    INVALID_TYPE_OF_TREE("The type of tree is invalid."),
    INVALID_TYPE_OF_BUILDING("The type of building is invalid."),
    INVALID_COUNT("The entered count for unit is invalid please enter a number greater than 0"),
    INVALID_TYPE_OF_UNIT("The type of unit is invalid."),
    EMPTY_X("Your entered X is empty"),
    EMPTY_Y("Your entered Y is empty"),
    INVALID_X("Your entered X is invalid"),
    INVALID_Y("Your entered Y is invalid"),
    OUT_OF_RANGE("Your entered position is out of range"),
    NOT_HAVING_TROOP("You don't have any troops in this position"),
    NOT_ENOUGH_POS_DATA("You don't enter enough data for position"),
    EMPTY_SELECTED_UNIT("You don't select any unit"),
    WRONG_PLACE("moving to this position is impossible"),
    VALID_X_Y("valid move"),
    INVALID_COMMAND("Invalid command"),
    VALID_SELECT_BUILDING("Building selected"),
    NOT_HAVING_BUILDING("You don't have any building in this position"),
    EMPTY_TYPE("Your entered Type is empty"),
    EMPTY_COUNT("Your entered Count is empty"),
//    INVALID_COUNT("Your entered Count is invalid"),
    EMPTY_SELECTED_BUILDING("Your don't select any building"),
    INVALID_MILITARY_TYPE("Invalid militaryUnit type"),
    NOT_ENOUGH_POPULATION("Not enough population"),
    NOT_ENOUGH_MONEY("Not Enough money"),
    NOT_ENOUGH_EQUIPMENT("Not enough equipment"),
    NOT_ENOUGH_STONE("Not enough stone"),
    SUCCESSFUL_REPAIR("Successful repair"),
    SUCCESSFUL_CREATE("Successful create"),
    INVALID_UNIT_STATE("Invalid unit state"),
    SUCCESSFUL_SET_STATE("Successful set unit state"),
    EMPTY_X1("Invalid X1"),
    EMPTY_X2("Invalid X2"),
    EMPTY_Y1("Invalid Y1"),
    EMPTY_Y2("Invalid Y2"),
    INVALID_X1("Invalid X1"),
    INVALID_X2("Invalid X2"),
    INVALID_Y1("Invalid Y1"),
    INVALID_Y2("Invalid Y2"),
    SUCCESSFUL_PATROL("Successful patrol"),
    SUCCESSFUL_CANCEL_PATROL("Successful cancel patrol"),
    INVALID_REGISTRATION_INPUT("Invalid input for registration."),
    RANDOM_PASSWORD_CONFIRMATION("Your random password is : "),
    INVALID_QUESTION_NUMBER("Your entered question number is invalid."),
    WRONG_ANSWER_CONFIRM("Your entered answer doesn't match with answer confirm."),
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
