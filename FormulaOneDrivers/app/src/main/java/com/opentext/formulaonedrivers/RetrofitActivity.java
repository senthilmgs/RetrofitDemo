package com.opentext.formulaonedrivers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opentext.formulaonedrivers.model.Driver;
import com.opentext.formulaonedrivers.model.DriverTable;
import com.opentext.formulaonedrivers.model.Drivers;
import com.opentext.formulaonedrivers.model.MRData;
import com.opentext.formulaonedrivers.model.RetrofitResult;
import com.opentext.formulaonedrivers.network.ApiClient;
import com.opentext.formulaonedrivers.network.ApiInterface;
import com.opentext.formulaonedrivers.utility.FormulaOneApplication;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sentmg on 5/29/2017.
 */

public class RetrofitActivity extends Activity {

    private TextView tv;
    private int limit = 10;
    private int pageOffset = 70;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private EditText searchText;

    private List<Driver> driverList = new ArrayList<Driver>();
    private DriversAdapter adapter;

    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;


    private boolean swipeRefreshData = false;
    private Realm mRealm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_layout);


        searchText = (EditText) findViewById(R.id.search_text);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final LinearLayoutManager layoutParams = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutParams);

        mRealm = Realm.getInstance(FormulaOneApplication.getInstance());

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!swipeRefreshData) {
                    limit += 10;
                    loadData();
                }
            }
        });


        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });







/*        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(int newState) {
            }

            @Override
            public void onScrolled(int dx, int dy) {
                refreshLayout.setEnabled(layoutParams.findFirstCompletelyVisibleItemPosition() == 0);
            }
        });

*/

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(RetrofitActivity.this);

        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        loadData();
    }


    private void loadData() {

        Call<RetrofitResult> call = apiInterface.getDriverDetails(limit, pageOffset);

        swipeRefreshData = true;
        call.enqueue(new Callback<RetrofitResult>() {

            @Override
            public void onResponse(Call<RetrofitResult> call, Response<RetrofitResult> response) {
                swipeRefreshData = false;
                refreshLayout.setRefreshing(false);

                progressDialog.dismiss();
                RetrofitResult retrofitResult = response.body();
                MRData mrData = retrofitResult.getMRData();
                if (mrData != null) {
                    DriverTable driverTable = mrData.getDriverTable();
                    if (driverTable != null) {
                        List<Driver> driversList = driverTable.getDrivers();
                        Log.v("drivers list size", "" + driversList.size());
                        // driverList.addAll(driversList);
                        //insertData(driversList);
                        setUpAdapter(driversList);


                    }
                }

            }

            @Override
            public void onFailure(Call<RetrofitResult> call, Throwable t) {
                swipeRefreshData = false;
                refreshLayout.setRefreshing(false);

                progressDialog.dismiss();

            }
        });

    }

    private void setUpAdapter(List<Driver> driversList) {
        //      List<Driver> driversList = mRealm.allObjects(Driver.class);
        adapter = new DriversAdapter(driversList, limit);
        recyclerView.setAdapter(adapter);
        Snackbar.make(refreshLayout, "Data received successfully!", Snackbar.LENGTH_LONG).show();

    }

    private void insertData(List<Driver> driversList) {

        for (Driver driver : driversList) {
            Driver driverExistance = mRealm.where(Driver.class).equalTo("driverId", driver.getDriverId()).findFirst();

            if (driverExistance != null) {
                // Exists
                Log.v("Existace", "Already exist here");
            } else {
                // Not exist
                mRealm.beginTransaction();
                mRealm.commitTransaction();
            }
        }

        // setUpAdapter(driversList );
    }
}
