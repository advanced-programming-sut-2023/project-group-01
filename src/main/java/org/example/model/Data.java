package org.example.model;

import java.util.ArrayList;

public class Data {

    private static ArrayList<User> users = new ArrayList<>();
    private static User stayedLoggedIn;
    private static String defaultMap;

    public static void setDefaultMap(String defaultMap) {
        Data.defaultMap = defaultMap;
    }

    public static String getDefaultMap() {
        return defaultMap;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        Data.users = users;
    }

    public static User getStayedLoggedIn() {
        return stayedLoggedIn;
    }

    public static void setStayedLoggedIn(User stayedLoggedIn) {
        Data.stayedLoggedIn = stayedLoggedIn;
    }

    public static User findUserWithUsername(String username){
        for (User user : users)
            if (user.getUsername().equals(username))
                return user;
        return null;
    }

    public static User findUserWithEmail(String email){
        for (User user : users)
            if (user.getEmail().equalsIgnoreCase(email))
                return user;
        return null;
    }

    public static void addUser(User user){
        users.add(user);
    }

}
