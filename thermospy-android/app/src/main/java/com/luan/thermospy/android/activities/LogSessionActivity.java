package com.luan.thermospy.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.Coordinator;
import com.luan.thermospy.android.core.pojo.TemperatureEntry;
import com.luan.thermospy.android.fragments.temperaturelog.LogSessionFragment;

import java.util.List;

public class LogSessionActivity extends ActionBarActivity implements LogSessionFragment.OnLogSessionFragmentListener {

    private static final String LOG_TAG = LogSessionActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_session);
        if (savedInstanceState == null) {
            int port = Coordinator.getInstance().getServerSettings().getPort();
            String ipAddress = Coordinator.getInstance().getServerSettings().getIpAddress();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, LogSessionFragment.newInstance(ipAddress, port))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_session, menu);
        return true;
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
    public void onShowTemperatureList(List<TemperatureEntry> temperatureEntryList) {
        Log.d(LOG_TAG, "Show temperature list. Number of items: "+temperatureEntryList.size());
    }

    @Override
    public void onLogSessionListError() {
        Log.d(LOG_TAG, "An error occured within the log session fragment...");
    }
}
