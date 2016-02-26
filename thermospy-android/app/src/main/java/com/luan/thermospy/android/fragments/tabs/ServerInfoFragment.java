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

package com.luan.thermospy.android.fragments.tabs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.Coordinator;
import com.luan.thermospy.android.core.pojo.Action;
import com.luan.thermospy.android.core.pojo.CameraControlAction;
import com.luan.thermospy.android.core.pojo.ServerStatus;
import com.luan.thermospy.android.core.pojo.ServiceStatus;
import com.luan.thermospy.android.core.rest.CameraControlReq;
import com.luan.thermospy.android.core.rest.GetServiceStatusReq;
import com.luan.thermospy.android.core.rest.ServiceStatusPolling;
import com.luan.thermospy.android.fragments.tabs.listadapter.GenericItemListAdapter;
import com.luan.thermospy.android.fragments.tabs.listcontent.generic.ListContent;
import com.luan.thermospy.android.fragments.tabs.listcontent.generic.SimpleItem;
import com.luan.thermospy.android.fragments.tabs.listcontent.generic.SwitchItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link com.luan.thermospy.android.fragments.tabs.ServerInfoFragment.OnServerInfoListener}
 * interface.
 */
public class ServerInfoFragment extends ListFragment implements ServiceStatusPolling.OnServiceStatusListener, CameraControlReq.OnCameraControlListener, GetServiceStatusReq.OnGetServiceStatus {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_IP_ADDRESS = "ipaddress";
    private static final String ARG_PORT = "port";
    private static final String ARG_SERVICE_STATUS = "servicestatus";
    private static final String ARG_CONNECTION_STATUS = "connection";

    private String mIpAddress;
    private int mPort;
    private ServerStatus mServerStatus;

    private OnServerInfoListener mListener;
    private CameraControlReq mCameraControlReq;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private GenericItemListAdapter mAdapter = null;
    private List<ListContent> mItems;
    private String LOG_TAG = ServerInfoFragment.class.getSimpleName();
    private boolean mRunning;
    private boolean mConnected;
    private RequestQueue mRequestQueue;
    private ServiceStatusPolling mServiceStatusPoller;
    private ProgressDialog mProgress;
    private GetServiceStatusReq mServiceStatusReq;

    @Override
    public void onCameraControlResp(Action action) {
        getStatus();
    }

    private void getStatus() {
        mServiceStatusReq.request(mIpAddress, mPort);
    }

    @Override
    public void onCameraControlError() {
        updateControlServiceSwitch(ServerInfoItems.CONTROL_SERVICE_ITEM);
        hideProgressDialog();
    }

    @Override
    public void onServiceStatusRecv(ServiceStatus status) {
        onServiceStatusPollerRecv(status);
        hideProgressDialog();
    }

    @Override
    public void onServiceStatusError() {
        updateControlServiceSwitch(ServerInfoItems.CONTROL_SERVICE_ITEM);
        onServiceStatusPollerError();
        hideProgressDialog();
    }

    private interface ServerInfoItems {
        public int CONTROL_SERVICE_ITEM = 0;
        public int IP_ADDRESS_ITEM = 1;
        public int PORT_ITEM = 2;
        public int SERVICE_STATUS_ITEM = 3;
        public int SERVER_STATUS_ITEM = 4;
        public int CONNECTION_STATUS_ITEM = 5;
    }

