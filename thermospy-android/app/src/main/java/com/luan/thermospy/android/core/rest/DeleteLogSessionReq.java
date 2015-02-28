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

package com.luan.thermospy.android.core.rest;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.GenericStringRequest;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import java.util.ArrayList;

/**
 * Created by ludde on 15-02-17.
 */
public class DeleteLogSessionReq implements AbstractServerRequest.ServerRequestListener<String>, RequestControl {

    private static final String LOG_TAG = DeleteLogSessionReq.class.getSimpleName();
    private final GenericStringRequest mStringRequest;
    private int mLogSessionTypeId = -1;
    private OnGetLogSessionTypesListener mListener;

    public interface OnGetLogSessionTypesListener
    {
        void onLogSessionTypeDeleted(int id);
        void onLogSessionTypeError();
    }


    public DeleteLogSessionReq(RequestQueue queue, OnGetLogSessionTypesListener listener, int cutTypeId)
    {
        mStringRequest = new GenericStringRequest(queue, this, UrlRequestType.LOG_SESSION, Request.Method.DELETE);
        mListener = listener;
        mLogSessionTypeId = cutTypeId;
    }

    public void setLogSessionTypeId(int id)
    {
        mLogSessionTypeId = id;
    }

    @Override
    public void request(String ip, int port) {
        mStringRequest.setRequestParams(new ArrayList<String>(){{
            add(Integer.toString(mLogSessionTypeId));
        }});
        mStringRequest.request(ip, port);
    }

    @Override
    public void cancel() {
        mStringRequest.cancel();
    }

    @Override
    public void onOkResponse(String response) {
        Log.d(LOG_TAG, "Received response! "+response);
        mListener.onLogSessionTypeDeleted(mLogSessionTypeId);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onLogSessionTypeError();
    }
}