package com.example.berna.cicekse2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Berna on 20.11.2017.
 */

public class Time {
    @SerializedName("IncKey")
    @Expose
    private int IncKey;
    @SerializedName("BaslangicTarihi")
    @Expose
    private String BaslangicTarihi;
    @SerializedName("BitisTarihi")
    @Expose
    private String BitisTarihi;
    @SerializedName("BaslangicSaati")
    @Expose
    private String BaslangicSaati;
    @SerializedName("BitisSaati")
    @Expose
    private String BitisSaati;
    @SerializedName("Periyot")
    @Expose
    private int Periyot;
    @SerializedName("PeriyotTipi")
    @Expose
    private String PeriyotTipi;

    public Time(int incKey, String baslangicTarihi, String bitisTarihi, String baslangicSaati, String bitisSaati, int periyot, String periyotTipi) {
        IncKey = incKey;
        BaslangicTarihi = baslangicTarihi;
        BitisTarihi = bitisTarihi;
        BaslangicSaati = baslangicSaati;
        BitisSaati = bitisSaati;
        Periyot = periyot;
        PeriyotTipi = periyotTipi;
    }

    public int getIncKey() {
        return IncKey;
    }

    public void setIncKey(int incKey) {
        IncKey = incKey;
    }

    public String getBaslangicTarihi() {
        return BaslangicTarihi;
    }

    public void setBaslangicTarihi(String baslangicTarihi) {
        BaslangicTarihi = baslangicTarihi;
    }

    public String getBitisTarihi() {
        return BitisTarihi;
    }

    public void setBitisTarihi(String bitisTarihi) {
        BitisTarihi = bitisTarihi;
    }

    public String getBaslangicSaati() {
        return BaslangicSaati;
    }

    public void setBaslangicSaati(String baslangicSaati) {
        BaslangicSaati = baslangicSaati;
    }

    public String getBitisSaati() {
        return BitisSaati;
    }

    public void setBitisSaati(String bitisSaati) {
        BitisSaati = bitisSaati;
    }

    public int getPeriyot() {
        return Periyot;
    }

    public void setPeriyot(int periyot) {
        Periyot = periyot;
    }

    public String getPeriyotTipi() {
        return PeriyotTipi;
    }

    public void setPeriyotTipi(String periyotTipi) {
        PeriyotTipi = periyotTipi;
    }
}
