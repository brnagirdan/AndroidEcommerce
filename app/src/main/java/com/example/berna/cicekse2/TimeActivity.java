package com.example.berna.cicekse2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.berna.cicekse2.Adapter.TimeAdapter;
import com.example.berna.cicekse2.Helper.GridSpacingItemDecoration;
import com.example.berna.cicekse2.api.Client;
import com.example.berna.cicekse2.api.Service;
import com.example.berna.cicekse2.model.CurrentTime;
import com.example.berna.cicekse2.DataResponse.CurrentTimeResponse;
import com.example.berna.cicekse2.model.Time;
import com.example.berna.cicekse2.DataResponse.TimeResponse;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TimeActivity extends AppCompatActivity {
    ProgressDialog pd;
    ImageButton geri;
    TextView pagename;
    RecyclerView recyclerView;
    int yıl,ay,gün,Hour;
    int period;
    List<String>deliveryTimes,dtimes,dtimes_t;
    String start_time,finish_time,period_type;
    private TimeAdapter adapter;
    Context context;
    @InjectView(R.id.continue_btn)
    Button goOn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        ButterKnife.inject(this);
        context=this;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        pagename = (TextView) findViewById(R.id.pagename);
        pagename.setText("Tarih ve Saat Seçimi");
        geri = (ImageButton) findViewById(R.id.geri);
        initViews();

       goOn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent mIntent = new Intent(TimeActivity.this, AddressActivity.class);
               startActivity(mIntent);
           }
       });

        geri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    private void loadJSON() {
        try {
            //select current time
            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<CurrentTimeResponse> call2 = apiService.getCurrent();
            call2.enqueue(new Callback<CurrentTimeResponse>() {
                @Override
                public void onResponse(Call<CurrentTimeResponse> call, Response<CurrentTimeResponse> response) {
                    List<CurrentTime> current = response.body().getCurrent();
                    for(int i=0;i<current.size();i++){
                        String[] date=current.get(i).getCurrent().substring(0,10).split("/");
                        ay=Integer.parseInt(date[0]);
                        gün=Integer.parseInt(date[1]);
                        yıl=Integer.parseInt(date[2]);
                        String[] s=current.get(i).getCurrent().split(" ");
                        Log.e("tarihh", String.valueOf(s[2])); // AM OR PM
                        String[] hour=current.get(i).getCurrent().substring(11,18).split(":");
                        if(s[2].equals("PM")){Hour=(Integer.parseInt(hour[0])+12);
                        Log.e("hourr", String.valueOf(Hour));}
                        else{Hour=Integer.parseInt(hour[0]);}

                    }
                    if(current!=null){
                        MaterialCalendarView mcv = (MaterialCalendarView) findViewById(R.id.calendarView);

                        if(ay!=12){
                            mcv.state().edit()
                                    .setFirstDayOfWeek(Calendar.MONDAY)
                                    .setMinimumDate(CalendarDay.from(yıl,ay-1,gün)) // 1 fazla olarak görüyor
                                    .setMaximumDate(CalendarDay.from(yıl,ay,gün))
                                    .setCalendarDisplayMode(CalendarMode.MONTHS)
                                    .commit();
                        }
                        else{
                            mcv.state().edit()
                                    .setFirstDayOfWeek(Calendar.MONDAY)
                                    .setMinimumDate(CalendarDay.from(yıl,ay-1,gün))
                                    .setMaximumDate(CalendarDay.from(yıl+1,0,gün))
                                    .setCalendarDisplayMode(CalendarMode.MONTHS)
                                    .commit();
                        }

                        mcv.setSelectedDate(CalendarDay.from(yıl,ay-1,gün));


                        setRecyclerView();
                        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
                            @Override
                            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                              if(date.equals(CalendarDay.from(yıl,ay-1,gün))){
                                  adapter = new TimeAdapter(context, dtimes_t);
                                  recyclerView.setAdapter(adapter); }
                                else{
                                  adapter = new TimeAdapter(context, dtimes);
                                  recyclerView.setAdapter(adapter);

                              }
                            }
                        });
                    }
                    pd.hide();
                }
                @Override
                public void onFailure(Call<CurrentTimeResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    pd.hide();
                }
            });

            /// select order dates
            Call<TimeResponse> call = apiService.getTimes();
            call.enqueue(new Callback<TimeResponse>() {
                @Override
                public void onResponse(Call<TimeResponse> call, Response<TimeResponse> response) {
                    List<Time> times = response.body().getTimes();
                    dtimes=new ArrayList<>();
                    dtimes_t=new ArrayList<>();
                    deliveryTimes=new ArrayList<>();
                    for(int i=0;i<times.size();i++){
                        start_time=times.get(i).getBaslangicSaati();
                        finish_time=times.get(i).getBitisSaati();
                        period=times.get(i).getPeriyot();
                        period_type=times.get(i).getPeriyotTipi();
                    }
                    if(period_type.equals("saat")){
                        int f_time= Integer.parseInt(finish_time.substring(0,2));
                        int s_time= Integer.parseInt(start_time.substring(0,2));
                        int cycle=(f_time-s_time)/period;
                        int remain=(f_time-s_time)%period;
                        deliveryTimes.add(String.valueOf(s_time));
                        for(int i=0;i<cycle;i++){
                            s_time=s_time+period;
                            deliveryTimes.add(String.valueOf(s_time));
                        }
                        deliveryTimes.add(String.valueOf(s_time+remain));

                        for(int i=0;i<deliveryTimes.size()-1;i++){
                            String time;
                            if(deliveryTimes.get(i).length()==1){time="0".concat(deliveryTimes.get(i).concat(":00-"+deliveryTimes.get(i+1).concat(":00")));}
                            else{time= deliveryTimes.get(i).concat(":00-"+deliveryTimes.get(i+1).concat(":00"));}
                            dtimes.add(time); // add all times
                            Log.e("timesss",deliveryTimes.get(i));
                            if(Hour<Integer.parseInt(deliveryTimes.get(i))){ // add available times for today
                                dtimes_t.add(dtimes.get(i));

                            }
                        }

                        setRecyclerView();
                        adapter = new TimeAdapter(context, dtimes_t);
                        recyclerView.setAdapter(adapter);

                    }
                }
                @Override
                public void onFailure(Call<TimeResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());

                }
            });


        } catch (Exception e) {

        }

    }

    /* recycler view */
    public void setRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context,2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}