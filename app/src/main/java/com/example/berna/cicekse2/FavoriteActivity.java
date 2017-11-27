package com.example.berna.cicekse2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.berna.cicekse2.Adapter.FavoriteAdapter;
import com.example.berna.cicekse2.Helper.GridSpacingItemDecoration;
import com.example.berna.cicekse2.model.Product;
import com.example.berna.cicekse2.sqLite.data_access_tier;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    Intent intent;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    ImageButton geri;
    Button basla;
    TextView pagename;
    RecyclerView recyclerView;
    List<Product> fav_pro;

    private FavoriteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        pagename=(TextView)findViewById(R.id.pagename);
        pagename.setText("Favorilerim");
        geri = (ImageButton) findViewById(R.id.geri);

          /* shared pref initialize */
        sp = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = sp.edit();


        final data_access_tier dt= new data_access_tier(this); // access data_access_tier of sqlite
        dt.open();
        fav_pro=dt.list();

        geri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if(fav_pro.isEmpty()){
            setContentView(R.layout.fav_no_exist);
            basla=(Button)findViewById(R.id.basla);
            basla.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(FavoriteActivity.this, ProductListeActivity.class);
                    editor.putInt("IncKey",0);
                    editor.commit();
                    startActivity(intent);
                }
            });
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

        }
        else
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new FavoriteAdapter(this, fav_pro);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}
