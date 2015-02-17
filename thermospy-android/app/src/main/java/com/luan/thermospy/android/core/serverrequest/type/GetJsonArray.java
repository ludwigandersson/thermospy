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
 * Request a JSON Object
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
