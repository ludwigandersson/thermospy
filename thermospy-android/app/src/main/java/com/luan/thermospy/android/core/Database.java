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

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

/**
 * A simple key-value database for settings
 */
public class Database {

    final Context context;
    DB snappydb;
    Database(Context context)
    {
        this.context = context;
        this.snappydb = null;
    }

    public void open() throws Exception {

        try {
            snappydb = DBFactory.open(context);
        }
        catch (SnappydbException ex)
        {

        }
    }

    public void close() throws Exception {
        if (snappydb != null)
        {
            snappydb.close();
            snappydb = null;
        }
    }

    public Boolean getBoolean(String key) throws Exception
    {
        return snappydb.getBoolean(key);
    }

    public void putBoolean(String key, Boolean value) throws Exception
    {
        snappydb.putBoolean(key, value);
    }

    public String getString(String key) throws Exception
    {
        return snappydb.get(key);
    }

    public void putString(String key, String value) throws Exception
    {
        snappydb.put(key, value);
    }

    public int getInt(String key) throws Exception
    {
        return snappydb.getInt(key);
    }

    public void putInt(String key, int value) throws Exception
    {
        snappydb.putInt(key, value);
    }

}
