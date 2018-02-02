package com.csci448.vgirkins.A1_vgirkins;

/**
 * Created by Tori on 2/2/2018.
 */

public class Question_FIB extends Question {
    private String answer;
    public Question_FIB(int textResId, String answer) {
        super(textResId);
        this.answer = answer;
    }

    public String getAnswer() {return answer; }
}
