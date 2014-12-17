package com.luan.thermospy.server.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ludde on 14-12-16.
 */
public class Boundary {
    private int x;
    private int y;
    private int width;
    private int height;

    public Boundary() {}
    public Boundary(int x, int y, int width, int height)
    {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
    }

    @JsonProperty
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    @JsonProperty
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    @JsonProperty
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    @JsonProperty
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
