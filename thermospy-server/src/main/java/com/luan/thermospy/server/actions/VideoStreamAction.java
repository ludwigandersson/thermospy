package com.luan.thermospy.server.actions;

/**
 * Created by ludde on 14-12-17.
 */
public class VideoStreamAction implements CameraAction {
    public VideoStreamAction() {}

    public void start() {
    }
    
    public boolean singleshot()
    {
        return false;
    }

    public void stop() {}
    public boolean isRunning() { return false;}
}