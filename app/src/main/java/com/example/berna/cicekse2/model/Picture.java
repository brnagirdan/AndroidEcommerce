package com.example.berna.cicekse2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Berna on 26.09.2017.
 */

public class Picture {
    @SerializedName("IncKey")
    @Expose
    private int IncKey;
    @SerializedName("MobilAppAnaResim")
    @Expose
    private String Resim;

    public int getIncKey() {
        return IncKey;
    }

    public void setIncKey(int incKey) {
        IncKey = incKey;
    }

    public String getResim() {
        return Resim;
    }

    public void setResim(String resim) {
        Resim = resim;
    }

    public Picture(int incKey,String resim) {
        IncKey = incKey;
        Resim=resim;
    }
}
