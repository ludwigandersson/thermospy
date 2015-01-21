package com.luan.thermospy.server.hal.impl;

import com.luan.thermospy.server.core.Boundary;
import com.luan.thermospy.server.hal.CameraDevice;
import org.eclipse.jetty.util.log.Log;
//import org.opencv.core.Mat;
//import org.opencv.highgui.Highgui;
//import org.opencv.highgui.VideoCapture;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.opencv.core.Mat;
//import org.opencv.highgui.Highgui;
//import org.opencv.imgproc.Imgproc;

/**
 * Created by ludwig on 2014-12-24.
 */
public class WebcamDevice extends CameraDevice {

    private final static String IMAGE_FILENAME = "thermopic.png";

   // private VideoCapture camera;
    private Lock mutex = new ReentrantLock();

   /* static {
        //System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);

        nu.pattern.OpenCV.loadLibrary();
    }*/

    public WebcamDevice()
    {
        /*camera = new VideoCapture(1);
        camera.set(Highgui.CV_CAP_PROP_FRAME_WIDTH, 640);
        camera.set(Highgui.CV_CAP_PROP_FRAME_HEIGHT, 480);
        if (!camera.isOpened()) {
            Log.getLog().info("Camera Error");
        } else {
            Log.getLog().info("Camera OK!");
        }*/

    }
    
  /*  public boolean toMonochrome(File file)
    {
        //Our greyscale image
        Mat grayscaleMat = Highgui.imread(file.getAbsolutePath(), Highgui.CV_LOAD_IMAGE_GRAYSCALE);
        if (grayscaleMat.empty())
        {
                return false;
        }
        //Binary image
        Mat binaryMat = new Mat(grayscaleMat.size(), grayscaleMat.type());

        //Apply thresholding
        double threshold = Imgproc.threshold(grayscaleMat, binaryMat, 125, 255, Imgproc.THRESH_BINARY);
        
        Highgui.imwrite(file.getAbsolutePath(), binaryMat);
        
        java.nio.file.Path path = Paths.get(new File("binary.png").getAbsolutePath());
        try {
            byte[] imageData = Files.readAllBytes(path);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(WebcamDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }*/

    public File capture(Boundary bounds) {

        /* Someday....
        camera.open(1);
        try {

            Mat frame = new Mat();

            camera.read(frame);
            if (!frame.empty()) {
                Log.getLog().info("Frame Obtained");
                Log.getLog().info("Captured Frame Width " + frame.width());

               if (Highgui.imwrite("camera.jpg", frame)) {
                   Log.getLog().info("OK - image captured and written to file.");
               }
            }
        } catch (Exception e)
        {
            Log.getLog().debug("Exception: "+e.getMessage(), e);
        }
        camera.release();

        return new File("camera.jpg");
        */
        ProcessBuilder builder = new ProcessBuilder();
        ArrayList<String> commands = new ArrayList<String>();
        commands.add("fswebcam");
        commands.add("-d");
        commands.add(getConfig().getCameraDevice());
        if (getConfig().isEnableGrayscale())
        {
            commands.add("--greyscale");
        }
        
	commands.add("-S 2");
        commands.add("-r");
        commands.add(getConfig().getWidth()+"x"+getConfig().getHeight());
        
        if (getConfig().isCropImage())
        {
            commands.add("--crop"); //--crop 10x10,0x0  Crops the 10x10 area at the top left corner of the image.
            commands.add(Integer.toString(bounds.getWidth())+"x"+Integer.toString(bounds.getHeight())+","+
                         Integer.toString(bounds.getX())+"x"+Integer.toString(bounds.getY()));
        }
        commands.add("--no-banner");
        commands.add(getConfig().getFilePath());
        builder.command(commands);
        builder.redirectErrorStream(true);
        Process process = null;
        BufferedReader reader = null;
        String output = "";
        File capturedFile = new File("");
        try {
            process = builder.start();
            InputStream std = process.getInputStream ();
            reader = new BufferedReader(new InputStreamReader(std));

            int result = process.waitFor();

            if (result == 0)
            {
                capturedFile = new File(IMAGE_FILENAME);
                if (getConfig().isEnableMonochrome())
                {
                    //toMonochrome(capturedFile);
                    Log.getLog().debug("Not implemented img to monochrome...yet!");
                }
            }
        }
        catch (IOException ex)
        {
            Log.getLog().debug("Exception: "+ex.getMessage(), ex);
        }
        catch (InterruptedException ex2)
        {
            Log.getLog().debug("Exception: "+ex2.getMessage(), ex2);
        }
        catch (Exception e)
        {
            Log.getLog().debug("Exception: "+e.getMessage(), e);
        }
        finally
        {
            try {
                if (reader != null) reader.close();
            } catch (IOException exio)
            {

            }
            if (process != null) process.destroy();
        }

        return capturedFile;

    }
}
