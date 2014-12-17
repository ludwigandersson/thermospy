package com.luan.thermospy.server.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ludde on 14-12-17.
 */
public class Image {
    private String path;

    public Image() {}

    public Image(String path) {
        this.path = path;
    }

    @JsonProperty
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
