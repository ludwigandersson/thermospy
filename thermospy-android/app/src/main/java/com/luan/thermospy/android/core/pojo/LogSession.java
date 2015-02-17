package com.luan.thermospy.android.core.pojo;

import java.util.Date;

/**
 * LogSession POJO
 */
public class LogSession {
    int id;
    private Integer fkFoodtypeId;
    private Integer fkCutId;
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

    public Integer getFkFoodtypeId() {
        return fkFoodtypeId;
    }

    public void setFkFoodtypeId(Integer fkFoodtypeId) {
        this.fkFoodtypeId = fkFoodtypeId;
    }

    public Integer getFkCutId() {
        return fkCutId;
    }

    public void setFkCutId(Integer fkCutId) {
        this.fkCutId = fkCutId;
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
