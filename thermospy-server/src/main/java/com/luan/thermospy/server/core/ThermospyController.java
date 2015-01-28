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
package com.luan.thermospy.server.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.luan.thermospy.server.actions.CameraAction;
import org.eclipse.jetty.util.log.Log;

/**
 * The man in the middle who connects all the dots.
 * The Controller contains the settings and actions and is accessed fromm
 * different threads.
 */
public class ThermospyController {
    @JsonProperty
    private int refreshRate = 0;
    @JsonIgnore
    final private Object myLock = new Object();
    @JsonIgnore
    CameraAction camera = null;
    @JsonIgnore
    private int temperature = Integer.MIN_VALUE;
    @JsonIgnore
    private Boundary displayBoundary = new Boundary(0,0,0,0);
    @JsonIgnore
    private ServerStatus serverStatus = ServerStatus.OK;
    
    public int getTemperature() {
        synchronized(myLock){
            return temperature;
        }
    }

    public void setTemperature(int temperature) {
        synchronized(myLock){
            if (this.temperature != temperature)
            {
                String fromTemperature = this.temperature == Integer.MIN_VALUE ? "--" : Integer.toString(this.temperature);
                String toTemperature = temperature == Integer.MIN_VALUE ? "--" : Integer.toString(temperature);
                Log.getLog().info("Temperature changed from " + fromTemperature + " to " + toTemperature );
                this.temperature = temperature;
            }
        }
    }

    public Boundary getDisplayBoundary() {
        synchronized(myLock){
            return displayBoundary;
        }
    }

    public void setDisplayBoundary(Boundary displayBoundary) {
        synchronized(myLock){
            this.displayBoundary = displayBoundary;
        }
    }

    public void start() {
        camera.start();
    }
    public boolean singleshot() {
        return camera.singleshot();
    }
    
    public void stop() {
        camera.stop();
    }
    public void setCameraAction(CameraAction actionHandler)
    {
        this.camera = actionHandler;
    }

    public int getRefreshRate() {
        synchronized(myLock) {
            return refreshRate;
        }
    }

    public void setRefreshRate(int refreshRate) {
        synchronized(myLock) {
            this.refreshRate = refreshRate;
        }
    }

    public boolean getServiceStatus() {
        return camera.isRunning();
    }

    /**
     * @return the serverStatus
     */
    public ServerStatus getServerStatus() {
        synchronized(myLock) {
            return serverStatus;
        }
    }

    /**
     * @param serverStatus the serverStatus to set
     */
    public void setServerStatus(ServerStatus serverStatus) {
        synchronized(myLock) {
            this.serverStatus = serverStatus;
        }
    }
    
    
}
