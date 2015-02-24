/**
 * 
 * Copyright 2015 Ludwig Andersson
 * 
 * This file is part of Thermospy-server.
 *
 *  Thermospy-server is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 * Thermospy-server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Thermospy-server.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package com.luan.thermospy.server.worker;

import com.luan.thermospy.server.core.ThermospyController;
import com.luan.thermospy.server.hal.CameraDevice;
import com.luan.thermospy.server.hal.DigitRecognizer;
import org.eclipse.jetty.util.log.Log;
import com.luan.thermospy.server.core.Boundary;
import com.luan.thermospy.server.core.ServerStatus;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class WebcamWorker extends Thread implements Runnable {

    
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
            boolean result = true;
            try {
                /*File snapshot = webCam.capture(controller.getDisplayBoundary());
                Boundary b = controller.getDisplayBoundary();

                String tempString = recognizer.recognize(snapshot, b);
                        */
               Random rand = new Random();

                // nextInt is normally exclusive of the top value,
                // so add 1 to make it inclusive
                String tempString = ""+rand.nextInt(10) + 20;
                try {
                    controller.setTemperature(Integer.parseInt(tempString));
                } catch (NumberFormatException nbrEx) {
                    controller.setTemperature(Integer.MIN_VALUE);
                }
                controller.setServerStatus(ServerStatus.OK);
            } catch (Exception e)
            {
                controller.setServerStatus(ServerStatus.INTERNAL_SERVER_ERROR);
                result = false;
            }
            return result;
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

                runonce();
                
                if (controller.getServerStatus() != ServerStatus.OK) {
                    Log.getLog().info("Server error exists. Stopping thread...");
                    pause();
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
