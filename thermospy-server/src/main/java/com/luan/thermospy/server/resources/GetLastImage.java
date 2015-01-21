package com.luan.thermospy.server.resources;

import com.luan.thermospy.server.core.CameraDeviceConfig;
import com.luan.thermospy.server.hal.CameraDevice;
import java.awt.Canvas;
import java.awt.Paint;
import javax.ws.rs.core.Response;

import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.eclipse.jetty.util.log.Log;
import static org.opencv.core.CvType.CV_8U;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import static org.opencv.highgui.Highgui.imread;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.threshold;

@Path("/thermospy-server/get-last-image")
@Produces("image/png")
public class GetLastImage {


    private final CameraDevice cameraDevice;

    public GetLastImage(CameraDevice controller) {
        this.cameraDevice = controller;
    }

    @GET
    public Response fetch() {
        File file = new File(cameraDevice.getConfig().getFilePath());
        if (file.exists()) {
            try {
                java.nio.file.Path path = Paths.get(file.getAbsolutePath());
                byte[] imageData = Files.readAllBytes(path);

                return Response.ok(new ByteArrayInputStream(imageData)).build();
            } catch (Exception e) {
                Log.getLog().info("Exception: " + e.getMessage(), e);
            }
        }

        // uncomment line below to send non-streamed
        return Response.status(Response.Status.NOT_FOUND).build();

    }

}
