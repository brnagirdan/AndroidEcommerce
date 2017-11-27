package com.example.berna.cicekse2;

import android.util.Log;

import com.example.berna.cicekse2.Helper.IPAddress;
import com.example.berna.cicekse2.api.Client;
import com.example.berna.cicekse2.api.Service;
import com.example.berna.cicekse2.model.UserInfo;
import com.facebook.Profile;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Berna on 29.09.2017.
 */

public class FacebookLogin {
    public void displayUserInfo(JSONObject object)
    {
        Profile profile= Profile.getCurrentProfile();
        String first_name,last_name,email,id,photoUrl,profileUrl,hesapturu,gender,displayname,ip;
        first_name="";
        last_name="";
        email="";
        id="";
        photoUrl="";
        profileUrl="";
        gender="";
        displayname="";
        ip="";
        hesapturu="";
        try {
            ip=new IPAddress().getLocalIpAddress();
            first_name=object.getString("first_name");
            last_name=object.getString("last_name");
            email=object.getString("email");
            id=object.getString("id");
            gender=object.getString("gender");;
            photoUrl=profile.getProfilePictureUri(200,200).toString();
            profileUrl=profile.getLinkUri().toString();
            displayname=first_name+" "+last_name;
            hesapturu="Facebook";

            Client Client = new Client();
            Service apiService =
                    Client.getClient().create(Service.class);
            UserInfo userInfo = new UserInfo(id,profileUrl,photoUrl,displayname,first_name,last_name,gender,"","0000-00-00",email,email,hesapturu,"",ip,"");
            Call<String> call = apiService.postUyelik(userInfo);
            Log.e("callll",call.toString());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("kayittt","completed");
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("kayittt","error");
                }
            });
           } catch (JSONException e) {
            e.printStackTrace();
           }

    }
}