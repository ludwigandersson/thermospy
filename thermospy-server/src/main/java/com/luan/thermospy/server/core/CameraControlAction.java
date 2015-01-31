/**
 * 
 * Copyright 2015 Ludwig Andersson
 * 
 * This file is part of Thermospy-server.
 *
 *  Thermospy-server is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 * Thermospy-server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Thermospy-server.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package com.luan.thermospy.server.core;

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
    
    public static CameraControlAction parse(int id)
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
