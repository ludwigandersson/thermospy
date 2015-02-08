package com.luan.thermospy.android.core.serverrequest.type;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;

import org.json.JSONObject;

/**
 * Request a JSON Object
 */
public class GetJsonObject extends AbstractServerRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    public GetJsonObject(RequestQueue requestQueue, ServerRequestListener listener, UrlRequestType requestType) {
        super(requestQueue, listener, requestType);
    }

    @Override
    public Request createRequest(String url) {

        return new JsonObjectRequest(url, null, this, this);
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
