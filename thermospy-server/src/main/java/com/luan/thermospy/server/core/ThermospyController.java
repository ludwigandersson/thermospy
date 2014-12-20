package com.luan.thermospy.server.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.luan.thermospy.server.actions.CameraAction;


public class ThermospyController {
    @JsonProperty
    private int refreshRate = 0;
    @JsonIgnore
    final private Object myLock = new Object();
    @JsonIgnore
    CameraAction takePhotoAction = null;
    @JsonIgnore
    private int temperature = 0;
    @JsonIgnore
    private Boundary displayBoundary = new Boundary(0,0,0,0);
    
    public int getTemperature() {
        synchronized(myLock){
            return temperature;
        }
    }

    public void setTemperature(int temperature) {
        synchronized(myLock){
            this.temperature = temperature;
        }
    }

    public Boundary getDisplayBoundary() {
        synchronized(myLock){
            return displayBoundary;
        }
    }

    public void setDisplayBoundary(Boundary displayBoundary) {
        synchronized(myLock){
            this.displayBoundary = displayBoundary;
        }
    }

    public void start() {
        takePhotoAction.start();
    }
    public void stop() {
        takePhotoAction.stop();
    }
    public void setCameraAction(CameraAction actionHandler)
    {
        this.takePhotoAction = actionHandler;
    }

    public int getRefreshRate() {
        synchronized(myLock) {
            return refreshRate;
        }
    }

    public void setRefreshRate(int refreshRate) {
        synchronized(myLock) {
            this.refreshRate = refreshRate;
        }
    }

    public boolean getServiceStatus() {
        return takePhotoAction.isRunning();
    }
}
