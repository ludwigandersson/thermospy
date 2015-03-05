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
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.Coordinator;
import com.luan.thermospy.android.core.pojo.Action;
import com.luan.thermospy.android.core.pojo.CameraControlAction;
import com.luan.thermospy.android.core.pojo.ServiceStatus;
import com.luan.thermospy.android.core.rest.CameraControlReq;
import com.luan.thermospy.android.core.rest.GetServiceStatusReq;
import com.luan.thermospy.android.core.rest.TemperaturePolling;

  /**
   * The server control class is responsible for polling temperature from the server service
   * after a connection and setup has been successfully made. The user can start/stop the service from this view.
   * Some connection details are visible as well.
   */
public class ServerControl extends Fragment implements TemperaturePolling.OnTemperatureChanged, GetServiceStatusReq.OnGetServiceStatus, CameraControlReq.OnCameraControlListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_IP_ADDRESS = "ip";
    private static final String ARG_PORT = "port";
    private static final String ARG_SERVER_STATUS = "status";
    private static final String ARG_ALARM = "alarm";
    private static final String ARG_TEMPERATURE = "temperature";

    private String mIpAddress;
    private int mPort;
    private boolean mRunning;
    private RequestQueue mRequestQueue;
    private String mAlarm;
    private TemperaturePolling mTemperaturePolling;

    private GetServiceStatusReq mServiceStatusReq;
    private CameraControlReq mCameraControlReq;

      private ToggleButton mToggleServerStatus;
      private OnServerControlListener mListener;

      private ProgressDialog mProgress = null;


    public static ServerControl newInstance(String ip, int port, boolean serverStatus, String alarm) {
        ServerControl fragment = new ServerControl();
        Bundle args = new Bundle();
        args.putString(ARG_IP_ADDRESS, ip);
        args.putInt(ARG_PORT, port);
        args.putBoolean(ARG_SERVER_STATUS, serverStatus);
        args.putString(ARG_ALARM, alarm);
        fragment.setArguments(args);
        return fragment;
    }

      private void getStatus()
      {
          mServiceStatusReq.request(mIpAddress, mPort);
      }

    public ServerControl() {
        // Required empty public constructor
    }

      private void controlServer(final int action)
      {
          mProgress = new ProgressDialog(getActivity());
          mProgress.setTitle("Please wait");
          mProgress.setCanceledOnTouchOutside(false);
          if (!mRunning) {
              mProgress.setMessage("Starting thermospy service...");
          }
          else
          {
              mProgress.setMessage("Stopping thermospy service...");
          }
          mProgress.show();
          mCameraControlReq.setCameraControlAction(new Action(CameraControlAction.fromInt(action)));
          mCameraControlReq.request(mIpAddress, mPort);
      }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mIpAddress = savedInstanceState.getString(ARG_IP_ADDRESS);
            mPort = savedInstanceState.getInt(ARG_PORT);
            mRunning = savedInstanceState.getBoolean(ARG_SERVER_STATUS);
            mAlarm = savedInstanceState.getString(ARG_ALARM);
            mTemperaturePolling.setTemperature(savedInstanceState.getInt(ARG_TEMPERATURE));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v  =  inflater.inflate(R.layout.fragment_server_control, container, false);

        setAlarmText(mAlarm);
        mToggleServerStatus = (ToggleButton) v.findViewById(R.id.toggleButton2);
        mToggleServerStatus.setChecked(!mRunning);
        mToggleServerStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRunning)
                {
                    // Stop service
                    controlServer(2);
                }
                else
                {
                    // Start service
                    controlServer(0);
                }
            }
        });

        return v;
    }

      @Override
      public void onDestroy() {
          super.onDestroy();
          if (mProgress != null && mProgress.isShowing()) {
              mProgress.dismiss();

          }
          mTemperaturePolling.cancel();
          mTemperaturePolling.clearListener();
          mServiceStatusReq.cancel();
          mCameraControlReq.cancel();
      }

      @Override
      public void onResume()
      {
          super.onResume();

          SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
          int interval = Integer.parseInt(settings.getString(getString(R.string.pref_key_refresh_interval), "5"));
          mTemperaturePolling.setInterval(interval);
          mTemperaturePolling.start();

      }

      @Override
      public void onPause()
      {
          super.onPause();
          mTemperaturePolling.cancel();
      }

      @Override
      public void onStop()
      {
          super.onStop();
          if (mProgress != null && mProgress.isShowing())
          {
              mProgress.dismiss();

          }
          mServiceStatusReq.cancel();
          mCameraControlReq.cancel();
      }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnServerControlListener) getParentFragment();

            mIpAddress = getArguments().getString(ARG_IP_ADDRESS);
            mPort = getArguments().getInt(ARG_PORT);
            mRunning = getArguments().getBoolean(ARG_SERVER_STATUS);
            mAlarm = getArguments().getString(ARG_ALARM);

            mRequestQueue = Coordinator.getInstance().getRequestQueue();
            mTemperaturePolling = new TemperaturePolling(getActivity(), Coordinator.getInstance().getRequestQueue(), 5, this);
            mServiceStatusReq = new GetServiceStatusReq(mRequestQueue, this);
            mCameraControlReq = new CameraControlReq(mRequestQueue, this, new Action(CameraControlAction.UNKNOWN));

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAlarmFragmentListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mTemperaturePolling.cancel();
        mTemperaturePolling.clearListener();
        mTemperaturePolling = null;
    }

      @Override
      public void onSaveInstanceState(Bundle outState) {
          super.onSaveInstanceState(outState);
          outState.putString(ARG_IP_ADDRESS, mIpAddress);
          outState.putInt(ARG_PORT, mPort);
          outState.putBoolean(ARG_SERVER_STATUS, mRunning);
          outState.putString(ARG_ALARM, mAlarm);
          outState.putInt(ARG_TEMPERATURE, mTemperaturePolling.getTemperature());
      }

      public void setAlarmText(String alarm)
      {
          mAlarm = alarm;
          if (mAlarm.isEmpty())
          {
              mAlarm = getString(R.string.not_enabled);
          }

      }

      @Override
      public void onTemperatureChanged(String temperature) {
        mListener.onNewTemperature(temperature);
      }

      @Override
      public void onError() {
          mToggleServerStatus.setChecked(true);
          onServiceStatusError();
      }

      private void hideProgress()
      {
          if (mProgress != null && mProgress.isShowing())
          {
              mProgress.hide();
          }
      }

      @Override
      public void onCameraControlResp(Action action) {

          getStatus();
      }

      @Override
      public void onCameraControlError() {
          mToggleServerStatus.setChecked(true);
          hideProgress();
      }

      @Override 
      public void onServiceStatusRecv(ServiceStatus status) {
          mRunning = status.isRunning();
          mToggleServerStatus.setChecked(!mRunning);
          if (mRunning) {
              mTemperaturePolling.start();
             // mTxtViewServerStatus.setText(getString(R.string.running));
              mListener.onNewTemperature(mTemperaturePolling.toString());
          } else {
              mTemperaturePolling.cancel();
              //mTxtViewServerStatus.setText(getString(R.string.not_running));
              mListener.onNewTemperature("--");
          }
          mListener.onServiceStatus(status);
          hideProgress();
      }

      @Override
      public void onServiceStatusError() {
          mToggleServerStatus.setChecked(true);
          //mTxtViewServerStatus.setText(getString(R.string.not_running));
          mListener.onNewTemperature("--");
          hideProgress();
          mListener.onConnectionLost();
      }

    public interface OnServerControlListener {
        // TODO: Update argument type and name
        public void onNewTemperature(String text);
        public void onConnectionLost();
        public void onServiceStatus(ServiceStatus status);
    }

}
