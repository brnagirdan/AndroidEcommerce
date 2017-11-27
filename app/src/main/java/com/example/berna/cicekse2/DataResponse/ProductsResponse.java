package com.example.berna.cicekse2.DataResponse;

import com.example.berna.cicekse2.model.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Berna on 31.08.2017.
 */

public class ProductsResponse {
    @SerializedName("Product")
    @Expose
    private List<Product> products;

    public List<Product> getProducts(){
        return products;
    }
    public void setProducts(List<Product>products){
        this.products = products;
    }


}
