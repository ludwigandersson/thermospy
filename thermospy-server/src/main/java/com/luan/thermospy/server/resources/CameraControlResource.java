package com.luan.thermospy.server.resources;

import com.codahale.metrics.annotation.Timed;
import com.luan.thermospy.server.core.Action;
import com.luan.thermospy.server.core.ThermospyController;
import com.luan.thermospy.server.actions.CameraAction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import io.dropwizard.jersey.params.IntParam;

@Path("/thermospy-server/camera-control")
@Consumes(MediaType.APPLICATION_JSON)
public class CameraControlResource {
    private final ThermospyController controller;
    public CameraControlResource(ThermospyController controller)
    {
        this.controller = controller;
    }


    @POST
    public Response set(Action actionReq) {
        if (actionReq.getActionId() == 0) {
            controller.start();
        }
        else
        {
            controller.stop();
        }
        int result = 0;

        return Response.created(UriBuilder.fromResource(CameraControlResource.class)
                .build("result", result))
                .build();
    }
}
