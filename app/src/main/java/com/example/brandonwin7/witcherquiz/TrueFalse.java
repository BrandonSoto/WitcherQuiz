package com.example.brandonwin7.witcherquiz;

public class TrueFalse {
    private int mQuestion; // holds resource id questin string
    private boolean mTrueQuestion;
    private int mImageID; // holds resrouce id of question image

    public TrueFalse(int question, boolean trueQuestion, int imgID) {
        mQuestion = question;
        mTrueQuestion = trueQuestion;
        mImageID = imgID;
    }

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public boolean isTrueQuestion() {
        return mTrueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        mTrueQuestion = trueQuestion;
    }

    public int getImageID() {
        return mImageID;
    }

    public void setImageID(int mImageID) {
        this.mImageID = mImageID;
    }
}
