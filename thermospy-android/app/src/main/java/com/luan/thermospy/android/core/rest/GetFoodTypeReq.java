package com.luan.thermospy.android.core.rest;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.luan.thermospy.android.core.pojo.FoodType;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.GetJsonObject;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ludde on 15-02-17.
 */
public class GetFoodTypeReq implements AbstractServerRequest.ServerRequestListener<JSONObject>, RequestControl {

    private final GetJsonObject mGetJsonObjectReq;
    private int mFoodTypeId = -1;
    private OnGetFoodTypesListener mListener;

    public interface OnGetFoodTypesListener
    {
        void onFoodTypeRecv(FoodType logSession);
        void onFoodTypeError();
    }


    public GetFoodTypeReq(RequestQueue queue, OnGetFoodTypesListener listener, int logSessionId)
    {
        mGetJsonObjectReq = new GetJsonObject(queue, this, UrlRequestType.FOOD_TYPE);
        mListener = listener;
        mFoodTypeId = logSessionId;
    }

    public void setFoodTypeId(int id)
    {
        mFoodTypeId = id;
    }

    @Override
    public void request(String ip, int port) {
        mGetJsonObjectReq.setRequestParams(new ArrayList<String>(){{
                                               add(Integer.toString(mFoodTypeId));
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
        mListener.onFoodTypeRecv(gson.fromJson(response.toString(), FoodType.class));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onFoodTypeError();
    }
}
