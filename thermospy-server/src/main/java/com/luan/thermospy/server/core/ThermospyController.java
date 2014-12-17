package com.luan.thermospy.server.core;

import com.luan.thermospy.server.actions.CameraAction;
import com.luan.thermospy.server.actions.TakePhotoAction;
import javafx.scene.Camera;

import java.awt.*;

public class ThermospyController {
    private Object myLock = new Object();
    TakePhotoAction takePhotoAction = new TakePhotoAction();
    private int temperature = 0;
    private int refreshRate = 0;
    private Boundary displayBoundary = new Boundary(0,0,0,0);
    private Point imageResolution = new Point(350, 350);

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

    public Point getImageResolution() {
        synchronized(myLock){
            return imageResolution;
        }
    }

    public void setImageResolution(Point imageResolution) {
        synchronized(myLock){
            this.imageResolution = imageResolution;
        }
    }

    public CameraAction getCameraAction(int id) {
        synchronized(myLock){
            return takePhotoAction;
        }
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
}
