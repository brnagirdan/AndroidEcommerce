package com.example.berna.cicekse2.DataResponse;

import com.example.berna.cicekse2.model.Province;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Berna on 17.11.2017.
 */

public class ProvinceResponse {
    @SerializedName("City")
    @Expose
    private List<Province> provinces;

    public List<Province> getProvinces(){
        return provinces;
    }
    public void setProvinces(List<Province>provinces){
        this.provinces = provinces;
    }
}
