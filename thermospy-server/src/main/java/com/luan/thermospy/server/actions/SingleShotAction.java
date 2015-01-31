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

package com.luan.thermospy.server.actions;

import org.eclipse.jetty.util.log.Log;
import com.luan.thermospy.server.worker.WebcamWorker;

/**
 * Implements the CameraAction interface and controls a worker thread
 * @see WebcamWorker. 
 */
public class SingleShotAction implements CameraAction {
    /**
     * The worker instance.
     */
    private WebcamWorker worker = null;
    /**
     * Default Constructor
     */
    public SingleShotAction() {}
    /**
     * Takes the webcam worker as input and keeps a reference to it.
     * @param thread The worker thread
     */
    public SingleShotAction(WebcamWorker thread) {
        this.worker = thread;
    }

    @Override
    public void stop()
    {
        if (worker.isAlive()) {
            Log.getLog().info("Pause worker!");
            worker.pause();
            Log.getLog().info("Worker status: "+(worker.isInterrupted() ? "Running" : "Stopped"));

        }
    }
    @Override
    public void start() {
        if (!worker.isAlive()) {
            Log.getLog().info("Start worker!");
            worker.start();
        }
        else
        {
            Log.getLog().info("Restart worker!");
            worker.wakeUp();
        }
    }
    @Override
    public boolean singleshot()
    {
        return worker.runonce();
    }
    @Override
    public boolean isRunning()
    {
        return !worker.isPaused();
    }
}

