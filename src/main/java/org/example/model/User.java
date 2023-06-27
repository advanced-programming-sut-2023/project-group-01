package org.example.model;

import org.example.Main;

import java.net.URL;
import java.util.ArrayList;
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
    private ArrayList<Map> maps = new ArrayList<>();
    private boolean inGame = false;
    private byte[] salt;
    private URL avatarUrl;

    public User(String username, String passwordHash, String nickname, String email, String slogan, String securityQuestion, String securityAnswer, byte[] salt) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.nickname = nickname;
        this.email = email;
        if (!slogan.equals(""))
            this.slogan = slogan;
        else this.slogan = "slogan is empty";
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.salt = salt;
        this.highScore = 0;
        this.rank = Data.getUsers().size() + 1;
        this.avatarUrl = Main.class.getResource("/Images/DefaultAvatar.jpg");
    }

    public String getUsername() {
        return username;
    }

    public URL getAvatarUrl() {
        if (avatarUrl==null)
            avatarUrl = Main.class.getResource("/Images/DefaultAvatar.jpg");
        return avatarUrl;
    }

    public void setAvatarUrl(URL avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public ArrayList<Map> getMaps() {
        if(maps == null) maps = new ArrayList<>();
        return maps;
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
        if (!slogan.equals(""))
            this.slogan = slogan;
        else this.slogan = "slogan is empty";
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
            return b.getHighScore() - a.getHighScore();
        }
    }

    public void addMap(Map map){
        maps.add(map);
    }
}

