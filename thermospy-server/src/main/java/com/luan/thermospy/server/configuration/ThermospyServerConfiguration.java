package com.luan.thermospy.server.configuration;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.luan.thermospy.server.core.Boundary;
import com.luan.thermospy.server.core.CameraDeviceConfig;
import com.luan.thermospy.server.core.DigitRecognizerConfig;
import com.luan.thermospy.server.core.ThermospyController;
import javax.annotation.Nonnull;
import org.hibernate.validator.constraints.NotEmpty;

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