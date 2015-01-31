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
 * along with Thermospy-server.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.luan.thermospy.server.configuration;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.luan.thermospy.server.core.CameraDeviceConfig;
import com.luan.thermospy.server.core.DigitRecognizerConfig;
import com.luan.thermospy.server.core.ThermospyController;
import javax.annotation.Nonnull;

/**
 * A configuration class. When starting the application the thermospy-server.yml
 * is provided to the application. Upon start the file gets parsed and this
 * Configuration object is created.
 */
public class ThermospyServerConfiguration extends Configuration {
    @Nonnull
    private CameraDeviceConfig cameraDeviceConfig = null;

    @Nonnull
    private DigitRecognizerConfig digitRecognizerConfig = null;
    
    @Nonnull
    private ThermospyController controller = null;

    /**
     * @return the cameraDeviceConfig
     */
    @JsonProperty
    public CameraDeviceConfig getCameraDeviceConfig() {
        return cameraDeviceConfig;
    }

    /**
     * @param cameraDeviceConfig the cameraDeviceConfig to set
     */
    @JsonProperty
    public void setCameraDeviceConfig(CameraDeviceConfig cameraDeviceConfig) {
        this.cameraDeviceConfig = cameraDeviceConfig;
    }

    /**
     * @return the digitRecognizerConfig
     */
    @JsonProperty
    public DigitRecognizerConfig getDigitRecognizerConfig() {
        return digitRecognizerConfig;
    }

    /**
     * @param digitRecognizerConfig the digitRecognizerConfig to set
     */
    @JsonProperty
    public void setDigitRecognizerConfig(DigitRecognizerConfig digitRecognizerConfig) {
        this.digitRecognizerConfig = digitRecognizerConfig;
    }

    /**
     * @return the controller
     */
    @JsonProperty
    public ThermospyController getController() {
        return controller;
    }

    /**
     * @param controller the controller to set
     */
    @JsonProperty
    public void setController(ThermospyController controller) {
        this.controller = controller;
    }

    
    
}