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
public class DigitRecognizerConfig {
    @JsonProperty
    private int retryCount;
    @JsonProperty
    private int threshold;
    @JsonProperty
    private boolean cropImage;
    
    public DigitRecognizerConfig()
    {
    }
    
    public DigitRecognizerConfig(int retryCount, int threshold, boolean cropImage)
    {
        this.retryCount = retryCount;
        this.threshold = threshold;
        this.cropImage = cropImage;
    }

    /**
     * @return the retryCount
     */
    public synchronized int getRetryCount() {
        return retryCount;
    }

    /**
     * @param retryCount the retryCount to set
     */
    public synchronized void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    /**
     * @return the threshold
     */
    public synchronized int getThreshold() {
        return threshold;
    }

    /**
     * @param threshold the threshold to set
     */
    public synchronized void setThreshold(int threshold) {
        this.threshold = threshold;
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
    public synchronized  void setCropImage(boolean cropImage) {
        this.cropImage = cropImage;
    }
    
    
    
    
}
