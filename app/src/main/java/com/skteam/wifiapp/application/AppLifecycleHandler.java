/*
 * Copyright (c) Ishant Sharma
 * Android Developer
 * ishant.sharma1947@gmail.com
 * 7732993378
 */

package com.skteam.wifiapp.application;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;


public final class AppLifecycleHandler implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {
    private boolean appInForeground;
    private final LifeCycleDelegate lifeCycleDelegate;
    int i;

    public void onActivityPaused(@Nullable Activity p0) {
    }

    public void onActivityResumed(@Nullable Activity p0) {
        if (!this.appInForeground) {
            this.appInForeground = true;
            this.lifeCycleDelegate.onAppForegrounded();
        }

    }

    public void onActivityStarted(@Nullable Activity p0) {
        i++;
    }

    public void onActivityDestroyed(@Nullable Activity p0) {
    }

    public void onActivitySaveInstanceState(@Nullable Activity p0, @Nullable Bundle p1) {
    }

    public void onActivityStopped(@Nullable Activity p0) {
        i--;
        if(i==0)
        {
            this.appInForeground = false;
            this.lifeCycleDelegate.onAppBackgrounded();
        }
    }

    public void onActivityCreated(@Nullable Activity p0, @Nullable Bundle p1) {
    }

    public void onLowMemory() {
    }

    public void onConfigurationChanged(@Nullable Configuration p0) {
    }

    public void onTrimMemory(int level) {
      /*  if (level == 20 || level == 40) {
            this.appInForeground = false;
            this.lifeCycleDelegate.onAppBackgrounded();
        }
*/
    }

    public AppLifecycleHandler(LifeCycleDelegate lifeCycleDelegate) {
        super();
        this.lifeCycleDelegate = lifeCycleDelegate;
    }
}

