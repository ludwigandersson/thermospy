package com.luan.thermospy.server.resources;
import com.codahale.metrics.annotation.Timed;
import com.luan.thermospy.server.core.Boundary;
import com.luan.thermospy.server.core.RefreshRate;
import com.luan.thermospy.server.core.ThermospyController;
import org.eclipse.jetty.util.log.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

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
