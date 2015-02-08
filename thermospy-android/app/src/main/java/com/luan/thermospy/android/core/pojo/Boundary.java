/**
 * 
 * Copyright 2015 Ludwig Andersson
 * 
 * This file is part of Thermospy-server.
 *
 *  Thermospy-server is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 * Thermospy-server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Thermospy-server.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package com.luan.thermospy.android.core.pojo;

/**
 * Data class. Contains information about what boundary the camera action should
 * use when taking photos.
 */
public class Boundary {
    private int x;
    private int y;
    private int width;
    private int height;


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

    public int getX() {

            return x;

    }


    public void setX(int x) {


            this.x = x;

    }

    public int getY() {


            return y;

    }

    public void setY(int y) {


            this.y = y;

    }

    public int getWidth() {


            return width;

    }

    public void setWidth(int width) {


            this.width = width;

    }

    public int getHeight() {


            return height;

    }

    public void setHeight(int height) {

            this.height = height;

    }
}
