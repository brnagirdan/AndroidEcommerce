package com.example.berna.cicekse2;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.berna.cicekse2.Adapter.PictureAdapter;
import com.example.berna.cicekse2.Adapter.ProductAdapter;
import com.example.berna.cicekse2.Adapter.PurposeListPicAdapter;
import com.example.berna.cicekse2.api.Client;
import com.example.berna.cicekse2.api.Service;
import com.example.berna.cicekse2.cartData.CartProductDbHelper;
import com.example.berna.cicekse2.DataResponse.CatPictureResponse;
import com.example.berna.cicekse2.model.Category;
import com.example.berna.cicekse2.model.Picture;
import com.example.berna.cicekse2.model.Product;
import com.example.berna.cicekse2.DataResponse.ProductsResponse;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.example.berna.cicekse2.Helper.GridSpacingItemDecoration;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.nex3z.notificationbadge.NotificationBadge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.berna.cicekse2.cartData.CartProductContract.ProductEntry.CART_TABLE;

public class MainActivity extends AppCompatActivity {
    Intent Pintent;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    DrawerLayout drawer;
    private RecyclerView recyclerView,recycler_coksatilanlar,recycler_pic2,recycler_pic_purpose;
    private PictureAdapter adapter;
    private PurposeListPicAdapter adapterPurposePic;
    private ProductAdapter adapter_bestseller;
    private List<Picture> pictureList;
    private List<Product> productList;
    ProgressDialog pd;

    Context context;
    NavigationView leftNavigationView;
    MenuItem nav_cicek,nav_amac,nav_lezzetcicekleri,nav_fiyatagore,nav_iletisim,nav_paylas,nav_oyla,nav_group2; // left menu items
    public Menu menu,menu_right;
    TextView left_textView;
    TextView coksatilanlar_txt;
    SpannableString s;  // for left menu group title
    Button ara;
    ImageButton sepet;
    LoginButton loginButton;
    CallbackManager callbackManager;

    private static final String JSON_URL="http://andwebapi20171127120129.azurewebsites.net/api/categories";
    List<Category> categoryList;
    List<Category> cat_amac=new <Category>ArrayList();
    List<Category> cat_kategori=new <Category>ArrayList();
    List<Category> cat_pasta=new <Category>ArrayList();
    List<Category> cat_fiyat=new <Category>ArrayList();
    com.android.volley.RequestQueue requestQueue;

