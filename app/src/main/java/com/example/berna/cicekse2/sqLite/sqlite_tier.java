package com.example.berna.cicekse2.sqLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.berna.cicekse2.cartData.CartProductContract;

/**
 * Created by Berna on 15.10.2017.
 */
// Bu katmanla SQLiteDatabase'e erişiyoruz.
public class sqlite_tier extends SQLiteOpenHelper {

    public sqlite_tier(Context c){
      super(c,"cicek",null,1);  // database name
    }
    public void onCreate(SQLiteDatabase db) { // create database when install app to linux

        String SQL_CREATE_CART_PRODUCT_TABLE = "CREATE TABLE fav_cicek ( id INTEGER PRIMARY KEY AUTOINCREMENT" + ", IncKey INTEGER NOT NULL" +
                ", Adi TEXT NOT NULL" + ", Kdv INTEGER NOT NULL" + ", ResimKucuk TEXT NOT NULL" + ", CicekPasta INTEGER NOT NULL" +
                ", SatisFiyat REAL NOT NULL" + ", ResimBuyuk TEXT NOT NULL" +", UrunKodu TEXT NOT NULL" +
                ", Icerik TEXT NOT NULL" + ", SiparisSayi INTEGER NOT NULL" + ", DefaultKategori INTEGER NOT NULL" +
                ", CicekFiloFiyat REAL NOT NULL)";

        db.execSQL(SQL_CREATE_CART_PRODUCT_TABLE);
        Log.d("", "Cicek Database Created Successfully" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int eski, int yeni) {
     db.execSQL("drop table if exists table fav_cicek");  //delete fav_cicek table
    }
}
