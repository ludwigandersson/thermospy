package com.luan.thermospy.server.actions;

import org.eclipse.jetty.util.log.Log;
import com.luan.thermospy.server.worker.WebcamWorker;

/**
 * Created by ludde on 14-12-17.
 */
public class TakePhotoAction implements CameraAction {
    public TakePhotoAction() {}
    private WebcamWorker worker = null;
    public TakePhotoAction(WebcamWorker thread) {
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
