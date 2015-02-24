package com.luan.thermospy.android.service;

import android.app.Service;
import android.content.Intent;
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

/**
 * Created by ludde on 15-02-24.
 */
public class TemperatureMonitorService extends Service {


    public interface ServiceArguments {
        static final String REFRESH_RATE = "refreshrate";
        static final String NOTIFICATION_ID = "notificationid";
        static final String IP_ADDRESS = "ipaddress";
        static final String PORT = "port";
    }

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private NotificationHandler mNotificationHandler;

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {

        public boolean m_error;
        private Temperature m_temperature = null;

        public ServiceHandler(Looper looper) {
            super(looper);
            mNotificationHandler = new NotificationHandler();
        }
        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            final long refreshInterval = msg.getData().getInt(ServiceArguments.REFRESH_RATE)*1000;
            final String ip = msg.getData().getString(ServiceArguments.IP_ADDRESS);
            final int port = msg.getData().getInt(ServiceArguments.PORT);

            RequestQueue requestQueue = Volley.newRequestQueue(TemperatureMonitorService.this);

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
                    break;
                }
                mNotificationHandler.update(TemperatureMonitorService.this, m_temperature.toString(), "");
                try {
                    Thread.sleep(refreshInterval);
                } catch (InterruptedException e) {
                    break;
                }

            }
            temperatureReq.cancel();
            mNotificationHandler.cancel(TemperatureMonitorService.this);
            stopSelf(msg.arg1);
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
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

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
        // We don't provide binding, so return null
        return mBinder;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
        mServiceLooper.quit();
        mServiceLooper.getThread().interrupt();

    }
}
