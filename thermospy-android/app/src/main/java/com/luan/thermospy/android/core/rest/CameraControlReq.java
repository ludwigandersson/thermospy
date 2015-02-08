package com.luan.thermospy.android.core.rest;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.luan.thermospy.android.core.pojo.Action;
import com.luan.thermospy.android.core.pojo.CameraControlAction;
import com.luan.thermospy.android.core.pojo.CameraControlActionDeserializer;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.PutJsonObject;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Request for CameraControl command. It will request the server to Start, stop or run once.
 */
public class CameraControlReq implements AbstractServerRequest.ServerRequestListener<JSONObject>, RequestControl {


    private static final String LOG_TAG = CameraControlReq.class.getSimpleName();
    private final OnCameraControlListener mListener;
    private Action mCameraControlAction;

    @Override
    public void request(String ip, int port) {
        mGetJsonObjectReq.request(ip, port);
    }

    @Override
    public void cancel() {
        mGetJsonObjectReq.cancel();
    }

    public Action getAction() {
        return mCameraControlAction;
    }

    public interface OnCameraControlListener
    {
        void onCameraControlResp(Action temperature);
        void onCameraControlError();
    }
    private final PutJsonObject mGetJsonObjectReq;

    public CameraControlReq(RequestQueue queue, OnCameraControlListener listener, Action cameraControlAction)
    {
        mCameraControlAction = cameraControlAction;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mGetJsonObjectReq = new PutJsonObject(queue, this, UrlRequestType.CAMERA_CONTROL, getJsonObject(), retryPolicy);
        mListener = listener;
    }

    public void setCameraControlAction(Action cameraControlAction)
    {
        mCameraControlAction = cameraControlAction;
        mGetJsonObjectReq.setJSONObject(getJsonObject());
    }

    private JSONObject getJsonObject()
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder = gsonBuilder.registerTypeAdapter(CameraControlAction.class, new CameraControlActionDeserializer() );
        Gson gson = gsonBuilder.create();

        try {
            String jsonStr = gson.toJson(mCameraControlAction, Action.class);
            return new JSONObject(jsonStr);
        } catch (JSONException | JsonIOException e) {
            Log.e(LOG_TAG, "Failed to create json object of Camera Control Action object!", e);
            return null;
        }
    }

    @Override
    public void onOkResponse(JSONObject response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder = gsonBuilder.registerTypeAdapter(CameraControlAction.class, new CameraControlActionDeserializer() );
        Gson gson = gsonBuilder.create();
        try {
            Action t = gson.fromJson(response.toString(), Action.class);
            mListener.onCameraControlResp(t);
        } catch (JsonSyntaxException ex)
        {
            mListener.onCameraControlError();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onCameraControlError();
    }
}
