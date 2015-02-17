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
public class DeleteFoodTypeReq implements AbstractServerRequest.ServerRequestListener<String>, RequestControl {

    private static final String LOG_TAG = DeleteFoodTypeReq.class.getSimpleName();
    private final GenericStringRequest mStringRequest;
    private int mFoodTypeId = -1;
    private OnGetFoodTypesListener mListener;

    public interface OnGetFoodTypesListener
    {
        void onFoodTypeDeleted(int id);
        void onFoodTypeError();
    }


    public DeleteFoodTypeReq(RequestQueue queue, OnGetFoodTypesListener listener, int cutTypeId)
    {
        mStringRequest = new GenericStringRequest(queue, this, UrlRequestType.FOOD_TYPE, Request.Method.DELETE);
        mListener = listener;
        mFoodTypeId = cutTypeId;
    }

    public void setFoodTypeId(int id)
    {
        mFoodTypeId = id;
    }

    @Override
    public void request(String ip, int port) {
        mStringRequest.setRequestParams(new ArrayList<String>(){{
            add(Integer.toString(mFoodTypeId));
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
        mListener.onFoodTypeDeleted(mFoodTypeId);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onFoodTypeError();
    }
}