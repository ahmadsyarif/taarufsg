package com.asyarif.taarufsg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ActivityMain extends AppCompatActivity {

    RecyclerView mRv_listPeople;
    ListPeopleAdapter listPeopleAdapter;

    public static final String EXTRA_USER_ID = "userId";

    public static FirebaseUser mUser;

    public static DatabaseReference mDatabase;
    public static DatabaseReference mDatabaseRoot;
    public static DatabaseReference mDatabaseUserRoot;
    public static User mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mRv_listPeople = (RecyclerView)findViewById(R.id.rv_list_people);
        mRv_listPeople.setHasFixedSize(false);
        mRv_listPeople.setLayoutManager(layoutManager);
        mRv_listPeople.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        listPeopleAdapter = new ListPeopleAdapter();

        mRv_listPeople.setAdapter(listPeopleAdapter);



    }
}
