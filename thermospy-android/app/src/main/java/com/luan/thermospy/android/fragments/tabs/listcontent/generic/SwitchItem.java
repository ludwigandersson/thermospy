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

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.luan.thermospy.android.R;

/**
 * Created by ludde on 15-03-15.
 */
public class SwitchItem implements ListContent {
    private final String mName;
    private final View.OnClickListener mOnClickListener;
    private boolean mRunning;
    private Switch mServiceSwitch;
    
    public SwitchItem(String name, boolean running, View.OnClickListener onClickListener)
    {
        mName = name;
        mRunning = running;
        mOnClickListener = onClickListener;

    }

    public void setRunning(boolean running)
    {
        mRunning = running;
        if (mServiceSwitch != null) {
            mServiceSwitch.setChecked(mRunning);
        }
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.simple_checkbox_item;
    }

    @Override
    public View populateContentView(View view) {
        TextView name = (TextView) view.findViewById(R.id.txtText);
        mServiceSwitch = (Switch) view.findViewById(R.id.switch2);

        name.setText(mName);
        mServiceSwitch.setChecked(mRunning);

        mServiceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mOnClickListener.onClick(buttonView);
            }
        });
        
        return view;
    }
}
