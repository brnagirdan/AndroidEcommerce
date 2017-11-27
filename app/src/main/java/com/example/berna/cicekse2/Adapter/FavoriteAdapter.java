package com.example.berna.cicekse2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.berna.cicekse2.DetailActivity;
import com.example.berna.cicekse2.FavoriteActivity;
import com.example.berna.cicekse2.R;
import com.example.berna.cicekse2.model.Product;
import com.example.berna.cicekse2.sqLite.data_access_tier;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Berna on 31.08.2017.
 */


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {

    private Context mContext;
    private List<Product> productList;

    public FavoriteAdapter(Context mContext, List<Product> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    @Override
    public FavoriteAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.favorite_product_item, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavoriteAdapter.MyViewHolder viewHolder, int i) {
        //  viewHolder.kod.setText(productList.get(i).getUrunKodu());
        viewHolder.fiyat.setText(String.valueOf((int)productList.get(i).getSatisFiyat()));

        //load product cover using picasso
        Picasso.with(mContext)
                .load(productList.get(i).getResimKucuk())
                .into(viewHolder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView kod, fiyat;
        public ImageView thumbnail;
        public ImageButton like;


        public MyViewHolder(View view) {
            super(view);
            //  kod = (TextView) view.findViewById(R.id.kod);
            fiyat = (TextView) view.findViewById(R.id.fiyat);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            like=(ImageButton)view.findViewById(R.id.like);

           like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Product clickedDataItem = productList.get(pos);
                        Log.e("silindi", String.valueOf(clickedDataItem));
                        final data_access_tier dt= new data_access_tier(mContext); // access data_access_tier of sqlite
                        dt.open();
                        dt.delete(clickedDataItem);
                        productList=dt.list();
                        productList.remove(clickedDataItem);
                        Log.e("silindi","silindi");
                        notifyDataSetChanged(); // listeden sildikten sonra ekliyorum ki değişimi görsün.
                        if(productList.isEmpty()){
                            Intent intent = new Intent(mContext, FavoriteActivity.class);
                            mContext.startActivity(intent);
                        }
                    }
                }
            });

            //on item click
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    bundleEvents();
                }
            });
            //on pic click
            thumbnail.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    bundleEvents();
                }
            });
        }

        public  void bundleEvents(){
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION){
                Product clickedDataItem = productList.get(pos);
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("IncKey", productList.get(pos).getIncKey());
                intent.putExtra("CicekPasta", productList.get(pos).getCicekPasta());
                intent.putExtra("UrunKodu", productList.get(pos).getUrunKodu());
                intent.putExtra("SatisFiyat", productList.get(pos).getSatisFiyat());
                intent.putExtra("Kdv", productList.get(pos).getKdv());
                intent.putExtra("ResimKucuk", productList.get(pos).getResimKucuk());
                intent.putExtra("SiparisSayi", productList.get(pos).getSiparisSayi());
                intent.putExtra("DefaultKategori", productList.get(pos).getDefaultKategori());
                intent.putExtra("CicekFiloFiyat ", productList.get(pos).getCicekFiloFiyat());
                intent.putExtra("Adi", productList.get(pos).getAdi());
                intent.putExtra("Icerik", productList.get(pos).getIcerik());
                intent.putExtra("ResimBuyuk", productList.get(pos).getResimBuyuk());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        }
    }


}
