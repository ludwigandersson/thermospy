package com.luan.thermospy.server.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.luan.thermospy.server.actions.CameraAction;
import org.eclipse.jetty.util.log.Log;


public class ThermospyController {
    @JsonProperty
    private int refreshRate = 0;
    @JsonIgnore
    final private Object myLock = new Object();
    @JsonIgnore
    CameraAction camera = null;
    @JsonIgnore
    private int temperature = Integer.MIN_VALUE;
    @JsonIgnore
    private Boundary displayBoundary = new Boundary(0,0,0,0);
    
    public int getTemperature() {
        synchronized(myLock){
            return temperature;
        }
    }

    public void setTemperature(int temperature) {
        synchronized(myLock){
            if (this.temperature != temperature)
            {
                String fromTemperature = this.temperature == Integer.MIN_VALUE ? "--" : Integer.toString(temperature);
                String toTemperature = this.temperature == Integer.MIN_VALUE ? "--" : Integer.toString(temperature);
                Log.getLog().info("Temperature changed from " + fromTemperature + " to " + toTemperature );
                this.temperature = temperature;
            }
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
        camera.start();
    }
    public boolean singleshot() {
        return camera.singleshot();
    }
    
    public void stop() {
        camera.stop();
    }
    public void setCameraAction(CameraAction actionHandler)
    {
        this.camera = actionHandler;
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
        return camera.isRunning();
    }
}
