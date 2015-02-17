package com.luan.thermospy.android.core.rest;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.luan.thermospy.android.core.pojo.Boundary;
import com.luan.thermospy.android.core.pojo.LogSession;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.PutJsonObject;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by ludde on 15-02-17.
 */
public class StopLogSessionReq implements AbstractServerRequest.ServerRequestListener<JSONObject>, RequestControl {

    private static final String LOG_TAG = StopLogSessionReq.class.getSimpleName();
    private final OnStopLogSessionListener mListener;
    private LogSession mLogSession;

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
            return new JSONObject(gson.toJson(mLogSession, Boundary.class));
        } catch (JSONException | JsonIOException e) {
            Log.e(LOG_TAG, "Failed to create json object of Camera Control Action object!", e);
            return null;
        }
    }

    public void setLogSession(LogSession logSession) {
        this.mLogSession = logSession;
        mPutJsonObjectReq.setJSONObject(getJsonObject());
    }

    public interface OnStopLogSessionListener
    {
        void onStopLogSessionRecv(LogSession session);
        void onStopLogSessionError();
    }
    private final PutJsonObject mPutJsonObjectReq;

    public StopLogSessionReq(RequestQueue queue, OnStopLogSessionListener listener, LogSession logSession)
    {
        mLogSession = logSession;
        mListener = listener;
        mPutJsonObjectReq = new PutJsonObject(queue, this, UrlRequestType.STOP_LOG_SESSION, getJsonObject());
    }

    @Override
    public void onOkResponse(JSONObject response) {
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        Gson gson = builder.create();
        try {
            LogSession logSession = gson.fromJson(response.toString(), LogSession.class);
            mListener.onStopLogSessionRecv(logSession);
        } catch (JsonSyntaxException ex)
        {
            mListener.onStopLogSessionError();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onStopLogSessionError();
    }
}
