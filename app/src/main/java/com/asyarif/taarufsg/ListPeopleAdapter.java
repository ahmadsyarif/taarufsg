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
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseRoot;
    public static ArrayList<User> mUsers;

    private Context mContext;
    private int mNumberOfUser;


    public ListPeopleAdapter(){
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabaseRoot  = mDatabase.child("database");

        mUsers = new ArrayList<User>();
        mUsers.clear();

        mDatabaseRoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                mNumberOfUser = (int)dataSnapshot.getChildrenCount();

                for(DataSnapshot user : dataSnapshot.getChildren()){
                    String name = (String)user.child("name").getValue();
                    String birthday = (String)user.child("birthday").getValue();
                    String description = (String)user.child("description").getValue();

                    mUsers.add(new User(name,birthday,description));
                }

                notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });

        mDatabaseRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                mNumberOfUser = (int)dataSnapshot.getChildrenCount();
                mUsers.clear();
                for(DataSnapshot user : dataSnapshot.getChildren()){
                    String name = (String)user.child("name").getValue();
                    String birthday = (String)user.child("birthday").getValue();
                    String description = (String)user.child("description").getValue();

                    mUsers.add(new User(name,birthday,description));
                }

                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });
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