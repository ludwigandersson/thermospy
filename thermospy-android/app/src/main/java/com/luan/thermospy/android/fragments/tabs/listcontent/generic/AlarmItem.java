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

package com.luan.thermospy.android.fragments.tabs.listcontent.generic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.luan.thermospy.android.R;
import com.luan.thermospy.android.fragments.AlarmCondition;

/**
 * Created by ludde on 15-03-16.
 */
public class AlarmItem implements ListContent {
    private String mAlarm;
    private AlarmCondition mAlarmCondition;
    private boolean mAlarmEnabled;
    private Context mContext;

    TextView mTxtAlarm;
    Switch mSwitchAlarmEnabled;
    Spinner mAlarmConditionSpinner;

    AlarmItemInteractionListener mListener;

    public void setAlarmEnabled(boolean alarmEnabled) {
        if (alarmEnabled != mAlarmEnabled) {
            this.mAlarmEnabled = alarmEnabled;
            mSwitchAlarmEnabled.setChecked(mAlarmEnabled);
        }
    }

    public interface AlarmItemInteractionListener {
        void onAlarmEnableSwitchChanged(boolean checked);
        void onAlarmChanged(int alarm);
        void onAlarmConditionChanged(AlarmCondition alarmCondition);
    }

    public AlarmItem(Context context, String alarm, AlarmCondition alarmCondition, boolean alarmEnabled, AlarmItemInteractionListener listener) {
        mAlarm = alarm;
        mAlarmCondition = alarmCondition;
        mAlarmEnabled = alarmEnabled;
        mContext = context;
        mListener = listener;
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.alarm_item;
    }

    @Override
    public View populateContentView(View view) {
        mTxtAlarm = (TextView)view.findViewById(R.id.text_alarm);
        mSwitchAlarmEnabled = (Switch)view.findViewById(R.id.switch_alarm_enabled);
        mAlarmConditionSpinner = (Spinner)view.findViewById(R.id.spinner_alarm_condition);

        mTxtAlarm.setText(mAlarm);
        mTxtAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlarmPicker();
            }
        });
        mSwitchAlarmEnabled.setChecked(mAlarmEnabled);
        mSwitchAlarmEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAlarmEnabled = isChecked;
                mListener.onAlarmEnableSwitchChanged(mAlarmEnabled);
            }
        });

        mAlarmConditionSpinner.setSelection(mAlarmCondition.getId());
        String[] items = new String[]{mContext.getResources().getString(R.string.gt_or_equal), mContext.getResources().getString(R.string.lt_or_equal)};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, items);
        mAlarmConditionSpinner.setAdapter(adapter);
        mAlarmConditionSpinner.setSelection(mAlarmCondition.getId());
        mAlarmConditionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AlarmCondition selected = AlarmCondition.fromInt(position);
                if (selected.getId() != mAlarmCondition.getId())
                {
                    mAlarmCondition = selected;
                    mAlarmConditionSpinner.setSelection(mAlarmCondition.getId());
                    mListener.onAlarmConditionChanged(mAlarmCondition);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }
    private void showAlarmPicker()
    {
        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle("Set Alarm")
                .setView(npView)
                .setPositiveButton(R.string.dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                mAlarm = hundreds.getValue() == 0 ?
                                        Integer.toString(tens.getValue() * 10 + ones.getValue()) :
                                        Integer.toString(hundreds.getValue() * 100 + tens.getValue() * 10 + ones.getValue());

                                mTxtAlarm.setText(mAlarm);
                                mListener.onAlarmChanged(Integer.parseInt(mAlarm));
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
}
