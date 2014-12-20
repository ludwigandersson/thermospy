package com.luan.thermospy.server.hal;

import com.luan.thermospy.server.core.Boundary;
import com.luan.thermospy.server.core.CameraDeviceConfig;
import java.awt.*;
import java.io.File;

/**
 * Created by ludwig on 2014-12-24.
 */
public abstract class CameraDevice {
    private CameraDeviceConfig config;
    public abstract File capture(Boundary bounds);
    
    public synchronized CameraDeviceConfig getConfig()
    {
        return config;
    }
    
    public synchronized void setConfig(CameraDeviceConfig config)
    {
        this.config = config;
    }
}
