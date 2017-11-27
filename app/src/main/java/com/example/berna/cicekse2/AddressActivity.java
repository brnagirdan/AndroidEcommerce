package com.example.berna.cicekse2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.berna.cicekse2.Helper.IPAddress;
import com.example.berna.cicekse2.api.Client;
import com.example.berna.cicekse2.api.Service;
import com.example.berna.cicekse2.model.UserInfo;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {
    ImageButton geri;
    TextView pagename;
    @InjectView(R.id.warning_name) TextView warningName;
    @InjectView(R.id.warning_tel) TextView warningTel;
    @InjectView(R.id.warning_adres) TextView warningAdres;
    @InjectView(R.id.warning_description) TextView warningDescription;
    @InjectView(R.id.name_et) TextView receiverName;
    @InjectView(R.id.tel_et) TextView receiverTel;
    @InjectView(R.id.adres_et) TextView receiverAddress;
    @InjectView(R.id.description) TextView adresDescription;
    @InjectView(R.id.continue_btn) Button goOn;
    String regex_phone = "[0-9]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.inject(this);
        warningName.setVisibility(View.GONE);
        warningTel.setVisibility(View.GONE);
        warningAdres.setVisibility(View.GONE);
        warningDescription.setVisibility(View.GONE);
        pagename = (TextView) findViewById(R.id.pagename);
        pagename.setText("Alıcı Bilgileri");
        geri = (ImageButton) findViewById(R.id.geri);

        geri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        goOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (receiverName.getText().toString().trim().length() < 3) {
                        warningName.setVisibility(View.VISIBLE);
                    } else {
                        warningName.setVisibility(View.GONE);
                    }
                    if (receiverTel.getText().toString().trim().length() < 10 || !receiverTel.getText().toString().trim().matches(regex_phone)) {
                        warningTel.setVisibility(View.VISIBLE);
                    } else {
                        warningTel.setVisibility(View.GONE);
                    }
                    if (receiverAddress.getText().toString().trim().length() < 10) {
                        warningAdres.setVisibility(View.VISIBLE);
                    } else {
                        warningAdres.setVisibility(View.GONE);
                    }


                    if(receiverName.getText().toString().trim().length()>=3 && receiverTel.getText().toString().trim().length()>=10 && receiverTel.getText().toString().trim().matches(regex_phone)&& receiverAddress.getText().toString().trim().length()>=10){
                        String name=receiverName.getText().toString().trim();
                        String recTelephone=receiverTel.getText().toString().trim();
                        String recAdress=receiverAddress.getText().toString().trim();
                        String adressDescription=adresDescription.getText().toString().trim();
                        Log.e("gönderilenAdres",name+" "+recTelephone+" "+recAdress+" "+adressDescription);
                    /*    Intent mIntent = new Intent(AddressActivity.this,ExtraActivity.class);
                        startActivity(mIntent); */
                    }

                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                }
            });


    }
}
