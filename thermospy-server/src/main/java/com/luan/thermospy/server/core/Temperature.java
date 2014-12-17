package com.luan.thermospy.server.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Temperature {

    private int temperature;
    public Temperature(){}

    public Temperature(int temperature)
    {
        this.temperature = temperature;
    }
    @JsonProperty
    public int getId()
    {
        return 0;
    }

    @JsonProperty
    public int getTemperature()
    {
        return this.temperature;
    }
}
