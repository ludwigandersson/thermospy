/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luan.thermospy.server.resources;

import com.luan.thermospy.server.core.CameraDeviceConfig;
import com.luan.thermospy.server.core.RefreshRate;
import com.luan.thermospy.server.core.ThermospyController;
import com.luan.thermospy.server.hal.CameraDevice;
import com.luan.thermospy.server.hal.DigitRecognizer;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.eclipse.jetty.util.log.Log;

/**
 *
 * @author ludwig
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
