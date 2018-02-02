package com.csci448.vgirkins.geoquizv2;

/**
 * Created by Tori on 1/19/2018.
 */

public class Question {
    private int mTextResId;

    public Question(int textResId) {
        this.mTextResId = textResId;
    }

    public int getTextResId() {
        return mTextResId;
    }
}