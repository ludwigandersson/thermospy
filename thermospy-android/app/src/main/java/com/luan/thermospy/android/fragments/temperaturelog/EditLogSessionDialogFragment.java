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
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.gson.JsonSyntaxException;
import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.Coordinator;
import com.luan.thermospy.android.core.ServerSettings;
import com.luan.thermospy.android.core.pojo.LogSession;
import com.luan.thermospy.android.core.rest.UpdateLogSessionReq;

import org.json.JSONException;

public class EditLogSessionDialogFragment extends DialogFragment implements UpdateLogSessionReq.OnUpdateLogSessionListener {

    private static final String ARG_IP_ADDRESS = "ipaddress";
    private static final String ARG_PORT = "port";
    private static final String ARG_LOG_SESSION = "logsession";

    String mIpAddress;
    int mPort;

    LogSession mSession;
    TextView mLogNameTxt;
    TextView mTargetTemperature;

    Button mBtnSubmit;
    Button mBtnCancel;

    UpdateLogSessionReq mUpdateLogSessionReq;
    Toast mToast;

    private RequestQueue mRequestQueue;
    private OnEditLogSessionListener mListener;

    public EditLogSessionDialogFragment()
    {

    }

    public static EditLogSessionDialogFragment newInstance(ServerSettings serverSettings, LogSession logSession) {
        EditLogSessionDialogFragment fragment = new EditLogSessionDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IP_ADDRESS, serverSettings.getIpAddress());
        args.putInt(ARG_PORT, serverSettings.getPort());
        try {
            args.putString(ARG_LOG_SESSION, LogSession.toJson(logSession).toString());
        } catch (JSONException e) {
            args.putString(ARG_LOG_SESSION, "");
        }

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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

        if (mSession == null)
        {
            mListener.onError();
            dismiss();
            return null;
        }
        if (mSession.getName() != null) {
            mLogNameTxt.setText(mSession.getName());
        }
        if (mSession.getTargetTemperature() != null) {
            mTargetTemperature.setText(Integer.toString(mSession.getTargetTemperature()));
        }

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

            mSession.setName(mLogNameTxt.getText().toString());
            if (!mTargetTemperature.getText().toString().isEmpty()) {
                try {
                    mSession.setTargetTemperature(Integer.parseInt(mTargetTemperature.getText().toString()));
                } catch (NumberFormatException efe) {
                    Toast t = Toast.makeText(getActivity(), getString(R.string.invalid_target_temperature), Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }
            }
            mUpdateLogSessionReq.setLogSession(mSession);
            mUpdateLogSessionReq.request(mIpAddress, mPort);
            mToast = Toast.makeText(getActivity(), getString(R.string.wait_log_session_update), Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    private void cancel()
    {
        dismiss();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnEditLogSessionListener) getTargetFragment();
            if (getArguments() != null) {
                mIpAddress = getArguments().getString(ARG_IP_ADDRESS);
                mPort = getArguments().getInt(ARG_PORT);
                try {
                    mSession = LogSession.fromJson(getArguments().getString(ARG_LOG_SESSION));
                } catch (JsonSyntaxException ex)
                {
                    mSession = null;
                    throw new IllegalArgumentException("No log session object provided to fragment!");
                }
            }


        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnEditLogSessionListener");
        }
        mRequestQueue = Coordinator.getInstance().getRequestQueue();
        mUpdateLogSessionReq = new UpdateLogSessionReq(mRequestQueue, this, mSession);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUpdateLogSessionReq.cancel();
    }

    @Override
    public void onStop()
    {
        super.onStop();

        mUpdateLogSessionReq.cancel();

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        if (mToast != null) {
            mToast.cancel();
        }
        super.onDismiss(dialog);
    }

    @Override
    public void onLogSessionUpdated(LogSession session) {

        mListener.onDone(session);
        dismiss();
    }

    @Override
    public void onUpdateLogSessionError() {
        mListener.onError();
        dismiss();
    }

    public interface OnEditLogSessionListener
    {
        public void onDone(LogSession session);
        public void onError();
    }


    @Override
    public void onCancel(DialogInterface dialog)
    {
        super.onCancel(dialog);
        mUpdateLogSessionReq.cancel();
    }

}
