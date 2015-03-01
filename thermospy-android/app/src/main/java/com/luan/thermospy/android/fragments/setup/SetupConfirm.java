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

package com.luan.thermospy.android.fragments.setup;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.Coordinator;
import com.luan.thermospy.android.core.pojo.Action;
import com.luan.thermospy.android.core.pojo.CameraControlAction;
import com.luan.thermospy.android.core.pojo.Temperature;
import com.luan.thermospy.android.core.rest.CameraControlReq;
import com.luan.thermospy.android.core.rest.GetTemperatureReq;

/**
 * The last fragment of the setup wizard. In this view the user decides if the data
 * received from the server is valid. If the user accepts a request is sent to start the
 * photo service in continuous mode. If cancelled the user is sent back to the Setup view.
 */
public class SetupConfirm extends Fragment implements GetTemperatureReq.OnGetTemperatureListener, 
                                                      CameraControlReq.OnCameraControlListener, 
                                                      DialogInterface.OnCancelListener {

    private static final String PARAM_TEMPERATURE = "temperature";
    private static final String LOG_TAG = SetupConfirm.class.getSimpleName();
    private OnThermoSpySetupConfirmedListener mListener;

    private GetTemperatureReq mGetTemperatureReq;
    private CameraControlReq mCameraControlReq;

    private TextView mTemperature;
    private String mTemperatureStr = "--";

    private ProgressDialog mProgress = null;
    private String mIpAddress;
    private int mPort;

    public static SetupConfirm newInstance() {
        return new SetupConfirm();
    }

    public SetupConfirm() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
        {
            mTemperatureStr = savedInstanceState.getString(PARAM_TEMPERATURE);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_confirm_setup, container, false);
        mTemperature = (TextView)v.findViewById(R.id.txtViewTemperature);
        Button btnAbort = (Button) v.findViewById(R.id.buttonRetry);
        Button btnOk = (Button) v.findViewById(R.id.buttonConfirm);
        btnAbort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSetupServerAborted();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStartService();
            }
        });

        mTemperature.setText(mTemperatureStr);

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnThermoSpySetupConfirmedListener) activity;
            final RequestQueue queue = Coordinator.getInstance().getRequestQueue();
            mGetTemperatureReq = new GetTemperatureReq(queue, this);
            mCameraControlReq = new CameraControlReq(queue, this, new Action(CameraControlAction.RUNONCE));

            mIpAddress = Coordinator.getInstance().getServerSettings().getIpAddress();
            mPort = Coordinator.getInstance().getServerSettings().getPort();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAlarmFragmentListener");
        }
    }

    private void showProgressAndTakePhoto()
    {
        if (mProgress == null) {
            mProgress = new ProgressDialog(getActivity());
            mProgress.setOnCancelListener(this);
        }
        mProgress.setTitle(R.string.please_wait);
        mProgress.setMessage(getString(R.string.progress_fetching_temperature));
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();
        takePhoto();

    }

    private void requestStartService()
    {
        if (mProgress == null) {
            mProgress = new ProgressDialog(getActivity());
            mProgress.setOnCancelListener(this);
        }
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.setTitle(R.string.please_wait);
        mProgress.setMessage(getString(R.string.progress_starting_service));
        mProgress.show();
        // Start server
        mCameraControlReq.setCameraControlAction(new Action(CameraControlAction.START));
        mCameraControlReq.request(mIpAddress, mPort);
    }

    private void requestTemperature()
    {
        mGetTemperatureReq.request(mIpAddress, mPort);
    }

    private void takePhoto()
    {
        mCameraControlReq.setCameraControlAction(new Action(CameraControlAction.RUNONCE));
        mCameraControlReq.request(mIpAddress, mPort);
    }

    @Override
    public void onDetach() {

        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgress();
        mGetTemperatureReq.cancel();
        mCameraControlReq.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mProgress == null) {
            showProgressAndTakePhoto();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgress();
        mGetTemperatureReq.cancel();
        mCameraControlReq.cancel();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PARAM_TEMPERATURE, mTemperatureStr);

    }

    @Override
    public void onTemperatureUpdate(Temperature temperature) {
        mTemperatureStr = temperature.toString();
        mTemperature.setText(mTemperatureStr);

        hideProgress();
    }

    @Override
    public void onTemperatureError() {
        hideProgress();
        mListener.onSetupServerAborted();
    }

    @Override
    public void onCameraControlResp(Action action) {
        if (mCameraControlReq.getAction().getActionId() == action.getActionId()) {
            if (action.getActionId() == CameraControlAction.RUNONCE) {
                // ServerSettings has taken a photo and parsed it. Request the parsed temperature!
                requestTemperature();
            } else if (action.getActionId() == CameraControlAction.START) {
                hideProgress();
                mListener.setupConfirmed(mTemperatureStr);
            } else {
                Log.w(LOG_TAG, "Received action that was not expected. Type: " + action.getActionId());
                mProgress.hide();
                mListener.onSetupServerAborted();
            }
        }
        else {
            Log.w(LOG_TAG, "Received action that was not expected. Type: " + action.getActionId());
            mProgress.hide();
            mListener.onSetupServerAborted();
        }
    }

    @Override
    public void onCameraControlError() {
        hideProgress();
        mListener.onSetupServerAborted();
    }

    private void hideProgress() {
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        mListener.onSetupServerAborted();
    }

    public interface OnThermoSpySetupConfirmedListener {
        public void setupConfirmed(String s);
        public void onSetupServerAborted();
    }

}
