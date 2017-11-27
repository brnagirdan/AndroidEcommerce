package com.example.berna.cicekse2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.berna.cicekse2.R;
import com.example.berna.cicekse2.model.Extra;
import com.example.berna.cicekse2.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Berna on 27.11.2017.
 */

public class ExtraAdapter extends BaseAdapter {
    Context context;
    List<Extra> rowItems;

    public ExtraAdapter (Context context, List<Extra> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() { return rowItems.size();}

    @Override
    public Object getItem(int i) {
        return rowItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return rowItems.indexOf(getItem(i));}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.urun_card, viewGroup, false);
        TextView name = (TextView) view.findViewById(R.id.flower_name);
        ImageView img = (ImageView) view.findViewById(R.id.flower_pic);
        TextView price = (TextView) view.findViewById(R.id.price);
        name.setText(rowItems.get(i).getUrunAdi());
        //load pic cover using picasso
        Picasso.with(context)
                .load(rowItems.get(i).getResim())
                .into(img);
        price.setText(String.valueOf((int) rowItems.get(i).getBirimFiyat()));
        return view;
    }
}
