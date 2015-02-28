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
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.luan.thermospy.android.core.pojo.TemperatureEntry;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.GetJsonArray;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ludde on 15-02-17.
 */
public class GetTemperatureEntryListReq implements AbstractServerRequest.ServerRequestListener<JSONArray>, RequestControl {

    private final GetJsonArray mGetJsonArrayReq;
    private OnGetTemperatureEntryListener mListener;
    private int mTemperatureEntryId;
    public interface OnGetTemperatureEntryListener
    {
        void onTemperatureEntryRecv(List<TemperatureEntry> logSessionList);
        void onTemperatureEntryError();
    }


    public GetTemperatureEntryListReq(RequestQueue queue, OnGetTemperatureEntryListener listener)
    {
        mGetJsonArrayReq = new GetJsonArray(queue, this, UrlRequestType.TEMPERATURE_ENTRY_LIST);
        mListener = listener;
    }

    public int getTemperatureEntryId() {
        return mTemperatureEntryId;
    }

    public void setTemperatureEntryId(int mTemperatureEntryId) {
        this.mTemperatureEntryId = mTemperatureEntryId;
    }

    @Override
    public void request(String ip, int port) {
        mGetJsonArrayReq.setRequestParams(new ArrayList<String>() {
            {
                add(Integer.toString(mTemperatureEntryId));
            }
        });
        mGetJsonArrayReq.request(ip, port);
    }

    @Override
    public void cancel() {
        mGetJsonArrayReq.cancel();
    }

    @Override
    public void onOkResponse(JSONArray response) {
        List<TemperatureEntry> logSessionsList = new ArrayList<TemperatureEntry>();
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        Gson gson = builder.create();
        try {
            for (int i = 0; i < response.length(); i++) {
                logSessionsList.add(gson.fromJson(response.getJSONObject(i).toString(), TemperatureEntry.class));
            }
            mListener.onTemperatureEntryRecv(logSessionsList);
        } catch (JSONException ex)
        {
            mListener.onTemperatureEntryError();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onTemperatureEntryError();
    }
}
