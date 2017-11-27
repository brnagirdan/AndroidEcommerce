package com.example.berna.cicekse2.model;

/**
 * Created by Berna on 1.09.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("IncKey")
    @Expose
    private int IncKey;
    @SerializedName("Adi")
    @Expose
    private String Adi;
    @SerializedName("ResimBuyuk")
    @Expose
    private String ResimBuyuk;
    @SerializedName("Icerik")
    @Expose
    private String Icerik;
    @SerializedName("CicekPasta")
    @Expose
    private int CicekPasta;
    @SerializedName("UrunKodu")
    @Expose
    private String UrunKodu;
    @SerializedName("SatisFiyat")
    @Expose
    private double SatisFiyat;
    @SerializedName("Kdv")
    @Expose
    private int Kdv;
    @SerializedName("ResimKucuk")
    @Expose
    private String ResimKucuk;
    @SerializedName("SiparisSayi")
    @Expose
    private int SiparisSayi;
    @SerializedName("DefaultKategori")
    @Expose
    private int DefaultKategori;
    @SerializedName("CicekFiloFiyat")
    @Expose
    private double CicekFiloFiyat;


    public Product(Product product) {
    }

    public Product(int incKey,int cicekPasta,String urunKodu,double satisFiyat,int kdv,String resimKucuk,int siparisSayi,int defaultKategori,double cicekFiloFiyat,String adi,String icerik,String resimBuyuk) {
        IncKey=incKey;
        CicekPasta=cicekPasta;
        UrunKodu=urunKodu;
        SatisFiyat=satisFiyat;
        Kdv=kdv;
        ResimKucuk=resimKucuk;
        SiparisSayi=siparisSayi;
        DefaultKategori=defaultKategori;
        CicekFiloFiyat=cicekFiloFiyat;
        Adi=adi;
        ResimBuyuk=resimBuyuk;
        Icerik=icerik;
    }

    public int getIncKey() {
        return IncKey;
    }

    public void setIncKey(int incKey) {
        IncKey = incKey;
    }

    public int getCicekPasta() {
        return CicekPasta;
    }

    public void setCicekPasta(int cicekPasta) {
        CicekPasta = cicekPasta;
    }

    public String getUrunKodu() {
        return UrunKodu;
    }

    public void setUrunKodu(String urunKodu) {
        UrunKodu = urunKodu;
    }

    public double getSatisFiyat() {
        return SatisFiyat;
    }

    public void setSatisFiyat(double satisFiyat) {
        SatisFiyat = satisFiyat;
    }

    public int getKdv() {
        return Kdv;
    }

    public void setKdv(int kdv) {
        Kdv = kdv;
    }

    public String getResimKucuk() {
        return ResimKucuk;
    }

    public void setResimKucuk(String resimKucuk) {
        ResimKucuk = resimKucuk;
    }

    public int getSiparisSayi() {
        return SiparisSayi;
    }

    public void setSiparisSayi(int siparisSayi) {
        SiparisSayi = siparisSayi;
    }

    public int getDefaultKategori() {
        return DefaultKategori;
    }

    public void setDefaultKategori(int defaultKategori) {
        DefaultKategori = defaultKategori;
    }

    public double getCicekFiloFiyat() {
        return CicekFiloFiyat;
    }

    public void setCicekFiloFiyat(double cicekFiloFiyat) {
        CicekFiloFiyat = cicekFiloFiyat;
    }

    public String getAdi() {
        return Adi;
    }

    public void setAdi(String adi) {
        Adi = adi;
    }

    public String getResimBuyuk() {
        return ResimBuyuk;
    }

    public void setResimBuyuk(String resimBuyuk) {
        ResimBuyuk = resimBuyuk;
    }

    public String getIcerik() {
        return Icerik;
    }

    public void setIcerik(String icerik) {
        Icerik = icerik;
    }
}