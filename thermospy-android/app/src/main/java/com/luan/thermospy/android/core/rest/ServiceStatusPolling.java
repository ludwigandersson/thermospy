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
import com.luan.thermospy.android.core.pojo.ServiceStatus;

/**
 * Helper class that polls the temperature every mInterval seconds.
 */
public class ServiceStatusPolling implements GetServiceStatusReq.OnGetServiceStatus {
    volatile private int mInterval = 2;
    private final Context mContext;

    private final GetServiceStatusReq mGetServiceStatusReq;
    private OnServiceStatusListener mListener = null;

    private final Handler mHandler = new Handler();
    private boolean running = false;


    @Override
    public void onServiceStatusRecv(ServiceStatus status) {
        mListener.onServiceStatusPollerRecv(status);
    }

    @Override
    public void onServiceStatusError() {
        mListener.onServiceStatusPollerError();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }


    public interface OnServiceStatusListener {
        public void onServiceStatusPollerRecv(ServiceStatus status);
        public void onServiceStatusPollerError();
    }


    public ServiceStatusPolling(Context context, RequestQueue queue, OnServiceStatusListener listener) {
        mContext = context;

        mListener = listener;

        mGetServiceStatusReq = new GetServiceStatusReq(queue, this);
    }

    public void start() {
        setRunning(true);
        mHandler.postDelayed(runnable, 100);
    }

    public void cancel() {
        mHandler.removeCallbacks(runnable);
        mGetServiceStatusReq.cancel();
    }

    public void clearListener() {
        mListener = null;
    }


    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
      /* do what you need to do */
            mGetServiceStatusReq.request(Coordinator.getInstance().getServerSettings().getIpAddress(),
                    Coordinator.getInstance().getServerSettings().getPort());
      /* and here comes the "trick" */
            mHandler.postDelayed(this, mInterval * 1000);
        }
    };

}
