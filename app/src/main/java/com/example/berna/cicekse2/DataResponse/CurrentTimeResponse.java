package com.example.berna.cicekse2.DataResponse;

import com.example.berna.cicekse2.model.CurrentTime;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Berna on 20.11.2017.
 */

public class CurrentTimeResponse {
    @SerializedName("Current")
    @Expose
    private List<CurrentTime> current;

    public List<CurrentTime> getCurrent() {
        return current;
    }

    public void setCurrent(List<CurrentTime> current) {
        this.current = current;
    }
}
