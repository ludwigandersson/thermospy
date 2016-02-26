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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.luan.thermospy.android.core.pojo.Boundary;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.PutJsonObject;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Sets the image bounds to be used by the server when taking photos
 */
public class SetImgBoundsReq implements AbstractServerRequest.ServerRequestListener<JSONObject>, RequestControl {

    private static final String LOG_TAG = SetImgBoundsReq.class.getSimpleName();
    private final OnSetImgBoundsListener mListener;
    private Boundary mBounds;

    @Override
    public void request(String ip, int port) {
        mGetJsonObjectReq.request(ip, port);
    }

    @Override
    public void cancel() {
        mGetJsonObjectReq.cancel();
    }

    private JSONObject getJsonObject()
    {
        Gson gson = new Gson();

        try {
            return new JSONObject(gson.toJson(mBounds, Boundary.class));
        } catch (JSONException | JsonIOException e) {
            Log.e(LOG_TAG, "Failed to create json object of Camera Control Action object!", e);
            return null;
        }
    }

    public void setBounds(Boundary b) {
        this.mBounds = b;
        mGetJsonObjectReq.setJSONObject(getJsonObject());
    }

    public interface OnSetImgBoundsListener
    {
        void onSetImgBoundsRecv(Boundary imgBounds);
        void onSetImgBoundsError();
    }
    private final PutJsonObject mGetJsonObjectReq;

    public SetImgBoundsReq(RequestQueue queue, OnSetImgBoundsListener listener, Boundary bounds)
    {
        mBounds = bounds;
        mListener = listener;
        mGetJsonObjectReq = new PutJsonObject(queue, this, UrlRequestType.IMG_BOUNDS, getJsonObject(), new DefaultRetryPolicy(10000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    @Override
    public void onOkResponse(JSONObject response) {
        Gson gson = new Gson();
        try {
            Boundary bounds = gson.fromJson(response.toString(), Boundary.class);
            mListener.onSetImgBoundsRecv(bounds);
        } catch (JsonSyntaxException ex)
        {
            mListener.onSetImgBoundsError();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onSetImgBoundsError();
    }
}
