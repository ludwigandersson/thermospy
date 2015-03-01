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
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.ServerSettings;
import com.luan.thermospy.android.core.pojo.ServiceStatus;


/**
 * The alarm class is responsible for handling Alarm settings.
 */
public class Alarm extends Fragment implements ServerControl.OnServerControlListener {

    private static final String ARG_ALARM_STRING = "alarm";
    private static final String ARG_ALARM_ENABLED = "alarmenabled";
    private static final String ARG_PORT = "port";
    private static final String ARG_IP_ADDRESS = "ipaddress";
    private static final String ARG_SERVER_RUNNING = "serverrunning";
    private static final String ARG_ALARM_CONDITION = "alarmcondition";
    private static final String ARG_TEMPERATURE_SCALE = "temperature_scale";

    private String mAlarm;
    private Boolean mAlarmSwitchChecked;
    private String mIpAddress;
    private int mPort;
    private boolean mRunning;
    private AlarmCondition mAlarmCondition;

    private Switch mAlarmSwitch;
    private TextView mAlarmText;
    private TextView mTemperatureText;
    private Spinner mAlarmConditionSpinner;
    private OnAlarmFragmentListener mListener;
    private ServerControl mServerControl;
    private String mTemperatureScaleStr;
    private TextView mTemperatureScale;


    public static Alarm newInstance(String alarm, Boolean switchEnabled, ServerSettings serverSettings, AlarmCondition alarmCondition) {
        Alarm fragment = new Alarm();
        Bundle args = new Bundle();

        args.putString(ARG_ALARM_STRING, alarm);
        args.putBoolean(ARG_ALARM_ENABLED, switchEnabled);
        args.putString(ARG_IP_ADDRESS, serverSettings.getIpAddress());
        args.putInt(ARG_PORT, serverSettings.getPort());
        args.putBoolean(ARG_SERVER_RUNNING, serverSettings.isRunning());
        args.putInt(ARG_ALARM_CONDITION, alarmCondition.getId());
        fragment.setArguments(args);
        return fragment;
    }

    public Alarm() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAlarm = getArguments().getString(ARG_ALARM_STRING);
            mAlarmSwitchChecked = getArguments().getBoolean(ARG_ALARM_ENABLED);
            mIpAddress = getArguments().getString(ARG_IP_ADDRESS);
            mPort = getArguments().getInt(ARG_PORT);
            mRunning = getArguments().getBoolean(ARG_SERVER_RUNNING);
            mAlarmCondition = AlarmCondition.fromInt(getArguments().getInt(ARG_ALARM_CONDITION));
            mTemperatureScaleStr = getArguments().getString(ARG_TEMPERATURE_SCALE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_alarm, container, false);

