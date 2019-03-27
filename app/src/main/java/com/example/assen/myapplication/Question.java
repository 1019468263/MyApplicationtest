package com.example.assen.myapplication;

public class Question {
    private int mTextID;//题目ID
    private boolean mAnswer;//答案


    public int getTextID() {
        return mTextID;
    }

    public void setTextID(int textID) {
        mTextID = textID;
    }

    public boolean isAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }

    public Question(int textID, boolean answer) {
        mTextID = textID;
        mAnswer = answer;
    }

}
