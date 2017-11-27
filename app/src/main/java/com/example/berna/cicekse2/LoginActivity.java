package com.example.berna.cicekse2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.berna.cicekse2.api.Client;
import com.example.berna.cicekse2.api.Service;
import com.example.berna.cicekse2.model.Login;
import com.example.berna.cicekse2.model.UserLogin;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.example.berna.cicekse2.DataResponse.LoginResponse;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Intent mIntent;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    TextView pagename;
    ImageButton geri;
    Button facebook_login,uye_ol,login;
    LoginButton loginButton;
    CallbackManager callbackManager;
    EditText eposta,password;
    TextView warning_mail,warning_sifre1;
    String emailPattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Context context = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        warning_mail=(TextView)findViewById(R.id.warning);
        warning_mail.setVisibility(View.GONE);

        warning_sifre1=(TextView)findViewById(R.id.warning_sifre1);
        warning_sifre1.setVisibility(View.GONE);

        eposta=(EditText)findViewById(R.id.eposta);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.giris);

        pagename=(TextView)findViewById(R.id.pagename);
        pagename.setText("Üye Girişi");
        geri = (ImageButton) findViewById(R.id.geri);
        facebook_login=(Button)findViewById(R.id.facebook_login);
        uye_ol=(Button)findViewById(R.id.uye_ol);

          /* shared pref initialize */
        sp = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = sp.edit();

          /* login button */
        loginButton=(LoginButton)findViewById(R.id.flogin_button);
        loginButton.setReadPermissions("email","public_profile");


        geri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        uye_ol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(mIntent);
            }
        });

        facebook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.performClick();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!eposta.getText().toString().matches(emailPattern)){ warning_mail.setVisibility(View.VISIBLE);}
                else{warning_mail.setVisibility(View.GONE);}
                if(password.getText().toString().trim().length()>=3) {
                    warning_sifre1.setVisibility(View.GONE);
                    if (password.getText().toString().trim().length() >= 3 && eposta.getText().toString().matches(emailPattern)) {
                        warning_mail.setVisibility(View.GONE);
                        warning_sifre1.setVisibility(View.GONE);
                        Client Client = new Client();
                        Service apiService = Client.getClient().create(Service.class);
                        UserLogin userlogin = new UserLogin(eposta.getText().toString(), password.getText().toString());
                        Call<LoginResponse> call = apiService.postLogin(userlogin);
                        call.enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                    List<Login> res = response.body().getLoginvalues();
                                        Log.e("token", String.valueOf(res.get(0).getTokenString()));
                                       if(res.get(0).getTokenString().equals("")){
                                           openDialogHata();
                                           Log.e("err", "err");
                                       }
                                       else{
                                           editor.putString("token",res.get(0).getTokenString());
                                           editor.putBoolean("loginDurum", true);
                                           editor.putBoolean("loginItem0", false);
                                           editor.putBoolean("loginItem1", false);
                                           editor.putBoolean("loginItem4", true);
                                           editor.putBoolean("loginItem5", true);
                                           editor.commit();
                                           Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
                                           startActivity(mIntent);
                                         //  Log.e("completed", "completed");
                                       }
                                }
                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {
                                Log.e("err", "fail");
                            }
                        });
                    }
                }
                else{
                     warning_sifre1.setVisibility(View.VISIBLE);
                }
            }
        });

         /* Facebook Login Events */
        callbackManager= CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //  Profile profile= Profile.getCurrentProfile();
                String userid=loginResult.getAccessToken().getUserId();
                editor.putString("token",userid);
                editor.putBoolean("loginDurum",true);
                editor.putBoolean("loginItem0",false);
                editor.putBoolean("loginItem1",false);
                editor.putBoolean("loginItem4",true);
                editor.putBoolean("loginItem5",true);
                editor.commit();
                GraphRequest graphRequest=GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        new FacebookLogin().displayUserInfo(object);
                    }
                });
                Bundle parameters=new Bundle();
                parameters.putString("fields","first_name,last_name,email,id,gender");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {


            }
        });

    }

    /* for fb login callback result */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        mIntent = new Intent(this, MainActivity.class);
        startActivity(mIntent);
    }


    public void openDialogHata() {
        final Dialog dialog = new Dialog(context); // Context, this, etc.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.registerhata_dialog);
        TextView msgtitle = (TextView) dialog.findViewById(R.id.msgtitle);
        msgtitle.setText("Hata");
        TextView tvSampleText = (TextView) dialog.findViewById(R.id.custom_dialog_tv_text);
        tvSampleText.setText("Mail adresi veya şifre hatalı.Lütfen bilgilerinizi kontrol ediniz.");
        dialog.show();
        Button btnDone = (Button) dialog.findViewById(R.id.dialog_btn_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


}
