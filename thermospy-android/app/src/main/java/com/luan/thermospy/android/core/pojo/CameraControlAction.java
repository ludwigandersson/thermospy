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
 * Contains the supported actions START; RUNONCE; STOP
 */
public enum CameraControlAction {
    UNKNOWN(-1),
    START(0),
    RUNONCE(1),
    STOP(2);
    
    private int id;
    CameraControlAction(int id) 
    {
        this.id = id;
    }
    
    public int getId()
    {
        return id;
    }
    
    public static CameraControlAction fromInt(int id)
    {
        switch (id)
        {
            case 0:
                return START;
            case 1:
                return RUNONCE;
            case 2:
                return STOP;
            default:
                return UNKNOWN;
                
        }
    }
}
