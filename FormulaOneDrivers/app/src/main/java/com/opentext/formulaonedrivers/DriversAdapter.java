package com.opentext.formulaonedrivers;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.opentext.formulaonedrivers.model.Driver;
import com.opentext.formulaonedrivers.model.DriverTable;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DriversAdapter extends RecyclerView.Adapter<DriversAdapter.MyViewHolder> {

    private List<Driver> driversList;
    private int limit;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView driverIdText, nameText, nationalityText, dobText;

        public MyViewHolder(View view) {
            super(view);
            driverIdText = (TextView) view.findViewById(R.id.text_driver_id);
            nationalityText = (TextView) view.findViewById(R.id.text_nationality);
            nameText = (TextView) view.findViewById(R.id.text_name);
            dobText = (TextView) view.findViewById(R.id.text_dob);
        }
    }


    public DriversAdapter(List<Driver> driversList, int limit) {
        this.driversList = driversList;
        this.limit = limit;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Driver driver = driversList.get(position);
        holder.driverIdText.setText(driver.getDriverId());
        holder.nationalityText.setText(driver.getNationality());
        holder.nameText.setText(driver.getGivenName() + ", " + driver.getFamilyName());
        holder.dobText.setText(driver.getDateOfBirth());
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}