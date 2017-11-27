package com.example.berna.cicekse2.model;

/**
 * Created by Berna on 18.09.2017.
 */

public class Category {
    public int IncKey,UrunAdet,Sira;
    public String Adi,Turu,OzelSaat,MobilAnaSayfa;


    public Category(int IncKey,int UrunAdet,int Sira, String Adi, String Turu,String OzelSaat,String MobilAnaSayfa) {
        this.IncKey=IncKey;
        this.UrunAdet=UrunAdet;
        this.Sira=Sira;
        this.Adi=Adi;
        this.Turu=Turu;
        this.OzelSaat=OzelSaat;
        this.MobilAnaSayfa=MobilAnaSayfa;
    }

    public int getIncKey() {
        return IncKey;
    }

    public void setIncKey(int incKey) {
        IncKey = incKey;
    }

    public int getUrunAdet() {
        return UrunAdet;
    }

    public void setUrunAdet(int urunAdet) {
        UrunAdet = urunAdet;
    }

    public int getSira() {
        return Sira;
    }

    public void setSira(int sira) {
        Sira = sira;
    }

    public String getAdi() {
        return Adi;
    }

    public void setAdi(String adi) {
        Adi = adi;
    }

    public String getTuru() {
        return Turu;
    }

    public void setTuru(String turu) {
        Turu = turu;
    }

    public String getOzelSaat() {
        return OzelSaat;
    }

    public void setOzelSaat(String ozelSaat) {
        OzelSaat = ozelSaat;
    }

    public String getMobilAnaSayfa() {
        return MobilAnaSayfa;
    }

    public void setMobilAnaSayfa(String mobilAnaSayfa) {
        MobilAnaSayfa = mobilAnaSayfa;
    }
}
