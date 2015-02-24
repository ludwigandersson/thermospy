package com.luan.thermospy.android.core.rest;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.luan.thermospy.android.core.pojo.LogSession;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.GetJsonObject;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ludde on 15-02-17.
 */
public class GetActiveLogSessionReq implements AbstractServerRequest.ServerRequestListener<JSONObject>, RequestControl {

    private final GetJsonObject mGetJsonObjectReq;

    private OnGetActiveLogSessionsListener mListener;

    public interface OnGetActiveLogSessionsListener
    {
        void onActiveLogSessionRecv(LogSession logSession);
        void onActiveLogSessionError();
    }


    public GetActiveLogSessionReq(RequestQueue queue, OnGetActiveLogSessionsListener listener)
    {
        mGetJsonObjectReq = new GetJsonObject(queue, this, UrlRequestType.LOG_SESSION);
        mListener = listener;
    }



    @Override
    public void request(String ip, int port) {
        mGetJsonObjectReq.setRequestParams(new ArrayList<String>(){{
                                               add("active");
                                           }});
        mGetJsonObjectReq.request(ip, port);
    }

    @Override
    public void cancel() {
        mGetJsonObjectReq.cancel();
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
        mListener.onActiveLogSessionRecv(gson.fromJson(response.toString(), LogSession.class));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onActiveLogSessionError();
    }
}