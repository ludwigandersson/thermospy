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
import com.luan.thermospy.server.core.Boundary;
import com.luan.thermospy.server.core.ThermospyController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Receives commands from clients to set and get the Boundary to be used for
 * cropping the image when taking photos/running the recognizer.
 */
@Path("/thermospy-server/img-boundary")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImageBoundaryResource {
    private final ThermospyController controller;
    public ImageBoundaryResource(ThermospyController controller)
    {
        this.controller = controller;
    }

    @GET
    public Boundary fetch() {

        return controller.getDisplayBoundary();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response set(Boundary imgBoundary) {
        controller.setDisplayBoundary(imgBoundary);
        return Response.ok().entity(imgBoundary).build();
    }
}
