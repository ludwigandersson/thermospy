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
package com.luan.thermospy.server.resources;


import com.codahale.metrics.annotation.Timed;
import com.luan.thermospy.server.core.Temperature;
import com.luan.thermospy.server.core.ThermospyController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
* Receives commands from clients and responds with current temperature
*/
@Path("/thermospy-server/get-temp")
@Produces(MediaType.APPLICATION_JSON)
public class GetTempResource {

    private final ThermospyController controller;
    public GetTempResource(ThermospyController controller)
    {
        this.controller = controller;
    }

    @GET
    @Timed
    public Temperature fetch() {
        int temp = controller.getTemperature();
        return new Temperature(temp);
    }
}
