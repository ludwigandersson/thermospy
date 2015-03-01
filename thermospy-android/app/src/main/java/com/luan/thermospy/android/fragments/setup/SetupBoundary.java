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

package com.luan.thermospy.android.fragments.setup;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.luan.thermospy.android.R;
import com.luan.thermospy.android.core.Coordinator;
import com.luan.thermospy.android.core.pojo.AspectRatio;
import com.luan.thermospy.android.core.pojo.Boundary;
import com.luan.thermospy.android.core.rest.GetImageReq;
import com.luan.thermospy.android.core.rest.ResetCameraAndGetImageReq;
import com.luan.thermospy.android.core.rest.SetImgBoundsReq;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The SetupBoundary class is responsible for fetching an image from the server and requesting the
 * user to specify the bounds of the image where the server will try to parse the temperature.
 *
 * When the view is created a request to fetch the last image is made. When the image is received
 * an intent is sent to the CropLib class which takes care of the cropping.
 *
 * If the user specifies a boundary and press Done the view will notify its listener.
 * If the user cancels the user returns to the first view of the setup.
 */
public class SetupBoundary extends Fragment implements GetImageReq.OnGetImgListener, SetImgBoundsReq.OnSetImgBoundsListener, ResetCameraAndGetImageReq.OnGetImgListener, DialogInterface.OnCancelListener {
    private File mFileTemp = null;
    private OnSetupBoundaryListener mListener;
    private final static String LOG_TAG = SetupBoundary.class.getCanonicalName();

    @Override
    public void onCancel(DialogInterface dialog) {
        mListener.onSetupServerAborted();
    }

    static interface Arguments {
        String IP_ADDRESS = "ipaddress";
        String PORT = "port";
    }

    private String mIpAddress;
    private int mPort;
    private RequestQueue mRequestQueue;
    ProgressDialog mProgress = null;

    GetImageReq mGetImageReq;
    ResetCameraAndGetImageReq mResetCameraAndGetImageReq;
    SetImgBoundsReq mSetImgBoundsReq;

    public static SetupBoundary newInstance(String ip, int port) {
        Bundle args = new Bundle();
        args.putString(Arguments.IP_ADDRESS, ip);
        args.putInt(Arguments.PORT, port);

        SetupBoundary fragment = new SetupBoundary();
        fragment.setArguments(args);
        return fragment;
    }

    public SetupBoundary() {
        // Required empty public constructor
    }

    private void takePhoto()
    {
        if (mProgress == null) {
            mProgress = new ProgressDialog(getActivity());
            mProgress.setOnCancelListener(this);
        }
        mProgress.setCanceledOnTouchOutside(false);

        mProgress.setTitle("Please wait");
        mProgress.setMessage("Fetching image from camera...");
        mResetCameraAndGetImageReq.request(mIpAddress, mPort);
        mProgress.show();
    }

    private void requestBounds(Boundary b)
    {
        Log.d(this.getClass().getName(), "Set bounds!");

        mProgress.setTitle("Please wait");
        mProgress.setMessage("Applying settings...");
        mSetImgBoundsReq.setBounds(b);
        mSetImgBoundsReq.request(mIpAddress, mPort);
        mProgress.show();

    }


    private void runImgCrop()
    {
       SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
       int val = Integer.parseInt(settings.getString(getString(R.string.pref_key_aspect_ratio), "1"));

       AspectRatio ratio = AspectRatio.fromInt(val);

       Uri outputUri = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
       new Crop(Uri.fromFile(mFileTemp)).output(outputUri).withAspect(ratio.getX(),ratio.getY()).start(getActivity(), this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Crop.REQUEST_CROP && resultCode == Activity.RESULT_OK) {
            Rect rect = Crop.getRect(data);
            Boundary b = new Boundary(rect.left, rect.top, rect.width(), rect.height());
            requestBounds(b);
        }
        else if (resultCode == Crop.RESULT_REFRESH)
        {
            takePhoto();
        }
        else {

            mListener.onSetupServerAborted();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mIpAddress = savedInstanceState.getString(Arguments.IP_ADDRESS, mIpAddress);
            mPort = savedInstanceState.getInt(Arguments.PORT, mPort);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_set_image_bounds, container, false);
        // No view exists for this dude.
        return v;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSetupBoundaryListener) activity;
            mIpAddress = getArguments().getString(Arguments.IP_ADDRESS);
            mPort = getArguments().getInt(Arguments.PORT);
            mRequestQueue = Coordinator.getInstance().getRequestQueue();
            mSetImgBoundsReq = new SetImgBoundsReq(mRequestQueue, this, new Boundary(0,0,0,0));
            mGetImageReq = new GetImageReq(mRequestQueue, this);
            mResetCameraAndGetImageReq = new ResetCameraAndGetImageReq(mRequestQueue, this);
            takePhoto();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAlarmFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mSetImgBoundsReq = null;
        mGetImageReq = null;
        mResetCameraAndGetImageReq = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();
        }
        mSetImgBoundsReq.cancel();
        mGetImageReq.cancel();
        mResetCameraAndGetImageReq.cancel();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();

        }

        mSetImgBoundsReq.cancel();
        mGetImageReq.cancel();
        mResetCameraAndGetImageReq.cancel();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Arguments.IP_ADDRESS, mIpAddress);
        outState.putInt(Arguments.PORT, mPort);

    }

    @Override
    public void onGetImgRecv(Bitmap bitmap) {
        //create a file to write bitmap data
        mFileTemp = new File(getActivity().getCacheDir(), "thermospy.png");
        try {
            mFileTemp.createNewFile();
            //Convert bitmap to byte array

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapData = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(mFileTemp);
            fos.write(bitmapData);

            runImgCrop();
            mProgress.dismiss();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to run crop activity!", e);
            mProgress.dismiss();
            mListener.onSetupServerAborted();
        }

    }

    @Override
    public void onGetImgError() {
        mListener.onSetupServerAborted();
    }

    @Override
    public void onSetImgBoundsRecv(Boundary imgBounds) {
        mProgress.dismiss();
        mListener.onBoundsSpecified(imgBounds);
    }

    @Override
    public void onSetImgBoundsError() {
        mProgress.hide();
        mListener.onSetupServerAborted();
    }

    public interface OnSetupBoundaryListener {
        public void onBoundsSpecified(Boundary bounds);
        public void onSetupServerAborted();
    }

}
