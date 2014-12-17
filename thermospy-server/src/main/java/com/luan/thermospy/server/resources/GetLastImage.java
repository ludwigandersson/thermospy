package com.luan.thermospy.server.resources;
import com.codahale.metrics.annotation.Timed;
import com.luan.thermospy.server.actions.CameraAction;
import com.luan.thermospy.server.core.Image;
import com.luan.thermospy.server.core.ThermospyController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/thermospy-server/get-last-image")
@Produces(MediaType.APPLICATION_JSON)
public class GetLastImage {
    private final ThermospyController controller;
    public GetLastImage(ThermospyController controller)
    {
        this.controller = controller;
    }

    @GET
    public Image fetch() {
        return new Image("img/lastpic.png");
    }
}
