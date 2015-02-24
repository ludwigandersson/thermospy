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

import com.luan.thermospy.server.core.Action;
import com.luan.thermospy.server.core.ThermospyController;
import com.luan.thermospy.server.core.CameraControlAction;
import com.luan.thermospy.server.core.CameraDeviceConfig;
import java.io.File;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Receives commands from clients about controlling the service.
 * Start, stop and runonce is supported.
 */
@Path("/thermospy-server/camera-control")
@Consumes(MediaType.APPLICATION_JSON)
public class CameraControlResource {
    private final ThermospyController controller;
    private final CameraDeviceConfig cameraConfig;
    public CameraControlResource(ThermospyController controller, CameraDeviceConfig cameraConfig)
    {
        this.controller = controller;
        this.cameraConfig = cameraConfig;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response set(Action actionReq) {
        CameraControlAction type = CameraControlAction.parse(actionReq.getActionId());
        
        File file = new File(cameraConfig.getFilePath());
        if (file.exists()) {
           file.delete();
        }
        
        boolean result = true;
        switch (type) {
         case START:
            controller.start();
                break;
         case RUNONCE:
            result = controller.singleshot();
            break;
         case STOP:
            controller.stop();
            
            break;
         default:
             break;
        }
        if (result) {
            return Response.ok().entity(actionReq).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(actionReq).build();
        }
    }
}
