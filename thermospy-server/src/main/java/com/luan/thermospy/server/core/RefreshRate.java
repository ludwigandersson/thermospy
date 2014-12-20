package com.luan.thermospy.server.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ludde on 14-12-17.
 */
public class RefreshRate {
    private int refreshRate;
    public RefreshRate()
    {

    }

    public RefreshRate(int refreshRate)
    {
        this.refreshRate = refreshRate;
    }

    @JsonProperty
    public int getRefreshRate()
    {
        return refreshRate;
    }
    
    public void setRefreshRate(int refreshRate)
    {
      this.refreshRate = refreshRate;
    }
}
