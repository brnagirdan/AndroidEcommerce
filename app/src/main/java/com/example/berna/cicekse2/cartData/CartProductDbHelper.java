package com.example.berna.cicekse2.cartData;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Berna on 10.10.2017.
 */

public class CartProductDbHelper extends SQLiteOpenHelper {
    private static final String TAG = CartProductDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;
    Context context;
    SQLiteDatabase db;
    ContentResolver mContentResolver;

    //Used to read data from res/ and assets/
    private Resources mResources;

    public CartProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mResources = context.getResources();
        mContentResolver = context.getContentResolver();

        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_CART_PRODUCT_TABLE = "CREATE TABLE " + CartProductContract.ProductEntry.CART_TABLE + " (" +
                CartProductContract.ProductEntry._CARTID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CartProductContract.ProductEntry.COLUMN_CART_NAME + " TEXT NOT NULL, " +
                CartProductContract.ProductEntry.COLUMN_CART_KDV + " INTEGER NOT NULL, " +
                CartProductContract.ProductEntry.COLUMN_CART_IMAGE + " TEXT NOT NULL, " +
                CartProductContract.ProductEntry.COLUMN_CART_QUANTITY + " INTEGER NOT NULL, " +
                CartProductContract.ProductEntry.COLUMN_CART_PRICE + " REAL NOT NULL " + " );";

        db.execSQL(SQL_CREATE_CART_PRODUCT_TABLE);
        Log.d(TAG, "Database Created Successfully" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: Handle database version upgrades
        db.execSQL("DROP TABLE IF EXISTS " + CartProductContract.ProductEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CartProductContract.ProductEntry.CART_TABLE);
        onCreate(db);
    }

}
