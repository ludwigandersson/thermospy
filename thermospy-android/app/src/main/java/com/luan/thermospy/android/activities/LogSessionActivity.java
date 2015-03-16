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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;

import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.Coordinator;
import com.luan.thermospy.android.core.pojo.LogSession;
import com.luan.thermospy.android.fragments.temperaturelog.LogSessionFragment;

import org.json.JSONException;


/**
 * The LogSessionActivity is responsible for displaying a list of the available Log Sessions from
 * the server.
 */
public class LogSessionActivity extends ActionBarActivity implements LogSessionFragment.OnLogSessionFragmentListener {

    private static final String LOG_TAG = LogSessionActivity.class.getSimpleName();
    public static final String DATEFORMAT = "key_dateformat";
    private String mDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_session);
        setTitle(getString(R.string.temperature_log));

        if (savedInstanceState != null) {
            mDateFormat = savedInstanceState.getString(DATEFORMAT);
        }
        else
        {
            mDateFormat = getIntent().getStringExtra(DATEFORMAT);
        }
        int port = Coordinator.getInstance().getServerSettings().getPort();
        String ipAddress = Coordinator.getInstance().getServerSettings().getIpAddress();
        getFragmentManager().beginTransaction()
        .replace(R.id.container, LogSessionFragment.newInstance(ipAddress, port, mDateFormat))
                .commit();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                android.app.FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    setTitle(getString(R.string.temperature_log));
                    fm.popBackStack();
                    return true;
                }
                break;
            default:
                break;

        }
        return false;
    }
    @Override
    public void onShowTemperatureList(LogSession session) {

        setTitle(session.getName());
        int port = Coordinator.getInstance().getServerSettings().getPort();
        String ipAddress = Coordinator.getInstance().getServerSettings().getIpAddress();

        Intent intent = new Intent(this, LineGraphActivity.class);
        intent.putExtra(LineGraphActivity.ARG_DATEFORMAT, mDateFormat);
        intent.putExtra(LineGraphActivity.ARG_IP_ADDRESS, ipAddress);
        intent.putExtra(LineGraphActivity.ARG_PORT, port);
        try {
            intent.putExtra(LineGraphActivity.ARG_SESSION_ID, LogSession.toJson(session).toString());
        } catch (JSONException e) {

        }
        startActivity(intent);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        setTitle(getString(R.string.temperature_log));
        android.app.FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onLogSessionListError() {
        Log.d(LOG_TAG, "An error occurred within the log session fragment...");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString(DATEFORMAT, mDateFormat);

        super.onSaveInstanceState(outState);
    }



}
