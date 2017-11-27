package com.example.berna.cicekse2;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.berna.cicekse2.Adapter.SimilarProductAdapter;
import com.example.berna.cicekse2.cartData.CartProductContract;
import com.example.berna.cicekse2.Helper.GridSpacingItemDecoration;
import com.example.berna.cicekse2.cartData.CartProductDbHelper;
import com.example.berna.cicekse2.model.Product;
import com.example.berna.cicekse2.sqLite.data_access_tier;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.gson.Gson;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.berna.cicekse2.cartData.CartProductContract.ProductEntry.CART_TABLE;


public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    ExpandableRelativeLayout expandable_layout;
    private int mNotificationsCount = 0;
    ContentResolver mContentResolver;
    private SQLiteDatabase mDb;
    Intent intent;
    ProgressDialog pd;
    Gson gson = new Gson();
    List<Product> pro_list=new <Product>ArrayList();;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Button goOn,goCart;
    ImageView imageView;
    private RecyclerView recyclerView;
    private SimilarProductAdapter adapter;
    private List<Product> productList;
    List<Product> fav_pro;
    Product product;
    ImageButton geri,sepet_icon,favori;
    Button aciklama_btn,sepet_btn;
    TextView adi,fiyat_text,kdv_fiyat,urun_adi,urun_kodu,urun_aciklama;
    String cicekAd,kdvFiyat;
    String ad,urunKodu,icerik,thumbnail,thumbnail_kucuk;
    double fiyat,cicekfilofiyat;
    int kdv,cicekpasta,defaultkategori,inckey,siparissayi;
    NotificationBadge mBadge;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_detail);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

        /* cart sqlite events */
        mContentResolver = this.getContentResolver();
        CartProductDbHelper dbHelper = new CartProductDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

              /* shared pref initialize */
        sp = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = sp.edit();

        adi = (TextView) findViewById(R.id.adi);
        fiyat_text = (TextView) findViewById(R.id.fiyat_txt);
        kdv_fiyat = (TextView) findViewById(R.id.kdv_fiyat);
        urun_adi = (TextView) findViewById(R.id.urun_adi);
        urun_kodu = (TextView) findViewById(R.id.urun_kodu);
        urun_aciklama = (TextView) findViewById(R.id.urun_aciklama);
        aciklama_btn=(Button)findViewById(R.id.aciklama_btn);
        sepet_btn=(Button)findViewById(R.id.sepet_btn);
        sepet_btn.setOnClickListener(this);
        geri = (ImageButton) findViewById(R.id.geri);
        sepet_icon=(ImageButton) findViewById(R.id.sepet);
        favori=(ImageButton) findViewById(R.id.favori);

        imageView = (ImageView) findViewById(R.id.thumbnail_image_header);


            /* add to front end */
        ad = getIntent().getExtras().getString("Adi");
        urunKodu = getIntent().getExtras().getString("UrunKodu");
        fiyat = getIntent().getExtras().getDouble("SatisFiyat");
        kdv=getIntent().getExtras().getInt("Kdv");
        icerik = getIntent().getExtras().getString("Icerik");
        thumbnail = getIntent().getExtras().getString("ResimBuyuk");
        thumbnail_kucuk = getIntent().getExtras().getString("ResimKucuk");


        cicekpasta = getIntent().getExtras().getInt("CicekPasta");
        defaultkategori = getIntent().getExtras().getInt("DefaultKategori");
        cicekfilofiyat = getIntent().getExtras().getDouble("CicekFiloFiyat");
        inckey=getIntent().getExtras().getInt("IncKey");
        siparissayi = getIntent().getExtras().getInt("SiparisSayi");


        mBadge=(NotificationBadge)findViewById(R.id.badge);
        new FetchCountTask().execute();

        geri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        sepet_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailActivity.this, CartActivity.class);
                startActivity(i);
            }
        });

        final data_access_tier dt= new data_access_tier(this); // access data_access_tier of sqlite
        dt.open();
        fav_pro=dt.list();
        product=new Product(inckey,cicekpasta,urunKodu,fiyat,kdv,thumbnail_kucuk,siparissayi,defaultkategori,cicekfilofiyat,ad,icerik,thumbnail);
        for(int i=0;i<fav_pro.size();i++){
            if(product.getIncKey()==fav_pro.get(i).getIncKey()){
                favori.setBackgroundResource(R.mipmap.heart_black);  // if product is added to favorites show it.
            }
        }
        favori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count=0;
                fav_pro=dt.list();
                for(int i=0;i<fav_pro.size();i++) {
                    if (product.getIncKey()== fav_pro.get(i).getIncKey()) {
                        count++;
                    }
                }
                if(count==0){
                    favori.setBackgroundResource(R.mipmap.heart_black);
                    dt.insert(product);
                    Log.e("eklendiii", "eklendii");
                }
                else if(count!=0){
                    favori.setBackgroundResource(R.mipmap.like);
                    dt.delete(product);
                }
                Log.e("done", "done");
            }
        });


        Glide.with(this)
                .load(thumbnail)
                .placeholder(R.drawable.load)
                .into(imageView);

        if (ad.length() >= 25) {
            cicekAd = ad.substring(0, 25) + "...";
        } else {
            cicekAd = ad;
        }

        urun_adi.setText("Ürün Adı : " + ad);
        urun_kodu.setText("Ürün Kodu : " + urunKodu);
        icerik.replace("<", "TL");
        urun_aciklama.setText(icerik);

        kdvFiyat = String.valueOf(new DecimalFormat("##.##").format((fiyat * 18 / 100) + fiyat));
        kdv_fiyat.setText("KDV DAHİL " + kdvFiyat + " TL");
        adi.setText(cicekAd);
        fiyat_text.setText(String.valueOf((int) fiyat));

         /* get preference of product informatin to show similar products */
        for (int i = 0; i < 6; i++) {
            String json = sp.getString("similarProduct" + i, "");
            Product product_rand = gson.fromJson(json, Product.class);
            pro_list.add(product_rand);
        }
        initViews();
    }

    @Override
    public void onClick(View view) {openDialog();}

    public void openDialog() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sepet_dialog);
        dialog.setTitle(" ");
        TextView added_txt = (TextView) dialog.findViewById(R.id.eklendi);
        added_txt.setText("Ürün eklendi");
        TextView addeddetail_txt = (TextView) dialog.findViewById(R.id.eklendi_detail);
        addeddetail_txt.setText("Ürün sepetinize başarıyla eklendi.");
        dialog.show();
        addValuesToCart(); // add process
        goOn=(Button)dialog.findViewById(R.id.devam);
        goCart=(Button)dialog.findViewById(R.id.sepete_git);
        goOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        goCart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    intent = new Intent(DetailActivity.this, CartActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void expandableButton(View view) {
        expandable_layout = (ExpandableRelativeLayout) findViewById(R.id.expandable_layout);
        expandable_layout.toggle(); // toggle expand and collapse
    }

    /* Load events for similar products   */
        /* Converting dp to pixel */
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

        productList = new ArrayList<>();
        adapter = new SimilarProductAdapter(this, productList);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(6, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        loadJSON();
    }


    private void loadJSON(){
        try{
            recyclerView.setAdapter(new SimilarProductAdapter(getApplicationContext(),pro_list));
            recyclerView.smoothScrollToPosition(0);
            pd.hide();
        }

        catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateNotificationsBadge(int count) {
        mNotificationsCount = count;
        mBadge.setNumber(mNotificationsCount);
    }


    private void addValuesToCart() {

        ContentValues cartValues = new ContentValues();

        cartValues.put(CartProductContract.ProductEntry.COLUMN_CART_NAME,ad);
        cartValues.put(CartProductContract.ProductEntry.COLUMN_CART_IMAGE,thumbnail_kucuk);
        cartValues.put(CartProductContract.ProductEntry.COLUMN_CART_QUANTITY,1); //insert product quantity is 1 as default
        cartValues.put(CartProductContract.ProductEntry.COLUMN_CART_PRICE,fiyat);
        cartValues.put(CartProductContract.ProductEntry.COLUMN_CART_KDV,kdv);

        mContentResolver.insert(CartProductContract.ProductEntry.CONTENT_URI, cartValues);

        new FetchCountTask().execute();

    }
    /*
Sample AsyncTask to fetch the notifications count
*/
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