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
package com.luan.thermospy.server.resources;
import com.luan.thermospy.server.core.RefreshRate;
import com.luan.thermospy.server.core.ThermospyController;
import org.eclipse.jetty.util.log.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/**
 * Receives commands from clients to set and return the current refresh rate.
 * The refresh rate is used by the server when it has started the camera service.
 * The service will sleep for <refresh rate> number of seconds before continuing.
 */
@Path("/thermospy-server/refresh-rate")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RefreshRateResource {

    private final ThermospyController controller;
    public RefreshRateResource(ThermospyController controller)
    {
        this.controller = controller;
    }

    @POST
    public Response set(RefreshRate refreshRate) {
        Log.getLog().info("Received post request: "+refreshRate.getRefreshRate());
        controller.setRefreshRate(refreshRate.getRefreshRate());
        return Response.ok().entity(refreshRate).build();
    }

    @GET
    public RefreshRate fetch() {
        return new RefreshRate(controller.getRefreshRate());
    }
}
