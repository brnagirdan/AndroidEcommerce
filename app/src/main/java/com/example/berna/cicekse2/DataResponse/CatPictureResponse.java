package com.example.berna.cicekse2.DataResponse;

import com.example.berna.cicekse2.model.Picture;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Berna on 26.09.2017.
 */

public class CatPictureResponse {

    @SerializedName("Category")
    @Expose
    private List<Picture> pictures;

    public List<Picture> getPictures(){
        return pictures;
    }
    public void setPictures(List<Picture>products){
        this.pictures = pictures;
    }
}
