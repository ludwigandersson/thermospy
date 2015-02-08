package com.luan.thermospy.android.core.rest;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.luan.thermospy.android.core.pojo.Boundary;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.GetJsonObject;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import org.json.JSONObject;

/**
 * Created by ludde on 15-02-01.
 */
public class GetImgBoundsReq implements AbstractServerRequest.ServerRequestListener<JSONObject>, RequestControl {

    private final OnGetImgBoundsListener mListener;

    @Override
    public void request(String ip, int port) {
        mGetJsonObjectReq.request(ip, port);
    }

    @Override
    public void cancel() {
        mGetJsonObjectReq.cancel();
    }

    public interface OnGetImgBoundsListener
    {
        void onImgBoundsRecv(Boundary imgBoundary);
        void onImgBoundsError();
    }
    private final GetJsonObject mGetJsonObjectReq;

    public GetImgBoundsReq(RequestQueue queue, OnGetImgBoundsListener listener)
    {
        mGetJsonObjectReq = new GetJsonObject(queue, this, UrlRequestType.IMG_BOUNDS);
        mListener = listener;
    }

    @Override
    public void onOkResponse(JSONObject response) {
        Gson gson = new Gson();
        try {
            Boundary t = gson.fromJson(response.toString(), Boundary.class);
            mListener.onImgBoundsRecv(t);
        } catch (JsonSyntaxException ex)
        {
            mListener.onImgBoundsError();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onImgBoundsError();
    }
}
