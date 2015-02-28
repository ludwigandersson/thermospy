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

package com.luan.thermospy.android.core.rest;

import android.content.Context;
import android.os.Handler;

import com.android.volley.RequestQueue;
import com.luan.thermospy.android.core.Coordinator;
import com.luan.thermospy.android.core.pojo.RefreshRate;
import com.luan.thermospy.android.core.pojo.Temperature;

/**
 * Helper class that polls the temperature every mInterval seconds.
 */
public class TemperaturePolling implements GetTemperatureReq.OnGetTemperatureListener, SetRefreshRateReq.OnSetRefreshRateListener {
    volatile private int mInterval = 5;
    private final Context mContext;

    private final GetTemperatureReq mGetTemperatureReq;
    private final SetRefreshRateReq mSetRefreshRateReq;

    private int mTemperature = Integer.MIN_VALUE;
    private OnTemperatureChanged mListener = null;

    private final Handler mHandler = new Handler();

    @Override
    public void onTemperatureUpdate(Temperature temperature) {
        mTemperature = temperature.getTemperature();
        notifyListener();
    }

    @Override
    public void onTemperatureError() {

        mListener.onError();
    }

    @Override
    public void onSetRefreshRateRecv(RefreshRate RefreshRate) {
        mInterval = RefreshRate.getRefreshRate();
    }

    @Override
    public void onSetRefreshRateError() {
        mSetRefreshRateReq.setRefreshRate(new RefreshRate(mInterval));
        mListener.onError();

    }

    public interface OnTemperatureChanged
    {
        public void onTemperatureChanged(String temperature);
        public void onError();
    }


    public TemperaturePolling(Context context, RequestQueue queue, int interval, OnTemperatureChanged listener)
    {
        mInterval = interval;
        mContext = context;

        mListener = listener;

        mGetTemperatureReq = new GetTemperatureReq(queue, this);

        mSetRefreshRateReq = new SetRefreshRateReq(queue, this, new RefreshRate(mInterval));
    }

    public void start()
    {
        notifyListener();
        mHandler.postDelayed(runnable, 100);
    }

    public void cancel()
    {
        mHandler.removeCallbacks(runnable);
        mGetTemperatureReq.cancel();
        mSetRefreshRateReq.cancel();
    }

    public void clearListener()
    {
        mListener = null;
    }

    public void notifyListener()
    {
        if (mListener != null) {
            String str = toString();
            mListener.onTemperatureChanged(str);
        }
    }

    @Override
    public String toString() {
      if (mTemperature == Integer.MIN_VALUE) {
          return "--";
      } else {
          return Integer.toString(mTemperature);
      }
  }
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
      /* do what you need to do */
            mGetTemperatureReq.request(Coordinator.getInstance().getServerSettings().getIpAddress(),
                                       Coordinator.getInstance().getServerSettings().getPort());
      /* and here comes the "trick" */
            mHandler.postDelayed(this, mInterval*1000);
        }
    };

    public void setTemperature(int temperature)
    {
        this.mTemperature = temperature;
    }

    public int getTemperature()
    {
        return mTemperature;
    }


    public void setInterval(int interval) {
        mSetRefreshRateReq.setRefreshRate(new RefreshRate(interval));
        mSetRefreshRateReq.request(Coordinator.getInstance().getServerSettings().getIpAddress(),
                                   Coordinator.getInstance().getServerSettings().getPort());
    }
}
