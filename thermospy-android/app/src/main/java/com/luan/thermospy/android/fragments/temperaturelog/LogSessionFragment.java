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
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.Coordinator;
import com.luan.thermospy.android.core.pojo.LogSession;
import com.luan.thermospy.android.core.rest.DeleteLogSessionReq;
import com.luan.thermospy.android.core.rest.GetLogSessionListReq;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnLogSessionFragmentListener}
 * interface.
 */
public class LogSessionFragment extends Fragment implements AbsListView.OnItemClickListener,
        GetLogSessionListReq.OnGetLogSessionsListener, DeleteLogSessionReq.OnGetLogSessionTypesListener, EditLogSessionDialogFragment.OnEditLogSessionListener, DialogInterface.OnCancelListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_IP_ADDRESS = "ipaddress";
    private static final String ARG_PARAM_PORT = "port";
    private static final String LOG_TAG = LogSessionFragment.class.getSimpleName();
    private static final String ARG_DATEFORMAT = "dateformat";

    // TODO: Rename and change types of parameters
    private String mIpAddress;
    private int mPort;

    private OnLogSessionFragmentListener mListener;

    private ProgressDialog mProgressDialog;

    private GetLogSessionListReq mGetLogSessionListReq;

    private List<LogSession> mLogSessionList;

    private LogSession mToBeDeleted = null;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ArrayAdapter mAdapter;

    private RequestQueue mRequestQueue;
    private DeleteLogSessionReq mDeleteLogSessionReq;
    private String mDateFormat;

    // TODO: Rename and change types of parameters
    public static LogSessionFragment newInstance(String ipAddress, int port, String dateformat) {
        LogSessionFragment fragment = new LogSessionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_IP_ADDRESS, ipAddress);
        args.putInt(ARG_PARAM_PORT, port);
        args.putString(ARG_DATEFORMAT,dateformat);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LogSessionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mIpAddress = savedInstanceState.getString(ARG_PARAM_IP_ADDRESS);
            mPort = savedInstanceState.getInt(ARG_PARAM_PORT);
            mDateFormat = savedInstanceState.getString(ARG_DATEFORMAT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logsession_list, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        //((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        registerForContextMenu(mListView);



        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==android.R.id.list) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(mLogSessionList.get(info.position).getName());
            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();

        LogSession logSession = mLogSessionList.get(info.position);
        switch (menuItemIndex)
        {
            case 0: {
                mListener.onShowTemperatureList(logSession);
            }
                break;

            case 1: {
                FragmentManager fm = getFragmentManager();
                EditLogSessionDialogFragment editLogSessionDialog = EditLogSessionDialogFragment.newInstance(Coordinator.getInstance().getServerSettings(), logSession);
                editLogSessionDialog.setTargetFragment(this, 1);
                editLogSessionDialog.show(fm, "fragment_edit_logsession");
            }
            break;
            case 2: {
                mToBeDeleted = logSession;
                mDeleteLogSessionReq.setLogSessionTypeId(logSession.getId());
                mDeleteLogSessionReq.request(mIpAddress, mPort);
            }
                break;
            default:
                return false;
        }
        return true;
    }

    private void requestLogSessionItems() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.setCanceledOnTouchOutside(false);

        mProgressDialog.setTitle("Please wait");
        mProgressDialog.setMessage("Fetching existing sessions...");
        mGetLogSessionListReq.request(mIpAddress, mPort);
        mProgressDialog.show();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnLogSessionFragmentListener) activity;
            mIpAddress = getArguments().getString(ARG_PARAM_IP_ADDRESS);
            mPort = getArguments().getInt(ARG_PARAM_PORT);
            mRequestQueue = Coordinator.getInstance().getRequestQueue();
            mGetLogSessionListReq = new GetLogSessionListReq(mRequestQueue,this);
            mDeleteLogSessionReq = new DeleteLogSessionReq(mRequestQueue, this, -1);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            //mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);

            LogSession session = mLogSessionList.get(position);

            mListener.onShowTemperatureList(session);

        }
    }

    @Override
    public void onLogSessionsRecv(List<LogSession> logSessionList) {


        mAdapter = new ArrayAdapter<LogSession>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, logSessionList);
        mListView.setAdapter(mAdapter);
        mLogSessionList = logSessionList;
        mProgressDialog.dismiss();


    }

    @Override
    public void onLogSessionsError() {
        mProgressDialog.dismiss();
        mListener.onLogSessionListError();
    }

    @Override
    public void onResume() {
        super.onResume();

        requestLogSessionItems();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        mGetLogSessionListReq.cancel();
        mDeleteLogSessionReq.cancel();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        mGetLogSessionListReq.cancel();
        mDeleteLogSessionReq.cancel();

    }

    @Override
    public void onLogSessionTypeDeleted(int id) {
        if (mToBeDeleted != null && mToBeDeleted.getId() == id)
        {
            mLogSessionList.remove(mToBeDeleted);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLogSessionTypeError() {
        mToBeDeleted = null;
        Log.d(LOG_TAG, "Failed to delete log session.");
    }

    @Override
    public void onDone(LogSession session) {
        LogSession inList = null;
        for (LogSession l : mLogSessionList)
        {
            if (session.getId() == l.getId())
            {
                inList = l;
                break;
            }
        }
        if (inList != null)
        {
            mLogSessionList.remove(inList);
            mLogSessionList.add(0, session);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString(ARG_PARAM_IP_ADDRESS, mIpAddress);
        outState.putInt(ARG_PARAM_PORT, mPort);
        outState.putString(ARG_DATEFORMAT, mDateFormat);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onError() {
        Toast t = Toast.makeText(getActivity(), getString(R.string.failed_to_update_logsession), Toast.LENGTH_SHORT);
        t.show();
    }

    @Override
    public void onCancel(DialogInterface dialog)
    {
        mDeleteLogSessionReq.cancel();
        mGetLogSessionListReq.cancel();
        mListener.onLogSessionListError();
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
    public interface OnLogSessionFragmentListener {
        // TODO: Update argument type and name
        public void onShowTemperatureList(LogSession session);

        void onLogSessionListError();
    }

}
