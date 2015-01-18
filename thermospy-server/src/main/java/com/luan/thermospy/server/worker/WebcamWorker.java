package com.luan.thermospy.server.worker;

import com.luan.thermospy.server.core.ThermospyController;
import com.luan.thermospy.server.hal.CameraDevice;
import com.luan.thermospy.server.hal.DigitRecognizer;
import org.eclipse.jetty.util.log.Log;
import com.luan.thermospy.server.core.Boundary;

import java.io.File;


/**
 * Created by ludwig on 2014-12-24.
 */
public class WebcamWorker extends Thread implements Runnable {

    private boolean running = true;
    private ThermospyController controller = null;
    private CameraDevice webCam = null;
    DigitRecognizer recognizer;
    volatile boolean paused = true;
    private final Object lockObj = new Object();
    public WebcamWorker(ThermospyController controller, CameraDevice webCam, DigitRecognizer recognizer)
    {
        this.controller = controller;
        this.webCam = webCam;
        this.recognizer = recognizer;
    }
    
    public boolean runonce()
    {
        synchronized (lockObj) {
            File snapshot = webCam.capture(controller.getDisplayBoundary());
            return snapshot.exists();
        }
    }

    public void pause()
    {
        synchronized (lockObj) {
            paused = true;
            lockObj.notify();
        }
    }

    public void wakeUp()
    {
        synchronized (lockObj) {
            paused = false;
            lockObj.notify();
        }
    }

    public boolean isPaused()
    {
        synchronized (lockObj) {
            return paused;
        }
    }

    @Override
    public void run() {
        synchronized (lockObj) {
            paused = false;
        }
        
        while (!isInterrupted())
        {
            try {

                Boundary b = controller.getDisplayBoundary();
                File imgFile;
                String tempString;
                synchronized (lockObj) {
                   imgFile = webCam.capture(b);
                   tempString = recognizer.recognize(imgFile, b);
                }

                
                try {
                    controller.setTemperature(Integer.parseInt(tempString));
                } catch (NumberFormatException nbrEx) {
                    controller.setTemperature(Integer.MIN_VALUE);
                }

                synchronized (lockObj) {

                    // suspend thread
                    lockObj.wait(controller.getRefreshRate()*1000);
                    // Check if a pause request has been made
                    if (isPaused()) {
                        Log.getLog().info("Darn! Time to sleep.");
                        lockObj.wait();
                        Log.getLog().info("Wakie-wakie! Time to take some pics!");
                    }
                }
            }
            catch (InterruptedException ex)
            {
                break;
            }
        }
        Log.getLog().info("Goodbye cruel world!");
    }
}
