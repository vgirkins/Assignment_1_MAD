package com.csci448.vgirkins.A1_vgirkins;

/**
 * Created by Tori on 2/2/2018.
 */

public class Question_TF extends Question {
    private boolean mAnswerTrue;

    public Question_TF(int textResId, boolean answerTrue) {
        super(textResId);
        this.mAnswerTrue = answerTrue;
    }
    public boolean getAnswerTrue(){ return mAnswerTrue; }
    public void setAnswerTrue(boolean answerTrue){
        this.mAnswerTrue = answerTrue;
    }
}
