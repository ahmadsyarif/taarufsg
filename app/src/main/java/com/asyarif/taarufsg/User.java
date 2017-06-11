package com.asyarif.taarufsg;

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
    public String mQuestion;
    public String mPhoneNumber;

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
        mQuestion = phoneNumber;
        mPhoneNumber = question;
    }

    public Map<String,Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", mName);
        result.put("birthday", mBirthday);
        result.put("description", mDescription);
        result.put("gender", mGender);
        result.put("question", mQuestion);
        result.put("phoneNumber", mPhoneNumber);

        return result;
    }
}
