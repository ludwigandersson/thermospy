/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luan.thermospy.server.resources;

import com.luan.thermospy.server.core.RefreshRate;
import com.luan.thermospy.server.core.ServiceStatus;
import com.luan.thermospy.server.core.ThermospyController;
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
@Path("/thermospy-server/service-status")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServiceStatusResource {
    private final ThermospyController controller;
    public ServiceStatusResource(ThermospyController controller)
    {
        this.controller = controller;
    }

    @GET
    public ServiceStatus fetch() {
        return new ServiceStatus(controller.getServiceStatus());
    }
}
