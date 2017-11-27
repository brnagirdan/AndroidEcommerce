package com.example.berna.cicekse2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.berna.cicekse2.Adapter.ProductAdapter;
import com.example.berna.cicekse2.Helper.GridSpacingItemDecoration;
import com.example.berna.cicekse2.api.Client;
import com.example.berna.cicekse2.api.Service;
import com.example.berna.cicekse2.cartData.CartProductDbHelper;
import com.example.berna.cicekse2.model.Product;
import com.example.berna.cicekse2.DataResponse.ProductsResponse;
import com.google.gson.Gson;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.berna.cicekse2.cartData.CartProductContract.ProductEntry.CART_TABLE;

public class ProductListeActivity extends AppCompatActivity {

    Gson gson = new Gson();
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;
    ProgressDialog pd;
    ImageButton geri,buyutec,sepet;
    Intent intent;
    Bundle extras;
    Random rand;
    private SQLiteDatabase mDb;
    ContentResolver mContentResolver;
    NotificationBadge mBadge;
    private int mNotificationsCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_liste);
       /* badge for cart */
        mBadge=(NotificationBadge)findViewById(R.id.badge);
          /* cart sqlite events */
        mContentResolver = this.getContentResolver();
        CartProductDbHelper dbHelper = new CartProductDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        new FetchCountTask().execute();
             /* shared pref initialize */
        sp = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = sp.edit();

        /* for random select to show similar products */
        rand = new Random();

           /* bundles for main pictures */
            try {
                intent=getIntent();
                extras= intent.getExtras();
                String key=String.valueOf(extras.getInt("IncKey"));
                Log.e("eeee",key);
                editor.putInt("IncKey",extras.getInt("IncKey"));
                editor.commit();
            }
            catch(Exception ex) {

            }

        initViews();
        initCollapsingToolbar();

        buyutec =(ImageButton)findViewById(R.id.buyutec);
        sepet=(ImageButton)findViewById(R.id.sepet);
        geri =(ImageButton)findViewById(R.id.geri);
        geri.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        buyutec.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    intent = new Intent(ProductListeActivity.this, SearchActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        sepet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ProductListeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });


    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        appBarLayout.setBackgroundColor(getResources().getColor(R.color.white));



        // hiding & showing the title when toolbar expanded & collapsed
      appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { //
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {

                    isShow = true;
                } else if (isShow) {

                    isShow = false;
                }
            }
        });
    }



    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void initViews(){
        pd = new ProgressDialog(this);
        pd.setMessage("Yükleniyor..."); //fetching..
        pd.setCancelable(false);
        pd.show();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    //    productList = new ArrayList<>();
    //    adapter = new ProductAdapter(this, productList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
     //   recyclerView.setAdapter(adapter);
        loadJSON();
    }

    private void loadJSON(){
        try{
                Client Client = new Client();
                Service apiService =
                        Client.getClient().create(Service.class);
                Call<ProductsResponse> call = apiService.getProducts(sp.getInt("IncKey", 0));
                call.enqueue(new Callback<ProductsResponse>() {
                    @Override
                    public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                        if(sp.getInt("IncKey", 0)!=0) {
                        List<Product> items = response.body().getProducts();
                        for(int i=0;i<6;i++){
                            Product pro_random = items.get(rand.nextInt(items.size()));
                            String json = gson.toJson(pro_random);
                            editor.putString("similarProduct"+i, json);
                          }
                        for(int i=0;i<items.size();i++){
                            Product pro_random = items.get(i);
                            String json = gson.toJson(pro_random);
                            editor.putString("products"+i, json);
                        }
                        editor.putInt("items", items.size());
                        editor.commit();

                            recyclerView.setAdapter(new ProductAdapter(getApplicationContext(), items));
                            recyclerView.smoothScrollToPosition(0);
                            pd.hide();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductsResponse> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                        pd.hide();
                    }
                });

            /* select all product */
            Call<ProductsResponse> callAll = apiService.getAllProducts();
            callAll.enqueue(new Callback<ProductsResponse>() {
                @Override
                public void onResponse(Call<ProductsResponse> callAll, Response<ProductsResponse> response) {
                    List<Product> itemsAll = response.body().getProducts();
                    for(int i=0;i<12;i++){
                        Product pro_random = itemsAll.get(rand.nextInt(itemsAll.size()));
                        String json = gson.toJson(pro_random);
                        editor.putString("searchProduct"+i, json);
                        Log.e("random",json);
                    }
                    editor.commit();
                    if(sp.getInt("IncKey", 0)==0){ // I set 0 for all products and it control 0 or not
                        recyclerView.setAdapter(new ProductAdapter(getApplicationContext(), itemsAll));
                        recyclerView.smoothScrollToPosition(0);
                        pd.hide();
                    }

                }

                @Override
                public void onFailure(Call<ProductsResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    pd.hide();
                }
            });

        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }



    private void updateNotificationsBadge(int count) {
        mNotificationsCount = count;
        mBadge.setNumber(mNotificationsCount);
    }

    class FetchCountTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            String countQuery = "SELECT  * FROM " + CART_TABLE;
            Cursor cursor = mDb.rawQuery(countQuery, null);
            int count = cursor.getCount();
            cursor.close();
            return count;
        }

        @Override
        public void onPostExecute(Integer count) {
            updateNotificationsBadge(count);
            Log.e("countttt",String.valueOf(count));
        }


    }

    // require to update after finish function
    @Override
    public void onRestart() {
        super.onRestart();
        new FetchCountTask().execute();
    }

}
