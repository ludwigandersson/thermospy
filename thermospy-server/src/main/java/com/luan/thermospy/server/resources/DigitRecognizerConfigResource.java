/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luan.thermospy.server.resources;

import com.luan.thermospy.server.core.DigitRecognizerConfig;
import com.luan.thermospy.server.core.RefreshRate;
import com.luan.thermospy.server.core.ThermospyController;
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

@Path("/thermospy-server/digit-recognizer-config")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DigitRecognizerConfigResource {
    private DigitRecognizer recognizer;
   
    public DigitRecognizerConfigResource(DigitRecognizer recognizer)
    {
        this.recognizer = recognizer;
    }
    
        @POST
    public Response set(DigitRecognizerConfig config) {
        recognizer.setConfig(config);
        return Response.created(UriBuilder.fromResource(DigitRecognizerConfigResource.class)
                .build("result", 0))
                .build();
    }

    @GET
    public DigitRecognizerConfig fetch() {
        return recognizer.getConfig();
    }
    
}
