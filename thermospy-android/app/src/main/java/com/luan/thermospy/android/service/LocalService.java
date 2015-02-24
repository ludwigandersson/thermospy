package com.luan.thermospy.android.service;

import com.luan.thermospy.android.fragments.AlarmCondition;

import java.util.Observable;

/**
 * Created by ludde on 15-02-24.
 */
public class LocalService extends Observable {
    private volatile int mRefreshInterval = 5;
    private volatile int mAlarm = 0;
    private AlarmCondition alarmCondition = AlarmCondition.GREATER_THAN_OR_EQUAL;
    private boolean alarmEnabled = false;

    synchronized public int getRefreshInterval() {
        return mRefreshInterval;
    }

    synchronized public void setRefreshInterval(int mRefreshInterval) {
        if (this.mRefreshInterval != mRefreshInterval) {
            this.mRefreshInterval = mRefreshInterval;
            setChanged();
            notifyObservers();
        }
    }

    synchronized public int getAlarm() {
        return mAlarm;
    }

    synchronized public void setAlarm(int mAlarm) {

        if (this.mAlarm != mAlarm)
        {
            this.mAlarm = mAlarm;
            setChanged();
            notifyObservers();
        }
    }

    synchronized public AlarmCondition getAlarmCondition() {
        return alarmCondition;
    }

    synchronized public void setAlarmCondition(AlarmCondition alarmCondition) {
        if (this.alarmCondition != alarmCondition) {
            this.alarmCondition = alarmCondition;
            setChanged();
            notifyObservers();
        }
    }



    synchronized public boolean isAlarmEnabled() {
        return alarmEnabled;
    }

    synchronized public void setAlarmEnabled(boolean alarmEnabled)
    {
        if (this.alarmEnabled != alarmEnabled) {
            this.alarmEnabled = alarmEnabled;
            setChanged();
            notifyObservers();
        }

    }
}
