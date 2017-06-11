package com.asyarif.taarufsg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class ActivityUserDetails extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = ActivityUserDetails.class.getSimpleName();

    private ImageView mIv_photo;
    private TextView mTv_username,mTv_birthday,mTv_description;
    private Button minterested_button, mnext_button, mprevious_button;

    User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Intent intent = getIntent();
        int userId = (int)intent.getIntExtra(ActivityMain.EXTRA_USER_ID,-1);

        mTv_username = (TextView)findViewById(R.id.tv_username);
        mTv_birthday = (TextView)findViewById(R.id.tv_birthday);
        mTv_description = (TextView)findViewById(R.id.tv_description);
        minterested_button = (Button)findViewById(R.id.interested_button);
        mnext_button = (Button) findViewById(R.id.next_button);
        mprevious_button = (Button) findViewById(R.id.previous_button);

        minterested_button.setOnClickListener(this);
        mnext_button.setOnClickListener(this);
        mprevious_button.setOnClickListener(this);

        if(userId!=-1){
            try{
                mUser = ListPeopleAdapter.mUsers.get(userId);
                mTv_username.setText(mUser.mName);
                mTv_birthday.setText(mUser.mBirthday);
                mTv_description.setText(mUser.mDescription);

                if(ActivityMain.mCurrentUser.mStatus.equals("free")){
                   if(ActivityMain.mCurrentUser.mSubject.equals(mUser.mUid)){
                       minterested_button.setText("Uninterest");
                   }
                   else if(ActivityMain.mCurrentUser.mObject.equals(mUser.mUid)){
                       minterested_button.setText("Accept Interest");
                   }
                   else if(!ActivityMain.mCurrentUser.mSubject.equals("")){
                       minterested_button.setText("Max Limit");
                   }
                   else{
                       minterested_button.setText("Interest");
                   }

                }
                else if(ActivityMain.mCurrentUser.mStatus.equals("first match")){
                    if(ActivityMain.mCurrentUser.mReplyToUser.equals("")){
                        minterested_button.setText("Answer Question");
                    }

                    else if(mUser.mReplyToUser.equals("")){
                        minterested_button.setText("First Match");
                    }
                    else {
                        minterested_button.setText("Accept Answer");
                    }

                }



            }
            catch (IndexOutOfBoundsException e){
                Log.v(TAG,e.getMessage());
            }
        }


    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.interested_button){

            if(minterested_button.getText().toString().equals("Interest")){
                mUser.mObject = ActivityMain.mCurrentUser.mUid;

                ActivityMain.mDatabaseRoot.child(mUser.mUid).child("object").setValue(ActivityMain.mCurrentUser.mUid);
                ActivityMain.mDatabaseUserRoot.child("subject").setValue(mUser.mUid);
                mUser.mObject = ActivityMain.mCurrentUser.mUid;
                ActivityMain.mCurrentUser.mSubject = mUser.mUid;

                minterested_button.setText("Uninterest");

                Log.v(TAG,ActivityMain.mCurrentUser.mUid + " interest to " + mUser.mUid);
            }
            else if(minterested_button.getText().toString().equals("Uninterest")){
                mUser.mObject = ActivityMain.mCurrentUser.mUid;

                ActivityMain.mDatabaseRoot.child(mUser.mUid).child("object").setValue("");
                ActivityMain.mDatabaseUserRoot.child("subject").setValue("");
                ActivityMain.mCurrentUser.mSubject = "";
                mUser.mObject = "";
                minterested_button.setText("Interest");

                Log.v(TAG,ActivityMain.mCurrentUser.mUid + " interest to " + mUser.mUid);
            }

            else if(minterested_button.getText().toString().equals("Accept Interest")){

                ActivityMain.mDatabaseRoot.child(mUser.mUid).child("object").setValue(ActivityMain.mCurrentUser.mUid);
                mUser.mObject = ActivityMain.mCurrentUser.mUid;
                ActivityMain.mDatabaseUserRoot.child("subject").setValue(mUser.mUid);
                ActivityMain.mCurrentUser.mSubject = mUser.mUid;

                Toast.makeText(ActivityUserDetails.this,"Congratulation you are match",
                        Toast.LENGTH_SHORT).show();

                ActivityMain.mDatabaseRoot.child(mUser.mUid).child("status").setValue("first match");
                ActivityMain.mDatabaseUserRoot.child("status").setValue("first match");
                mUser.mStatus = "first match";
                ActivityMain.mCurrentUser.mStatus = "first match";

                minterested_button.setText("Answer Question");
            }
            else if(minterested_button.getText().toString().equals("Answer Question")){
                //TODO open new activity

            }
            else if(minterested_button.getText().toString().equals("Accept Answers")){
                //TODO open new activity
                ActivityMain.mDatabaseRoot.child(mUser.mUid).child("status").setValue("second match");
                ActivityMain.mDatabaseUserRoot.child("status").setValue("second match");
                mUser.mStatus = "second match";
                ActivityMain.mCurrentUser.mStatus = "second match";
            }


        }
    }
}
