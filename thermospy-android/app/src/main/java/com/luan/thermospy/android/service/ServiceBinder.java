package com.luan.thermospy.android.service;

import android.os.Binder;

import com.luan.thermospy.android.fragments.AlarmCondition;

/**
 * Created by ludde on 15-02-24.
 */
public class ServiceBinder extends Binder {
    private volatile int mRefreshInterval = 5;
    private volatile int mAlarm = 0;
    private AlarmCondition alarmCondition = AlarmCondition.GREATER_THAN_OR_EQUAL;
    private boolean alarmEnabled = false;

    public int getRefreshInterval() {
        return mRefreshInterval;
    }

    public void setRefreshInterval(int mRefreshInterval) {
        this.mRefreshInterval = mRefreshInterval;
    }

    public int getAlarm() {
        return mAlarm;
    }

    public void setAlarm(int mAlarm) {
        this.mAlarm = mAlarm;
    }

    public AlarmCondition getAlarmCondition() {
        return alarmCondition;
    }

    public void setAlarmCondition(AlarmCondition alarmCondition) {
        this.alarmCondition = alarmCondition;
    }


    ServiceBinder getService() {
            // Return this instance of LocalService so clients can call public methods
            return ServiceBinder.this;
    }

    public boolean isAlarmEnabled() {
        return alarmEnabled;
    }

    public void setAlarmEnabled(boolean alarmEnabled)
    {
        this.alarmEnabled = alarmEnabled;
    }
}
