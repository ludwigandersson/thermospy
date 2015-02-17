package com.luan.thermospy.android.core.rest;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.luan.thermospy.android.core.pojo.FoodType;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.PutJsonObject;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ludde on 15-02-17.
 */
public class CreateFoodTypeReq implements AbstractServerRequest.ServerRequestListener<JSONObject>, RequestControl {

    private static final String LOG_TAG = CreateFoodTypeReq.class.getSimpleName();
    private final OnFoodTypeListener mListener;
    private FoodType mFoodType;

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
            return new JSONObject(gson.toJson(mFoodType, FoodType.class));
        } catch (JSONException | JsonIOException e) {
            Log.e(LOG_TAG, "Failed to create json object of Camera Control Action object!", e);
            return null;
        }
    }

    public void setFoodType(FoodType logSession) {
        this.mFoodType = logSession;
        mPutJsonObjectReq.setJSONObject(getJsonObject());
    }

    public interface OnFoodTypeListener
    {
        void onFoodTypeRecv(FoodType session);
        void onFoodTypeError();
    }
    private final PutJsonObject mPutJsonObjectReq;

    public CreateFoodTypeReq(RequestQueue queue, OnFoodTypeListener listener, FoodType foodType)
    {
        mFoodType = foodType;
        mListener = listener;
        mPutJsonObjectReq = new PutJsonObject(queue, this, UrlRequestType.FOOD_TYPE, getJsonObject());
    }

    @Override
    public void onOkResponse(JSONObject response) {


        Gson gson = new Gson();
        try {
            FoodType logSession = gson.fromJson(response.toString(), FoodType.class);
            mListener.onFoodTypeRecv(logSession);
        } catch (JsonSyntaxException ex)
        {
            mListener.onFoodTypeError();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onFoodTypeError();
    }
}
