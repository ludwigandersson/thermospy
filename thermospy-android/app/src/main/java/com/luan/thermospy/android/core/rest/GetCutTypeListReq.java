package com.luan.thermospy.android.core.rest;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.luan.thermospy.android.core.pojo.CutType;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.GetJsonArray;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludde on 15-02-17.
 */
public class GetCutTypeListReq implements AbstractServerRequest.ServerRequestListener<JSONArray>, RequestControl {

    private final GetJsonArray mGetJsonArrayReq;
    private OnGetCutTypeListener mListener;

    public interface OnGetCutTypeListener
    {
        void onCutTypeRecv(List<CutType> logSessionList);
        void onCutTypeError();
    }


    public GetCutTypeListReq(RequestQueue queue, OnGetCutTypeListener listener)
    {
        mGetJsonArrayReq = new GetJsonArray(queue, this, UrlRequestType.CUT_TYPE_LIST);
        mListener = listener;
    }

    @Override
    public void request(String ip, int port) {
        mGetJsonArrayReq.request(ip, port);
    }

    @Override
    public void cancel() {
        mGetJsonArrayReq.cancel();
    }

    @Override
    public void onOkResponse(JSONArray response) {
        List<CutType> logSessionsList = new ArrayList<CutType>();
        Gson gson = new Gson();
        try {
            for (int i = 0; i < response.length(); i++) {
                logSessionsList.add(gson.fromJson(response.getJSONObject(i).toString(), CutType.class));
            }
            mListener.onCutTypeRecv(logSessionsList);
        } catch (JSONException ex)
        {
            mListener.onCutTypeError();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onCutTypeError();
    }
}
