package com.example.berna.cicekse2;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.berna.cicekse2.Adapter.ExtraAdapter;
import com.example.berna.cicekse2.api.Client;
import com.example.berna.cicekse2.api.Service;
import com.example.berna.cicekse2.model.Extra;
import com.example.berna.cicekse2.DataResponse.ExtraResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExtraActivity extends AppCompatActivity {
    @InjectView(R.id.pagename)
    TextView pagename;
    @InjectView(R.id.geri)
    ImageButton geri;
    ProgressDialog pd;
    List<Extra> extra_list=new <Extra>ArrayList();
    @InjectView(R.id.list)
    ListView list;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);
        ButterKnife.inject(this);
        context=this;
        pagename.setText("Ek Ürünler");

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
            Call<ExtraResponse> call = apiService.getExtras();
            call.enqueue(new Callback<ExtraResponse>() {
                @Override
                public void onResponse(Call<ExtraResponse> call, Response<ExtraResponse> response) {
                        List<Extra> items = response.body().getExtras();
                        for(int i=0;i<items.size();i++){
                            extra_list.add(items.get(i));
                            Log.e("extra",extra_list.get(i).getUrunAdi());
                        }
                       final ExtraAdapter adapter = new ExtraAdapter(context,extra_list);
                       list.setAdapter(adapter);

                    }

                @Override
                public void onFailure(Call<ExtraResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    pd.hide();
                }
            });
            }
          catch (Exception e){
          Log.d("Error", e.getMessage());
          Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show(); }
    }
}
