package com.example.berna.cicekse2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.berna.cicekse2.Helper.IPAddress;
import com.example.berna.cicekse2.api.Client;
import com.example.berna.cicekse2.api.Service;
import com.example.berna.cicekse2.model.UserInfo;

import org.json.JSONException;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    ImageButton geri;
    TextView pagename;
    EditText name_et,surname_et,mail_et,password,password_tekrar;
    CheckBox sozlesme;
    Button uye_btn;
    String first_name,last_name,email,id,photoUrl,profileUrl,hesapturu,gender,displayname,ip,passwd;
    String emailPattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextView warning_mail,warning_sifre,warning_ad,warning_soyad,warning_sifre1;
    LinearLayout input_layout_sifre;
    Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;

       /* shared pref initialize */
        sp = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = sp.edit();


        geri = (ImageButton) findViewById(R.id.geri);
        pagename=(TextView)findViewById(R.id.pagename);
        pagename.setText("Üye Ol");

        warning_mail=(TextView)findViewById(R.id.warning);
        warning_mail.setVisibility(View.GONE);

        warning_sifre=(TextView)findViewById(R.id.warning_sifre);
        warning_sifre.setVisibility(View.GONE);

        warning_ad=(TextView)findViewById(R.id.warning_ad);
        warning_ad.setVisibility(View.GONE);

        warning_soyad=(TextView)findViewById(R.id.warning_soyad);
        warning_soyad.setVisibility(View.GONE);

        warning_sifre1=(TextView)findViewById(R.id.warning_sifre1);
        warning_sifre1.setVisibility(View.GONE);

        uye_btn=(Button)findViewById(R.id.uye_btn);
        sozlesme=(CheckBox)findViewById(R.id.sozlesme);
        name_et=(EditText)findViewById(R.id.name_et);
        surname_et=(EditText)findViewById(R.id.surname_et);
        mail_et=(EditText)findViewById(R.id.mail_et);
        password=(EditText)findViewById(R.id.password);
        password_tekrar=(EditText)findViewById(R.id.password_tekrar);

        input_layout_sifre=(LinearLayout)findViewById(R.id.input_layout_sifre);

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
        passwd="";


        geri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        uye_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ip=new IPAddress().getLocalIpAddress();
                    displayname=name_et.getText().toString()+" "+surname_et.getText().toString();
                    hesapturu="Register";
                    id=getToken(16);

                    if(name_et.getText().toString().trim().length()<3) {  warning_ad.setVisibility(View.VISIBLE);}
                    else{warning_ad.setVisibility(View.GONE);}
                    if(surname_et.getText().toString().trim().length()<3){ warning_soyad.setVisibility(View.VISIBLE);}
                    else{warning_soyad.setVisibility(View.GONE);}
                    if(password.getText().toString().trim().length()<3){ warning_sifre1.setVisibility(View.VISIBLE);}
                    else{warning_sifre1.setVisibility(View.GONE);}
                    if(mail_et.getText().toString().trim().matches(emailPattern)){
                        warning_mail.setVisibility(View.GONE);
                        passwd=password.getText().toString();
                        if(passwd.equals(password_tekrar.getText().toString()) && !passwd.equals("")&& name_et.getText().toString().trim().length()>=3 && surname_et.getText().toString().trim().length()>=3 && password.getText().toString().trim().length()>=3) {
                            warning_sifre.setVisibility(View.GONE);
                            if(sozlesme.isChecked()){
                                Client Client = new Client();
                                Service apiService = Client.getClient().create(Service.class);
                                UserInfo userInfo = new UserInfo(id,profileUrl,photoUrl,displayname,name_et.getText().toString(),surname_et.getText().toString(),"","","0000-00-00",mail_et.getText().toString().trim(),mail_et.getText().toString().trim(),hesapturu,"",ip,passwd);
                                Call<String> call =apiService.postUyelik(userInfo);
                                Log.e("calllll",call.toString());
                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) { // ters çalışıyor.
                                        openDialogHata();
                                        Log.e("kayittt","error");
                                    }
                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Log.e("kayittt","completed");
                                        editor.putBoolean("uyelik",true);
                                        editor.commit();
                                        openDialogOnay();
                                    }
                               });
                            }
                            else{
                                openDialog();
                            }
                        }
                        else{
                            warning_sifre.setVisibility(View.VISIBLE);

                        }
                    }
                    else
                    {
                        warning_mail.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void openDialogHata() {
        final Dialog dialog = new Dialog(context); // Context, this, etc.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.registerhata_dialog);
        TextView msgtitle = (TextView) dialog.findViewById(R.id.msgtitle);
        msgtitle.setText("Hata");
        TextView tvSampleText = (TextView) dialog.findViewById(R.id.custom_dialog_tv_text);
        tvSampleText.setText("Email adresi kullanımda.");
        dialog.show();
        Button btnDone = (Button) dialog.findViewById(R.id.dialog_btn_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    public void openDialog() {
        final Dialog dialog = new Dialog(context); // Context, this, etc.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.register_dialog);
        dialog.setTitle("Bilgi");
        TextView tvSampleText = (TextView) dialog.findViewById(R.id.custom_dialog_tv_text);
        tvSampleText.setText("Lütfen üyelik ve gizlilik sözleşmesini onaylayınız.");
        dialog.show();
        Button btnDone = (Button) dialog.findViewById(R.id.dialog_btn_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    public void openDialogOnay() {
        final Dialog dialog = new Dialog(context); // Context, this, etc.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.register_dialog);
        dialog.setCancelable(false);
        TextView msgtitle = (TextView) dialog.findViewById(R.id.msgtitle);
        msgtitle.setText("Bilgi");
        TextView tvSampleText = (TextView) dialog.findViewById(R.id.custom_dialog_tv_text);
        tvSampleText.setText("Hesabınız başarıyla oluşturulmuştur.");
        dialog.show();
        Button btnDone = (Button) dialog.findViewById(R.id.dialog_btn_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                editor.putString("token",id);
                editor.putBoolean("loginDurum",true);
                editor.putBoolean("loginItem0",false);
                editor.putBoolean("loginItem1",false);
                editor.putBoolean("loginItem4",true);
                editor.putBoolean("loginItem5",true);
                editor.commit();
                Intent mIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(mIntent);
            }
        });
    }

       public String getToken(int chars) {
        String CharSet = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ1234567890";
        String Token = "";
        for (int a = 1; a <= chars; a++) {
            Token += CharSet.charAt(new Random().nextInt(CharSet.length()));
        }
           Log.e("token",Token);
        return Token;
    }
}
