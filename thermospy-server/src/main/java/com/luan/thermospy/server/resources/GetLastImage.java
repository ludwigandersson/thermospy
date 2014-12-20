package com.luan.thermospy.server.resources;
import com.luan.thermospy.server.core.CameraDeviceConfig;
import com.luan.thermospy.server.hal.CameraDevice;
import javax.ws.rs.core.Response;

import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.awt.image.BufferedImage;
import java.io.*;

@Path("/thermospy-server/get-last-image")
@Produces("image/png")
public class GetLastImage {
    private final CameraDevice cameraDevice;
    public GetLastImage(CameraDevice controller)
    {
        this.cameraDevice = controller;
    }

    @GET
    public Response fetch() {


        try {
            BufferedImage image = ImageIO.read(new File(cameraDevice.getConfig().getFilePath()));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageData = baos.toByteArray();
            // uncomment line below to send streamed
            return Response.ok(new ByteArrayInputStream(imageData)).build();
        } catch (IOException e) {
            e.printStackTrace();
        }



        // uncomment line below to send non-streamed
        return Response.status(Response.Status.NOT_FOUND).build();




    }
}
