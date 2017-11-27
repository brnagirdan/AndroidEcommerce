
/**
 * Created by Berna on 28.09.2017.
 */
package com.example.berna.cicekse2.Adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.berna.cicekse2.DetailActivity;
import com.example.berna.cicekse2.R;
import com.example.berna.cicekse2.model.Product;
import com.squareup.picasso.Picasso;
import java.util.List;



public class SimilarProductAdapter extends RecyclerView.Adapter<SimilarProductAdapter.MyViewHolder> {

    private Context mContext;
    private List<Product> productList;

    public SimilarProductAdapter(Context mContext, List<Product> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    @Override
    public SimilarProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.urun_card_similar, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimilarProductAdapter.MyViewHolder viewHolder, int i) {
     //   viewHolder.kod.setText(productList.get(i).getUrunKodu());
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

        public MyViewHolder(View view) {
            super(view);
      //      kod = (TextView) view.findViewById(R.id.kod);
            fiyat = (TextView) view.findViewById(R.id.fiyat);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

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