    public static ServerInfoFragment newInstance(String ipAddress, int port) {
        ServerInfoFragment fragment = new ServerInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IP_ADDRESS, ipAddress);
        args.putInt(ARG_PORT, port);
        args.putBoolean(ARG_SERVICE_STATUS, false);
        args.putBoolean(ARG_CONNECTION_STATUS, false);

        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ServerInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItems = new ArrayList<>();
        if (getArguments() != null) {
            mIpAddress = getArguments().getString(ARG_IP_ADDRESS);
            mPort = getArguments().getInt(ARG_PORT);
            mRunning = getArguments().getBoolean(ARG_SERVICE_STATUS);
            mConnected = getArguments().getBoolean(ARG_CONNECTION_STATUS);
        }
        else
        {
            mIpAddress = "Unknown";
            mPort = -1;
            mRunning = false;
            mConnected = false;
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_serverinfo, container, false);



        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        mServiceStatusPoller.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        hideProgressDialog();
        mServiceStatusPoller.cancel();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnServerInfoListener) getParentFragment();
            mRequestQueue = Coordinator.getInstance().getRequestQueue();
            mServiceStatusPoller = new ServiceStatusPolling(mRequestQueue, this);
            mCameraControlReq = new CameraControlReq(mRequestQueue, this, new Action(CameraControlAction.UNKNOWN));
            mServiceStatusReq = new GetServiceStatusReq(mRequestQueue, this);
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

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    @Override
    public void onServiceStatusPollerRecv(ServiceStatus status) {
        if (mAdapter == null)
        {
            initializeAdapter();
        }

        if (mRunning != status.isRunning())
        {
            mRunning = status.isRunning();
            updateSimpleItemValue(ServerInfoItems.SERVICE_STATUS_ITEM, mRunning ? "Running" : "Not running");
            updateControlServiceSwitch(ServerInfoItems.CONTROL_SERVICE_ITEM);

            mListener.onServiceStatus(status);
        }

        if (!mConnected)
        {
            mConnected = true;
            updateSimpleItemValue(ServerInfoItems.CONNECTION_STATUS_ITEM, "Connected");
        }

        if (mServerStatus != status.getError()) {
            mServerStatus = status.getError();
            updateSimpleItemValue(ServerInfoItems.SERVER_STATUS_ITEM, mServerStatus.toString());
        }

    }

    private void initializeAdapter() {
        mItems.add(new SwitchItem("Control service", mRunning, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraControlAction action;
                if (mRunning)
                {
                    // Stop server service
                    action = CameraControlAction.STOP;
                }
                else
                {
                    // Start server service
                    action = CameraControlAction.START;
                }
                controlServer(action);
            }
        }));
        mItems.add(new SimpleItem("IP Address", mIpAddress));
        mItems.add(new SimpleItem("Port", Integer.toString(mPort)));
        mItems.add(new SimpleItem("State", mRunning ? "Running" : "Not running"));
        mItems.add(new SimpleItem("Server status", "OK"));
        mItems.add(new SimpleItem("Connection status", mConnected ? "Connected" : "Not connected"));


        mAdapter = new GenericItemListAdapter(getActivity(), mItems);
        setListAdapter(mAdapter);

    }

    private void controlServer(CameraControlAction action)
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
        mCameraControlReq.setCameraControlAction(new Action(action));
        mCameraControlReq.request(mIpAddress, mPort);
    }

    private void updateControlServiceSwitch(int controlServiceItem) {
        SwitchItem switchItem = (SwitchItem)mItems.get(controlServiceItem);
        switchItem.setRunning(mRunning);
        mAdapter.notifyDataSetChanged();
    }

    private void updateSimpleItemValue(int itemId, String value)
    {
        SimpleItem item = (SimpleItem)mItems.get(itemId);
        item.setValue(value);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onServiceStatusPollerError() {
        if (mAdapter == null)
        {
            initializeAdapter();
        }

        if (mConnected)
        {
            updateSimpleItemValue(ServerInfoItems.CONNECTION_STATUS_ITEM, "Not connected");
            updateSimpleItemValue(ServerInfoItems.SERVICE_STATUS_ITEM, "Unknown");
        }
        mListener.onServiceStatusError();
    }

    private void hideProgressDialog()
    {
        if (mProgress != null) {
            mProgress.dismiss();
        }
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
    public interface OnServerInfoListener {
        public void onServiceStatus(ServiceStatus serviceStatus);
        public void onServiceStatusError();
    }

}
