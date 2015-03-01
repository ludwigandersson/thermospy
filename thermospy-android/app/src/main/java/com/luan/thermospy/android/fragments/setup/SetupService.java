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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.Coordinator;
import com.luan.thermospy.android.core.pojo.ServiceStatus;
import com.luan.thermospy.android.core.rest.GetServiceStatusReq;

/**
 * The setup service class is responsible for user input of IP and port.
 * If the ip and port was verified ok and it could make a connection to a thermospy server
 * it will store the ip and port in the db for later use.
 *
 * If successful the view will notify its listener and the wizard will continue.
 *
 */
public class SetupService extends Fragment implements GetServiceStatusReq.OnGetServiceStatus, DialogInterface.OnCancelListener {
    private static final String LOG_TAG = SetupService.class.getSimpleName();

    private static final String ARG_IP_ADDRESS = "ipaddress";
    private static final String ARG_PORT = "port";

    private ProgressDialog mProgress;

    private EditText editIp;
    private EditText editPort;
    private Button btnConnect;
    private View rootView;
    private RequestQueue requestQueue = null;

    private GetServiceStatusReq mServerStatusReq;

    private OnSetupServerListener mListener;
    private String mIpAddress;
    private int mPort;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SetupServer.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance(String ipAddress, int port) {
        SetupService fragment = new SetupService();
        Bundle args = new Bundle();
        args.putString(ARG_IP_ADDRESS, ipAddress);
        args.putInt(ARG_PORT, port);
        fragment.setArguments(args);
        return fragment;
    }

    public SetupService() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
        {
            mIpAddress = savedInstanceState.getString(ARG_IP_ADDRESS);
            mPort = savedInstanceState.getInt(ARG_PORT);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_PORT, mPort);
        outState.putString(ARG_IP_ADDRESS, mIpAddress);
    }

    private void requestServerStatus()
    {
        mIpAddress = editIp.getText().toString();

        if (android.text.TextUtils.isDigitsOnly(editPort.getText().toString())) {
            mPort = Integer.parseInt(editPort.getText().toString());
            mServerStatusReq.request(mIpAddress, mPort);
        }
        else
        {
            editPort.setText("");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_setup_server, container, false);

        editIp = (EditText) rootView.findViewById(R.id.editTxtIP);
        editPort = (EditText) rootView.findViewById(R.id.editTxtPort);
        btnConnect = (Button) rootView.findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something
                mProgress = new ProgressDialog(getActivity());
                mProgress.setOnCancelListener(SetupService.this);
                mProgress.setCanceledOnTouchOutside(false);
                mProgress.setTitle("Please wait");
                mProgress.setMessage("Trying to connect to server...");
                requestServerStatus();
                mProgress.show();
            }
        });




        editIp.setText(mIpAddress);
        editPort.setText(Integer.toString(mPort));


        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSetupServerListener) activity;
            requestQueue = Coordinator.getInstance().getRequestQueue();
            mServerStatusReq = new GetServiceStatusReq(requestQueue, this);

            if (getArguments() != null)
            {
                mIpAddress = getArguments().getString(ARG_IP_ADDRESS);
                mPort = getArguments().getInt(ARG_PORT);
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAlarmFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();

        }
        mServerStatusReq.cancel();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mProgress != null)
        {
            mProgress.dismiss();

        }
        mServerStatusReq.cancel();
    }





    @Override
    public void onServiceStatusRecv(ServiceStatus status) {
        mPort = Integer.parseInt(editPort.getText().toString());
        mIpAddress = editIp.getText().toString();

        mListener.onSetupServerConnectionEstablished(mIpAddress, mPort, true);

    }

    @Override
    public void onServiceStatusError() {
        hideProgress();
        mListener.onSetupServerFailed();
    }




    private void hideProgress() {
        if (mProgress != null && mProgress.isShowing())
        {
            mProgress.hide();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        hideProgress();
        mServerStatusReq.cancel();
        mListener.onSetupServerAborted();
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
    public interface OnSetupServerListener {
        // TODO: Update argument type and name
        public void onSetupServerConnectionEstablished(String ipAddress, int port, boolean running);
        public void onSetupServerFailed();
        public void onSetupServerAborted();
    }


}
