package com.luan.thermospy.server.resources;


import com.codahale.metrics.annotation.Timed;
import com.luan.thermospy.server.core.Temperature;
import com.luan.thermospy.server.core.ThermospyController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/thermospy-server/get-temp")
@Produces(MediaType.APPLICATION_JSON)
public class GetTempResource {

    private final ThermospyController controller;
    public GetTempResource(ThermospyController controller)
    {
        this.controller = controller;
    }

    @GET
    @Timed
    public Temperature fetch() {
        int temp = controller.getTemperature();
        controller.setTemperature(temp+1);

        return new Temperature(temp);
    }
}
