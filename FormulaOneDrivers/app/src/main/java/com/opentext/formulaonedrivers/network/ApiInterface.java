package com.opentext.formulaonedrivers.network;

import com.opentext.formulaonedrivers.model.RetrofitResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sentmg on 5/29/2017.
 */

public interface ApiInterface {

    @GET("drivers.json?")
    Call<RetrofitResult> getDriverDetails(@Query("limit") int limit, @Query("offset") int offset);
}
