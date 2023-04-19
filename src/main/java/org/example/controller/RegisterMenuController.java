package org.example.controller;

import org.example.model.Data;
import org.example.view.enums.Outputs;
import org.example.view.enums.SecurityQuestion;
import org.example.view.enums.commands.RegisterMenuCommands;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Matcher;

public class RegisterMenuController {

    public Outputs registerUser(String username , String password , String email , String nickname ,
                                String passwordConfirm , String slogan , String sloganSwitch) throws NoSuchAlgorithmException {

        boolean randomPassword=false,randomSlogan=false;
        Outputs outputs;

        if (!((outputs =registerValidationCheck(username,password,email,nickname,passwordConfirm,slogan,sloganSwitch)).
                equals(Outputs.VALID_REGISTRATION_INPUT)))
            return outputs;

        if (password.equals("random"))
            randomPassword=true;

        if (randomPassword){
            password = createRandomPassword();
        }

        System.out.println(password);

        byte[] salt = createNewSalt();
        String passwordHash = getPasswordHash(password,salt);
        System.out.println(passwordHash);

        return outputs;

    }

    public Outputs registerValidationCheck(String username , String password , String email , String nickname ,
                                           String passwordConfirm , String slogan , String sloganSwitch){

        boolean randomPassword=false;

        if (username==null||password==null||email==null||nickname==null)
            return Outputs.NOT_ENOUGH_DATA;

        if (password.equals("random"))
            randomPassword=true;

        Matcher validUsernameMatcher = RegisterMenuCommands.getMatcher(username,RegisterMenuCommands.VALID_USERNAME);
        if (!validUsernameMatcher.find())
            return Outputs.INVALID_USERNAME;

        if (Data.findUserWithUsername(username)!=null)
            return Outputs.USER_EXISTS;

        if (!randomPassword){
            Outputs passwordStatus=checkPasswordIsSecure(password);
            if (!passwordStatus.equals(Outputs.SECURE_PASSWORD))
                return passwordStatus;
        }


        if (!randomPassword)
            if (!password.equals(passwordConfirm))
                return Outputs.WRONG_PASSWORD_CONFIRM;

        if (Data.findUserWithEmail(email)!=null)
            return Outputs.EMAIL_EXISTS;

        if ((sloganSwitch!=null)&&(slogan==null))
            return Outputs.NOT_ENOUGH_DATA;

        Matcher validEmailMatcher = RegisterMenuCommands.getMatcher(email,RegisterMenuCommands.VALID_EMAIL);
        if (!validEmailMatcher.find())
            return Outputs.INVALID_EMAIL;

        return Outputs.VALID_REGISTRATION_INPUT;
    }

    private Outputs checkPasswordIsSecure(String password){
        Matcher matcher = RegisterMenuCommands.getMatcher(password,RegisterMenuCommands.SECURE_PASSWORD);
        if (!matcher.find())
            return Outputs.SHORT_PASSWORD;
        if (matcher.group("number")==null)
            return Outputs.PASSWORD_WITHOUT_NUMBER;
        if (matcher.group("smallLetter")==null)
            return Outputs.PASSWORD_WITHOUT_SMALL_LETTER;
        if (matcher.group("capitalLetter")==null)
            return Outputs.PASSWORD_WITHOUT_CAPITAL_LETTER;
        if (matcher.group("specialCharacter")==null)
            return Outputs.PASSWORD_WITHOUT_SPECIAL_CHARACTER;
        return Outputs.SECURE_PASSWORD;
    }

    private String createRandomPassword(){
        CharacterRule smallLetter = new CharacterRule(EnglishCharacterData.LowerCase);
        CharacterRule capitalletter = new CharacterRule(EnglishCharacterData.UpperCase);
        CharacterRule digit = new CharacterRule(EnglishCharacterData.Digit);
        CharacterRule specialCharacter = new CharacterRule(EnglishCharacterData.Special);
        PasswordGenerator passGen = new PasswordGenerator();
        Random random = new Random();
        String password = passGen.generatePassword(random.nextInt(10)+6, specialCharacter, smallLetter, capitalletter, digit);
        return password.replaceAll("\"","#");
    }
    private byte[] createNewSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
    private String getPasswordHash(String password, byte[] salt) {

        String generatedPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(salt);
            byte[] bytes = messageDigest.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

}
