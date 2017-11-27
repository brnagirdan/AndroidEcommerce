package com.example.berna.cicekse2.DataResponse;

import com.example.berna.cicekse2.model.Time;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Berna on 20.11.2017.
 */

public class TimeResponse {
    @SerializedName("Time")
    @Expose
    private List<Time> times;

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }
}
