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
 * Digit recognizer configuration class. Contains settings for the recognizer.
 * Found in the thermospy-server.yml
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
