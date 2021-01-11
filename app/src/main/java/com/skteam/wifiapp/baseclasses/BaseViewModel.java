/*
 * Copyright (c) Ishant Sharma
 * Android Developer
 * ishant.sharma1947@gmail.com
 * 7732993378
 */
package com.skteam.wifiapp.baseclasses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import androidx.lifecycle.ViewModel;

import com.skteam.wifiapp.prefrences.SharedPre;

import java.lang.ref.WeakReference;

public abstract class BaseViewModel<N> extends ViewModel {
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private WeakReference<N> mNavigator;
    private SharedPre sharedPre;
    private Activity activity;
    private Toast toast;


    public BaseViewModel(Context context, SharedPre sharedPre, Activity activity) {
        this.sharedPre = sharedPre;
        this.context = context;
        this.activity = activity;

    }

    public Activity getActivity() {
        return activity;
    }

    public SharedPre getSharedPre() {
        return sharedPre;
    }

    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N Navigator) {
        this.mNavigator = new WeakReference<>(Navigator);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
