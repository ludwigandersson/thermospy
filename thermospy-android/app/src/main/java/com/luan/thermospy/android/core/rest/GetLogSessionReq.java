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
import com.luan.thermospy.android.core.pojo.LogSession;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.GetJsonObject;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

/**
<<<<<<< HEAD
 * Request that gets a log sesisson
=======
 * Created by ludde on 15-02-17.
>>>>>>> 490ad3b24612c7ca510805e33294de062c538504
 */
public class GetLogSessionReq implements AbstractServerRequest.ServerRequestListener<JSONObject>, RequestControl {

    private final GetJsonObject mGetJsonObjectReq;
    private int mLogSessionId = -1;
    private OnGetLogSessionsListener mListener;

    public interface OnGetLogSessionsListener
    {
        void onLogSessionRecv(LogSession logSession);
        void onLogSessionError();
    }


    public GetLogSessionReq(RequestQueue queue, OnGetLogSessionsListener listener, int logSessionId)
    {
        mGetJsonObjectReq = new GetJsonObject(queue, this, UrlRequestType.LOG_SESSION);
        mListener = listener;
        mLogSessionId = logSessionId;
    }

    public void setLogSessionId(int id)
    {
        mLogSessionId = id;
    }

    @Override
    public void request(String ip, int port) {
        mGetJsonObjectReq.setRequestParams(new ArrayList<String>(){{
                                               add(Integer.toString(mLogSessionId));
                                           }});
        mGetJsonObjectReq.request(ip, port);
    }

    @Override
    public void cancel() {
        mGetJsonObjectReq.cancel();
    }

    @Override
    public void onOkResponse(JSONObject response) {
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        Gson gson = builder.create();
        mListener.onLogSessionRecv(gson.fromJson(response.toString(), LogSession.class));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onLogSessionError();
    }
}
