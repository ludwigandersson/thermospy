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
        CameraAction action = controller.getCameraAction(actionReq.getActionId());
        int result = action.run();
        return Response.created(UriBuilder.fromResource(CameraControlResource.class)
                .build("result", result))
                .build();
    }
}
