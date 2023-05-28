package org.example.controller.mainMenuController;

import org.example.controller.PasswordHash;
import org.example.model.User;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.RegisterMenuCommands;

import java.util.regex.Matcher;

public class ProfileMenuController {

    private User currentUser;

    public ProfileMenuController(User currentUser) {
        this.currentUser = currentUser;
    }

    public Outputs changeUsername(String username) {
        Matcher validUsernameMatcher = RegisterMenuCommands.getMatcher(username, RegisterMenuCommands.VALID_USERNAME);
        if (!validUsernameMatcher.find()) return Outputs.INVALID_USERNAME;
        currentUser.setUsername(username);
        return Outputs.USERNAME_CHANGE_SUCCESSFUL;
    }

    public Outputs changeEmail(String email) {
        Matcher validEmailMatcher = RegisterMenuCommands.getMatcher(email, RegisterMenuCommands.VALID_EMAIL);
        if (!validEmailMatcher.find()) return Outputs.INVALID_EMAIL;
        currentUser.setEmail(email);
        return Outputs.EMAIL_CHANGE_SUCCESSFUL;
    }

    public Outputs changePassword(String oldPassword, String newPassword) {
        if (oldPassword.equals(newPassword)) return Outputs.SAME_NEW_PASSWORD;
        if (!(PasswordHash.getPasswordHash(oldPassword, currentUser.getSalt()).equals(currentUser.getPasswordHash())))
            return Outputs.WRONG_OLD_PASSWORD;

//        if (!RegisterMenuController.checkPasswordIsSecure(newPassword).equals(Outputs.SECURE_PASSWORD))
//            return RegisterMenuController.checkPasswordIsSecure(newPassword);

        currentUser.setPasswordHash(PasswordHash.getPasswordHash(newPassword, currentUser.getSalt()));
        return Outputs.PASSWORD_CHANGE_SUCCESSFUL;
    }

}
