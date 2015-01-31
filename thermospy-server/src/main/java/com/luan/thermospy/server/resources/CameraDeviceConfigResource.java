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

import com.luan.thermospy.server.core.CameraDeviceConfig;
import com.luan.thermospy.server.hal.CameraDevice;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/**
 * Receives commands from clients about camera device configuration.
 */
@Path("/thermospy-server/camera-device-config")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CameraDeviceConfigResource {
    private CameraDevice cameraDevice;
    public CameraDeviceConfigResource(CameraDevice cameraDevice)
    {
        this.cameraDevice = cameraDevice;
    }

    @POST
    public Response set(CameraDeviceConfig config) {
        cameraDevice.getConfig().setCropImage(config.isCropImage());
        cameraDevice.getConfig().setEnableGrayscale(config.isEnableGrayscale());
        cameraDevice.getConfig().setHeight(config.getHeight());
        cameraDevice.getConfig().setWidth(config.getWidth());
        return Response.created(UriBuilder.fromResource(CameraDeviceConfigResource.class)
                .build("result", 0))
                .build();
    }
    

    @GET
    public CameraDeviceConfig fetch() {
        return cameraDevice.getConfig();
    }    

}
