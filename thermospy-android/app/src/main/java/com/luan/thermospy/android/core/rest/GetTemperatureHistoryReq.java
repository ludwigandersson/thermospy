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
import com.luan.thermospy.android.core.pojo.Temperature;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.GetJsonArray;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Request that fetches all log sessions created at the server.
 */
public class GetTemperatureHistoryReq implements AbstractServerRequest.ServerRequestListener<JSONArray>, RequestControl {

    private final GetJsonArray mGetJsonArrayReq;
    private OnGetTemperatureHistoryListener mListener;

    public interface OnGetTemperatureHistoryListener
    {
        void onGetTemperatureHistoryRecv(List<Temperature> temperatureList);
        void onGetTemperatureHistoryError();
    }


    public GetTemperatureHistoryReq(RequestQueue queue, OnGetTemperatureHistoryListener listener)
    {
        mGetJsonArrayReq = new GetJsonArray(queue, this, UrlRequestType.TEMPERATURE_HISTORY);
        mListener = listener;
    }

    @Override
    public void request(String ip, int port) {
        mGetJsonArrayReq.request(ip, port);
    }

    @Override
    public void cancel() {
        mGetJsonArrayReq.cancel();
    }

    @Override
    public void onOkResponse(JSONArray response) {
        List<Temperature> temperatureList = new ArrayList<Temperature>();
        // Creates the json object which will manage the information received


        Gson gson = new Gson();
        try {
            for (int i = 0; i < response.length(); i++) {
                temperatureList.add(gson.fromJson(response.getJSONObject(i).toString(), Temperature.class));
            }
            mListener.onGetTemperatureHistoryRecv(temperatureList);
        } catch (JSONException ex)
        {
            mListener.onGetTemperatureHistoryError();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onGetTemperatureHistoryError();
    }
}
