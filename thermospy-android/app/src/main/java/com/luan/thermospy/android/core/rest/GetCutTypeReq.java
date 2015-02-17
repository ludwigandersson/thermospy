package com.luan.thermospy.android.core.rest;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.luan.thermospy.android.core.pojo.CutType;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.GetJsonObject;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ludde on 15-02-17.
 */
public class GetCutTypeReq implements AbstractServerRequest.ServerRequestListener<JSONObject>, RequestControl {

    private final GetJsonObject mGetJsonObjectReq;
    private int mCutTypeId = -1;
    private OnGetCutTypesListener mListener;

    public interface OnGetCutTypesListener
    {
        void onCutTypeRecv(CutType logSession);
        void onCutTypeError();
    }


    public GetCutTypeReq(RequestQueue queue, OnGetCutTypesListener listener, int logSessionId)
    {
        mGetJsonObjectReq = new GetJsonObject(queue, this, UrlRequestType.CUT_TYPE);
        mListener = listener;
        mCutTypeId = logSessionId;
    }

    public void setCutTypeId(int id)
    {
        mCutTypeId = id;
    }

    @Override
    public void request(String ip, int port) {
        mGetJsonObjectReq.setRequestParams(new ArrayList<String>(){{
                                               add(Integer.toString(mCutTypeId));
                                           }});
        mGetJsonObjectReq.request(ip, port);
    }

    @Override
    public void cancel() {
        mGetJsonObjectReq.cancel();
    }

    @Override
    public void onOkResponse(JSONObject response) {
        Gson gson = new Gson();
        mListener.onCutTypeRecv(gson.fromJson(response.toString(), CutType.class));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onCutTypeError();
    }
}
