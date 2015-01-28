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
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package com.luan.thermospy.server.actions;

/**
 * 
 * The CameraAction interface describes the functions that needs to be
 * supported by the camera device. 
 */
public interface CameraAction
{
    /**
     * Start the Camera device. Keep running until stop is called.
     */
    public void start();
    /**
     * Stop the Camera Device. Stop the running device.
     */
    public void stop();
    /**
     * Run the camera device once
     * @return true if successful
     */
    public boolean singleshot();
    /**
     * Status
     * @return true if the camera device is up and running
     */
    public boolean isRunning();
}
