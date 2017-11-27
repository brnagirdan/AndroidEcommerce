package com.example.berna.cicekse2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.berna.cicekse2.Adapter.SearchListAdapter;
import com.example.berna.cicekse2.model.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity {
    TextView tavsiye;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    ImageButton geri;
    Gson gson = new Gson();
    ListView mylistview;
    EditText searchtext;
    List<Product> pro_list=new <Product>ArrayList();
    List<Product> filter_list=new <Product>ArrayList();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tavsiye = (TextView) findViewById(R.id.tavsiye);
        tavsiye.setText("Size Özel Önerilerimiz");

          /* shared pref initialize */
        sp = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = sp.edit();


          /* get preference 12 rand products informatin  */
        for (int i = 0; i < 12; i++) {
            String json = sp.getString("searchProduct" + i, "");
            Product product_rand = gson.fromJson(json, Product.class);
            pro_list.add(product_rand);
            Log.e("searchpro",product_rand.getAdi());
        }
           /* get preference all of products informatin  */
        for (int i=0;i<sp.getInt("items", 0);i++) {
            String json = sp.getString("products"+i, "");
            Product product_rand = gson.fromJson(json, Product.class);
            filter_list.add(product_rand);
        }

        mylistview = (ListView) findViewById(R.id.list);
        mylistview.setTextFilterEnabled(true);
        searchtext=(EditText) findViewById(R.id.searchtext);
        geri = (ImageButton) findViewById(R.id.geri);

        geri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        final SearchListAdapter adapter = new SearchListAdapter(this,pro_list,filter_list);
        mylistview.setAdapter(adapter);

         searchtext.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }


}
