package com.skteam.wifiapp.ui.register;

import android.app.Activity;
import android.content.Context;

import com.skteam.wifiapp.baseclasses.BaseViewModel;
import com.skteam.wifiapp.prefrences.SharedPre;

public class RegisterViewModel extends BaseViewModel<RegisterNav> {
    public RegisterViewModel(Context context, SharedPre sharedPre, Activity activity) {
        super(context, sharedPre, activity);
    }
}
