package org.example.view.enums;

public enum SecurityQuestion {

    Temp("dasd");
    private final String question;

    SecurityQuestion(String question){
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}
