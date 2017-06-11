package com.asyarif.taarufsg;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Created by asyarif on 10/06/17.
 */

public class ListPeopleAdapter extends RecyclerView.Adapter<ListPeopleAdapter.ListPeopleViewHolder> {

    private static final String TAG = ListPeopleAdapter.class.getSimpleName();
    private static final int TAG_POSITION = 0x0001;
    public static ArrayList<User> mUsers;

    private Context mContext;
    private int mNumberOfUser;


    public ListPeopleAdapter(){

        mUsers = new ArrayList<User>();
        mUsers.clear();

        ActivityMain.mDatabaseRoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                mNumberOfUser = (int)dataSnapshot.getChildrenCount();


                for(DataSnapshot user : dataSnapshot.getChildren()){
                    User newUser = createUser(user);
                    if(newUser!=null){
                        mUsers.add(newUser);
                    }

                }

                notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });

        ActivityMain.mDatabaseRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                mNumberOfUser = (int)dataSnapshot.getChildrenCount();
                mUsers.clear();
                for(DataSnapshot user : dataSnapshot.getChildren()){
                    User newUser = createUser(user);
                    if(newUser!=null){
                        mUsers.add(newUser);
                    }
                }

                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });
        ActivityMain.mDatabaseUserRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ActivityMain.mCurrentUser = new User(dataSnapshot);

                if(!ActivityMain.mCurrentUser.mObject.equals("") && mContext!=null){
                    for(User user : mUsers){
                        if(user.mUid.equals(ActivityMain.mCurrentUser.mObject)){
                            String name = user.mName;
                            Toast.makeText(mContext, name + " has interest to you",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public User createUser(DataSnapshot user){
        Log.v(TAG,user.getKey());
        Log.v(TAG,ActivityMain.mCurrentUser.mUid);
        User newUser = null;
        if(!user.getKey().equals(ActivityMain.mCurrentUser.mUid) ){

            String gender = (String)user.child("gender").getValue();

            if(ActivityMain.mCurrentUser.mGender.equals(gender)){
                return null;
            }

            newUser = new User(user);
        }

        return newUser;
    }



    @Override
    public ListPeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.list_people;
        mContext = context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(layoutId,parent,shouldAttachToParentImmediately);

        ListPeopleViewHolder viewHolder = new ListPeopleViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListPeopleViewHolder holder, int position) {
        User user = mUsers.get(position);
        holder.bind(position,user);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    class ListPeopleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mUser;
        TextView mBirthday;
        TextView mDescription;
        ImageView mMaleIcon;
        ImageView mFemaleIcon;
        int mId;

        public ListPeopleViewHolder(View itemView) {
            super(itemView);

            mUser = (TextView) itemView.findViewById(R.id.tv_username);
            mDescription = (TextView)itemView.findViewById(R.id.tv_description);
            mBirthday = (TextView)itemView.findViewById(R.id.tv_birthday);
            mMaleIcon = (ImageView)itemView.findViewById(R.id.iv_man);
            mFemaleIcon = (ImageView)itemView.findViewById(R.id.iv_women);
            mId = 0;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.v(TAG,v.toString());
            try{
                Log.v(TAG,mUsers.get(mId).mName);
                Intent intent = new Intent(mContext,ActivityUserDetails.class);
                intent.putExtra(ActivityMain.EXTRA_USER_ID,mId);
                mContext.startActivity(intent);

            }
            catch (IndexOutOfBoundsException e){
                Log.v(TAG,e.getMessage());
            }

        }

        public void bind(int position,User user) {

            try{
                mId = position;
                mUser.setText(user.mName);
                mMaleIcon.setVisibility(View.VISIBLE);
                mFemaleIcon.setVisibility(View.INVISIBLE);
                mBirthday.setText(user.mBirthday);
                mDescription.setText(user.mDescription);
            }
            catch (IndexOutOfBoundsException e)
            {
                Log.v(TAG,e.getMessage());
            }

        }
    }
}