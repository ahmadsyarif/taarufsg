package com.asyarif.taarufsg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

public class ActivityUserDetails extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = ActivityUserDetails.class.getSimpleName();

    private ImageView mIv_photo;
    private TextView mTv_username,mTv_birthday,mTv_description;
    private Button minterested_button, mnext_button, mprevious_button;

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
                User user = ListPeopleAdapter.mUsers.get(userId);
                mTv_username.setText(user.mName);
                mTv_birthday.setText(user.mBirthday);
                mTv_description.setText(user.mDescription);

            }
            catch (IndexOutOfBoundsException e){
                Log.v(TAG,e.getMessage());
            }
        }


    }

    @Override
    public void onClick(View view) {

    }
}
