package org.example.controller;

import org.example.view.enums.Outputs;
import org.example.view.enums.SecurityQuestion;

import java.util.regex.Matcher;

public class RegisterMenuController {

    public RegisterMenuController(){

    }

    public Outputs checkRegister(String username, String password, String nickname, String email,
                                 String slogan, SecurityQuestion securityQuestion, String securityAnswer){
        return null;
    }

    public String getPasswordHash(byte[] salt, String password){
        return null;
    }

    public byte[] createNewSalt(){
        return null;
    }

    public Outputs checkPasswordIsSecure(String password){
        return null;
    }

    public String createRandomPassword(){
        return null;
    }
}
