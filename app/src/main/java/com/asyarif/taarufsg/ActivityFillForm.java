package com.asyarif.taarufsg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static com.asyarif.taarufsg.ActivityMain.mDatabase;

public class ActivityFillForm extends AppCompatActivity implements ValueEventListener,View.OnClickListener{

    private static final String TAG = "ActivityFillForm";

    EditText mEt_name,mEt_birthday, mEt_gender, mEt_phoneNumber,mEt_description, mEt_question;
    Button mB_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_form);

        ActivityMain.mDatabase = FirebaseDatabase.getInstance().getReference();
        ActivityMain.mDatabaseRoot = ActivityMain.mDatabase.child("database");

        mEt_name = (EditText)findViewById(R.id.et_name);
        mEt_birthday = (EditText)findViewById(R.id.et_birthday);
        mEt_gender = (EditText)findViewById(R.id.et_gender);
        mEt_phoneNumber = (EditText)findViewById(R.id.et_phoneNumber);
        mEt_description = (EditText)findViewById(R.id.et_description);
        mEt_question = (EditText)findViewById(R.id.et_question);

        mB_save = (Button)findViewById(R.id.b_save);
        mB_save.setOnClickListener(this);

    }

    @Override
    protected void onStart(){
        super.onStart();

        ActivityMain.mDatabaseRoot.addListenerForSingleValueEvent(this);
    }

    private void updateUI(DatabaseReference user){

        if(user!=null){
            Intent intent = new Intent(ActivityFillForm.this,ActivityMain.class);
            startActivity(intent);
        }

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if(dataSnapshot.hasChild(ActivityMain.mUser.getUid())){

            DataSnapshot userDataSnapshot =  dataSnapshot.child(ActivityMain.mUser.getUid());

            ActivityMain.mCurrentUser = new User(userDataSnapshot);

            ActivityMain.mDatabaseUserRoot = ActivityMain.mDatabaseRoot.child(ActivityMain.mUser.getUid());

            updateUI(ActivityMain.mDatabaseRoot.child(ActivityMain.mUser.getUid()));
        }
        else{
            updateUI(null);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override
    public void onClick(View v) {
        if(mEt_name.getText().toString().equals("") ||
                mEt_birthday.getText().toString().equals("") ||
                mEt_gender.getText().toString().equals("") ||
                mEt_phoneNumber.getText().toString().equals("") ||
                mEt_description.getText().toString().equals("") ||
                mEt_question.getText().toString().equals("")

                ){
            Toast.makeText(ActivityFillForm.this,
                    "Data must be filled",
                    Toast.LENGTH_SHORT).show();

        }
        else{
            String key = ActivityMain.mDatabaseRoot.push().getKey();

            User user = new User(mEt_name.getText().toString(),
                    mEt_birthday.getText().toString(),
                    mEt_description.getText().toString(),
                    mEt_gender.getText().toString(),
                    mEt_phoneNumber.getText().toString(),
                    mEt_question.getText().toString());



            Map<String, Object> map = user.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/"+ActivityMain.mUser.getUid(),map);

            user.mUid = ActivityMain.mUser.getUid();
            ActivityMain.mCurrentUser = user;


            ActivityMain.mDatabaseRoot.updateChildren(childUpdates);
            ActivityMain.mDatabaseUserRoot = ActivityMain.mDatabaseRoot.child(ActivityMain.mUser.getUid());

            updateUI( ActivityMain.mDatabaseUserRoot);

        }
    }
}
