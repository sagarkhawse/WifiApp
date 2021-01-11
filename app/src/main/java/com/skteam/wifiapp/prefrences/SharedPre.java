package com.skteam.wifiapp.prefrences;
/*
 * Copyright (c) Ishant Sharma
 * Android Developer
 * ishant.sharma1947@gmail.com
 * 7732993378
 */

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class SharedPre {
    private static final String APP_NAME = "WIFI-SK-TEAM";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String OUR_PROFILE = "myProfileFromApi";
    private static final String CLIENT_PROFILE = "profileFromFaceBook";
    private static final String CLIENT_ID = "clientId";
    private static final String MOBILE_NO = "mobile_no";
    private static final String APP_BACKGROUND = "app_in_background";
    private static final String IS_ADD_MOB_SAVED = "isSvedAddMobData";
    private static final String IS_LOGGED_IN = "login";
    private static final String CITY_ID = "cityId";
    private static final String USER_ID = "userId";
    private static final String FIREBASE_TOKEN = "firebaseToken";
    private static final String RINGTON_PATH = "rington";
    private static final String NOTIFICATION_MUTED = "notification_muted";
    private static final String IS_LOGGED_IN_VIA_EMAIL = "emailLoggedin";
    private static final String FB_ACCESS_TOKEN = "usertoken";
    private static SharedPre Instance;
    @NonNull
    Context mContext;

    private SharedPre(Context context) {
        if (Instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class( Mr.professional - Ishant ).");
        }
        mContext = context.getApplicationContext();
    }

    public synchronized static SharedPre getInstance(Context context) {
        if (Instance == null) {
            Instance = new SharedPre(context);
        }
        return Instance;
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
    }

    public boolean isSavedAd() {
        return GetDataBoolean(this.IS_ADD_MOB_SAVED);
    }

    public void setIsSavedAd(boolean value) {
        SetDataBoolean(this.IS_ADD_MOB_SAVED, value);
    }

    public boolean isLoggedIn() {
        return GetDataBoolean(this.IS_LOGGED_IN);
    }

    public void setIsLoggedIn(boolean value) {
        SetDataBoolean(this.IS_LOGGED_IN, value);
    }


    public boolean isEmailLoggedIn() {
        return GetDataBoolean(this.IS_LOGGED_IN_VIA_EMAIL);
    }

    public void setIsEmailLoggedIn(boolean value) {
        SetDataBoolean(this.IS_LOGGED_IN_VIA_EMAIL, value);
    }

    public String getRegisterCity() {
        return GetDataString(this.CITY_ID);
    }

    public void setRegisterCity(String value) {
        SetDataString(this.CITY_ID, value);
    }

    public void setIsAppBackground(boolean b) {
        SetDataBoolean(this.APP_BACKGROUND, b);
    }

    public boolean IsAppBackgrounded() {
        return GetDataBoolean(this.APP_BACKGROUND);
    }

    public void setUserId(String uid) {
        SetDataString(this.USER_ID, uid);
    }

    public String getUserId() {
        return GetDataString(this.USER_ID);
    }

    public void setUserMobile(String userMobile) {
        SetDataString(this.MOBILE_NO, userMobile);
    }

    public String getUserMobile() {
        return GetDataString(this.MOBILE_NO);
    }

    public void setName(String name) {
        SetDataString(this.NAME, name);
    }

    public String getName() {
        return GetDataString(this.NAME);
    }

    public void setClientId(String id) {
        SetDataString(this.CLIENT_ID, id);
    }

    public String getClientId() {
        return GetDataString(this.CLIENT_ID);
    }

    public void setFaceBookAccessToken(String accessToken) {
        SetDataString(this.FB_ACCESS_TOKEN, accessToken);
    }

    public String getFaceBookAccessToken() {
        return GetDataString(this.FB_ACCESS_TOKEN);
    }

    public void setEmailProfile(String googleProfile) {
        SetDataString(this.OUR_PROFILE, googleProfile);
    }

    public String getEmailProfile() {
        return GetDataString(this.OUR_PROFILE);
    }

    public void setClientProfile(String profileClient) {
        SetDataString(this.CLIENT_PROFILE, profileClient);
    }

    public String getClientProfile() {
        return GetDataString(this.CLIENT_PROFILE);
    }

    public void setUserEmail(String email) {
        SetDataString(this.EMAIL, email);
    }

    public String getUserEmail() {
        return GetDataString(this.EMAIL);
    }

    public String getFirebaseDeviceToken() {
        return GetDataString(this.FIREBASE_TOKEN);
    }

    public void setFirebaseToken(String token) {
        SetDataString(this.FIREBASE_TOKEN, token);
    }

    public boolean isNotificationMuted() {
        return GetDataBoolean(this.NOTIFICATION_MUTED);
    }

    public void setNotificationMuted(boolean notificationMuted) {
        SetDataBoolean(this.NOTIFICATION_MUTED, notificationMuted);
    }

    public String getNotificationSound() {
        return GetDataString(this.RINGTON_PATH);
    }

    public void setNotificationSound(String uri) {
        SetDataString(this.RINGTON_PATH, uri);
    }
//--------------------------------------Boolean Values--------------------------------------------

    //------------------------------------------------------------------------------------------------
    private String GetDataString(String key) {
        String cbValue = null;
        try {
            cbValue = getSharedPreferences(mContext).getString(key, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cbValue;
    }


    private String GetDataStringZero(String key) {
        String cbValue = null;
        try {
            cbValue = getSharedPreferences(mContext).getString(key, "0.0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cbValue;
    }

    private void SetDataString(String key, String value) {
        SharedPreferences.Editor edit = getSharedPreferences(mContext).edit();
        edit.putString(key, value);
        edit.commit();

    }

    private int GetDataInt(String key) {
        int cbValue = getSharedPreferences(mContext).getInt(key, 0);
        return cbValue;
    }

    private void SetDataInt(String key, int value) {
        SharedPreferences.Editor edit = getSharedPreferences(mContext).edit();
        edit.putInt(key, value);
        edit.commit();
    }

    private long GetDataLong(String key) {
        long cbValue = getSharedPreferences(mContext).getLong(key, 0);
        return cbValue;
    }

    private void SetDataLong(String key, long value) {
        SharedPreferences sp = getSharedPreferences(mContext);
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    private Boolean GetDataBoolean(String key) {
        boolean cbValue = getSharedPreferences(mContext).getBoolean(key, false);
        if (cbValue) {
            return true;
        } else {
            return false;
        }
    }

    private void SetDataBoolean(String key, Boolean value) {
        SharedPreferences.Editor edit = getSharedPreferences(mContext).edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public void Logout() {

        getSharedPreferences(mContext).edit().clear().commit();
        LogoutPrefrences();
    }

    private static void removePreferences(String key, Context cntxt) {
        getSharedPreferences(cntxt).edit().remove(key).commit();
    }

    private void LogoutPrefrences() {
        removePreferences(NAME, mContext);
        removePreferences(EMAIL, mContext);
        removePreferences(OUR_PROFILE, mContext);
        removePreferences(CLIENT_PROFILE, mContext);
        removePreferences(CLIENT_ID, mContext);
        removePreferences(MOBILE_NO, mContext);
        removePreferences(IS_ADD_MOB_SAVED, mContext);
        removePreferences(IS_LOGGED_IN, mContext);
        removePreferences(CITY_ID, mContext);
        removePreferences(USER_ID, mContext);
        removePreferences(FIREBASE_TOKEN, mContext);
        removePreferences(RINGTON_PATH, mContext);
        removePreferences(NOTIFICATION_MUTED, mContext);
        removePreferences(IS_LOGGED_IN_VIA_EMAIL, mContext);

    }


}


