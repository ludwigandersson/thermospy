package com.luan.thermospy.android.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;

import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.Coordinator;
import com.luan.thermospy.android.core.pojo.LogSession;
import com.luan.thermospy.android.fragments.temperaturelog.LogSessionFragment;
import com.luan.thermospy.android.fragments.temperaturelog.TemperatureGraph;

public class LogSessionActivity extends ActionBarActivity implements LogSessionFragment.OnLogSessionFragmentListener, TemperatureGraph.OnTemperatureGraphFragmentListener {

    private static final String LOG_TAG = LogSessionActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_session);
        setTitle(getString(R.string.temperature_log));
        if (savedInstanceState == null) {
            int port = Coordinator.getInstance().getServerSettings().getPort();
            String ipAddress = Coordinator.getInstance().getServerSettings().getIpAddress();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, LogSessionFragment.newInstance(ipAddress, port))
                    .commit();
        }
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
        getFragmentManager().beginTransaction()
                .add(R.id.container, TemperatureGraph.newInstance(ipAddress, port, session.getId()))
                .addToBackStack(null)
                .commit();
        getFragmentManager().executePendingTransactions();
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
        Log.d(LOG_TAG, "An error occured within the log session fragment...");
    }

    @Override
    public void onError() {
        Log.d(LOG_TAG, "An error occured within the temperature graph fragment...");
    }
}
