package com.skteam.wifiapp.ui.splash;

import android.app.Activity;
import android.content.Context;

import com.skteam.wifiapp.baseclasses.BaseViewModel;
import com.skteam.wifiapp.prefrences.SharedPre;

public class SpalshViewmodel extends BaseViewModel<SplashNav> {
    public SpalshViewmodel(Context context, SharedPre sharedPre, Activity activity) {
        super(context, sharedPre, activity);
    }
}
