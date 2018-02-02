package com.csci448.vgirkins.geoquizv2;

/**
 * Created by Tori on 2/2/2018.
 */

public class Question_MC extends Question {
    private int correctAnswerIndex;
    private int[] allAnswerIds;

    public Question_MC(int textResId, int correctAnswerIndex, int[] answerIds) {
        super(textResId);
        this.correctAnswerIndex = correctAnswerIndex;
        this.allAnswerIds = answerIds;
    }

    public int getCorrectAnswerIndex() {return correctAnswerIndex; }
    public int[] getAllAnswerIds() {return allAnswerIds; }
}
