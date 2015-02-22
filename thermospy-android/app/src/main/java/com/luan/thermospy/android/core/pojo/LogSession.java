package com.luan.thermospy.android.core.pojo;

import java.util.Date;

/**
 * LogSession POJO
 */
public class LogSession {
    int id;
    private Integer targettemperature;

    public Boolean getIsopen() {
        return isopen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isopen = isOpen;
    }

    public Integer getTargetTemperature() {
        return targettemperature;
    }

    public void setTargetTemperature(Integer targetTemperature) {
        this.targettemperature = targetTemperature;
    }

    private Boolean isopen;
    private String name;
    private Date startTimestamp;
    private Date endTimestamp;
    private String comment;


    public LogSession() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Date getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Date endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return name;
    }
}
