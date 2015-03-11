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

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.luan.thermospy.android.core.NotificationHandler;
import com.luan.thermospy.android.core.pojo.Temperature;
import com.luan.thermospy.android.core.rest.GetTemperatureReq;
import com.luan.thermospy.android.fragments.AlarmCondition;

import java.util.Observable;
import java.util.Observer;

/**
 * The TemperatureMonitorService is responsible for monitoring the current temperature
 * and notifing the user about the temperature changes/sounding the alarm etc.
 */
public class TemperatureMonitorService extends Service {

    private LocalService mService = new LocalService();
    private ServiceBinder mBinder = new ServiceBinder(mService);

    public interface ServiceArguments {
        static final String REFRESH_RATE = "refreshrate";

        static final String IP_ADDRESS = "ipaddress";
        static final String PORT = "port";
        static final String ALARM_CONDITION = "alarmcondition";
        static final String ALARM_ENABLED = "alarmenabled";
        static final String ALARM = "alarm";
    }

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private NotificationHandler mNotificationHandler;

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler implements Observer {

        public boolean m_error;
        private Temperature m_temperature = null;

        public ServiceHandler(Looper looper) {
            super(looper);
            mNotificationHandler = new NotificationHandler();
            mService.addObserver(this);
        }
        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.

            long refreshInterval = msg.getData().getInt(ServiceArguments.REFRESH_RATE)*1000;
            String ip = msg.getData().getString(ServiceArguments.IP_ADDRESS);
            int port = msg.getData().getInt(ServiceArguments.PORT);

            RequestQueue requestQueue = Volley.newRequestQueue(TemperatureMonitorService.this);
            boolean mAlarmFired =false;
            final GetTemperatureReq temperatureReq = new GetTemperatureReq(requestQueue, new GetTemperatureReq.OnGetTemperatureListener() {


                @Override
                public void onTemperatureUpdate(Temperature temperature) {
                    synchronized (ServiceHandler.this) {
                        m_temperature = temperature;
                        ServiceHandler.this.notify();
                    }
                }

                @Override
                public void onTemperatureError() {
                    synchronized (ServiceHandler.this) {
                        m_temperature = null;
                        m_error = true;
                        ServiceHandler.this.notify();
                    }
                }
            });

            mService.setRunning(true);

            while (!mServiceLooper.getThread().isInterrupted()) {

                synchronized (ServiceHandler.this) {

                    try {
                        temperatureReq.request(ip,port );
                        wait(refreshInterval*10);
                    } catch (Exception e) {
                        break;
                    }
                }
                if (m_error || m_temperature == null)
                {
                    mService.onServerError();
                    break;
                }

                mService.temperatureChanged(m_temperature);

                boolean alarmEnabled = mService.isAlarmEnabled();

                if (!mAlarmFired && alarmEnabled) {
                    int alarm = mService.getAlarm();
                    AlarmCondition alarmCondition = mService.getAlarmCondition();
                    boolean triggerAlarm = alarmCondition.evaluate(m_temperature.getTemperature(), alarm);
                    if (triggerAlarm) {
                        mNotificationHandler.playSound(TemperatureMonitorService.this, m_temperature.toString());
                        mAlarmFired = true;
                        mService.alarmTriggered();
                    }
                    else
                    {
                        mNotificationHandler.update(TemperatureMonitorService.this, m_temperature.toString(), getColor(alarmCondition, m_temperature.getTemperature(), alarm));
                    }
                } else {
                    if (mAlarmFired && !alarmEnabled) {
                        mAlarmFired = false;
                    }
                    mNotificationHandler.update(TemperatureMonitorService.this, m_temperature.toString(), Notification.COLOR_DEFAULT);
                }
                synchronized (ServiceHandler.this) {
                    try {
                        refreshInterval = mService.getRefreshInterval() * 1000;
                        wait(refreshInterval);
                    } catch (InterruptedException e) {
                        break;
                    }
                }

            }
            temperatureReq.cancel();
            mNotificationHandler.cancel(TemperatureMonitorService.this);
            stopSelf(msg.arg1);
            mService.setRunning(false);
        }

        private int getColor(AlarmCondition condition, int temperature, int alarm) {

            double remaining;
            if (condition == AlarmCondition.GREATER_THAN_OR_EQUAL) remaining = (double)temperature/alarm;
            else remaining = (double)alarm/temperature;

            if (remaining > 0.8) return Color.RED;
            else if (remaining > 0.6) return 0xffa500;
            else if (remaining > 0.3) return 0x32cd32;
            else return Color.BLUE;

        }


        @Override
        public void update(Observable observable, Object data) {
            synchronized (ServiceHandler.this) {
                ServiceHandler.this.notify();
            }
        }

    }


    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                android.os.Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Temperature monitor starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;

        msg.setData(intent.getExtras());
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {

        int condition = intent.getExtras().getInt(ServiceArguments.ALARM_CONDITION);
        boolean alarmEnabled = intent.getExtras().getBoolean(ServiceArguments.ALARM_ENABLED);
        int alarm = intent.getExtras().getInt(ServiceArguments.ALARM);
        int refreshInterval = intent.getExtras().getInt(ServiceArguments.REFRESH_RATE);
        // We don't provide binding, so return null
        mService.setRefreshInterval(refreshInterval);
        mService.setAlarmEnabled(alarmEnabled);
        mService.setAlarmCondition(AlarmCondition.fromInt(condition));
        mService.setAlarm(alarm);
        return mBinder;
    }

    @Override
    public void onDestroy() {

        mServiceLooper.quit();
        mServiceLooper.getThread().interrupt();

    }
}
