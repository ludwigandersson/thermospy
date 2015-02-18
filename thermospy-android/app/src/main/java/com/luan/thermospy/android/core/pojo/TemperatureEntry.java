package com.luan.thermospy.android.core.pojo;

import java.util.Date;

/**
 * Created by ludde on 15-02-17.
 */
public class TemperatureEntry {
    int id;
    Date timestamp;
    int fkSessionId;
    int temperature;

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getFkSessionId() {
        return fkSessionId;
    }

    public void setFkSessionId(int fkSessionId) {
        this.fkSessionId = fkSessionId;
    }
}
