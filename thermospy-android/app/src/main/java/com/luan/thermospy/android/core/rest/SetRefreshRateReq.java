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

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.luan.thermospy.android.core.pojo.RefreshRate;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.PutJsonObject;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ludde on 15-02-01.
 */
public class SetRefreshRateReq implements AbstractServerRequest.ServerRequestListener<JSONObject>, RequestControl {

    private static final String LOG_TAG = SetRefreshRateReq.class.getSimpleName();
    private final OnSetRefreshRateListener mListener;
    private RefreshRate mRefreshRate;

    @Override
    public void request(String ip, int port) {
        mPutJsonObjectReq.request(ip, port);
    }

    @Override
    public void cancel() {
        mPutJsonObjectReq.cancel();
    }

    private JSONObject getJsonObject()
    {
        Gson gson = new Gson();

        try {
            return new JSONObject(gson.toJson(mRefreshRate, RefreshRate.class));
        } catch (JSONException | JsonIOException e) {
            Log.e(LOG_TAG, "Failed to create json object of Refresh Rate object!", e);
            return null;
        }
    }

    public void setRefreshRate(RefreshRate b) {
        this.mRefreshRate = b;
        mPutJsonObjectReq.setJSONObject(getJsonObject());
    }

    public interface OnSetRefreshRateListener
    {
        void onSetRefreshRateRecv(RefreshRate RefreshRate);
        void onSetRefreshRateError();
    }
    private final PutJsonObject mPutJsonObjectReq;

    public SetRefreshRateReq(RequestQueue queue, OnSetRefreshRateListener listener, RefreshRate refreshRate)
    {
        mRefreshRate = refreshRate;
        mListener = listener;
        mPutJsonObjectReq = new PutJsonObject(queue, this, UrlRequestType.REFRESH_RATE, getJsonObject());

    }

    @Override
    public void onOkResponse(JSONObject response) {
        Gson gson = new Gson();
        try {
            RefreshRate refreshRate = gson.fromJson(response.toString(), RefreshRate.class);
            mListener.onSetRefreshRateRecv(refreshRate);
        } catch (JsonSyntaxException ex)
        {
            mListener.onSetRefreshRateError();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onSetRefreshRateError();
    }
}
