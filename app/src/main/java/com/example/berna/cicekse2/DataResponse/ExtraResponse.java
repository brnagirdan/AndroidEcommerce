package com.example.berna.cicekse2.DataResponse;

import com.example.berna.cicekse2.model.Extra;
import com.example.berna.cicekse2.model.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Berna on 27.11.2017.
 */

public class ExtraResponse {
    @SerializedName("Extras")
    @Expose
    private List<Extra> extras;

    public List<Extra> getExtras(){
        return extras;
    }
    public void setExtras(List<Product>products){
        this.extras = extras;
    }
}
