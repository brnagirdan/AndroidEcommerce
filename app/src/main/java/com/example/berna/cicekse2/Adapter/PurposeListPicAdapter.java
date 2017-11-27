package com.example.berna.cicekse2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.berna.cicekse2.ProductListeActivity;
import com.example.berna.cicekse2.R;
import com.example.berna.cicekse2.model.Picture;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Berna on 9.10.2017.
 */

public class PurposeListPicAdapter extends RecyclerView.Adapter<PurposeListPicAdapter.MyViewHolder> {

    private Context mContext;
    private List<Picture> pictureList;


    public PurposeListPicAdapter(Context mContext, List<Picture> pictureList) {
        this.mContext = mContext;
        this.pictureList = pictureList;
    }


    @Override
    public PurposeListPicAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.purpose_pic_card, viewGroup, false);

        return new PurposeListPicAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PurposeListPicAdapter.MyViewHolder viewHolder, int i) {
        //load pic cover using picasso
        Picasso.with(mContext)
                .load(pictureList.get(i).getResim())
                .into(viewHolder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            //on pic click
            thumbnail.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Picture clickedDataItem = pictureList.get(pos);
                        Intent intent = new Intent(mContext, ProductListeActivity.class);
                        intent.putExtra("IncKey", pictureList.get(pos).getIncKey());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }
            });
        }

    }

}
