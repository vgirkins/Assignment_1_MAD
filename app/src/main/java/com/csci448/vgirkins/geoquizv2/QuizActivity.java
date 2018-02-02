/*
https://stackoverflow.com/questions/2506876/how-to-change-position-of-toast-in-android#2507069
https://www.android-examples.com/change-toast-message-background-color-in-android/
https://stackoverflow.com/questions/31162236/setbackgroundresource-in-android-studio-expects-drawable-not-int#31162324
https://stackoverflow.com/questions/3275333/how-to-use-a-xml-shape-drawable#4437303
 */
package com.csci448.vgirkins.geoquizv2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG="QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mOption1Button;
    private Button mOption2Button;
    private Button mOption3Button;
    private Button mOption4Button;
    private Button mSubmitButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private EditText mEditText;

    private Question[] mQuestions = new Question[] {
            new Question_TF(R.string.question_1, true),
            new Question_MC(R.string.question_2, 4, new int[]{R.string.question_2_o1, R.string.question_2_o2, R.string.question_2_o3, R.string.question_2_o4}),
            new Question_FIB(R.string.question_3, "Europe"),
            new Question_MC(R.string.question_4, 2, new int[]{R.string.question_4_o1, R.string.question_4_o2, R.string.question_4_o3, R.string.question_4_o4}),
            new Question_TF(R.string.question_5, false),
            new Question_FIB(R.string.question_6, "Ceylon")
    };

    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate(Bundle) called");
        super.onCreate(savedInstanceState);
        // Set the correct content view specific to this question
        setLayout();
        // Restore the index of the current question if it exists
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        // Initialize components of the view
        initializeView();

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    private void updateQuestion() {
        setLayout();
        initializeView();
        int question = mQuestions[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        // Initialize components of the view specific to question type
    }

    private void checkTFAnswer(boolean userPressedTrue) {
        Question_TF question = (Question_TF)mQuestions[mCurrentIndex];
        boolean isAnswerTrue = question.getAnswerTrue();
        int messageResId;

        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        }
        else {
            messageResId = (userPressedTrue == isAnswerTrue ? R.string.right_answer_toast : R.string.wrong_answer_toast);
        }

        Toast toast = Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT );
        toast.setGravity(Gravity.TOP, 0, 0);
        View toastView = toast.getView();
        toastView.setBackgroundResource(R.drawable.background_toast);
        toast.show();
    }

    private void checkMCAnswer(int userPressedButton) {
        Question_MC question = (Question_MC)mQuestions[mCurrentIndex];
        int correctIndex = question.getCorrectAnswerIndex();
        int messageResId = (userPressedButton == correctIndex? R.string.right_answer_toast : R.string.wrong_answer_toast);

        Toast toast = Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT );
        toast.setGravity(Gravity.TOP, 0, 0);
        View toastView = toast.getView();
        toastView.setBackgroundResource(R.drawable.background_toast);
        toast.show();
    }

    private void checkFIBAnswer(String userSubmitted) {
        Question_FIB question = (Question_FIB)mQuestions[mCurrentIndex];
        String correctAnswer = question.getAnswer().toLowerCase();
        userSubmitted = userSubmitted.toLowerCase();
        int messageResId = (userSubmitted.equals(correctAnswer) ? R.string.right_answer_toast : R.string.wrong_answer_toast);

        Toast toast = Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT );
        toast.setGravity(Gravity.TOP, 0, 0);
        View toastView = toast.getView();
        toastView.setBackgroundResource(R.drawable.background_toast);
        toast.show();
    }

    private void setLayout() {
        // A method to decide based on the current question which view to use.
        // Called in onCreate() and updateQuestion().

        if (mQuestions[mCurrentIndex].getClass() == Question_TF.class) {
            setContentView(R.layout.activity_quiz);
        }
        else if (mQuestions[mCurrentIndex].getClass() == Question_MC.class) {
            setContentView(R.layout.activity_quiz_mc);
        }
        else if (mQuestions[mCurrentIndex].getClass() == Question_FIB.class) {
            setContentView(R.layout.activity_quiz_fib);
        }
        else {
            // This should never be reached; included for safety.
            Log.d(TAG, "Invalid question type; unknown cause of error");
        }
    }

    private void initializeView() {
        // A method to initialize components specific to each view.
        // Called in onCreate() and updateQuestion().

        // Set up parts of the view that are consistent across layouts.

        mQuestionTextView = findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                updateQuestion();
            }
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        // Set up parts of the view specific to different layouts

        // Code specific to true/false questions
        if (mQuestions[mCurrentIndex].getClass() == Question_TF.class) {
            mTrueButton = findViewById(R.id.true_button);
            mTrueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkTFAnswer(true);

                }
            });

            mFalseButton = findViewById(R.id.false_button);
            mFalseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkTFAnswer(false);
                }
            });
        }

        // Code specific to multiple choice questions
        if (mQuestions[mCurrentIndex].getClass() == Question_MC.class) {
            // Retrieve the current question so we can access the choices
            Question_MC question = (Question_MC) mQuestions[mCurrentIndex];

            mOption1Button = findViewById(R.id.option_1_button);
            mOption1Button.setText(question.getAllAnswerIds()[0]);
            mOption1Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkMCAnswer(1);
                }
            });

            mOption2Button = findViewById(R.id.option_2_button);
            mOption2Button.setText(question.getAllAnswerIds()[1]);
            mOption2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkMCAnswer(2);
                }
            });

            mOption3Button = findViewById(R.id.option_3_button);
            mOption3Button.setText(question.getAllAnswerIds()[2]);
            mOption3Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkMCAnswer(3);
                }
            });

            mOption4Button = findViewById(R.id.option_4_button);
            mOption4Button.setText(question.getAllAnswerIds()[3]);
            mOption4Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkMCAnswer(4);
                }
            });
        }

        // Code specific to FIB questions
        if (mQuestions[mCurrentIndex].getClass() == Question_FIB.class) {
            mEditText = findViewById(R.id.answer_edit_text);
            mSubmitButton = findViewById(R.id.submit_button);
            mSubmitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkFIBAnswer(mEditText.getText().toString());
                }
            });
        }
    }
}
