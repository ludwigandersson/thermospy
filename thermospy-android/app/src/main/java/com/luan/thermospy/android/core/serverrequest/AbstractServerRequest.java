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

package com.luan.thermospy.android.core.serverrequest;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import java.util.List;

/**
 * Abstract class handling common functions for creating a requests. It builds the Url that the
 * derived class specifies and carries out the request to the server.
 *
 * The derived class needs to implement the createRequest method.
 * @param <T> The type of Request that will be used (Volley Request type)
 * @param <R> The type of response that will be
 */
public abstract class AbstractServerRequest<T, R> implements RequestControl {
    private static final String LOG_TAG = AbstractServerRequest.class.getSimpleName();

    public interface ServerRequestListener<R>
    {
        void onOkResponse(R response);
        void onErrorResponse(VolleyError error);
    };

    private final ServerRequestListener<R> mListener;
    private final UrlRequestType mRequestType;
    private final RequestQueue mRequestQueue;
    private List<String> requestParams = null;

    protected ServerRequestListener<R> getListener()
    {
        return mListener;
    }

    public AbstractServerRequest(RequestQueue requestQueue, ServerRequestListener listener, UrlRequestType requestType)
    {
        this.mRequestQueue = requestQueue;
        this.mListener = listener;
        this.mRequestType = requestType;
    }

    protected abstract Request<T> createRequest(String url);

    public void setRequestParams(List<String> params)
    {
        requestParams = params;
    }
    public void request(String ip, int port)
    {
        try {
            final UrlRequest request = new UrlRequest.UrlRequestBuilder().setIpAddress(ip).setPort(port).setUrlRequest(mRequestType).setRequestParams(requestParams).build();
            final String url = request.toString();

            Request<T> serverRequest = createRequest(url);

            serverRequest.setTag(this);
            mRequestQueue.add(serverRequest);
        } catch (Exception e)
        {
            if (mRequestQueue != null)
            {
                mRequestQueue.cancelAll(this);
            }
            mListener.onErrorResponse(new VolleyError("Failed to perform request!", e));
        }
    }
    public void cancel()
    {
        if (mRequestQueue != null) {
            this.mRequestQueue.cancelAll(this);
        }
    }

}
