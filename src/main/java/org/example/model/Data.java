package org.example.model;

import java.util.ArrayList;

public class Data {

    private static ArrayList<User> users = new ArrayList<>();

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        Data.users = users;
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

}
