package com.example.berna.cicekse2.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Berna on 16.11.2017.
 */

public class Login {
    @SerializedName("sonuc")
    boolean successString;
    @SerializedName("token")
    String tokenString;

    public Login (boolean successString, String tokenString) {
        this.successString = successString;
        this.tokenString = tokenString;
    }

    public boolean isSuccessString() {
        return successString;
    }

    public void setSuccessString(boolean successString) {
        this.successString = successString;
    }

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }
}

