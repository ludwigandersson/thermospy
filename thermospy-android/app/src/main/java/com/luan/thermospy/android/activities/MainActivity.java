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

package com.luan.thermospy.android.activities;

import android.app.ActivityManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.AlarmSettings;
import com.luan.thermospy.android.core.Coordinator;
import com.luan.thermospy.android.core.ITemperatureObserver;
import com.luan.thermospy.android.core.ITemperatureSubject;
import com.luan.thermospy.android.core.ServerSettings;
import com.luan.thermospy.android.core.pojo.Boundary;
import com.luan.thermospy.android.core.pojo.ServiceStatus;
import com.luan.thermospy.android.core.pojo.Temperature;
import com.luan.thermospy.android.fragments.Alarm;
import com.luan.thermospy.android.fragments.AlarmCondition;
import com.luan.thermospy.android.fragments.MonitorFragment;
import com.luan.thermospy.android.fragments.NavigationDrawerFragment;
import com.luan.thermospy.android.fragments.setup.SetupBoundary;
import com.luan.thermospy.android.fragments.setup.SetupConfirm;
import com.luan.thermospy.android.fragments.setup.SetupService;
import com.luan.thermospy.android.fragments.temperaturelog.LogSessionDialogFragment;
import com.luan.thermospy.android.service.LocalService;
import com.luan.thermospy.android.service.ServiceBinder;
import com.luan.thermospy.android.service.TemperatureMonitorService;

import java.util.ArrayList;

/**
 * Main activity
 */
