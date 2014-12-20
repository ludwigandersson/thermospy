package com.luan.thermospy.server.hal;

import com.luan.thermospy.server.core.Boundary;
import com.luan.thermospy.server.core.DigitRecognizerConfig;

import java.io.File;

/**
 * Created by ludwig on 2014-12-24.
 */
public abstract class DigitRecognizer {
    private DigitRecognizerConfig config;
    
    public abstract String recognize(File imgFile, Boundary crop);

    public synchronized void setConfig(DigitRecognizerConfig config)
    {
        this.config = config;
    }
    public synchronized DigitRecognizerConfig getConfig()
    {
        return config;
    }
}
