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

import com.luan.thermospy.server.core.ServiceStatus;
import com.luan.thermospy.server.core.ThermospyController;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Receives commands from clients to return the current server and service status.
 * The server status is the status about the server. The services status is
 * a boolean telling whether or not the service is running.
 */
@Path("/thermospy-server/service-status")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServiceStatusResource {
    private final ThermospyController controller;
    public ServiceStatusResource(ThermospyController controller)
    {
        this.controller = controller;
    }

    @GET
    public ServiceStatus fetch() {
        return new ServiceStatus(controller.getServiceStatus(), controller.getServerStatus());
    }
}
