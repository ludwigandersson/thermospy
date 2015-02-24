package com.luan.thermospy.android.service;

import android.os.Binder;

/**
 * Created by ludde on 15-02-24.
 */
public class ServiceBinder extends Binder {

    private final LocalService mLocalService;

    public ServiceBinder(LocalService localService) {
        mLocalService = localService;
    }

    public LocalService getService() {
            // Return this instance of LocalService so clients can call public methods
            return mLocalService;
    }

    
}
