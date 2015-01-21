/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luan.thermospy.server.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author ludwig
 */
public class CameraDeviceConfig {
    @JsonProperty
    private int height;
    @JsonProperty
    private int width;
    @JsonProperty
    private boolean enableGrayscale;
    @JsonProperty
    private String cameraDevice;
    @JsonProperty
    private boolean cropImage;
    @JsonProperty
    private String filePath;
    @JsonProperty
    private boolean enableMonochrome;
    
    public CameraDeviceConfig()
    {
       
    }
    
    public CameraDeviceConfig(int width, int height, boolean enableGreyscale, String cameraDevice)
    {
        this.width = width;
        this.height = height;
        this.enableGrayscale = enableGreyscale;
        this.cameraDevice = cameraDevice;
    }

    

    /**
     * @return the enableGrayscale
     */
    public synchronized boolean isEnableGrayscale() {
        return enableGrayscale;
    }

    /**
     * @param enableGrayscale the enableGrayscale to set
     */
    public synchronized void setEnableGrayscale(boolean enableGrayscale) {
        this.enableGrayscale = enableGrayscale;
    }

    /**
     * @return the cameraDevice
     */
    public synchronized String getCameraDevice() {
        return cameraDevice;
    }

    /**
     * @param cameraDevice the cameraDevice to set
     */
    public synchronized void setCameraDevice(String cameraDevice) {
        this.cameraDevice = cameraDevice;
    }

    /**
     * @return the cropImage
     */
    public synchronized boolean isCropImage() {
        return cropImage;
    }

    /**
     * @param cropImage the cropImage to set
     */
    public synchronized void setCropImage(boolean cropImage) {
        this.cropImage = cropImage;
    }

    /**
     * @return the height
     */
    public synchronized int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public synchronized void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the width
     */
    public synchronized int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public synchronized  void setWidth(int width) {
        this.width = width;
    }
    
    public synchronized void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public synchronized String getFilePath() {
        return this.filePath;
    }

    /**
     * @return the enableMonochrome
     */
    public synchronized boolean isEnableMonochrome() {
        return enableMonochrome;
    }

    /**
     * @param enableMonochrome the enableMonochrome to set
     */
    public synchronized void setEnableMonochrome(boolean enableMonochrome) {
        this.enableMonochrome = enableMonochrome;
    }
    
    
    
    
    
}
