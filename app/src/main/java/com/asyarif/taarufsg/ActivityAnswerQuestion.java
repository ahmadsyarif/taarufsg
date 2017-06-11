package com.asyarif.taarufsg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class ActivityAnswerQuestion extends AppCompatActivity implements View.OnClickListener {

    TextView mTv_question;
    EditText mEt_answer;
    Button mB_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        mTv_question = (TextView)findViewById(R.id.tv_question);
        mEt_answer = (EditText)findViewById(R.id.et_answer);
        mB_send = (Button)findViewById(R.id.b_answer);

        mB_send.setOnClickListener(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        mTv_question.setText(ActivityUserDetails.mUser.mQuestionText);
    }

    @Override
    public void onClick(View v) {

        String answer = mEt_answer.getText().toString();

        if(!answer.equals("")){
            ActivityMain.mDatabaseUserRoot.child("replyTo").child("name").setValue(ActivityUserDetails.mUser.mUid);
            ActivityMain.mDatabaseUserRoot.child("replyTo").child("text").setValue(answer);

            ActivityMain.mCurrentUser.mReplyToUser = ActivityUserDetails.mUser.mUid;
            ActivityMain.mCurrentUser.mReplyToText = answer;

            DatabaseReference test = ActivityMain.mDatabaseRoot.child(ActivityUserDetails.mUser.mUid).child("question").child("answerFrom").child("name");
            test.setValue(ActivityMain.mCurrentUser.mUid);
            test = ActivityMain.mDatabaseRoot.child(ActivityUserDetails.mUser.mUid).child("question").child("answerFrom").child("text");
            test.setValue(answer);

            ActivityUserDetails.mUser.mQuestionAnswerFrom = ActivityMain.mCurrentUser.mUid;
            ActivityUserDetails.mUser.mQuestionAnswerText = answer;

            finish();
        }

    }
}
