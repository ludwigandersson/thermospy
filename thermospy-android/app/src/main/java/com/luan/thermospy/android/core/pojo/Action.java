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

package com.luan.thermospy.android.core.pojo;


/**
 * Data class. Contains information about what camera action to be run
 */
public class Action {
    private CameraControlAction actionId = CameraControlAction.UNKNOWN;

    public Action() {}

    public Action(CameraControlAction actionId)
    {
        this.actionId = actionId;
    }

    public CameraControlAction getActionId()
    {
        return actionId;
    }
}
