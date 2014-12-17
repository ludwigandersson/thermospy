package com.luan.thermospy.server.resources;
import com.codahale.metrics.annotation.Timed;
import com.luan.thermospy.server.core.RefreshRate;
import com.luan.thermospy.server.core.ThermospyController;
import io.dropwizard.jersey.params.IntParam;
import org.eclipse.jetty.util.log.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/thermospy-server/refresh-rate")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RefreshRateResource {

    private final ThermospyController controller;
    public RefreshRateResource(ThermospyController controller)
    {
        this.controller = controller;
    }

    @POST
    public Response set(RefreshRate refreshRate) {
        Log.getLog().info("Received post request: "+refreshRate.getRefreshRate());
        controller.setRefreshRate(refreshRate.getRefreshRate());
        return Response.created(UriBuilder.fromResource(RefreshRateResource.class)
                .build("result", 0))
                .build();
    }

    @GET
    public RefreshRate fetch() {
        return new RefreshRate(controller.getRefreshRate());
    }
}
