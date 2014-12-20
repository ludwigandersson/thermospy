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
public class ServiceStatus {
    @JsonProperty
    private boolean running;
    
    public ServiceStatus()
    {}
    
    public ServiceStatus(boolean status)
    {
        running = status;
    }

    /**
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @param running the running to set
     */
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    
}
