package com.asyarif.taarufsg;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;


/**
 * Created by asyarif on 10/06/17.
 */

public class ListPeopleAdapter extends RecyclerView.Adapter<ListPeopleAdapter.ListPeopleViewHolder> {


    @Override
    public ListPeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.list_people;

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(layoutId,parent,shouldAttachToParentImmediately);

        ListPeopleViewHolder viewHolder = new ListPeopleViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListPeopleViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ListPeopleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mUser;
        TextView mBirthday;
        TextView mDescription;
        ImageView mMaleIcon;
        ImageView mFemaleIcon;

        public ListPeopleViewHolder(View itemView) {
            super(itemView);

            mUser = (TextView) itemView.findViewById(R.id.tv_username);
            mDescription = (TextView)itemView.findViewById(R.id.tv_description);
            mBirthday = (TextView)itemView.findViewById(R.id.tv_birthday);
            mMaleIcon = (ImageView)itemView.findViewById(R.id.iv_man);
            mFemaleIcon = (ImageView)itemView.findViewById(R.id.iv_women);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        public void bind(int position) {
            if(position==0){
                mUser.setText("Syarif");
                mMaleIcon.setVisibility(View.VISIBLE);
                mFemaleIcon.setVisibility(View.INVISIBLE);
                mBirthday.setText("20 April 1992");
                mDescription.setText("Hi, My name is syarif and I'm married already. so sorry loh");
            }
            else if (position==1){
                mUser.setText("Multazam");
                mMaleIcon.setVisibility(View.VISIBLE);
                mFemaleIcon.setVisibility(View.INVISIBLE);
                mBirthday.setText("21 April 1992");
                mDescription.setText("Hi, My name is Multazam and I'm not married yet. so come on loh");
            }
            else if (position==2){
                mUser.setText("Aziz");
                mMaleIcon.setVisibility(View.VISIBLE);
                mFemaleIcon.setVisibility(View.INVISIBLE);
                mBirthday.setText("31 Mei 1992");
                mDescription.setText("Hi, My name is Aziz and I'm not married yet. so come on loh");
            }
        }
    }
}