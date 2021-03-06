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

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.luan.thermospy.android.core.pojo.ServerStatus;
import com.luan.thermospy.android.core.pojo.ServerStatusSerializer;
import com.luan.thermospy.android.core.pojo.ServiceStatus;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.GetJsonObject;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import org.json.JSONObject;

/**
 * Request that gets the service status
 */
public class GetServiceStatusReq implements AbstractServerRequest.ServerRequestListener<JSONObject>, RequestControl {

    private final OnGetServiceStatus mListener;

    @Override
    public void request(String ip, int port) {
        mGetJsonObjectReq.request(ip, port);
    }

    @Override
    public void cancel() {
        mGetJsonObjectReq.cancel();
    }

    public interface OnGetServiceStatus
    {
        void onServiceStatusRecv(ServiceStatus temperature);
        void onServiceStatusError();
    }
    private final GetJsonObject mGetJsonObjectReq;

    public GetServiceStatusReq(RequestQueue queue, OnGetServiceStatus listener)
    {
        mGetJsonObjectReq = new GetJsonObject(queue, this, UrlRequestType.SERVER_STATUS);
        mListener = listener;
    }

    @Override
    public void onOkResponse(JSONObject response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder = gsonBuilder.registerTypeAdapter(ServerStatus.class, new ServerStatusSerializer() );
        Gson gson = gsonBuilder.create();
        try {
            ServiceStatus t = gson.fromJson(response.toString(), ServiceStatus.class);
            mListener.onServiceStatusRecv(t);
        } catch (JsonSyntaxException ex)
        {
            mListener.onServiceStatusError();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onServiceStatusError();
    }
}