package org.example.view.enums;

public enum SecurityQuestion {

    QUESTION_1("What is my father’s name?", 1), QUESTION_2("What was my first pet’s name? ", 2), QUESTION_3("What is my mother’s last name?", 3);
    private final String question;
    private final int number;

    SecurityQuestion(String question, int number) {
        this.question = question;
        this.number = number;
    }

    public static String getQuestion(int number) {
        return SecurityQuestion.values()[number].question;
    }
}
