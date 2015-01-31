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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data class. Contains settings for the refresh rate to be used.
 * When the server is running it will sleep for some time, determined, by this
 * class.
 */
public class RefreshRate {
    private int refreshRate;
    public RefreshRate()
    {

    }

    public RefreshRate(int refreshRate)
    {
        this.refreshRate = refreshRate;
    }

    @JsonProperty
    public int getRefreshRate()
    {
        return refreshRate;
    }
    
    public void setRefreshRate(int refreshRate)
    {
      this.refreshRate = refreshRate;
    }
}
