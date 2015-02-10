/**
 * 
 * Copyright 2015 Ludwig Andersson
 * 
 * This file is part of Thermospy-android.
 *
 *  Thermospy-android is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 * Thermospy-android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Thermospy-android.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package com.luan.thermospy.android.core.pojo;



/**
 * Camera configuration class. Contains settings for the camera.
 * Found in the thermospy-server.yml
 */
public class CameraDeviceConfig {

    private int height;
    private int width;
    private boolean enableGrayscale;
    private String cameraDevice;
    private boolean cropImage;
    private String filePath;
    private boolean enableMonochrome;
    
    public CameraDeviceConfig()
    {
       
    }
    
    public CameraDeviceConfig(int height,
                              int width,
                              boolean enableGreyscale,
                              String cameraDevice,
                              boolean cropImage,
                              String filePath,
                              boolean enableMonochrome)
    {
        this.width = width;
        this.height = height;
        this.enableGrayscale = enableGreyscale;
        this.cameraDevice = cameraDevice;
        this.cropImage = cropImage;
        this.filePath = filePath;
        this.enableMonochrome = enableMonochrome;
    }

    /**
     * @return the enableGrayscale
     */
    public  boolean isEnableGrayscale() {
        return enableGrayscale;
    }

    /**
     * @param enableGrayscale the enableGrayscale to set
     */
    public  void setEnableGrayscale(boolean enableGrayscale) {
        this.enableGrayscale = enableGrayscale;
    }

    /**
     * @return the cameraDevice
     */
    public  String getCameraDevice() {
        return cameraDevice;
    }

    /**
     * @param cameraDevice the cameraDevice to set
     */
    public  void setCameraDevice(String cameraDevice) {
        this.cameraDevice = cameraDevice;
    }

    /**
     * @return the cropImage
     */
    public  boolean isCropImage() {
        return cropImage;
    }

    /**
     * @param cropImage the cropImage to set
     */
    public  void setCropImage(boolean cropImage) {
        this.cropImage = cropImage;
    }

    /**
     * @return the height
     */
    public  int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public  void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the width
     */
    public  int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public   void setWidth(int width) {
        this.width = width;
    }
    
    public  void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public  String getFilePath() {
        return this.filePath;
    }

    /**
     * @return the enableMonochrome
     */
    public  boolean isEnableMonochrome() {
        return enableMonochrome;
    }

    /**
     * @param enableMonochrome the enableMonochrome to set
     */
    public  void setEnableMonochrome(boolean enableMonochrome) {
        this.enableMonochrome = enableMonochrome;
    }
    
    
    
    
    
}
