
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.gson.JsonSyntaxException;
import com.luan.thermospy.android.core.pojo.LogSession;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.PutJsonObject;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Request that starts a new log session
 */
public class UpdateLogSessionReq implements AbstractServerRequest.ServerRequestListener<JSONObject>, RequestControl {

    private static final String LOG_TAG = UpdateLogSessionReq.class.getSimpleName();
    private final OnUpdateLogSessionListener mListener;
    private LogSession mLogSession;

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
        try {
            return LogSession.toJson(mLogSession);
        } catch (JSONException e) {
            return null;
        }
    }

    public void setLogSession(LogSession logSession) {
        this.mLogSession = logSession;
        mPutJsonObjectReq.setJSONObject(getJsonObject());
    }

    public interface OnUpdateLogSessionListener
    {
        void onLogSessionUpdated(LogSession session);
        void onUpdateLogSessionError();
    }
    private final PutJsonObject mPutJsonObjectReq;

    public UpdateLogSessionReq(RequestQueue queue, OnUpdateLogSessionListener listener, LogSession logSession)
    {
        mLogSession = logSession;
        mListener = listener;
        mPutJsonObjectReq = new PutJsonObject(queue, this, UrlRequestType.UPDATE_LOG_SESSION, getJsonObject(), new DefaultRetryPolicy(2000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public void onOkResponse(JSONObject response) {

        try {
            LogSession logSession = LogSession.fromJson(response.toString());
            mListener.onLogSessionUpdated(logSession);
        } catch (JsonSyntaxException ex)
        {
            mListener.onUpdateLogSessionError();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onUpdateLogSessionError();
    }
}
