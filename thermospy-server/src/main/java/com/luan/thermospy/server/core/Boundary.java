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
    private Object lock1 = new Object();

    public Boundary() {
        x = 0;
        y = 0;
        width = 0;
        height = 0;
    }
    public Boundary(int x, int y, int width, int height)
    {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
    }

    @JsonProperty
    public int getX() {
        synchronized(lock1)
        {
            return x;
        }
    }


    public void setX(int x) {

        synchronized(lock1)
        {
            this.x = x;
        }
    }
    @JsonProperty
    public int getY() {

        synchronized(lock1)
        {
            return y;
        }
    }

    public void setY(int y) {

        synchronized(lock1)
        {
            this.y = y;
        }
    }
    @JsonProperty
    public int getWidth() {

        synchronized(lock1)
        {
            return width;
        }
    }

    public void setWidth(int width) {

        synchronized(lock1)
        {
            this.width = width;
        }
    }
    @JsonProperty
    public int getHeight() {

        synchronized(lock1)
        {
            return height;
        }
    }

    public void setHeight(int height) {
        synchronized(lock1)
        {
            this.height = height;
        }
    }
}
