package com.example.berna.cicekse2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.berna.cicekse2.api.Client;
import com.example.berna.cicekse2.api.Service;
import com.example.berna.cicekse2.model.Province;
import com.example.berna.cicekse2.DataResponse.ProvinceResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProvinceActivity extends AppCompatActivity {
    ImageButton geri;
    ListView listView ;
    ProgressDialog pd;
    Context context;
    EditText searchtext;
    ArrayAdapter<String> adapter;
    TextView sonucyok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        geri = (ImageButton) findViewById(R.id.geri);
        context=this;

        sonucyok=(TextView)findViewById(R.id.sonucyok);
        searchtext=(EditText) findViewById(R.id.searchtext);
        listView = (ListView) findViewById(R.id.list);
        listView.setTextFilterEnabled(true);

        geri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        initViews();

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ListView Clicked item index
                int itemPosition     = position;
                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);
                Intent intent = new Intent(ProvinceActivity.this, TimeActivity.class);
                startActivity(intent);

            }

         });

        /*  search editbox  Listener*/
        searchtext.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);

           /*     if(adapter.isEmpty()){ // if not found result
                    sonucyok.setVisibility(View.VISIBLE);
                    Log.e("yok","yok");
                }
                else{
                    sonucyok.setVisibility(View.GONE);
                    Log.e("var","var");
                } */
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initViews(){
        pd = new ProgressDialog(this);
        pd.setMessage("Yükleniyor..."); //fetching..
        pd.setCancelable(false);
        pd.show();
        loadJSON();
    }

    private void loadJSON(){
        try{
            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<ProvinceResponse> call = apiService.getProvinces(String.valueOf(35));
            call.enqueue(new Callback<ProvinceResponse>() {
                @Override
                public void onResponse(Call<ProvinceResponse> call, Response<ProvinceResponse> response) {
                    List<Province> provinces = response.body().getProvinces();
                    List<String> pro_name = new ArrayList<>();
                    for(int i=0;i<provinces.size();i++){
                        pro_name.add(provinces.get(i).getSehirAdi());
                    }
                    adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,pro_name);
                    listView.setAdapter(adapter);
                    pd.hide();
                }

                @Override
                public void onFailure(Call<ProvinceResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    pd.hide();
                }
            });

        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
