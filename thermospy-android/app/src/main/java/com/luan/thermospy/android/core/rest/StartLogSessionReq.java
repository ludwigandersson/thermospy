package com.luan.thermospy.android.core.rest;

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
import com.luan.thermospy.android.core.pojo.LogSession;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.PutJsonObject;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ludde on 15-02-17.
 */
public class StartLogSessionReq implements AbstractServerRequest.ServerRequestListener<JSONObject>, RequestControl {

    private static final String LOG_TAG = StartLogSessionReq.class.getSimpleName();
    private final OnStartLogSessionListener mListener;
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
        List<LogSession> logSessionsList = new ArrayList<LogSession>();
        // Creates the json object which will manage the information received
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        Gson gson = builder.create();

        try {
            return new JSONObject(gson.toJson(mLogSession, LogSession.class));
        } catch (JSONException | JsonIOException e) {
            return null;
        }
    }

    public void setLogSession(LogSession logSession) {
        this.mLogSession = logSession;
        mPutJsonObjectReq.setJSONObject(getJsonObject());
    }

    public interface OnStartLogSessionListener
    {
        void onStartLogSessionRecv(LogSession session);
        void onStartLogSessionError();
    }
    private final PutJsonObject mPutJsonObjectReq;

    public StartLogSessionReq(RequestQueue queue, OnStartLogSessionListener listener, LogSession logSession)
    {
        mLogSession = logSession;
        mListener = listener;
        mPutJsonObjectReq = new PutJsonObject(queue, this, UrlRequestType.START_LOG_SESSION, getJsonObject());
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
            mListener.onStartLogSessionRecv(logSession);
        } catch (JsonSyntaxException ex)
        {
            mListener.onStartLogSessionError();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onStartLogSessionError();
    }
}
