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
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.LocalServiceObserver;
import com.luan.thermospy.android.core.LocalServiceSubject;
import com.luan.thermospy.android.core.pojo.Temperature;
import com.luan.thermospy.android.fragments.AlarmCondition;
import com.luan.thermospy.android.fragments.tabs.listadapter.GenericItemListAdapter;
import com.luan.thermospy.android.fragments.tabs.listcontent.generic.AlarmItem;
import com.luan.thermospy.android.fragments.tabs.listcontent.generic.ListContent;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link com.luan.thermospy.android.fragments.tabs.AlarmTabFragment.OnAlarmTabListener}
 * interface.
 */
public class AlarmTabFragment extends ListFragment implements AlarmItem.AlarmItemInteractionListener, LocalServiceObserver {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ALARM = "alarm";
    private static final String ARG_ALARM_ENABLED = "alarm_enabled";
    private static final String ARG_ALARM_CONDITION = "alarm_condition";

    private String mAlarm;
    private boolean mAlarmEnabled;
    private AlarmCondition mAlarmCondition;

    private GenericItemListAdapter mAdapter = null;
    private List<ListContent> mItems;

    private String LOG_TAG = AlarmTabFragment.class.getSimpleName();

    private OnAlarmTabListener mListener;
    private LocalServiceSubject mLocalServiceSubject;

    @Override
    public void onAlarmEnableSwitchChanged(boolean checked) {
        mListener.onAlarmEnableSwitchChanged(checked);
    }

    @Override
    public void onAlarmChanged(int alarm) {
        mListener.onAlarmChanged(alarm);
    }

    @Override
    public void onAlarmConditionChanged(AlarmCondition alarmCondition) {
        mListener.onAlarmConditionChanged(alarmCondition);
    }

    @Override
    public void onTemperatureRecv(Temperature temperature) {

    }

    @Override
    public void onServerError() {

    }

    @Override
    public void onAlarmTriggered() {
        AlarmItem item = (AlarmItem)mItems.get(0);
        item.setAlarmEnabled(false);
        mAdapter.notifyDataSetChanged();
    }


    private interface ServerInfoItems {
        public int ALARM_CONTROL_ITEM = 0;

    }

    public static AlarmTabFragment newInstance(String alarm, AlarmCondition alarmCondition, boolean alarmEnabled) {
        AlarmTabFragment fragment = new AlarmTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ALARM, alarm);
        args.putInt(ARG_ALARM_CONDITION, alarmCondition.getId());
        args.putBoolean(ARG_ALARM_ENABLED, alarmEnabled);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AlarmTabFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItems = new ArrayList<>();
        if (getArguments() != null) {
            mAlarm = getArguments().getString(ARG_ALARM);
            mAlarmCondition = AlarmCondition.fromInt(getArguments().getInt(ARG_ALARM_CONDITION));
            mAlarmEnabled = getArguments().getBoolean(ARG_ALARM_ENABLED);
        }
        else
        {
            mAlarm = "--";
            mAlarmCondition = AlarmCondition.GREATER_THAN_OR_EQUAL;
            mAlarmEnabled = false;
        }
        mItems.add(new AlarmItem(getActivity(), mAlarm, mAlarmCondition, mAlarmEnabled, this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarmtab, container, false);

        mAdapter = new GenericItemListAdapter(getActivity(), mItems);
        setListAdapter(mAdapter);
        return view;
    }


    @Override
    public void onStop() {
        super.onStop();
        mLocalServiceSubject.unregisterObserver(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnAlarmTabListener) getParentFragment();
            mLocalServiceSubject = (LocalServiceSubject)activity;

            if (getArguments() != null)
            {
                mAlarm = getArguments().getString(ARG_ALARM);
                mAlarmEnabled = getArguments().getBoolean(ARG_ALARM_ENABLED);
                mAlarmCondition = AlarmCondition.fromInt(getArguments().getInt(ARG_ALARM_CONDITION));
            }

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mLocalServiceSubject.registerObserver(this);
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_ALARM, mAlarm);
        outState.putBoolean(ARG_ALARM_ENABLED, mAlarmEnabled);
        outState.putInt(ARG_ALARM_CONDITION, mAlarmCondition.getId());
        super.onSaveInstanceState(outState);
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
    public interface OnAlarmTabListener {

        void onAlarmEnableSwitchChanged(boolean checked);

        void onAlarmChanged(int alarm);

        void onAlarmConditionChanged(AlarmCondition alarmCondition);
    }

}
