package com.example.berna.cicekse2.Adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.berna.cicekse2.R;
import com.example.berna.cicekse2.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Berna on 5.10.2017.
 */

public class SearchListAdapter extends BaseAdapter implements Filterable {
    View mainActivity;
    CustomFilter filter;
    List<Product> filterList;
    Context context;
    List<Product> rowItems;

    public SearchListAdapter (Context context, List<Product> rowItems,List<Product> filterList) {
        this.context = context;
        this.rowItems = rowItems;
        this.filterList=filterList;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_search_item, null);
            mainActivity = inflater.inflate(R.layout.activity_search, null); //for showing result not found
        }
        TextView name = (TextView) convertView.findViewById(R.id.flower_name);
        ImageView img = (ImageView) convertView.findViewById(R.id.flower_pic);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        name.setText(rowItems.get(position).getAdi());
        //load pic cover using picasso
        Picasso.with(context)
                .load(rowItems.get(position).getResimKucuk())
                .into(img);
         price.setText(String.valueOf((int) rowItems.get(position).getSatisFiyat()));
        return convertView;
    }


    @Override
    public Filter getFilter() {
        // TODO Auto-generated method stub
        if(filter == null)
        {
            filter=new CustomFilter();
        }
        return filter;
    }

    //INNER CLASS
    class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                //CONSTARINT TO UPPER
                constraint = constraint.toString().toUpperCase();
               List<Product> filters = new ArrayList<Product>();
                //get specific items
                for (int i = 0; i < filterList.size(); i++) {
                    if (filterList.get(i).getAdi().toUpperCase().contains(constraint)) {
                        Product p = new Product(filterList.get(i).getIncKey(),filterList.get(i).getCicekPasta(),filterList.get(i).getUrunKodu(),
                                filterList.get(i).getSatisFiyat(),filterList.get(i).getKdv(),filterList.get(i).getResimKucuk(),
                                filterList.get(i).getSiparisSayi(),filterList.get(i).getDefaultKategori(),filterList.get(i).getCicekFiloFiyat(),
                                filterList.get(i).getAdi(), filterList.get(i).getResimBuyuk(), filterList.get(i).getIcerik());
                        filters.add(p);
                    }
                }
                results.count = filters.size();
                results.values = filters;
                if(filters.size()==0){ // if not found result
                    TextView tv= (TextView) mainActivity.findViewById(R.id.sonucyok);
                    tv.setText("Üzgünüz, aradığınız sonucu bulamadık..");
                    Log.e("bbı","oıfnot");
                }
                else
                    mainActivity.findViewById(R.id.sonucyok).setVisibility(View.INVISIBLE);

            } else {
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub
            rowItems = (List<Product>) results.values;
            notifyDataSetChanged();
        }
    }
}
