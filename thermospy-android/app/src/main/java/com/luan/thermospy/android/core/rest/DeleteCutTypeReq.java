package com.luan.thermospy.android.core.rest;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.GenericStringRequest;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import java.util.ArrayList;

/**
 * Created by ludde on 15-02-17.
 */
public class DeleteCutTypeReq implements AbstractServerRequest.ServerRequestListener<String>, RequestControl {

    private static final String LOG_TAG = DeleteCutTypeReq.class.getSimpleName();
    private final GenericStringRequest mStringRequest;
    private int mCutTypeId = -1;
    private OnGetCutTypesListener mListener;

    public interface OnGetCutTypesListener
    {
        void onCutTypeDeleted(int id);
        void onCutTypeError();
    }


    public DeleteCutTypeReq(RequestQueue queue, OnGetCutTypesListener listener, int cutTypeId)
    {
        mStringRequest = new GenericStringRequest(queue, this, UrlRequestType.CUT_TYPE, Request.Method.DELETE);
        mListener = listener;
        mCutTypeId = cutTypeId;
    }

    public void setCutTypeId(int id)
    {
        mCutTypeId = id;
    }

    @Override
    public void request(String ip, int port) {
        mStringRequest.setRequestParams(new ArrayList<String>(){{
            add(Integer.toString(mCutTypeId));
        }});
        mStringRequest.request(ip, port);
    }

    @Override
    public void cancel() {
        mStringRequest.cancel();
    }

    @Override
    public void onOkResponse(String response) {
        Log.d(LOG_TAG, "Received response! "+response);
        mListener.onCutTypeDeleted(mCutTypeId);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onCutTypeError();
    }
}