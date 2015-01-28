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
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package com.luan.thermospy.server.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Camera configuration class. Contains settings for the camera.
 * Found in the thermospy-server.yml
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
