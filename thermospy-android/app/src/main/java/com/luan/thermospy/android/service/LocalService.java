/*
 * Copyright 2015 Ludwig Andersson
 *
 * This file is part of Thermospy-android.
 *
 * Thermospy-android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Thermospy-android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Thermospy-android.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.luan.thermospy.android.service;

import com.luan.thermospy.android.core.LocalServiceObserver;
import com.luan.thermospy.android.core.LocalServiceSubject;
import com.luan.thermospy.android.core.pojo.Temperature;
import com.luan.thermospy.android.fragments.AlarmCondition;

import java.util.ArrayList;
import java.util.Observable;

/**
 * The local service is an object that is shared between the TemperatureMonitorService and the
 * MainActivity.
 */
public class LocalService extends Observable implements LocalServiceSubject {
    private volatile int mRefreshInterval = 5;
    private volatile int mAlarm = 0;
    private AlarmCondition alarmCondition = AlarmCondition.GREATER_THAN_OR_EQUAL;
    private boolean alarmEnabled = false;
    private ArrayList<LocalServiceObserver> mObservers = new ArrayList<>();
    private Temperature mTemperatureEntry = null;

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

    @Override
    synchronized public void registerObserver(LocalServiceObserver listener) {
        if (mTemperatureEntry != null) {
            listener.onTemperatureRecv(mTemperatureEntry);
        }
        mObservers.add(listener);
    }

    @Override
    synchronized public void unregisterObserver(LocalServiceObserver listener) {
        mObservers.remove(listener);
    }

    @Override
    synchronized public void temperatureChanged(Temperature temperatureEntry) {
        mTemperatureEntry = temperatureEntry;
        for (LocalServiceObserver observer : mObservers)
        {
            observer.onTemperatureRecv(temperatureEntry);
        }
    }

    @Override
    public void alarmTriggered() {
        for (LocalServiceObserver observer : mObservers)
        {
            observer.onAlarmTriggered();
        }
    }
}
