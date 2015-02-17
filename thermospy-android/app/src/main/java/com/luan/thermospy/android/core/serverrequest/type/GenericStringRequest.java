package com.luan.thermospy.android.core.serverrequest.type;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;


public class GenericStringRequest extends AbstractServerRequest implements Response.Listener<String>, Response.ErrorListener {
    private final int method;

    public GenericStringRequest(RequestQueue requestQueue, ServerRequestListener listener, UrlRequestType requestType, int method) {
        super(requestQueue, listener, requestType);
        this.method = method;
    }

    @Override
    public Request createRequest(String url) {
        return new StringRequest(method, url, this, this);
    }
    @Override
    public void onResponse(String response) {
            getListener().onOkResponse(response);
    }
    @Override
    public void onErrorResponse(VolleyError error)
    {
        getListener().onErrorResponse(error);
    }
}
