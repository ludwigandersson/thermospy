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

package com.luan.thermospy.android.core.serverrequest;

/**
 * Enum describing the different requests that are available.
 */
public enum UrlRequestType {
    SERVER_STATUS(1, "service-status"),
    GET_TEMPERATURE(2, "get-temp"),
    CAMERA_CONTROL(3, "camera-control"),
    GET_IMAGE(4, "get-last-image"),
    IMG_BOUNDS(5, "img-boundary"),
    REFRESH_RATE(6, "refresh-rate"),
    CAMERA_DEVICE_CONFIG(7, "camera-device-config"),
    OCR_CONFIG(8, "digit-recognizer-config"),
    LOG_SESSION_LIST(9, "log-session/list"),
    LOG_SESSION(10, "log-session"),
    START_LOG_SESSION(11, "log-session/start"),
    STOP_LOG_SESSION(12, "log-session/stop"),
    FOOD_TYPE(13, "food-type"),
    FOOD_TYPE_LIST(14, "food-type/list"),
    CUT_TYPE(15, "cut-type"),
    CUT_TYPE_LIST(16, "cut-type/list"),
    TEMPERATURE_ENTRY_LIST(17, "get-temperature-entries/list"),


    UNKNOWN(-1, "");

    private final int mId;
    private final String mRequest;
    private UrlRequestType(int id, String requestString)
    {
        mId = id;
        mRequest = requestString;
    }

    @Override
    public String toString() {
        return mRequest;
    }
}

