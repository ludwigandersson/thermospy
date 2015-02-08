package com.luan.thermospy.android.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.luan.thermospy.android.R;
import com.luan.thermospy.android.fragments.SettingsFragment;

public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new SettingsFragment())
                    .commit();
        }
    }

}
