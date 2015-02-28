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

import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;

/**
 * Request an image from the server
 */
public class GetImage extends AbstractServerRequest<ImageRequest, Bitmap> implements Response.Listener<Bitmap>, Response.ErrorListener {
    public GetImage(RequestQueue requestQueue, ServerRequestListener listener, UrlRequestType type) {
        super(requestQueue, listener, type);
    }

    @Override
    public Request createRequest(String url) {
        return new com.android.volley.toolbox.ImageRequest(url, this, 0, 0, Bitmap.Config.ALPHA_8, this );
    }
    @Override
    public void onResponse(Bitmap response) {
        getListener().onOkResponse(response);
    }
    @Override
    public void onErrorResponse(VolleyError error)
    {
        getListener().onErrorResponse(error);
    }
}
