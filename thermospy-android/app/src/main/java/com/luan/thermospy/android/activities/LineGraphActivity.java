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

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.pojo.LogSession;
import com.luan.thermospy.android.core.pojo.TemperatureEntry;
import com.luan.thermospy.android.core.rest.GetTemperatureEntryListReq;

import org.json.JSONException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LineGraphActivity extends ActionBarActivity implements GetTemperatureEntryListReq.OnGetTemperatureEntryListener {

    public static final String ARG_IP_ADDRESS = "ipaddress";
    public static final String ARG_PORT = "port";
    public static final String ARG_SESSION_ID = "sessionid";
    public static final String ARG_DATEFORMAT = "dateformat";
    private static final String LOG_TAG = LineGraphActivity.class.getSimpleName();

    private LogSession mLogSession;
    private int mPort;
    private String mIpAddress;

    private ProgressDialog mProgressDialog;


    private RequestQueue mRequestQueue;
    private LineChart mChart;
    private GetTemperatureEntryListReq mGetTemperatureEntryListReq;

    private ShareActionProvider mShareActionProvider;
    private List<TemperatureEntry> mTemperatureList;
    private String mDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);

        if (savedInstanceState != null)
        {
            mLogSession = LogSession.fromJson(savedInstanceState.getString(ARG_SESSION_ID));
            mPort = savedInstanceState.getInt(ARG_PORT);
            mIpAddress = savedInstanceState.getString(ARG_IP_ADDRESS);
            mDateFormat = savedInstanceState.getString(ARG_DATEFORMAT);
        }
        else
        {
            mLogSession = LogSession.fromJson(getIntent().getStringExtra(ARG_SESSION_ID));
            mPort = getIntent().getIntExtra(ARG_PORT,0);
            mIpAddress = getIntent().getStringExtra(ARG_IP_ADDRESS);
            mDateFormat = getIntent().getStringExtra(ARG_DATEFORMAT);
        }

        mRequestQueue = Volley.newRequestQueue(this);
        mGetTemperatureEntryListReq = new GetTemperatureEntryListReq(mRequestQueue, this);

        setTitle(mLogSession.getName());

        mChart = (LineChart) findViewById(R.id.chart);


        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable value highlighting
        mChart.setHighlightEnabled(true);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        //MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // set the marker to the chart
        //mChart.setMarkerView(mv);

        // enable/disable highlight indicators (the lines that indicate the
        // highlighted Entry)
        mChart.setHighlightIndicatorEnabled(false);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_line_graph, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_item_share);
        mShareActionProvider =  (ShareActionProvider)MenuItemCompat.getActionProvider(menuItem);


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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Intent createShareIntent() {
        File outputDir = getExternalCacheDir(); // context being the Activity pointer
        File outputFile = null;
        try {
            outputFile = File.createTempFile("export", ".csv", outputDir);

            BufferedWriter writer = null;
            try
            {
                writer = new BufferedWriter( new FileWriter( outputFile.getAbsolutePath()));

                SimpleDateFormat df = new SimpleDateFormat(mDateFormat);

                for (TemperatureEntry entry : mTemperatureList)
                {
                    StringBuilder builder = new StringBuilder();
                    builder.append(entry.getId()).append(",")
                            .append(df.format(entry.getTimestamp().getTime()))
                            .append(",")
                            .append(entry.getTemperature())
                            .append("\n");

                    writer.write(builder.toString());
                }
                writer.flush();


            }
            catch ( IOException e)
            {
            }
            finally
            {
                try
                {
                    if ( writer != null)
                        writer.close( );
                }
                catch ( IOException e)
                {
                }
            }

        } catch (IOException | NullPointerException npe) {

        }

        if (outputFile != null) {
            Uri u1 = Uri.fromFile(outputFile);

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Temperature log from Thermospy");
            sendIntent.putExtra(Intent.EXTRA_EMAIL, mLogSession.getName());
            sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
            sendIntent.setType("application/csv");
            sendIntent.addFlags(
                    Intent.FLAG_GRANT_READ_URI_PERMISSION);
            return sendIntent;
        }
        return null;
    }
    private void requestTemperatureEntries() {
        if (mProgressDialog == null)
        {
            mProgressDialog = new ProgressDialog(this);
        }

        mProgressDialog.setCanceledOnTouchOutside(false);

        mProgressDialog.setTitle("Please wait");
        mProgressDialog.setMessage("Initializing graph...");
        mGetTemperatureEntryListReq.setTemperatureEntryId(mLogSession.getId());
        mGetTemperatureEntryListReq.request(mIpAddress, mPort);
        mProgressDialog.show();
    }

    @Override
    public void onTemperatureEntryRecv(List<TemperatureEntry> logSessionList) {
        if (logSessionList.size() > 0) {
            mTemperatureList =logSessionList;
            mShareActionProvider.setShareIntent(createShareIntent());

            setupLineChart();
            setData(logSessionList);
            showStats();
        }
        else
        {
            //dmListener.onError();
        }
        mProgressDialog.dismiss();
    }

    private void showStats() {

    }

    private void setupLineChart()
    {

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable value highlighting
        mChart.setHighlightIndicatorEnabled(false);
        mChart.setDrawGridBackground(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);

        // enable/disable highlight indicators (the lines that indicate the
        // highlighted Entry)
        mChart.setHighlightIndicatorEnabled(false);

    }

    private void setData(List<TemperatureEntry> logSessionList) {

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < logSessionList.size(); i++) {
            xVals.add((i) + "");
            yVals.add(new Entry(logSessionList.get(i).getTemperature(), i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, getString(R.string.temperature));
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(2f);
        set1.setDrawValues(false);
        set1.setCircleSize(4f);
        set1.setFillAlpha(65);
        set1.setFillColor(Color.BLACK);

        // set1.setShader(new LinearGradient(0, 0, 0, mChart.getHeight(),
        // Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR));

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);


        LimitLine ll1 = new LimitLine(mLogSession.getTargetTemperature(), "Target temperature");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.POS_RIGHT);
        ll1.setTextSize(10f);


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.addLimitLine(ll1);
        leftAxis.setStartAtZero(true);

        mChart.getAxisRight().setEnabled(false);

        // set data
        mChart.setData(data);


        mChart.invalidate();
    }

    @Override
    public void onTemperatureEntryError() {
        finish();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mGetTemperatureEntryListReq.cancel();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString(ARG_IP_ADDRESS, mIpAddress);
        outState.putInt(ARG_PORT, mPort);
        outState.putString(ARG_DATEFORMAT, mDateFormat);
        try {
            outState.putString(ARG_SESSION_ID, LogSession.toJson(mLogSession).toString());
        } catch (JSONException e) {

        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();

        }
        mGetTemperatureEntryListReq.cancel();
        mRequestQueue.cancelAll(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        requestTemperatureEntries();
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
}
