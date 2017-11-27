package com.example.berna.cicekse2.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.berna.cicekse2.R;

import java.util.List;



/**
 * Created by Berna on 23.11.2017.
 */

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.MyViewHolder> {
    private Context mContext;
    private List<String>timeList;
    private int selectedPosition = 0;// no selection by default

    public TimeAdapter(Context mContext, List<String> timeList) {
        this.mContext = mContext;
        this.timeList =timeList;
    }


    @Override
    public TimeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_card, parent, false);

        return new TimeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TimeAdapter.MyViewHolder holder, int i) {
            holder.selectTime.setText(timeList.get(i));

        if(selectedPosition==i){
            holder.selectTime.setSelected(true);
        }
        else{
            holder.selectTime.setSelected(false);
        }

    }

    @Override
    public int getItemCount(){ return timeList.size();}



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public Button selectTime;


        public MyViewHolder(View view) {
            super(view);
            selectTime = (Button)view.findViewById(R.id.time_select);

            selectTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                       selectedPosition=getAdapterPosition();
                       notifyDataSetChanged();
                }
            });
        }

        public  void bundleEvents(){
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION){


            }
        }
    }
}
