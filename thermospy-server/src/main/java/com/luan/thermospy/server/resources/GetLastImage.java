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

import com.luan.thermospy.server.core.Boundary;
import com.luan.thermospy.server.core.ServerStatus;
import com.luan.thermospy.server.core.ThermospyController;
import com.luan.thermospy.server.hal.CameraDevice;
import javax.ws.rs.core.Response;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.eclipse.jetty.util.log.Log;

/**
 * Receives commands from clients and responds with last available picture
 */
@Path("/thermospy-server/get-last-image")
@Produces("image/png")
public class GetLastImage {


    private final CameraDevice cameraDevice;
    private final ThermospyController controller;
    public GetLastImage(ThermospyController controller, CameraDevice cameraDevice) {
        this.cameraDevice = cameraDevice;
        this.controller = controller;
    }

    @GET
    public Response fetch() {
        File file = new File(cameraDevice.getConfig().getFilePath());
        if (file.exists()) {
            try {
                java.nio.file.Path path = Paths.get(file.getAbsolutePath());
                byte[] imageData = Files.readAllBytes(path);

                return Response.ok(new ByteArrayInputStream(imageData)).build();
            } catch (Exception e) {
                Log.getLog().info("Exception: " + e.getMessage(), e);
            }
        }

        // uncomment line below to send non-streamed
        return Response.status(Response.Status.NOT_FOUND).build();

    }
    
    @GET
    @Path("/reset-and-fetch")
    public Response resetAndFetch()
    {
        // Stop service if running
        if (controller.getServiceStatus())
        {
            controller.stop();
        }
        
        controller.setDisplayBoundary(new Boundary());
        controller.singleshot();
        
        if (controller.getServerStatus() == ServerStatus.OK) {
            return fetch();
        } else {
            Log.getLog().warn("Cannot fetch last image because an internal server error occured!");
            return Response.serverError().build();
        }
        
    }

}
