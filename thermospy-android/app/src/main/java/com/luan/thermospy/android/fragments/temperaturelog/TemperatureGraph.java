package com.luan.thermospy.android.fragments.temperaturelog;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ShareActionProvider;

import com.android.volley.RequestQueue;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;
import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.Coordinator;
import com.luan.thermospy.android.core.pojo.TemperatureEntry;
import com.luan.thermospy.android.core.rest.GetTemperatureEntryListReq;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnTemperatureGraphFragmentListener} interface
 * to handle interaction events.
 * Use the {@link TemperatureGraph#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TemperatureGraph extends Fragment implements GetTemperatureEntryListReq.OnGetTemperatureEntryListener {

    private static final String ARG_IP_ADDRESS = "ipaddress";
    private static final String ARG_PORT = "port";
    private static final String ARG_SESSION_ID = "sessionid";

    private int mSessionId;
    private int mPort;
    private String mIpAddress;

    private ProgressDialog mProgressDialog;

    private OnTemperatureGraphFragmentListener mListener;
    private RequestQueue mRequestQueue;
    private LineChart mChart;
    private GetTemperatureEntryListReq mGetTemperatureEntryListReq;

    private ShareActionProvider mShareActionProvider;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param ipAddress Ip address of thermospy-server
     * @param port Port to use
     * @param sessionId The session id
     * @return A new instance of fragment TemperatureGraph.
     */
    // TODO: Rename and change types and number of parameters
    public static TemperatureGraph newInstance(String ipAddress, int port, int sessionId) {
        TemperatureGraph fragment = new TemperatureGraph();
        Bundle args = new Bundle();
        args.putString(ARG_IP_ADDRESS, ipAddress);
        args.putInt(ARG_PORT, port);
        args.putInt(ARG_SESSION_ID, sessionId);
        fragment.setArguments(args);
        return fragment;
    }

    public TemperatureGraph() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIpAddress = getArguments().getString(ARG_IP_ADDRESS);
            mPort = getArguments().getInt(ARG_PORT);
            mSessionId = getArguments().getInt(ARG_SESSION_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_temperature_graph, container, false);
        mChart = (LineChart) v.findViewById(R.id.chart);

        requestTemperatureEntries();

        return v;
    }



    private void requestTemperatureEntries() {
        if (mProgressDialog == null)
        {
            mProgressDialog = new ProgressDialog(getActivity());
        }

        mProgressDialog.setCanceledOnTouchOutside(false);

        mProgressDialog.setTitle("Please wait");
        mProgressDialog.setMessage("Initializing graph...");
        mGetTemperatureEntryListReq.setTemperatureEntryId(mSessionId);
        mGetTemperatureEntryListReq.request(mIpAddress, mPort);
        mProgressDialog.show();
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTemperatureGraphFragmentListener) activity;
            mRequestQueue = Coordinator.getInstance().getRequestQueue();
            mGetTemperatureEntryListReq = new GetTemperatureEntryListReq(mRequestQueue, this);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onTemperatureEntryRecv(List<TemperatureEntry> logSessionList) {
        if (logSessionList.size() > 0) {
            setupLineChart();
            setData(logSessionList);
        }
        else
        {
            //dmListener.onError();
        }
        mProgressDialog.dismiss();
    }

    private void setupLineChart()
    {
        //mChart.setOnChartValueSelectedListener(this);
        mChart.setValueTextColor(Color.WHITE);

        mChart.setUnit(" °C");
        mChart.setDrawUnitsInChart(true);

        // if enabled, the chart will always start at zero on the y-axis
        mChart.setStartAtZero(false);

        // disable the drawing of values into the chart
        mChart.setDrawYValues(false);

        mChart.setDrawBorder(true);
        mChart.setBorderPositions(new BarLineChartBase.BorderPosition[] {
                BarLineChartBase.BorderPosition.BOTTOM
        });

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
        mChart.setDrawVerticalGrid(false);
        mChart.setDrawHorizontalGrid(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.GRAY);


    }

    private void setData(List<TemperatureEntry> logSessionList) {

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < logSessionList.size(); i++) {
            xVals.add((i) + "");
            yVals.add(new Entry(logSessionList.get(i).getTemperature(), i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setCircleColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(2f);
        set1.setCircleSize(4f);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);
        //l.setTypeface(tf);
        l.setTextColor(Color.WHITE);

        XLabels xl = mChart.getXLabels();
        // xl.setTypeface(tf);
        xl.setTextColor(Color.WHITE);

        YLabels yl = mChart.getYLabels();
        // yl.setTypeface(tf);
        yl.setTextColor(Color.WHITE);
    }

    @Override
    public void onTemperatureEntryError() {
        mListener.onError();
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
    public void onStop() {
        super.onStop();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();

        }
        mGetTemperatureEntryListReq.cancel();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTemperatureGraphFragmentListener {

        public void onError();
    }

}
