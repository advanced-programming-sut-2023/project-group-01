package org.example.model;

import org.example.view.enums.SecurityQuestion;
import java.util.Comparator;

public class User {

    private String username;
    private String passwordHash;
    private String nickname;
    private String email;
    private String slogan;
    private String securityQuestion;
    private String securityAnswer;
    private int highScore;
    private int rank;

    private boolean inGame = false;
    private byte[] salt;


    public User(String username, String passwordHash, String nickname, String email, String slogan,
                String securityQuestion, String securityAnswer, byte[] salt) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.salt = salt;
        this.highScore=0;
        this.rank=0;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getSlogan() {
        return slogan;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public byte[] getSalt() {
        return salt;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getRank() {
        return rank;
    }

    public boolean getInGame() {
        return inGame;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setInGame(Boolean inGame) {
        this.inGame = inGame;
    }

    public static class Sort implements Comparator<User> {
        public int compare(User a, User b) {
            return b.getHighScore()-a.getHighScore();
        }
    }


}

