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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response set(Action actionReq) {
        if (actionReq.getActionId() == 0) {
            controller.start();
        }
        else if (actionReq.getActionId() == 1) 
        {
            boolean result = controller.singleshot();
            if (!result)
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(actionReq).build();
            }
        }
        else
        {
            controller.stop();
        }


        return Response.ok().entity(actionReq).build();
    }
}