    NotificationBadge mBadge;
    private int mNotificationsCount = 0;
    ContentResolver mContentResolver;
    private SQLiteDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
           /* cart sqlite events */
        mContentResolver = this.getContentResolver();
        CartProductDbHelper dbHelper = new CartProductDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        mBadge=(NotificationBadge)findViewById(R.id.badge);
        new FetchCountTask().execute();
         /* txt for cok satılanlar */
        coksatilanlar_txt=(TextView)findViewById(R.id.coksatilanlar_txt);
        categoryList = new ArrayList<>();
        // for main pic categories
        initViews();
        loadCategoryList();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ara=(Button)findViewById(R.id.ara);
        sepet=(ImageButton)findViewById(R.id.sepet);
        sepet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(mIntent);
            }
        });
        ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(mIntent);
            }
        });

        /* login button */
        loginButton=(LoginButton)findViewById(R.id.flogin_button);
        loginButton.setReadPermissions("email","public_profile");


        /* shared pref initialize */
        sp = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = sp.edit();


        /* Intent for ProductListeActivity */
        Pintent = new Intent(this, ProductListeActivity.class);

         /* right navigation */
        NavigationView rightNavigationView = (NavigationView) findViewById(R.id.nav_right_view);
        menu_right= rightNavigationView.getMenu();
        menu_right.add(1,0,0,"Üye Girişi").setIcon(R.mipmap.user);
        menu_right.add(2,1,0,"Facebook İle Giriş").setIcon(R.mipmap.facebook);
        menu_right.add(3,2,0,"Adres Defterim").setIcon(R.mipmap.map);
        menu_right.add(4,3,0,"Favorilerim").setIcon(R.mipmap.star);
        menu_right.add(5,4,0,"Siparişlerim").setIcon(R.mipmap.purchase_order);
        menu_right.add(6,5,0,"Çıkış").setIcon(R.mipmap.logout).setVisible(false);

        /* set some default pref value  for right menu*/
        menu_right.getItem(0).setVisible(sp.getBoolean("loginItem0",true));
        menu_right.getItem(1).setVisible(sp.getBoolean("loginItem1",true));
        menu_right.getItem(4).setVisible(sp.getBoolean("loginItem4",false));
        menu_right.getItem(5).setVisible(sp.getBoolean("loginItem5",false));
        sp.getBoolean("loginDurum",false);
        if(sp.getBoolean("loginDurum",false)==true){ //If LoginActivity returns true and user is login, this command change right menu
            menu_right.getItem(0).setVisible(false);
            menu_right.getItem(1).setVisible(false);
            menu_right.getItem(4).setVisible(true);
            menu_right.getItem(5).setVisible(true);
        }

         /* left naviga tion */
        leftNavigationView = (NavigationView) findViewById(R.id.nav_left_view);
        menu= leftNavigationView.getMenu();

        nav_cicek = menu.findItem(R.id.nav_cicek);
        nav_amac = menu.findItem(R.id.nav_amac);
        nav_lezzetcicekleri = menu.findItem(R.id.nav_lezzetcicekleri);
        nav_fiyatagore = menu.findItem(R.id.nav_fiyatagore);
        nav_iletisim = menu.findItem(R.id.nav_iletisim);
        nav_paylas = menu.findItem(R.id.nav_paylas);
        nav_oyla = menu.findItem(R.id.nav_oyla);
        nav_group2=menu.findItem(R.id.nav_group2);


        s = new SpannableString("İLETİŞİM");
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#949494")), 0, s.length(), 0);
        nav_group2.setTitle(s);


        leftNavigationEvents();

        rightNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle Right navigation view item clicks here.
                int id = item.getItemId();

                if (id == 0) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);

                } else if (id == 1 &&  sp.getBoolean("loginDurum",false)==false ) {
                    //facebook login
                    loginButton.performClick();

                } else if (id == 2) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Adres defterini kullanabilmeniz için üye girişi yapmanız gerekmektedir.", Toast.LENGTH_SHORT).show();

                } else if (id == 3) {
                    Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                    startActivity(intent);
                }
                else if(id==5 && sp.getBoolean("loginDurum",false)==true){  //facebook logout
                    if(sp.getBoolean("uyelik",false)==false){
                        loginButton.performClick(); // click facebook buton
                    }
                    editor.putBoolean("loginDurum",false);
                    editor.putBoolean("loginItem0",true);
                    editor.putBoolean("loginItem1",true);
                    editor.putBoolean("loginItem4",false);
                    editor.putBoolean("loginItem5",false);
                    editor.commit();
                    menu_right.getItem(0).setVisible(true);
                    menu_right.getItem(1).setVisible(true);
                    menu_right.getItem(4).setVisible(false);
                    menu_right.getItem(5).setVisible(false);
                }
                return true;
            }
        });

        /* Facebook Login Events */
        callbackManager= CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //  Profile profile= Profile.getCurrentProfile();
                String userid=loginResult.getAccessToken().getUserId();
                editor.putBoolean("uyelik",false);
                editor.putString("token",userid);
                editor.putBoolean("loginDurum",true);
                editor.putBoolean("loginItem0",false);
                editor.putBoolean("loginItem1",false);
                editor.putBoolean("loginItem4",true);
                editor.putBoolean("loginItem5",true);
                editor.commit();
                menu_right.getItem(0).setVisible(false);
                menu_right.getItem(1).setVisible(false);
                menu_right.getItem(4).setVisible(true);
                menu_right.getItem(5).setVisible(true);

                GraphRequest graphRequest=GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        new FacebookLogin().displayUserInfo(object);
                    }
                });
                Bundle parameters=new Bundle();
                parameters.putString("fields","first_name,last_name,email,id,gender");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {


            }
        });

    }

    /* for fb login callback result */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void leftNavigationEvents(){
          /*
        /* Left side menu */
        leftNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle Left navigation view item clicks here.
                int id = item.getItemId();
                for(int i=0;i<categoryList.size();i++){
                    if(categoryList.get(i).getIncKey()==id){
                        Log.e("çiçekidddd",String.valueOf(id));
                        editor.putInt("IncKey",id);
                        editor.commit();
                        startActivity(Pintent);
                    }
                }

                menu.removeItem(R.id.nav_amac);
                menu.removeItem(R.id.nav_iletisim);
                menu.removeItem(R.id.nav_cicek);
                menu.removeItem(R.id.nav_fiyatagore);
                menu.removeItem(R.id.nav_oyla);
                menu.removeItem(R.id.nav_paylas);
                menu.removeItem(R.id.nav_lezzetcicekleri);
                menu.removeItem(R.id.nav_group2);

                if (id == R.id.nav_cicek) {
                    for (int i = 0; i < cat_kategori.size(); i++){
                        menu.add(0, cat_kategori.get(i).getIncKey(), 0, cat_kategori.get(i).getAdi());
                    }
                    back();
                }
                else if(id==R.id.nav_amac){
                    for (int i = 0; i < cat_amac.size(); i++){
                        menu.add(0, cat_amac.get(i).getIncKey(), 0, cat_amac.get(i).getAdi());
                    }
                    back();
                }
                else if(id==R.id.nav_lezzetcicekleri){
                    for (int i = 0; i < cat_pasta.size(); i++){
                        menu.add(0, cat_pasta.get(i).getIncKey(), 0, cat_pasta.get(i).getAdi());
                    }
                    back();
                }
                else if(id==R.id.nav_fiyatagore){
                    for (int i = 0; i < cat_fiyat.size(); i++){
                        String updateAd= cat_fiyat.get(i).getAdi();
                        String Updated=updateAd.replace("<i class=\"fa fa-try fa-fw\"></i>","TL");
                        menu.add(0, cat_fiyat.get(i).getIncKey(), 0, Updated);
                    }
                    back();
                }

                else if(id==R.id.nav_iletisim){
                    // iletişim
                    drawer.closeDrawer(GravityCompat.START);
                    menuAdd();
                    Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                    startActivity(intent);
                }
                else if(id==R.id.nav_paylas){
                    //paylaş
                    menuAdd();
                }
                else if(id==R.id.nav_oyla){
                    // oyla
                    menuAdd();
                }
                return true;
            }
        });

    }

    // back
    private void back(){
        left_textView = (TextView) findViewById(R.id.left_header); //header for left navigation
        left_textView.setText("");
        left_textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_back, 0, 0, 0); // set back button image
        left_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.clear();
                left_textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                left_textView.setText("TÜM ÜRÜNLER");
                menuAdd();
            }
        });
    }

    private void menuAdd(){
        menu.add(0,R.id.nav_cicek,0,"Çiçek");
        menu.add(0,R.id.nav_amac,0,"Gönderim Amacı");
        menu.add(0,R.id.nav_lezzetcicekleri,0,"Lezzet Çiçekleri");
        menu.add(0,R.id.nav_fiyatagore,0,"Fiyatlarına Göre Çiçekler");
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#949494")), 0, s.length(), 0);
        menu.add(1, R.id.nav_group2, 0, s);
        menu.add(1, R.id.nav_iletisim, 0, "Bize Ulaşın");
        menu.add(1, R.id.nav_paylas, 0, "Uygulamayı Paylaş");
        menu.add(1, R.id.nav_oyla,0, "Uygulamayı Oyla");
    }

    private void loadCategoryList() {

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray catArray = obj.getJSONArray("Category");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < catArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject catObject = catArray.getJSONObject(i);

                                //creating a category object and giving them the values from json object
                                Category cat = new Category(catObject.getInt("IncKey"), catObject.getInt("UrunAdet"),catObject.getInt("Sira"),
                                        catObject.getString("Adi"),catObject.getString("Turu"),catObject.getString("OzelSaat"),catObject.getString("MobilAnaSayfa"));

                                //adding the category to categorylist
                                categoryList.add(cat);
                                if (cat.Turu.equals("amac")){
                                    cat_amac.add(cat);
                                }
                                else if (cat.Turu.equals("kategori")) {
                                    cat_kategori.add(cat);
                                }
                                else if(cat.Turu.equals("pasta")){
                                    cat_pasta.add(cat);
                                }
                                else if(cat.Turu.equals("fiyat")){
                                    cat_fiyat.add(cat);
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        final Dialog dialog = new Dialog(context); // Context, this, etc.
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.connection_dialog);
                        dialog.setCancelable(false);
                        TextView tvSampleText = (TextView) dialog.findViewById(R.id.custom_dialog_tv_text);
                        tvSampleText.setText("Lütfen internet bağlantınızı kontrol ediniz.");
                        dialog.show();
                        TextView btnDone = (TextView) dialog.findViewById(R.id.dialog_btn_done);
                        btnDone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                            }
                        });
                    }
                });

        //creating a request queue
        requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }



    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {  /*Closes the Appropriate Drawer*/
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_openRight) {
            drawer.openDrawer(GravityCompat.END); /*Opens the Right Drawer*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // events for full recyclerview
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void initViews(){
        pd = new ProgressDialog(this);
        pd.setMessage(""); //fetching..
        pd.setCancelable(false);
        pd.show();

        recycler_pic2 = (RecyclerView) findViewById(R.id.recycler_pic2);
        recycler_pic_purpose = (RecyclerView) findViewById(R.id.recycler_pic_purpose);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        pictureList = new ArrayList<>();
        adapter = new PictureAdapter(this, pictureList);
        adapterPurposePic = new PurposeListPicAdapter(this, pictureList);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        /* for purpose images categories */
        RecyclerView.LayoutManager mLayoutManager3 = new GridLayoutManager(this, 3);
        recycler_pic_purpose.setLayoutManager(mLayoutManager3);
        recycler_pic_purpose.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(5), true));
        recycler_pic_purpose.setItemAnimator(new DefaultItemAnimator());
        recycler_pic_purpose.setAdapter(adapterPurposePic);

        /* for seconds main category pictures */
        RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(this, 1);
        recycler_pic2.setLayoutManager(mLayoutManager2);
        recycler_pic2.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recycler_pic2.setItemAnimator(new DefaultItemAnimator());
        recycler_pic2.setAdapter(adapter);


        /* for best seller */
        recycler_coksatilanlar = (RecyclerView) findViewById(R.id.recycler_coksatilanlar);
        productList = new ArrayList<>();
        adapter_bestseller = new ProductAdapter(this, productList);

        RecyclerView.LayoutManager mLayoutManager_bestseller = new GridLayoutManager(this, 2);
        recycler_coksatilanlar.setLayoutManager(mLayoutManager_bestseller);
        recycler_coksatilanlar.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recycler_coksatilanlar.setItemAnimator(new DefaultItemAnimator());
        recycler_coksatilanlar.setAdapter(adapter_bestseller);

        loadJSON();

    }

    private void loadJSON(){
        try{
            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<CatPictureResponse> call = apiService.getPictures();
            call.enqueue(new Callback<CatPictureResponse>() {
                @Override
                public void onResponse(Call<CatPictureResponse> call, retrofit2.Response<CatPictureResponse> response) {
                    List<Picture> items = response.body().getPictures();
                    recyclerView.setAdapter(new PictureAdapter(getApplicationContext(), items));
                    recyclerView.smoothScrollToPosition(0);
                    pd.hide();
                }
                @Override
                public void onFailure(Call<CatPictureResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    pd.hide();
                }
            });
            Call<ProductsResponse> call_bestseller = apiService.getProducts4();
            call_bestseller.enqueue(new Callback<ProductsResponse>() {
                @Override
                public void onResponse(Call<ProductsResponse> call, retrofit2.Response<ProductsResponse> response) {
                    List<Product> bests_items = response.body().getProducts();
                    recycler_coksatilanlar.setAdapter(new ProductAdapter(getApplicationContext(), bests_items));
                    recycler_coksatilanlar.smoothScrollToPosition(0);
                    coksatilanlar_txt.setText("ÇOK SATILANLAR");

                }

                @Override
                public void onFailure(Call<ProductsResponse> call_bestseller, Throwable t) {
                    Log.d("Error", t.getMessage());
                    pd.hide();
                }
            });

            Call<CatPictureResponse> call_pic2 = apiService.getPictures2();
            call_pic2.enqueue(new Callback<CatPictureResponse>() {
                @Override
                public void onResponse(Call<CatPictureResponse> call, retrofit2.Response<CatPictureResponse> response) {
                    List<Picture> items_pic2 = response.body().getPictures();
                    recycler_pic2.setAdapter(new PictureAdapter(getApplicationContext(), items_pic2));
                    recycler_pic2.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<CatPictureResponse> call_pic2, Throwable t) {
                    Log.d("Error", t.getMessage());
                    pd.hide();
                }
            });

            Call<CatPictureResponse> call_purpose_pic = apiService.getPicturesPurpose();
            call_purpose_pic.enqueue(new Callback<CatPictureResponse>() {
                @Override
                public void onResponse(Call<CatPictureResponse> call, retrofit2.Response<CatPictureResponse> response) {
                    List<Picture> items = response.body().getPictures();
                    recycler_pic_purpose.setAdapter(new PurposeListPicAdapter(getApplicationContext(), items));
                    recycler_pic_purpose.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<CatPictureResponse> call_pic2, Throwable t) {
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