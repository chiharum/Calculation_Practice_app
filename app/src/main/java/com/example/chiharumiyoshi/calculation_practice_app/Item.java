package com.example.chiharumiyoshi.calculation_practice_app;

public class Item {

    public boolean isRight = false;
    String questionNumber;
    String answer;
    String correctAnswer;
    String question;

    public Item(int questionNumber, int answer, int correctAnswer, String question) {
        this.questionNumber = questionNumber + "問目";
        this.question = question;
        this.answer = String.valueOf(answer);
        this.correctAnswer = String.valueOf(correctAnswer);

        if (answer == correctAnswer) {
            isRight = true;
        } else {
            isRight = false;
        }

//        isRight = (answer == correctAnswer);
    }
}
