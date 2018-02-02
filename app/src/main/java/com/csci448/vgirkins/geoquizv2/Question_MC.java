package com.csci448.vgirkins.geoquizv2;

/**
 * Created by Tori on 2/2/2018.
 */

public class Question_MC extends Question {
    private int correctAnswerIndex;

    public Question_MC(int textResId, int correctAnswerIndex) {
        super(textResId);
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public int getCorrectAnswerIndex() {return correctAnswerIndex; }
}
