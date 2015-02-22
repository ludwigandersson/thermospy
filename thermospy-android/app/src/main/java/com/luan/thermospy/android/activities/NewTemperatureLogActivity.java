package com.luan.thermospy.android.activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.Coordinator;
import com.luan.thermospy.android.core.ServerSettings;
import com.luan.thermospy.android.core.pojo.LogSession;
import com.luan.thermospy.android.core.rest.StartLogSessionReq;

public class NewTemperatureLogActivity extends DialogFragment implements StartLogSessionReq.OnStartLogSessionListener {

    private static final String ARG_IP_ADDRESS = "ipaddress";
    private static final String ARG_PORT = "port";
    private static final String ARG_FOODTYPE_LIST = "foodtype";
    private static final String ARG_CUTTYPE_LIST = "cuttype";

    String mIpAddress;
    int mPort;

    TextView mLogNameTxt;
    Spinner mSpinnerFoodType;
    Spinner mSpinnerCutType;
    TextView mWeightTxt;
    TextView mTargetTemperature;

    Button mBtnSubmit;
    Button mBtnCancel;

    ProgressDialog mProgressDialog;

    StartLogSessionReq mStartLogSessionReq;
    private OnNewLogSessionListener mListener;
    private RequestQueue mRequestQueue;
    private DialogInterface.OnDismissListener mDissmissListener;

    public NewTemperatureLogActivity()
    {

    }

    public static NewTemperatureLogActivity newInstance(ServerSettings serverSettings) {
        NewTemperatureLogActivity fragment = new NewTemperatureLogActivity();
        Bundle args = new Bundle();
        args.putString(ARG_IP_ADDRESS, serverSettings.getIpAddress());
        args.putInt(ARG_PORT, serverSettings.getPort());

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mIpAddress = savedInstanceState.getString(ARG_IP_ADDRESS);
            mPort = savedInstanceState.getInt(ARG_PORT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_new_temperature_log, container);
        mLogNameTxt = (TextView)v.findViewById(R.id.txtLogSessionName);
        mWeightTxt = (TextView)v.findViewById(R.id.txtWeight);
        mBtnCancel = (Button)v.findViewById(R.id.btnCancel);
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

       mBtnSubmit = (Button)v.findViewById(R.id.btnSubmit);
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });


        return v;
    }





    private void submit()
    {
        LogSession session = new LogSession();
        session.setName(mLogNameTxt.getText().toString());

        mStartLogSessionReq.setLogSession(session);

        mStartLogSessionReq.request(mIpAddress, mPort);
    }

    private void cancel()
    {
        dismiss();
    }

    private void showProgressDialog(String text)
    {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setTitle("Please wait");
        mProgressDialog.setMessage(text);

        mProgressDialog.show();
    }

    private void dismissProgressDialog()
    {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }

    }





    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnNewLogSessionListener) activity;
            if (getArguments() != null) {
                mIpAddress = getArguments().getString(ARG_IP_ADDRESS);
                mPort = getArguments().getInt(ARG_PORT);



            }


        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAlarmFragmentListener");
        }
        mRequestQueue = Coordinator.getInstance().getRequestQueue();
        mStartLogSessionReq = new StartLogSessionReq(mRequestQueue, this, null);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
        mStartLogSessionReq.cancel();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        dismissProgressDialog();

        mStartLogSessionReq.cancel();

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_IP_ADDRESS, mIpAddress);
        outState.putInt(ARG_PORT, mPort);
    }

    @Override
    public void onStartLogSessionRecv(LogSession session) {
        mListener.onLogSessionCreated();
        dismiss();
    }

    @Override
    public void onStartLogSessionError() {
        mListener.onLogSessionCancel();
    }

    public void setDismissListener(DialogInterface.OnDismissListener dismissListener) {
        mDissmissListener = dismissListener;
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        mDissmissListener.onDismiss(dialog);

    }

    public interface OnNewLogSessionListener {
        public void onLogSessionCreated();
        public void onLogSessionCancel();

    }
}
