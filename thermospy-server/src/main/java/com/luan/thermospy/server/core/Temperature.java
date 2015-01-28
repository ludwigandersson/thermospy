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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 */
public class Temperature {

    private int temperature = 0;
    private int id = 0;
    public Temperature(){}

    public Temperature(int temperature)
    {
        this.temperature = temperature;
    }
    @JsonProperty
    public int getId()
    {
        return id;
    }

    @JsonProperty
    public int getTemperature()
    {
        return this.temperature;
    }
    
    public void setTemperature(int temperature)
    {
        this.temperature = temperature;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
}
