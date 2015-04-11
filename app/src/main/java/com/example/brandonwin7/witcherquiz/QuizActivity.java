package com.example.brandonwin7.witcherquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

    private static final String TAG = QuizActivity.class.getSimpleName();
    private static final String KEY_INDEX = "index";

    // gui components
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private ImageView mImage;
    private TextView mQuestionTextView;

    private boolean mIsCheater;
    private TrueFalse[] mAnswerKey = new TrueFalse[] {
            new TrueFalse(R.string.q_dandelion, true, R.drawable.dandelion2),
            new TrueFalse(R.string.q_vergen, false, R.drawable.vergen3),
            new TrueFalse(R.string.q_nilfgaard, true, R.drawable.nilfgaard),
            new TrueFalse(R.string.q_henselt, true, R.drawable.henselt2),
            new TrueFalse(R.string.q_love, false, R.drawable.triss2)
    };

    int mCurrentIndex = 0;

    private void updateQuestion() {
        int question = mAnswerKey[mCurrentIndex].getQuestion();
        mImage.setImageResource(mAnswerKey[mCurrentIndex].getImageID());
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
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

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mIsCheater = false;

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);

        mImage = (ImageView)findViewById(R.id.question_image);
        mImage.setImageResource(mAnswerKey[mCurrentIndex].getImageID());
        mImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);



        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mAnswerKey.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mAnswerKey[mCurrentIndex].isTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(i, 0);
            }
        });

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

}
