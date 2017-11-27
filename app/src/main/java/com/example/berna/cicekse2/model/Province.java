package com.example.berna.cicekse2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Berna on 17.11.2017.
 */

public class Province {
    @SerializedName("IncKey")
    @Expose
    private int IncKey;
    @SerializedName("PlakaKodu")
    @Expose
    private int PlakaKodu;
    @SerializedName("Sira")
    @Expose
    private int Sira;
    @SerializedName("Yayinla")
    @Expose
    private String Yayinla;
    @SerializedName("SehirAdi")
    @Expose
    private String SehirAdi;

    public Province(int incKey, int plakaKodu, int sira, String yayinla, String sehirAdi) {
        IncKey = incKey;
        PlakaKodu = plakaKodu;
        Sira = sira;
        Yayinla = yayinla;
        SehirAdi = sehirAdi;
    }

    public int getIncKey() {
        return IncKey;
    }

    public void setIncKey(int incKey) {
        IncKey = incKey;
    }

    public int getPlakaKodu() {
        return PlakaKodu;
    }

    public void setPlakaKodu(int plakaKodu) {
        PlakaKodu = plakaKodu;
    }

    public int getSira() {
        return Sira;
    }

    public void setSira(int sira) {
        Sira = sira;
    }

    public String getYayinla() {
        return Yayinla;
    }

    public void setYayinla(String yayinla) {
        Yayinla = yayinla;
    }

    public String getSehirAdi() {
        return SehirAdi;
    }

    public void setSehirAdi(String sehirAdi) {
        SehirAdi = sehirAdi;
    }
}
