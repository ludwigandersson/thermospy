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
package com.luan.thermospy.android.core.pojo;

/**
 * The server status enum
 */
public enum ServerStatus {
    UNKNOWN(-1),
    OK(0),
    INTERNAL_SERVER_ERROR(100);


    private int id;
    ServerStatus(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public static ServerStatus parse(int id)
    {
        switch (id)
        {
            case 0:
                return OK;
            case 100:
                return INTERNAL_SERVER_ERROR;
            default:
                return UNKNOWN;
        }
    }

    public static ServerStatus parseString(String typeInt) {
        switch (typeInt)
        {
            case "OK":
                return OK;
            case "INTERNAL_SERVER_ERROR":
                return INTERNAL_SERVER_ERROR;

            default:
                return UNKNOWN;

        }
    }

    @Override
    public String toString() {
        switch (id)
        {
            case 0:
                return "OK";
            case 100:
                return "Internal Server Error";
            default:
                return "Unknown Error";
        }
    }
}
