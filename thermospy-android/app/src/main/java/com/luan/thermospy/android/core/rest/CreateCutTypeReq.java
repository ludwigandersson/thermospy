package com.luan.thermospy.android.core.rest;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.luan.thermospy.android.core.pojo.CutType;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.PutJsonObject;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ludde on 15-02-17.
 */
public class CreateCutTypeReq implements AbstractServerRequest.ServerRequestListener<JSONObject>, RequestControl {

    private static final String LOG_TAG = CreateCutTypeReq.class.getSimpleName();
    private final OnCutTypeListener mListener;
    private CutType mCutType;

    @Override
    public void request(String ip, int port) {
        mPutJsonObjectReq.request(ip, port);
    }

    @Override
    public void cancel() {
        mPutJsonObjectReq.cancel();
    }

    private JSONObject getJsonObject()
    {
        Gson gson = new Gson();

        try {
            return new JSONObject(gson.toJson(mCutType, CutType.class));
        } catch (JSONException | JsonIOException e) {
            Log.e(LOG_TAG, "Failed to create json object of Camera Control Action object!", e);
            return null;
        }
    }

    public void setCutType(CutType logSession) {
        this.mCutType = logSession;
        mPutJsonObjectReq.setJSONObject(getJsonObject());
    }

    public interface OnCutTypeListener
    {
        void onCutTypeRecv(CutType session);
        void onCutTypeError();
    }
    private final PutJsonObject mPutJsonObjectReq;

    public CreateCutTypeReq(RequestQueue queue, OnCutTypeListener listener, CutType cut)
    {
        mCutType = cut;
        mListener = listener;
        mPutJsonObjectReq = new PutJsonObject(queue, this, UrlRequestType.CUT_TYPE, getJsonObject());
    }

    @Override
    public void onOkResponse(JSONObject response) {


        Gson gson = new Gson();
        try {
            CutType logSession = gson.fromJson(response.toString(), CutType.class);
            mListener.onCutTypeRecv(logSession);
        } catch (JsonSyntaxException ex)
        {
            mListener.onCutTypeError();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onCutTypeError();
    }
}