        mAlarmSwitch = (Switch)v.findViewById(R.id.switch1);
        mAlarmSwitch.setChecked(mAlarmSwitchChecked);
        mAlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAlarmSwitchChecked = isChecked;
                setAlarmText();
                mListener.onAlarmSwitchChanged(isChecked);
                maybeDisableAlarm();

            }
        });

        mAlarmText = (TextView)v.findViewById(R.id.txtViewAlarm);
        mAlarmText.setText(mAlarm);
        mAlarmText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlarmPicker();
            }
        });

        mTemperatureText = (TextView)v.findViewById(R.id.text);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/digital-7.ttf");
        mTemperatureText.setTypeface(typeface);
        mTemperatureText.setTextSize(72);

        mTemperatureScale = (TextView)v.findViewById(R.id.txtTemperatureScale);
        mTemperatureScale.setTextSize(36);
        mTemperatureScale.setText(mTemperatureScaleStr);

        mAlarmConditionSpinner = (Spinner)v.findViewById(R.id.spinner);

        String[] items = new String[]{getString(R.string.gt_or_equal), getString(R.string.lt_or_equal)};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        mAlarmConditionSpinner.setAdapter(adapter);
        mAlarmConditionSpinner.setSelection(mAlarmCondition.getId());
        mAlarmConditionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AlarmCondition selected = AlarmCondition.fromInt(position);
                if (selected.getId() != mAlarmCondition.getId())
                {
                    mAlarmCondition = selected;
                    mListener.onAlarmConditionChanged(mAlarmCondition);
                    maybeDisableAlarm();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FragmentManager manager = getChildFragmentManager();
        manager.beginTransaction().replace(R.id.server_control_alarm, mServerControl).commit();

        setAlarmText();

        return v;
    }

    private void setAlarmText()
    {
        if (!mAlarmText.getText().toString().equals(mAlarm)) {
            mListener.onAlarmTextChanged(mAlarm);
        }
        AlarmCondition selectedItem = AlarmCondition.fromInt(mAlarmConditionSpinner.getSelectedItemPosition());
        if (mAlarmCondition.getId() != selectedItem.getId())
        {
            mAlarmCondition = selectedItem;
            mListener.onAlarmConditionChanged(mAlarmCondition);
        }
        mAlarmText.setText(mAlarm);
        if (mServerControl != null) {
            if (mAlarmSwitchChecked) {
                mServerControl.setAlarmText(mAlarm);
            } else {
                mServerControl.setAlarmText(getString(R.string.not_enabled));
            }
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnAlarmFragmentListener) activity;
            if (getArguments() != null)
            {
                mAlarm = getArguments().getString(ARG_ALARM_STRING);
                mAlarmSwitchChecked = getArguments().getBoolean(ARG_ALARM_ENABLED);
                mIpAddress = getArguments().getString(ARG_IP_ADDRESS);
                mPort = getArguments().getInt(ARG_PORT);
                mRunning = getArguments().getBoolean(ARG_SERVER_RUNNING);
                if (mAlarmSwitchChecked)
                    mServerControl = ServerControl.newInstance(mIpAddress, mPort, mRunning, mAlarm);
                else
                    mServerControl = ServerControl.newInstance(mIpAddress, mPort, mRunning, getString(R.string.not_enabled));

                mAlarmCondition = AlarmCondition.fromInt(getArguments().getInt(ARG_ALARM_CONDITION));
            }
            if (mListener == null)
                throw new ClassCastException();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAlarmFragmentListener");
        }
    }

    @Override
    public void onResume() {
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_ALARM_STRING, mAlarm);
        outState.putBoolean(ARG_ALARM_ENABLED, mAlarmSwitchChecked);
        outState.putString(ARG_IP_ADDRESS, mIpAddress);
        outState.putInt(ARG_PORT, mPort);
        outState.putBoolean(ARG_SERVER_RUNNING, mRunning);
        outState.putInt(ARG_ALARM_CONDITION, mAlarmCondition.getId());
        outState.putString(ARG_TEMPERATURE_SCALE, mTemperatureScaleStr);
    }

    private void showAlarmPicker()
    {
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View npView = inflater.inflate(R.layout.number_picker_dialog_layout, null);
        final NumberPicker hundreds = (NumberPicker)npView.findViewById(R.id.numberPicker3);
        final NumberPicker tens = (NumberPicker)npView.findViewById(R.id.numberPicker2);
        final NumberPicker ones = (NumberPicker)npView.findViewById(R.id.numberPicker);

        hundreds.setMinValue(0);
        hundreds.setMaxValue(9);
        tens.setMinValue(0);
        tens.setMaxValue(9);
        ones.setMinValue(0);
        ones.setMaxValue(9);

        hundreds.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        tens.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        ones.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        try
        {
            int alarm = Integer.parseInt(mAlarm);
            hundreds.setValue(alarm/100);
            tens.setValue((alarm%100)/10);
            ones.setValue(alarm%10);
        } catch (NumberFormatException ex)
        {
            hundreds.setValue(0);
            tens.setValue(0);
            ones.setValue(0);
        }

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Set Alarm")
                .setView(npView)
                .setPositiveButton(R.string.dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                mAlarm = hundreds.getValue() == 0 ?
                                        Integer.toString(tens.getValue() * 10 + ones.getValue()) :
                                        Integer.toString(hundreds.getValue() * 100 + tens.getValue() * 10 + ones.getValue());
                                setAlarmText();
                                maybeDisableAlarm();
                            }
                        })
                .setNegativeButton(R.string.dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                .create();
        dialog.show();
    }

    @Override
    public void onNewTemperature(String text) {
        mTemperatureText.setText(text);
        mListener.onNewTemperature(text);
        maybeDisableAlarm();
    }

    private void maybeDisableAlarm() {
        if (mAlarmSwitchChecked) {
            try {
                // todo Ugly...
                int alarm = Integer.parseInt(mAlarmText.getText().toString());
                int tval = Integer.parseInt(mTemperatureText.getText().toString());
                boolean playSound = mAlarmCondition.evaluate(tval, alarm);
                if (playSound) {
                    mAlarmSwitchChecked = false;
                    mAlarmSwitch.setChecked(false);
                    mListener.onAlarmSwitchChanged(false);
                }

            } catch (NumberFormatException ex) {

            }
        }
    }

    @Override
    public void onConnectionLost() {
        mListener.onServiceStatus(new ServiceStatus());
    }

    @Override
    public void onServiceStatus(ServiceStatus status) {
        mListener.onServiceStatus(status);
    }

    public interface OnAlarmFragmentListener {
        public void onAlarmTextChanged(String alarmText);
        public void onAlarmSwitchChanged(Boolean isChecked);
        public void onServiceStatus(ServiceStatus status);
        public void onAlarmConditionChanged(AlarmCondition mAlarmCondition);
        public void onNewTemperature(String text);
    }

}
