package com.opentext.formulaonedrivers.utility;

import android.app.Application;

import com.opentext.formulaonedrivers.model.Drivers;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static java.security.AccessController.getContext;

/**
 * Created by sentmg on 5/26/2017.
 */

public class FormulaOneApplication extends Application {

    private static FormulaOneApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);


    }

    public static FormulaOneApplication getInstance() {
        return instance;
    }
}