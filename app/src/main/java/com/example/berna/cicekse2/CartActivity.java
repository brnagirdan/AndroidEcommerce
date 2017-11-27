package com.example.berna.cicekse2;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.berna.cicekse2.Adapter.CartAdapter;
import com.example.berna.cicekse2.Helper.SimpleDividerItemDecoration;
import com.example.berna.cicekse2.cartData.CartProductContract;
import com.example.berna.cicekse2.cartData.CartProductDbHelper;

import static android.text.TextUtils.split;
import static com.example.berna.cicekse2.cartData.CartProductContract.ProductEntry.CART_TABLE;

public class CartActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>  {
    Intent intent;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private SQLiteDatabase mDb;
    ContentResolver mContentResolver;
    TextView pagename, fiyatplus_txt;
    ImageButton geri;
    Button basla;
    Double productKDV=0.0;
    int cart_count=0;
    Context context;
    Button sepet_btn;
    /**
     * Identifier for the employee data loader
     */
    private static final int CART_LOADER = 0;

    /**
     * Adapter for the ListView
     */
    CartAdapter cartAdapter;
    RecyclerView mRecyclerView;
    Double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
          /* cart sqlite events */
        mContentResolver = this.getContentResolver();
        CartProductDbHelper dbHelper = new CartProductDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

           /* shared pref initialize */
        sp = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = sp.edit();


        sepet_btn=(Button)findViewById(R.id.sepet_btn);
        geri = (ImageButton) findViewById(R.id.geri);

        pagename = (TextView) findViewById(R.id.pagename);
        fiyatplus_txt = (TextView) findViewById(R.id.fiyatplus_txt);
        pagename.setText("Sepetim");


        geri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        sepet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, ProvinceActivity.class);
                startActivity(intent);
            }
        });


        // Set the RecyclerView to its corresponding view
        mRecyclerView = (RecyclerView) findViewById(R.id.cart_recycler);

        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


            // Initialize the adapter and attach it to the RecyclerView
            cartAdapter = new CartAdapter(this);
            mRecyclerView.setAdapter(cartAdapter);
            mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        /* touch delete process */

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete

                // COMPLETED (1) Construct the URI for the item to delete
                //[Hint] Use getTag (from the adaptResolverer code) to get the id of the swiped item
                // Retrieve the id of the task to delete
                int id = (int) viewHolder.itemView.getTag();

                // Build appropriate uri with String row id appended
                String stringId = Integer.toString(id);
                Uri uri = CartProductContract.ProductEntry.CONTENT_URI_CART;
                uri = uri.buildUpon().appendPath(stringId).build();

                // COMPLETED (2) Delete a single row of data using a ContentResolver
                getContentResolver().delete(uri, null, null);
                // COMPLETED (3) Restart the loader to re-query for all tasks after a deletion
                getLoaderManager().restartLoader(CART_LOADER, null, CartActivity.this);

            }
        }).attachToRecyclerView(mRecyclerView);

        getLoaderManager().initLoader(CART_LOADER, null, this);

        new FetchCountTask().execute();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                CartProductContract.ProductEntry._CARTID,
                CartProductContract.ProductEntry.COLUMN_CART_NAME,
                CartProductContract.ProductEntry.COLUMN_CART_IMAGE,
                CartProductContract.ProductEntry.COLUMN_CART_QUANTITY,
                CartProductContract.ProductEntry.COLUMN_CART_PRICE,
                CartProductContract.ProductEntry.COLUMN_CART_KDV,
        };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                CartProductContract.ProductEntry.CONTENT_URI_CART,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.e("countttt222",String.valueOf(cart_count));
        if(cart_count!=0){
            cartAdapter.swapCursor(cursor);
            calculateTotal(cursor);
        }
        else {
            setContentView(R.layout.cart_no_exist);
            pagename = (TextView) findViewById(R.id.pagename);
            pagename.setText("Sepetim");
            geri = (ImageButton) findViewById(R.id.geri);
            basla=(Button)findViewById(R.id.basla);
            basla.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    intent = new Intent(CartActivity.this, ProductListeActivity.class);
                    editor.putInt("IncKey",0);
                    editor.commit();
                    startActivity(intent);
                }
            });

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
          }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(CART_LOADER, null, CartActivity.this);
    }

    public double calculateTotal(Cursor cursor) {
        totalPrice = 0.00;
        for (int i = 0; i < cursor.getCount(); i++) {
            int price = cursor.getColumnIndex(CartProductContract.ProductEntry.COLUMN_CART_PRICE);
            int kdv = cursor.getColumnIndex(CartProductContract.ProductEntry.COLUMN_CART_KDV);

            cursor.moveToPosition(i);
            Double productPrice = cursor.getDouble(price);
            productKDV = cursor.getDouble(kdv);
            totalPrice += productPrice;
        }

        TextView totalCost = (TextView) findViewById(R.id.fiyat_txt);
        double plusKDV = ((totalPrice * productKDV) / 100 + totalPrice);

        Log.e("pluskdv", String.valueOf(plusKDV));

        // String convertPrice = NumberFormat.getCurrencyInstance().format(plusKDV);
        String convertPrice = String.valueOf((int) plusKDV);
        String extra = String.valueOf(plusKDV).replace(convertPrice, "");


        if (extra.length() == 2) {
            extra = extra + "0";
            Log.e("extra1", extra + "0");
        } else if (extra.length() > 3) {
            extra = extra.substring(0, 3);
            Log.e("extra2", extra);
        }
        fiyatplus_txt.setText(extra.replace(".", ",") + " TL");
        totalCost.setText(convertPrice);
        return plusKDV;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cartAdapter.swapCursor(null);
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
            cart_count=count;
            Log.e("countttt333",String.valueOf(cart_count));
            Log.e("countttt",String.valueOf(count));
        }

    }



}