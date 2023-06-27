package org.example.model;

import java.util.ArrayList;

public class Chat {
     public ArrayList<Message> messages;
     public ArrayList<User> members;
     public Type type;
}

enum Type{
    PUBLIC,
    PRIVATE,
    ROOM
}
