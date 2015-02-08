package com.luan.thermospy.android.core.serverrequest.type;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;

import org.json.JSONObject;

/**
 * Created by ludde on 15-02-01.
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

