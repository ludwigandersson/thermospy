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

package com.luan.thermospy.android.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.AlarmSettings;
import com.luan.thermospy.android.core.Coordinator;
import com.luan.thermospy.android.core.LocalServiceObserver;
import com.luan.thermospy.android.core.LocalServiceSubject;
import com.luan.thermospy.android.core.pojo.ServiceStatus;
import com.luan.thermospy.android.core.pojo.Temperature;
import com.luan.thermospy.android.core.rest.GetServiceStatusReq;
import com.luan.thermospy.android.fragments.tabs.AlarmTabFragment;
import com.luan.thermospy.android.fragments.tabs.ServerInfoFragment;


/**
 * The monitor fragment is responsible for displaying temperature as well as holding a sub-fragment.
 * The sub-fragment displays some server information etc.
 */
public class MonitorFragment extends android.support.v4.app.Fragment implements
        GetServiceStatusReq.OnGetServiceStatus,
        LocalServiceObserver,
        ServerInfoFragment.OnServerInfoListener,
        AlarmTabFragment.OnAlarmTabListener{
    private static final String ARG_IP_ADDRESS = "ipaddress";
    private static final String ARG_PORT = "port";
    private static final String ARG_ALARM_STR = "alarm";
    private static final String ARG_TEMPERATURE_STR = "temperature";
    private static final String LOG_TAG = MonitorFragment.class.getSimpleName();
    private static final String ARG_TEMPERATURE_SCALE_STR = "temperature_scale";
    private static final String ARG_ACTIVE_TAB = "active_tab";

    private String mIpAddress;
    private int mPort;
    private String mAlarm;
    private String mTemperatureStr = "";
    private RequestQueue mRequestQueue;
    private TextView mTemperature;

    private TextView mTemperatureScale;

    //private ToggleButton mStartStopLogSessionButton;
    //private ServerControl mServerControl;


    private ProgressDialog mProgress = null;

    private OnMonitorFragmentListener mListener;

    private GetServiceStatusReq mServiceStatusReq;

    private String mTemperatureScaleStr;

    private LocalServiceSubject mTemperatureSubject;
    private android.support.v4.app.FragmentTabHost mTabHost;
    private int mActiveTab = 0;

    public static MonitorFragment newInstance(String ip, int port, String alarm, String temperature) {
        MonitorFragment fragment = new MonitorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IP_ADDRESS, ip);
        args.putInt(ARG_PORT, port);
        args.putString(ARG_ALARM_STR, alarm);
        args.putString(ARG_TEMPERATURE_STR, temperature);
        fragment.setArguments(args);

        return fragment;
    }

    public MonitorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mIpAddress = savedInstanceState.getString(ARG_IP_ADDRESS);
            mPort = savedInstanceState.getInt(ARG_PORT);
            mAlarm = savedInstanceState.getString(ARG_ALARM_STR);
            mTemperatureStr = savedInstanceState.getString(ARG_TEMPERATURE_STR);
            mTemperatureScaleStr = savedInstanceState.getString(ARG_TEMPERATURE_SCALE_STR);
            mActiveTab = savedInstanceState.getInt(ARG_ACTIVE_TAB);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mTemperature = (TextView) v.findViewById(R.id.text);
        mTemperatureScale = (TextView)v.findViewById(R.id.txtTemperatureScale);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/digital-7.ttf");
        mTemperature.setTypeface(typeface);
        mTemperature.setTextSize(21);
        mTemperature.setText(mTemperatureStr);
        mTemperatureScale.setTextSize(16);

        mTemperatureScale.setText(mTemperatureScaleStr);
        View o = v.findViewById(R.id.tabHost);
        mTabHost = (android.support.v4.app.FragmentTabHost)o;
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        RealtimeChartFragment realtimeChartFragment = RealtimeChartFragment.newInstance(mIpAddress, mPort);
        mTabHost.addTab(mTabHost.newTabSpec("realtime").setIndicator("Monitoring"),
                RealtimeChartFragment.class, realtimeChartFragment.getArguments());
        //Alarm alarm = Alarm.newInstance(Coordinator.getInstance().getServerSettings(), Coordinator.getInstance().getAlarmSettings());
        AlarmSettings alarmSettings = Coordinator.getInstance().getAlarmSettings();
        AlarmTabFragment alarm = AlarmTabFragment.newInstance(alarmSettings.getAlarm(), alarmSettings.getAlarmCondition(), alarmSettings.isAlarmSwitchEnabled());
        mTabHost.addTab(mTabHost.newTabSpec("alarm").setIndicator("Alarm"),
                AlarmTabFragment.class, alarm.getArguments());
        //ServerControl control = ServerControl.newInstance(mIpAddress, mPort, Coordinator.getInstance().getServerSettings().isRunning());
        ServerInfoFragment infoFragment = ServerInfoFragment.newInstance(mIpAddress, mPort);
        mTabHost.addTab(mTabHost.newTabSpec("info").setIndicator("Info"),
                ServerInfoFragment.class, infoFragment.getArguments());



        mTabHost.setCurrentTab(mActiveTab);
        /*mStartStopLogSessionButton = (ToggleButton)v.findViewById(R.id.btnStartStopLogSession);
        mStartStopLogSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mStartStopLogSessionButton.isChecked()) {
                    mListener.onShowCreateLogSessionDialog(MonitorFragment.this);

                } else {
                    requestStopLogSession();
                }
                mStartStopLogSessionButton.setChecked(mStartStopLogSessionButton.isChecked());



            }
        });
        mStartStopLogSessionButton.setChecked(mLogSessionActive);
        mStartStopLogSessionButton.setText(getString(R.string.start_recording));*/


        return v;
    }



    public void getServerStatus()
    {
        mProgress = new ProgressDialog(getActivity());
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.setTitle("Please wait");
        mProgress.setMessage("Fetching server status...");
        mServiceStatusReq.request(mIpAddress, mPort);
        mProgress.show();
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnMonitorFragmentListener) activity;
            mTemperatureSubject = (LocalServiceSubject)activity;
            if (getArguments() != null) {
                mIpAddress = getArguments().getString(ARG_IP_ADDRESS);
                mPort = getArguments().getInt(ARG_PORT);
                mAlarm = getArguments().getString(ARG_ALARM_STR);
                mTemperatureStr = getArguments().getString(ARG_TEMPERATURE_STR);

            }

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAlarmFragmentListener");
        }
        mRequestQueue = Coordinator.getInstance().getRequestQueue();
        mServiceStatusReq = new GetServiceStatusReq(mRequestQueue, this);

    }

    @Override
    public void onResume()
    {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String temperatureScale = settings.getString(getString(R.string.pref_key_temperature_degree), "");
        if (temperatureScale.equals("1"))
        {
            mTemperatureScaleStr = getString(R.string.temperature_scale_celsius);
        }
        else
        {
            mTemperatureScaleStr = getString(R.string.temperature_scale_fahrenheit);
        }
        mTemperatureScale.setText(mTemperatureScaleStr);
        mTemperatureSubject.registerObserver(this);
        getServerStatus();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();

        }
        mServiceStatusReq.cancel();

    }

    @Override
    public void onStop()
    {
        super.onStop();
        mTemperatureSubject.unregisterObserver(this);
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();

        }

        mServiceStatusReq.cancel();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString(ARG_IP_ADDRESS, mIpAddress);
        outState.putInt(ARG_PORT, mPort);
        outState.putString(ARG_ALARM_STR, mAlarm);
        outState.putString(ARG_TEMPERATURE_STR, mTemperatureStr);
        outState.putString(ARG_TEMPERATURE_SCALE_STR, mTemperatureScaleStr);
        outState.putInt(ARG_ACTIVE_TAB, mTabHost.getCurrentTab());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onServiceStatusRecv(ServiceStatus status) {

        if (!status.isRunning()) {
            mTemperature.setText("--");
        }

       mProgress.dismiss();
       mListener.onServiceStatus(status);
    }


    @Override
    public void onServiceStatus(ServiceStatus serviceStatus) {
        onServiceStatusRecv(serviceStatus);
    }

    public void onServiceStatusError() {
        mProgress.dismiss();
        Toast t = Toast.makeText(getActivity(), R.string.lost_connection, Toast.LENGTH_SHORT);
        t.show();
        mListener.onServerNotRunning();
    }

    @Override
    public void onTemperatureRecv(Temperature temperature) {
        mTemperature.setText(temperature.toString());
    }

    @Override
    public void onServerError() {
        mTemperature.setText("--");
    }

    @Override
    public void onAlarmTriggered() {
        // Dont care... yet
    }

    @Override
    public void onAlarmEnableSwitchChanged(boolean checked) {
        mListener.onAlarmEnableSwitchChanged(checked);
    }

    @Override
    public void onAlarmChanged(int alarm) {
        mListener.onAlarmChanged(alarm);
    }

    @Override
    public void onAlarmConditionChanged(AlarmCondition alarmCondition) {
        mListener.onAlarmConditionChanged(alarmCondition);
    }

    public interface OnMonitorFragmentListener {
        public void onServerNotRunning();
        public void onServiceStatus(ServiceStatus status);


        void onAlarmEnableSwitchChanged(boolean checked);

        void onAlarmChanged(int alarm);

        void onAlarmConditionChanged(AlarmCondition alarmCondition);
    }

}
