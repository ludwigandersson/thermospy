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
import com.google.gson.JsonSyntaxException;
import com.luan.thermospy.android.core.pojo.Temperature;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.GetJsonObject;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import org.json.JSONObject;



/**
 * Get the current temperature
 */
public class GetTemperatureReq implements AbstractServerRequest.ServerRequestListener<JSONObject>, RequestControl {

    private final OnGetTemperatureListener mListener;

    @Override
    public void request(String ip, int port) {
        mGetJsonObjectReq.request(ip, port);
    }

    @Override
    public void cancel() {
        mGetJsonObjectReq.cancel();
    }

    public interface OnGetTemperatureListener
    {
        void onTemperatureUpdate(Temperature temperature);
        void onTemperatureError();
    }
    private final GetJsonObject mGetJsonObjectReq;

    public GetTemperatureReq(RequestQueue queue, OnGetTemperatureListener listener)
    {
        mGetJsonObjectReq = new GetJsonObject(queue, this, UrlRequestType.GET_TEMPERATURE);
        mListener = listener;
    }

    @Override
    public void onOkResponse(JSONObject response) {
        Gson gson = new Gson();
        try {
            Temperature t = gson.fromJson(response.toString(), Temperature.class);
            mListener.onTemperatureUpdate(t);
        } catch (JsonSyntaxException ex)
        {
            mListener.onTemperatureError();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onTemperatureError();
    }
}
