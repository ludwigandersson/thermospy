package com.luan.thermospy.android.core.serverrequest.type;

import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;

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
