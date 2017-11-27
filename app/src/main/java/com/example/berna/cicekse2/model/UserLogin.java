package com.example.berna.cicekse2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Berna on 28.10.2017.
 */

public class UserLogin {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("Password")
    @Expose
    private String Password;

    public UserLogin(String email, String password) {
        this.email = email;
        Password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
