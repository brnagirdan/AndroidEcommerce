package com.example.berna.cicekse2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Berna on 27.11.2017.
 */

public class Extra {
    @SerializedName("IncKey")
    @Expose
    private int IncKey;
    @SerializedName("EkstraUrunId")
    @Expose
    private int EkstraUrunId;
    @SerializedName("BirimFiyat")
    @Expose
    private int BirimFiyat;
    @SerializedName("Kdv")
    @Expose
    private int Kdv;
    @SerializedName("UrunAdi")
    @Expose
    private String UrunAdi;
    @SerializedName("Resim")
    @Expose
    private String Resim;

    public Extra(int incKey, int ekstraUrunId, int birimFiyat, int kdv, String urunAdi, String resim) {
        IncKey = incKey;
        EkstraUrunId = ekstraUrunId;
        BirimFiyat = birimFiyat;
        Kdv = kdv;
        UrunAdi = urunAdi;
        Resim = resim;
    }

    public int getIncKey() {
        return IncKey;
    }

    public void setIncKey(int incKey) {
        IncKey = incKey;
    }

    public int getEkstraUrunId() {
        return EkstraUrunId;
    }

    public void setEkstraUrunId(int ekstraUrunId) {
        EkstraUrunId = ekstraUrunId;
    }

    public int getBirimFiyat() {
        return BirimFiyat;
    }

    public void setBirimFiyat(int birimFiyat) {
        BirimFiyat = birimFiyat;
    }

    public int getKdv() {
        return Kdv;
    }

    public void setKdv(int kdv) {
        Kdv = kdv;
    }

    public String getUrunAdi() {
        return UrunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        UrunAdi = urunAdi;
    }

    public String getResim() {
        return Resim;
    }

    public void setResim(String resim) {
        Resim = resim;
    }
}