public class MainActivity extends ActionBarActivity
        implements SetupService.OnSetupServerListener,
        NavigationDrawerFragment.NavigationDrawerCallbacks,
        SetupBoundary.OnSetupBoundaryListener,
        SetupConfirm.OnThermoSpySetupConfirmedListener,
        MonitorFragment.OnMonitorFragmentListener,
        Alarm.OnAlarmFragmentListener,
        ITemperatureSubject,
        ITemperatureObserver{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Keeps track of the last selected "page"/fragment
     */
    private int mLastSelected = -1;

    /**
     * The local service is responsible for notification handling. The main activity communicates
     * alarm settings, refresh rate etc.
     *
     */
    LocalService mService;
    /**
     * Flag telling whether or not the local service is bound or not.
     */

    boolean mBound = false;

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ServiceBinder binder = (ServiceBinder) service;
            mService = binder.getService();
            mService.registerObserver(MainActivity.this);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            mService.unregisterObserver(MainActivity.this);
        }
    };

    RequestQueue requestQueue;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private ArrayList<ITemperatureObserver> mTemperatureObservers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(this);

        Coordinator coordinator = Coordinator.getInstance();
        coordinator.initDb(getApplicationContext());
        coordinator.setRequestQueue(requestQueue);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    @Override
    protected void onStart()
    {
        super.onStart();

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Coordinator.getInstance().save();


        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }


    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (mLastSelected == 3)
        {
            mNavigationDrawerFragment.selectItem(0);
        }

        if (mBound) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            int interval = Integer.parseInt(settings.getString(getString(R.string.pref_key_refresh_interval), "5"));
            if (mService.getRefreshInterval() != interval)
            {
                mService.setRefreshInterval(interval);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Coordinator.getInstance().save();
    }

    @Override
    public void onBackPressed()
    {
        if (mLastSelected > 0) {
            mNavigationDrawerFragment.selectItem(0);
        }
        else
        {
            super.onBackPressed();
        }
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        onSectionAttached(position);
        FragmentManager fragmentManager = getFragmentManager();
        final ServerSettings serverSettings = Coordinator.getInstance().getServerSettings();
        final AlarmSettings alarmSettings = Coordinator.getInstance().getAlarmSettings();
        final String temperature = Coordinator.getInstance().getTemperature();

        final String ip = serverSettings.getIpAddress();
        final int port = serverSettings.getPort();

        final boolean isAlarmSwitchChecked = alarmSettings.isAlarmSwitchEnabled();
        final AlarmCondition condition = alarmSettings.getAlarmCondition();
        final String alarm = alarmSettings.getAlarm();

        FragmentTransaction transaction;
        if (position == 0) {

            if (ip.isEmpty() == false && port != -1)
            {
                Fragment fragment;
                if (isAlarmSwitchChecked)
                {
                    fragment = MonitorFragment.newInstance(ip, port, alarm, temperature);
                }
                else
                {
                    fragment = MonitorFragment.newInstance(ip, port, getString(R.string.not_enabled), temperature);
                }
                transaction = fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment);
            }
            else
            {
                // No ip or port available. Show server setup fragment
                transaction = fragmentManager.beginTransaction()
                       .replace(R.id.container, SetupService.newInstance(ip, port));

            }
        }
        else if (position == 1)
        {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            transaction = ft.replace(R.id.container, Alarm.newInstance(alarm, isAlarmSwitchChecked, Coordinator.getInstance().getServerSettings(), condition));
        }
        else if (position == 2)
        {
            transaction = fragmentManager.beginTransaction()
                    .replace(R.id.container, SetupService.newInstance(ip, port));
        }
        else if (position == 3)
        {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            String dateformat = settings.getString(getString(R.string.pref_key_dateformat), "yyyy-MM-dd");
            String timeformat = settings.getString(getString(R.string.pref_key_timeformat), "HH");
            String dateTimeFormat = dateformat+" "+timeformat+":mm:ss";
            if (timeformat.equals("hh"))
            {
                // Show am/pm marker
                dateTimeFormat += " a";
            }
            Intent intent = new Intent(this, LogSessionActivity.class);
            intent.putExtra(LogSessionActivity.DATEFORMAT, dateTimeFormat);
            startActivity(intent);
            return;
        }
        else
        {
            transaction = fragmentManager.beginTransaction();
        }

        mLastSelected = position;
        transaction.commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.title_section1);
                break;
            case 1:
                mTitle = getString(com.luan.thermospy.android.R.string.title_section2);
                break;
            case 2:
                mTitle = getString(com.luan.thermospy.android.R.string.title_section3);
                break;
            case 3:
                mTitle = getString(com.luan.thermospy.android.R.string.title_section4);
                break;
            default:
                mTitle = "";
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSetupServerConnectionEstablished(String ipAddress, int port, boolean running) {
        FragmentManager fragmentManager = getFragmentManager();
        ServerSettings serverSettings = Coordinator.getInstance().getServerSettings();
        serverSettings.setRunning(running);
        serverSettings.setIpAddress(ipAddress);
        serverSettings.setPort(port);
        serverSettings.setConnected(true);

        fragmentManager.beginTransaction()
                .replace(R.id.container, SetupBoundary.newInstance(serverSettings.getIpAddress(), serverSettings.getPort()))
                .commit();

    }

    @Override
    public void onResetBounds() {
        ServerSettings serverSettings = Coordinator.getInstance().getServerSettings();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, SetupBoundary.newInstance(serverSettings.getIpAddress(), serverSettings.getPort()))
                .commit();
    }


    @Override
    public void onSetupServerFailed() {
        onServiceStatus(new ServiceStatus());
        Toast toast = Toast.makeText(getApplicationContext(), R.string.setup_server_failed, Toast.LENGTH_SHORT);
        toast.show();
        mNavigationDrawerFragment.selectItem(2);
        Coordinator.getInstance().getServerSettings().setConnected(false);
    }

    @Override
    public void onSetupServerAborted() {
        onServiceStatus(new ServiceStatus());
        Toast toast = Toast.makeText(getApplicationContext(), R.string.setup_aborted, Toast.LENGTH_SHORT);
        toast.show();
        mNavigationDrawerFragment.selectItem(2);
        Coordinator.getInstance().getServerSettings().setConnected(false);
    }


    @Override
    public void onBoundsSpecified(Boundary bounds) {
        FragmentManager fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container, SetupConfirm.newInstance())
                .commit();

    }
    @Override
    public void setupConfirmed(String s) {
        onServiceStatus(new ServiceStatus());
        // Start
        mNavigationDrawerFragment.selectItem(0);
    }

    @Override
    public void onServerNotRunning() {
        onServiceStatus(new ServiceStatus());
        mNavigationDrawerFragment.selectItem(2);
        mLastSelected = -1;

        if (isMyServiceRunning(TemperatureMonitorService.class)) {
            if (mBound) {
                unbindService(mConnection);
                mBound = false;
            }

            Intent intent = new Intent(this, TemperatureMonitorService.class);
            stopService(intent);
        }
        Coordinator.getInstance().getServerSettings().setConnected(false);
    }

    @Override
    public void onNewTemperature(String temperature) {
        Coordinator.getInstance().setTemperature(temperature);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void startBackgroundService() {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        int interval = Integer.parseInt(settings.getString(getString(R.string.pref_key_refresh_interval), "5"));
        if (!isMyServiceRunning(TemperatureMonitorService.class))
        {
            // Send intent to start the local service
            Intent intent = new Intent(this, TemperatureMonitorService.class);
            Bundle bundle = new Bundle();
            bundle.putString(TemperatureMonitorService.ServiceArguments.IP_ADDRESS, Coordinator.getInstance().getServerSettings().getIpAddress());
            bundle.putInt(TemperatureMonitorService.ServiceArguments.PORT, Coordinator.getInstance().getServerSettings().getPort());
            bundle.putInt(TemperatureMonitorService.ServiceArguments.REFRESH_RATE, interval);
            intent.putExtras(bundle);

            startService(intent);
        }

        if (isMyServiceRunning(TemperatureMonitorService.class) && !mBound) {
            // Create bind to service after the server has been started
            Intent intent = new Intent(this, TemperatureMonitorService.class);
            Bundle bundle = new Bundle();
            bundle.putInt(TemperatureMonitorService.ServiceArguments.ALARM, Integer.parseInt(Coordinator.getInstance().getAlarmSettings().getAlarm()));
            bundle.putInt(TemperatureMonitorService.ServiceArguments.ALARM_CONDITION, Coordinator.getInstance().getAlarmSettings().getAlarmCondition().getId());
            bundle.putInt(TemperatureMonitorService.ServiceArguments.REFRESH_RATE, interval);
            bundle.putBoolean(TemperatureMonitorService.ServiceArguments.ALARM_ENABLED, Coordinator.getInstance().getAlarmSettings().isAlarmSwitchEnabled());
            intent.putExtras(bundle);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }

    }


    @Override
    public void onServiceStatus(ServiceStatus status) {

        if (!Coordinator.getInstance().getServerSettings().isConnected()) {
            Coordinator.getInstance().getServerSettings().setConnected(true);
        }

        Coordinator.getInstance().getServerSettings().setRunning(status.isRunning());
        if (status.isRunning()) {
            // Service is up and running, check if we are bound if not start the service
            if (!mBound) {
                startBackgroundService();
            }
        } else {
            if (isMyServiceRunning(TemperatureMonitorService.class)) {
                // Service is stopped. Unbound and stop the service
                if (mBound) {
                    unbindService(mConnection);
                    mBound = false;
                }
                Intent intent = new Intent(this, TemperatureMonitorService.class);
                stopService(intent);
            }
        }
    }


    public void onShowCreateLogSessionDialog(DialogInterface.OnDismissListener dismissListener) {
        FragmentManager fm = getFragmentManager();
        LogSessionDialogFragment editNameDialog = LogSessionDialogFragment.newInstance(Coordinator.getInstance().getServerSettings());
        editNameDialog.setDismissListener(dismissListener);
        editNameDialog.show(fm, "fragment_edit_name");

    }

    public void onAlarmConditionChanged(AlarmCondition alarmCondition) {
            Coordinator.getInstance().getAlarmSettings().setAlarmCondition(alarmCondition);

        if (mBound)
        {
            mService.setAlarmCondition(alarmCondition);
        }
        else
        {
            startBackgroundService();
        }
    }

    public void onAlarmTextChanged(String alarmText) {
        Coordinator.getInstance().getAlarmSettings().setAlarm(alarmText);

        if (mBound)
        {
            mService.setAlarm(Integer.parseInt(alarmText));
        }
        else
        {
            startBackgroundService();
        }
    }

    @Override
    public void onAlarmSwitchChanged(Boolean isChecked) {
        CharSequence text;
        if (isChecked)
            text = getString(R.string.enable_alarm);
        else
            text = getString(R.string.disable_alarm);

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
        Coordinator.getInstance().getAlarmSettings().setAlarmSwitchEnabled(isChecked);

        if (mBound)
        {
            mService.setAlarmEnabled(isChecked);
        }
        else
        {
            startBackgroundService();
        }
    }


    @Override
    public void registerObserver(ITemperatureObserver listener) {
        mTemperatureObservers.add(listener);
    }

    @Override
    public void unregisterObserver(ITemperatureObserver listener) {
        mTemperatureObservers.remove(listener);
    }

    @Override
    public void notifyObservers(Temperature entry) {
        for (ITemperatureObserver observer : mTemperatureObservers)
        {
            observer.onTemperatureRecv(entry);
        }
    }

    @Override
    public void onTemperatureRecv(final Temperature temperature) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    //stuff that updates ui
                notifyObservers(temperature);
            }
        });

    }

    @Override
    public void onTemperatureError() {

    }
}
