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

package com.luan.thermospy.android.core.serverrequest.type;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;

import org.json.JSONObject;

/**
 * Put a json object to the server
 */
public class PutJsonObject extends AbstractServerRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    private JSONObject mJsonObject;

    private RetryPolicy mRetryPolicy = new DefaultRetryPolicy();

    public PutJsonObject(RequestQueue requestQueue, ServerRequestListener listener, UrlRequestType requestType, JSONObject object) {
        super(requestQueue, listener, requestType);
        this.mJsonObject = object;
    }
    public PutJsonObject(RequestQueue requestQueue, ServerRequestListener listener, UrlRequestType requestType, JSONObject object, RetryPolicy retryPolicy) {
        super(requestQueue, listener, requestType);
        this.mJsonObject = object;
        this.mRetryPolicy = retryPolicy;
    }
    
    public void setJSONObject(JSONObject jsonObject)
    {
        mJsonObject = jsonObject;
    }
    
    @Override
    public Request createRequest(String url) {
        Request<JSONObject> request = new JsonObjectRequest(url, mJsonObject, this, this);
        request.setRetryPolicy(mRetryPolicy);
        return request;
    }

    @Override
    public void onResponse(JSONObject response) {
        getListener().onOkResponse(response);
    }
    @Override
    public void onErrorResponse(VolleyError error)
    {
        getListener().onErrorResponse(error);
    }
}

