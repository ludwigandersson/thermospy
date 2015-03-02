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
package com.luan.thermospy.android.fragments.temperaturelog;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.Coordinator;
import com.luan.thermospy.android.core.ServerSettings;
import com.luan.thermospy.android.core.pojo.LogSession;
import com.luan.thermospy.android.core.rest.StartLogSessionReq;

public class LogSessionDialogFragment extends DialogFragment implements StartLogSessionReq.OnStartLogSessionListener, DialogInterface.OnCancelListener {

    private static final String ARG_IP_ADDRESS = "ipaddress";
    private static final String ARG_PORT = "port";

    String mIpAddress;
    int mPort;

    TextView mLogNameTxt;
    TextView mTargetTemperature;

    Button mBtnSubmit;
    Button mBtnCancel;

    ProgressDialog mProgressDialog;

    StartLogSessionReq mStartLogSessionReq;
    private RequestQueue mRequestQueue;
    private DialogInterface.OnDismissListener mDissmissListener;

    public LogSessionDialogFragment()
    {

    }

    public static LogSessionDialogFragment newInstance(ServerSettings serverSettings) {
        LogSessionDialogFragment fragment = new LogSessionDialogFragment();
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
        mTargetTemperature = (TextView)v.findViewById(R.id.txtTargetTemperature);
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

        getDialog().setTitle(getString(R.string.start_log_session));

        return v;
    }





    private void submit()
    {
        if (mLogNameTxt.getText().toString().isEmpty())
        {
            Toast t = Toast.makeText(getActivity(), getString(R.string.invalid_logname), Toast.LENGTH_SHORT);
            t.show();
        }
        else {
            LogSession session = new LogSession();
            session.setName(mLogNameTxt.getText().toString());
            if (!mTargetTemperature.getText().toString().isEmpty()) {
                try {
                    session.setTargetTemperature(Integer.parseInt(mTargetTemperature.getText().toString()));
                } catch (NumberFormatException efe) {
                    Toast t = Toast.makeText(getActivity(), getString(R.string.invalid_target_temperature), Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }
            }
            mStartLogSessionReq.setLogSession(session);
            mStartLogSessionReq.request(mIpAddress, mPort);
        }
    }

    private void cancel()
    {
        dismiss();
    }

    private void showProgressDialog(String text)
    {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setOnCancelListener(this);
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
        dismiss();
    }

    @Override
    public void onStartLogSessionError() {
        dismiss();
    }

    public void setDismissListener(DialogInterface.OnDismissListener dismissListener) {
        mDissmissListener = dismissListener;
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        mDissmissListener.onDismiss(dialog);
        mStartLogSessionReq.cancel();

    }

    @Override
    public void onCancel(DialogInterface dialog)
    {
        super.onCancel(dialog);
        mStartLogSessionReq.cancel();
    }



}
