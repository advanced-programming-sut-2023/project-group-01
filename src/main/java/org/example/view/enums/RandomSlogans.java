package org.example.view.enums;

import java.util.Random;

public enum RandomSlogans {

    SLOGAN_0("Gamers’ power.",0),
    SLOGAN_1("Take your game to the next level.",1),
    SLOGAN_2("Let’s find a way to win together.",2),
    SLOGAN_3("Be calm, be a gamer, be a game lover.",3),
    SLOGAN_4("Life is too short to play a bad game.",4);

    private final String slogan;
    private final int number;
    RandomSlogans(String slogan, int number) {
        this.slogan= slogan;
        this.number = number;
    }

    public static String getRandomSlogan(){
        Random random = new Random();
        return getSlogan(random.nextInt(5));
    }
    public static String getSlogan(int number){
        return RandomSlogans.values()[number].slogan;
    }

}
