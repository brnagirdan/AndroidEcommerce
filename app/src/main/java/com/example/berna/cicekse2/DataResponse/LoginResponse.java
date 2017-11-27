package com.example.berna.cicekse2.DataResponse;

import com.example.berna.cicekse2.model.Login;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Berna on 16.11.2017.
 */

public class LoginResponse {
    @SerializedName("Response")
    @Expose
    private List<Login> loginvalues;

    public List<Login> getLoginvalues() {
        return loginvalues;
    }
    public void setLoginvalues(List<Login> loginvalues) {
        this.loginvalues = loginvalues;
    }
}
