package com.example.berna.cicekse2.sqLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.berna.cicekse2.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Berna on 15.10.2017.
 */

public class data_access_tier {
    SQLiteDatabase db;
    sqlite_tier bdb; // kendi yazdığım sqlite

    public data_access_tier(Context c){
        bdb=new sqlite_tier(c);  // initialize ediyorum.
    }

    public void open(){
     db=bdb.getWritableDatabase(); // kendi yazdığım veri tabanını writeable olarak açıyorum ve sqlite'a eşliyorum..
    }

    public void close(){
     bdb.close();
    }

    public void insert(Product p){
        ContentValues val= new ContentValues();
        val.put("IncKey",p.getIncKey());
        val.put("Adi",p.getAdi());
        val.put("ResimBuyuk",p.getResimBuyuk());
        val.put("Icerik",p.getIcerik());
        val.put("CicekPasta",p.getCicekPasta());
        val.put("UrunKodu",p.getKdv());
        val.put("SatisFiyat",p.getSatisFiyat());
        val.put("Kdv",p.getKdv());
        val.put("ResimKucuk",p.getResimKucuk());
        val.put("SiparisSayi",p.getSiparisSayi());
        val.put("DefaultKategori",p.getDefaultKategori());
        val.put("CicekFiloFiyat",p.getCicekFiloFiyat());

        db.insert("fav_cicek",null,val);
    }

    public List<Product> list(){
      String columns[]={"Adi","ResimKucuk","SatisFiyat","Kdv","ResimBuyuk","UrunKodu","Icerik","SiparisSayi","IncKey","CicekFiloFiyat","CicekPasta","DefaultKategori"}; // getirmek istediğim kolonlar
      List<Product>pro_list=new ArrayList<Product>();
      Cursor c= db.query("fav_cicek",columns,null,null,null,null,null); // cursoru iterative olarak kullanıyorum.
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    String Adi=c.getString(0);
                    String ResimKucuk=c.getString(1);
                    double SatisFiyat=c.getDouble(2);
                    int Kdv=c.getInt(3);
                    String ResimBuyuk=c.getString(4);
                    String UrunKodu=c.getString(5);
                    String Icerik=c.getString(6);
                    int SiparisSayi=c.getInt(7);
                    int IncKey=c.getInt(8);
                    double CicekFiloFiyat=c.getDouble(9);
                    int CicekPasta=c.getInt(10);
                    int DefaultKategori=c.getInt(11);

                    Product product=new Product(IncKey,CicekPasta,UrunKodu,SatisFiyat,Kdv,ResimKucuk,SiparisSayi,DefaultKategori,CicekFiloFiyat,Adi,Icerik,ResimBuyuk);
                    pro_list.add(product);

                }while (c.moveToNext());
            }
        }

        c.close();
        return pro_list;
    }

    public void delete(Product product){
        int IncKey =product.getIncKey();
        db.delete("fav_cicek","IncKey="+IncKey,null);
    }


}
