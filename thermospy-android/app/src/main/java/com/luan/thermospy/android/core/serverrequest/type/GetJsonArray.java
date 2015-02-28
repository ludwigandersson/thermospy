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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;

import org.json.JSONArray;

/**
<<<<<<< HEAD
 * Request a JSON array Object
=======
 * Request a JSON Object
>>>>>>> 490ad3b24612c7ca510805e33294de062c538504
 */
public class GetJsonArray extends AbstractServerRequest implements Response.Listener<JSONArray>, Response.ErrorListener {
    public GetJsonArray(RequestQueue requestQueue, ServerRequestListener listener, UrlRequestType requestType) {
        super(requestQueue, listener, requestType);
    }

    @Override
    public Request createRequest(String url) {

        return new JsonArrayRequest(url, this, this);
    }
    @Override
    public void onResponse(JSONArray response) {
        getListener().onOkResponse(response);
    }
    @Override
    public void onErrorResponse(VolleyError error)
    {
        getListener().onErrorResponse(error);
    }
}
