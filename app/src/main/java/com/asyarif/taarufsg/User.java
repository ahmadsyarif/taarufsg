package com.asyarif.taarufsg;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by asyarif on 10/06/17.
 */

public class User {
    public String mName;
    public String mBirthday;
    public String mDescription;
    public String mGender;
    public String mQuestionText;
    public String mQuestionAnswerFrom;
    public String mQuestionAnswerText;
    public String mReplyToUser;
    public String mReplyToText;
    public String mPhoneNumber;
    public String mStatus;
    public String mObject;
    public String mSubject;
    public String mUid;


    public User(String name, String birthday, String description){
        mName=name;
        mBirthday = birthday;
        mDescription = description;
    }
    public User(String name, String birthday, String description, String gender, String phoneNumber, String question){
        mName=name;
        mBirthday = birthday;
        mDescription = description;
        mGender = gender;
        mPhoneNumber = phoneNumber;
        mStatus = "free";
        mObject = "";
        mSubject = "";
        mQuestionText = question;
        mQuestionAnswerFrom = "";
        mQuestionAnswerText = "";
        mReplyToUser ="";
        mReplyToText = "";
        mSubject = "";
        mObject = "";
    }

    public User(DataSnapshot dataSnapshot){
        mName=dataSnapshot.child("name").getValue().toString();
        mBirthday = dataSnapshot.child("birthday").getValue().toString();
        mDescription = dataSnapshot.child("description").getValue().toString();;
        mGender = dataSnapshot.child("gender").getValue().toString().toLowerCase();
        mPhoneNumber = dataSnapshot.child("phone number").getValue().toString().toLowerCase();
        mStatus = dataSnapshot.child("status").getValue().toString().toLowerCase();
        mUid = dataSnapshot.getKey();
        mQuestionText = dataSnapshot.child("question").child("text").getValue().toString();
        mQuestionAnswerFrom = dataSnapshot.child("question").child("answerFrom").child("name").getValue().toString();
        mQuestionAnswerText =dataSnapshot.child("question").child("answerFrom").child("text").getValue().toString();
        mReplyToUser = dataSnapshot.child("replyTo").child("name").getValue().toString();
        mReplyToText = dataSnapshot.child("replyTo").child("text").getValue().toString();
        mSubject = dataSnapshot.child("subject").getValue().toString();
        mObject = dataSnapshot.child("object").getValue().toString();


    }

    public Map<String,Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", mName);
        result.put("birthday", mBirthday);
        result.put("description", mDescription);
        result.put("gender", mGender);
        result.put("question/text", mQuestionText);
        result.put("question/answerFrom/name", "");
        result.put("question/answerFrom/text", "");
        result.put("replyTo/name", "");
        result.put("replyTo/text", "");
        result.put("phone number", mPhoneNumber);
        result.put("status",mStatus);
        result.put("object",mObject);
        result.put("subject",mSubject);
        return result;
    }
}
