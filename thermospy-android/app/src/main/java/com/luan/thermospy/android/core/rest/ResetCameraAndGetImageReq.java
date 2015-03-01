/*
 * Copyright 2015 Ludwig Andersson
 *
 * This file is part of Thermospy-android.
 *
 * Thermospy-android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Thermospy-android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Thermospy-android.  If not, see <http://www.gnu.org/licenses/>.
 */

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
public class ResetCameraAndGetImageReq implements AbstractServerRequest.ServerRequestListener<Bitmap>, RequestControl {

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

    public ResetCameraAndGetImageReq(RequestQueue queue, OnGetImgListener listener)
    {
        mImageReq = new GetImage(queue, this, UrlRequestType.RESET_AND_GET_IMAGE);
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