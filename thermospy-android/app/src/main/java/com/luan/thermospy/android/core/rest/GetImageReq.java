package com.luan.thermospy.android.core.rest;

import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.luan.thermospy.android.core.serverrequest.AbstractServerRequest;
import com.luan.thermospy.android.core.serverrequest.UrlRequestType;
import com.luan.thermospy.android.core.serverrequest.type.GetImage;
import com.luan.thermospy.android.core.serverrequest.type.RequestControl;

/**
 * Created by ludde on 15-02-01.
 */
public class GetImageReq implements AbstractServerRequest.ServerRequestListener<Bitmap>, RequestControl {

    private final OnGetImgListener mListener;

    @Override
    public void request(String ip, int port) {
        mImageReq.request(ip, port);
    }

    @Override
    public void cancel() {
        mImageReq.cancel();
    }

    public interface OnGetImgListener
    {
        void onGetImgRecv(Bitmap imgBoundary);
        void onGetImgError();
    }
    private final GetImage mImageReq;

    public GetImageReq(RequestQueue queue, OnGetImgListener listener)
    {
        mImageReq = new GetImage(queue, this, UrlRequestType.GET_IMAGE);
        mListener = listener;
    }

    @Override
    public void onOkResponse(Bitmap image) {
        mListener.onGetImgRecv(image);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListener.onGetImgError();
    }
}