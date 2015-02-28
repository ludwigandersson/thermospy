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
 * 
 * Contains information about the server and the running service.
 * 
 */
public class ServiceStatus {
    private boolean running;

    private ServerStatus error;
    
    public ServiceStatus()
    {
        running = false;
        error = ServerStatus.UNKNOWN;
    }
    
    public ServiceStatus(boolean status, ServerStatus error)
    {
        running = status;
        this.error = error;
    }

    /**
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @param running the running to set
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * @return the error
     */
    public ServerStatus getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(ServerStatus error) {
        this.error = error;
    }
    
    
}
