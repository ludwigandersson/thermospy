package com.luan.thermospy.server.actions;

/**
 * Created by ludde on 14-12-17.
 */
public interface CameraAction
{
    public void start();
    public void stop();
    public boolean singleshot();

    public boolean isRunning();
}