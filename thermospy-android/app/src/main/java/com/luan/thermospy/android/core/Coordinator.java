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

package com.luan.thermospy.android.core;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.luan.thermospy.android.fragments.AlarmCondition;

/**
 * Singleton class, acts like an interface for common data.
 */
public class Coordinator {
    private static final Coordinator ourInstance = new Coordinator();
    private AlarmSettings mAlarmSettings;
    private String temperature;

    public static Coordinator getInstance() {
        return ourInstance;
    }

    private Database database;

    private ServerSettings mServerSettings;

    private RequestQueue requestQueue;

    private Coordinator() {

    }

    public void setRequestQueue(RequestQueue requestQueue)
    {
        this.requestQueue = requestQueue;
    }

    public RequestQueue getRequestQueue()
    {
        return requestQueue;
    }

    public void initDb(Context context)
    {
        database = new Database(context);
        try {
            database.open();
            mServerSettings = ServerSettings.fromDb(database);
            mAlarmSettings = AlarmSettings.fromDb(database);
            temperature = database.getString("temperature");
        } catch (Exception e) {
            mServerSettings = new ServerSettings(0, "", false);
            mAlarmSettings = new AlarmSettings("0", false, AlarmCondition.GREATER_THAN_OR_EQUAL);
            temperature = "--";
        }
    }

    public void save()
    {
        mServerSettings.serialize(database);
        mAlarmSettings.serialize(database);
        try {
            database.putString("temperature", temperature);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ServerSettings getServerSettings() {
        return mServerSettings;
    }

    public AlarmSettings getAlarmSettings() { return mAlarmSettings; }


    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
