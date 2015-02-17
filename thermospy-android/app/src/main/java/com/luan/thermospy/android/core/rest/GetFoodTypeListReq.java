package com.luan.thermospy.android.core.rest;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.luan.thermospy.android.core.pojo.FoodType;
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
public class GetFoodTypeListReq implements AbstractServerRequest.ServerRequestListener<JSONArray>, RequestControl {

    private final GetJsonArray mGetJsonArrayReq;
    private OnGetFoodTypeListener mListener;

    public interface OnGetFoodTypeListener
    {
        void onFoodTypeRecv(List<FoodType> logSessionList);
        void onFoodTypeError();
    }


    public GetFoodTypeListReq(RequestQueue queue, OnGetFoodTypeListener listener)
    {
        mGetJsonArrayReq = new GetJsonArray(queue, this, UrlRequestType.FOOD_TYPE_LIST);
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
        List<FoodType> logSessionsList = new ArrayList<FoodType>();
        Gson gson = new Gson();
        try {
            for (int i = 0; i < response.length(); i++) {
                logSessionsList.add(gson.fromJson(response.getJSONObject(i).toString(), FoodType.class));
            }
            mListener.onFoodTypeRecv(logSessionsList);
        } catch (JSONException ex)
        {
            mListener.onFoodTypeError();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onFoodTypeError();
    }
}
