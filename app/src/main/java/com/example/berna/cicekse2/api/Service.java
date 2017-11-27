package com.example.berna.cicekse2.api;

import com.example.berna.cicekse2.DataResponse.CurrentTimeResponse;
import com.example.berna.cicekse2.DataResponse.ExtraResponse;
import com.example.berna.cicekse2.DataResponse.ProductsResponse;
import com.example.berna.cicekse2.DataResponse.CatPictureResponse;
import com.example.berna.cicekse2.DataResponse.ProvinceResponse;
import com.example.berna.cicekse2.DataResponse.TimeResponse;
import com.example.berna.cicekse2.model.UserInfo;
import com.example.berna.cicekse2.model.UserLogin;
import com.example.berna.cicekse2.DataResponse.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Berna on 31.08.2017.
 */

public interface Service {
    @GET("/api/products/{MobilKategori}")
    Call<ProductsResponse> getProducts(@Path("MobilKategori") int IncKey);

    @GET("/api/products")
    Call<ProductsResponse> getAllProducts();

    @GET("/api/top4product")
    Call<ProductsResponse> getProducts4();

    @GET("/api/pictures/Get01")
    Call<CatPictureResponse> getPictures();

    @GET("/api/pictures/Get02")
    Call<CatPictureResponse> getPictures2();

    @GET("/api/pictures/Get03")
    Call<CatPictureResponse> getPicturesPurpose();

    @GET("/api/city/{Parent}")
    Call<ProvinceResponse> getProvinces(@Path("Parent") String Parent);

    @GET("/api/time/Time")
    Call<TimeResponse> getTimes();

    @GET("/api/time/CurrentTime")
    Call<CurrentTimeResponse> getCurrent();

    @GET("/api/extras")
    Call<ExtraResponse> getExtras();

    @POST("/api/Uyelik/Register")
    Call<String>postUyelik(@Body UserInfo userInfo);

    @POST("/api/Uyelik/Login")
    Call<LoginResponse>postLogin(@Body UserLogin userLogin);
}
