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
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.LocalServiceObserver;
import com.luan.thermospy.android.core.LocalServiceSubject;
import com.luan.thermospy.android.core.pojo.LogSession;
import com.luan.thermospy.android.core.pojo.Temperature;
import com.luan.thermospy.android.core.pojo.TemperatureEntry;
import com.luan.thermospy.android.core.rest.GetActiveLogSessionReq;
import com.luan.thermospy.android.core.rest.GetTemperatureHistoryReq;
import com.luan.thermospy.android.core.rest.StopLogSessionReq;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.luan.thermospy.android.core.LocalServiceObserver} interface
 * to handle interaction events.
 * Use the {@link RealtimeChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RealtimeChartFragment extends Fragment implements          StopLogSessionReq.OnStopLogSessionListener,       DialogInterface.OnDismissListener,OnChartValueSelectedListener, LocalServiceObserver, GetTemperatureHistoryReq.OnGetTemperatureHistoryListener, GetActiveLogSessionReq.OnGetActiveLogSessionsListener {

    private final static String ARG_IP_ADDRESS = "ipaddress";
    private final static String ARG_PORT = "port";
    private final static String ARG_LOG_SESSION_ACTIVE = "logsessionactive";

    private LocalServiceSubject mTemperatureSubject;
    private LineChart mChart;
    private String LOG_TAG = RealtimeChartFragment.class.getSimpleName();
    private long mMaxY = Integer.MIN_VALUE;
    private long mMinY = Integer.MAX_VALUE;

    private String mIpAddress;
    private int mPort;
    private RequestQueue mRequestQueue;
    private GetTemperatureHistoryReq mGetTemperatureHistoryReq;
    private GetActiveLogSessionReq mGetActiveLogSession;
    private StopLogSessionReq mStopLogSessionReq;
    private ImageButton mImageButton;
    private TextView mLogSessionText;
    private boolean mActiveLogSession = false;
    private ProgressDialog mProgress;
    private OnRealtimeChartFragmentListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RealtimeChartFragment.
     * @param ipAddress
     * @param port
     */

    public static RealtimeChartFragment newInstance(String ipAddress, int port) {
        RealtimeChartFragment fragment = new RealtimeChartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IP_ADDRESS, ipAddress);
        args.putInt(ARG_PORT, port);
        fragment.setArguments(args);
        return fragment;
    }

    public RealtimeChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
        {
            mIpAddress = savedInstanceState.getString(ARG_IP_ADDRESS);
            mPort = savedInstanceState.getInt(ARG_PORT);
            mActiveLogSession = savedInstanceState.getBoolean(ARG_LOG_SESSION_ACTIVE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_realtime_chart, container, false);

        mImageButton = (ImageButton)v.findViewById(R.id.imageButton);
        mLogSessionText = (TextView)v.findViewById(R.id.txt_startstop_logsession);
        final LinearLayout layout = (LinearLayout)v.findViewById(R.id.log_session_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mActiveLogSession) {
                    mListener.onShowCreateLogSessionDialog(RealtimeChartFragment.this);

                } else {
                    requestStopLogSession();
                }

                
            }
        });



        mChart = (LineChart)v.findViewById(R.id.chart);
        mChart.setOnChartValueSelectedListener(this);

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
        mChart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);

        LineData data = new LineData();
        data.setValueTextColor(Color.BLACK);

        // add empty data
        mChart.setData(data);

        //Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);
       // l.setTypeface(tf);
        l.setTextColor(Color.BLACK);

        XAxis xl = mChart.getXAxis();
        //xl.setTypeface(tf);
        xl.setTextColor(Color.BLACK);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);

        YAxis leftAxis = mChart.getAxisLeft();
        //leftAxis.setTypeface(tf);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setAxisMaxValue(120f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        return v;
    }

    private void addEntry(Temperature temperature) {

        LineData data = mChart.getData();

        if (data != null) {

            LineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            TemperatureEntry temperatureEntry = new TemperatureEntry();
            temperatureEntry.setTemperature(temperature.getTemperature());
            temperatureEntry.setTimestamp(new Date(temperature.getTimestamp()));

            // add a new x-value first
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            data.addXValue(dateFormat.format(temperatureEntry.getTimestamp()));
            data.addEntry(new Entry(temperatureEntry.getTemperature() == Integer.MIN_VALUE ? 0 : temperatureEntry.getTemperature(), set.getEntryCount(), temperatureEntry), 0);

            // let the chart know it's data has changed
            mChart.notifyDataSetChanged();

            // limit the number of visible entries
            mChart.setVisibleXRange(5);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            mChart.moveViewToX(data.getXValCount()-6);

            // this automatically refreshes the chart (calls invalidate())
//             mChart.moveViewTo(data.getXValCount()-7, 55f, AxisDependency.LEFT);

            // redraw the chart
//            mChart.invalidate();

            if (set.getEntryCount() > 0 && temperature.getTemperature() != Integer.MIN_VALUE) {


                if (temperature.getTemperature() > mMaxY)
                {
                    mMaxY = temperature.getTemperature()+20;
                }
                if (temperature.getTemperature() < mMinY)
                {
                    mMinY = temperature.getTemperature()-20;
                }

                YAxis yaxis = mChart.getAxisLeft();
                yaxis.setAxisMaxValue(mMaxY);
                yaxis.setAxisMinValue(mMinY);
            }

        }
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Temperature");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(ColorTemplate.getHoloBlue());
        set.setLineWidth(2f);
        set.setCircleSize(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.BLACK);
        set.setValueTextSize(10f);
        return set;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRealtimeChartFragmentListener)activity;
            if (getArguments() != null) {
                mIpAddress = getArguments().getString(ARG_IP_ADDRESS);
                mPort = getArguments().getInt(ARG_PORT);
            }
            mTemperatureSubject = (LocalServiceSubject) activity;

            mRequestQueue = Volley.newRequestQueue(getActivity());
            mGetTemperatureHistoryReq = new GetTemperatureHistoryReq(mRequestQueue, this);
            mGetActiveLogSession = new GetActiveLogSessionReq(mRequestQueue,this);
            mStopLogSessionReq = new StopLogSessionReq(mRequestQueue, this);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mTemperatureSubject = null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mGetTemperatureHistoryReq.cancel();
        mGetActiveLogSession.cancel();
        mStopLogSessionReq.cancel();
        if (mProgress != null) mProgress.dismiss();
    }
    @Override
    public void onResume()
    {
        super.onResume();
        fetchHistory();
        fetchActiveLogSession();
    }

    private void fetchActiveLogSession() {
        mGetActiveLogSession.request(mIpAddress, mPort);
    }

    private void fetchHistory() {
        if (mChart.getData().getDataSetCount() == 0) {
            mGetTemperatureHistoryReq.request(mIpAddress, mPort);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_IP_ADDRESS, mIpAddress);
        outState.putInt(ARG_PORT, mPort);
        outState.putBoolean(ARG_LOG_SESSION_ACTIVE, mActiveLogSession);

    }

    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onStop(){
        super.onStop();
        mTemperatureSubject.unregisterObserver(this);
        mGetTemperatureHistoryReq.cancel();
        mGetActiveLogSession.cancel();
        mStopLogSessionReq.cancel();
        if (mProgress != null) mProgress.dismiss();
    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onTemperatureRecv(Temperature temperature) {
        addEntry(temperature);
    }

    @Override
    public void onServerError() {
        Log.i(LOG_TAG, "Received temperature error");
    }

    @Override
    public void onAlarmTriggered() {
        // Dont care yet
    }

    @Override
    public void onGetTemperatureHistoryRecv(List<Temperature> temperatureList) {
        for (Temperature t : temperatureList) {
            addEntry(t);
        }
        mTemperatureSubject.registerObserver(this);
    }

    @Override
    public void onGetTemperatureHistoryError() {
        Toast.makeText(getActivity(), "Failed to get history...", Toast.LENGTH_SHORT);
    }

    @Override
    public void onActiveLogSessionRecv(LogSession logSession) {
        mActiveLogSession = true;
        mImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_stop));
        mLogSessionText.setText(getString(R.string.stop_recording));
    }

    @Override
    public void onActiveLogSessionError() {
        mActiveLogSession = false;
        mImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_play));
        mLogSessionText.setText(getString(R.string.start_recording));
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        mActiveLogSession = false;
        mGetActiveLogSession.request(mIpAddress, mPort);
        mImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_play));
        mLogSessionText.setText(getString(R.string.start_recording));
    }

    private void requestStopLogSession() {

        mProgress = new ProgressDialog(getActivity());
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.setTitle("Please wait");
        mProgress.setMessage("Finalizing log session...");
        mStopLogSessionReq.request(mIpAddress, mPort);
        mProgress.show();
    }

    @Override
    public void onStopLogSessionRecv(LogSession session) {
        mActiveLogSession = false;
        mImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_play));
        mLogSessionText.setText(getString(R.string.start_recording));
        mProgress.dismiss();
    }

    @Override
    public void onStopLogSessionError() {
        mGetActiveLogSession.request(mIpAddress,mPort);
        mProgress.dismiss();
    }

    public interface OnRealtimeChartFragmentListener
    {
        public void onShowCreateLogSessionDialog(DialogInterface.OnDismissListener dismissListener);
    }
}
