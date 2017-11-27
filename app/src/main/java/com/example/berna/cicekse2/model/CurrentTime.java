package com.example.berna.cicekse2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Berna on 20.11.2017.
 */

public class CurrentTime {
    @SerializedName("current")
    @Expose
    private String current;

    public CurrentTime(String current) {
        this.current = current;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }
}
