package com.example.brandonwin7.witcherquiz;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class QuizFragment extends Fragment {

    // gui components
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;
    private ImageView mImage;
    private TextView mQuestionTextView;

    private boolean mIsCheater;
    private TrueFalse[] mAnswerKey;

    private int mCurrentIndex = 0;

    public QuizFragment() {
        super();

        mCurrentIndex = 0;
        mIsCheater = false;
        mAnswerKey = new TrueFalse[] {
                new TrueFalse(R.string.q_dandelion, true, R.drawable.dandelion),
                new TrueFalse(R.string.q_vergen, false, R.drawable.vergen),
                new TrueFalse(R.string.q_nilfgaard, true, R.drawable.nilfgaard),
                new TrueFalse(R.string.q_henselt, true, R.drawable.henselt),
                new TrueFalse(R.string.q_love, false, R.drawable.triss_img)
        };
    }

    private void updateQuestion(boolean increment) {
        if (increment) {
            mCurrentIndex++;
        } else {
            mCurrentIndex--;
        }

        if (mCurrentIndex < 0) {
            mCurrentIndex += mAnswerKey.length;
        }

        mCurrentIndex = mCurrentIndex % mAnswerKey.length;

        Log.d("FRAGMENT", "index: " + mCurrentIndex);

        int question = mAnswerKey[mCurrentIndex].getQuestion();
        mImage.setImageResource(mAnswerKey[mCurrentIndex].getImageID());
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(Context parent, boolean userPressedTrue) {
        boolean answerIsTrue = mAnswerKey[mCurrentIndex].isTrueQuestion();

        int messageResId = 0;

        if (mIsCheater) {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.judgment_toast;
            } else {
                messageResId = R.string.incorrect_judgement_toast;
            }
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(parent, messageResId, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_quiz, parent, false);
        final Context pContext = parent.getContext();


        mQuestionTextView = (TextView) v.findViewById(R.id.question_text_view);

        mImage = (ImageView)v.findViewById(R.id.question_image);
        mImage.setImageResource(mAnswerKey[mCurrentIndex].getImageID());
        mImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);



        mTrueButton = (Button)v.findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(pContext, true);
                updateQuestion(true);
            }
        });

        mFalseButton = (Button)v.findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(pContext, false);
                updateQuestion(true);
            }
        });

        mNextButton = (Button)v.findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsCheater = false;
                updateQuestion(true);
            }
        });

        mPrevButton = (Button)v.findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsCheater = false;
                updateQuestion(false);
            }
        });

        mCheatButton = (Button)v.findViewById(R.id.cheat_button);


        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(QuizActivity.KEY_INDEX, 0);
        }

        updateQuestion(true);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(QuizActivity.KEY_INDEX, mCurrentIndex);
    }



}
