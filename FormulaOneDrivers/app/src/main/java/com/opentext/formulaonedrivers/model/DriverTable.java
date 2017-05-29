
package com.opentext.formulaonedrivers.model;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverTable {

    @SerializedName("Drivers")
    @Expose
    private List<Driver> drivers = null;

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

}
