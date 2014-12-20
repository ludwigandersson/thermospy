package com.luan.thermospy.server.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Temperature {

    private int temperature = 0;
    private int id = 0;
    public Temperature(){}

    public Temperature(int temperature)
    {
        this.temperature = temperature;
    }
    @JsonProperty
    public int getId()
    {
        return id;
    }

    @JsonProperty
    public int getTemperature()
    {
        return this.temperature;
    }
    
    public void setTemperature(int temperature)
    {
        this.temperature = temperature;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
}
