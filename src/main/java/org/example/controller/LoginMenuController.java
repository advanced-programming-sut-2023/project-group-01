package org.example.controller;

import org.example.model.Data;
import org.example.model.User;
import org.example.view.enums.Outputs;

public class LoginMenuController{

    public Outputs login(String username, String password){
        if (username==null||password==null)
            return Outputs.NOT_ENOUGH_DATA;

        User user;
        if ((user = Data.findUserWithUsername(username))==null)
            return Outputs.NOT_EXISTING_USERNAME;

        if (!(user.getPasswordHash().equals(PasswordHash.getPasswordHash(password,user.getSalt()))))
            return Outputs.WRONG_PASSWORD;

        return Outputs.SUCCESS;
    }

    public Outputs forgetPassword(String securityAnswer, String username){
        return null;
    }

}
