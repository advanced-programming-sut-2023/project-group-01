package org.example.model;

import org.example.view.enums.SecurityQuestion;

public class User {
    private String username;

    private String hashPassword;

    private String nickName;

    private String slogan;

    private SecurityQuestion securityQuestion;

    private String securityAnswer;

    private byte[] salt;

    User(String username, String hashPassword, String nickName, String slogan,
         SecurityQuestion securityQuestion, String securityAnswer, byte[] salt) {
        this.username = username;
        this.hashPassword = hashPassword;
        this.nickName = nickName;
        this.slogan = slogan;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.salt = salt;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setSecurityQuestion(SecurityQuestion securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public String getUsername() {
        return username;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public String getNickName() {
        return nickName;
    }

    public String getSlogan() {
        return slogan;
    }

    public SecurityQuestion getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public byte[] getSalt() {
        return salt;
    }
}
