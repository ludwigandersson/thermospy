/*
 * Copyright 2015 Ludwig Andersson
 *
 * This file is part of Thermospy-android.
 *
 * Thermospy-android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Thermospy-android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Thermospy-android.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.luan.thermospy.android.core.pojo;

/**
 * Enum class. Used when specifying the boundary for the image. Currently the following
 * ratios are supported:
 */
public enum AspectRatio {
    ASPECT_RATIO_UNKNOWN(0,0,0),
    ASPECT_RATIO_NONE(1,0,0),
    ASPECT_RATIO_1_1(2,1,1),
    ASPECT_RATIO_2_1(3,2,1),
    ASPECT_RATIO_4_3(4,4,3);

    private final int x;
    private final int y;
    private int id;
    AspectRatio(int id, int width, int height)
    {
        this.id = id;
        this.x = width;
        this.y = height;
    }

    public int getId()
    {
        return id;
    }
    public int getX() {return x;}
    public int getY() {return y;}

    public static AspectRatio parse(String string)
    {
        switch(string) {
            case "1":
                return ASPECT_RATIO_NONE;
            case "2":
                return ASPECT_RATIO_1_1;
            case "3":
                return ASPECT_RATIO_2_1;
            case "4":
                return ASPECT_RATIO_4_3;
            default:
                return ASPECT_RATIO_UNKNOWN;
        }
    }

    public static AspectRatio fromInt(int id)
    {
        switch (id)
        {
            case 1:
                return ASPECT_RATIO_NONE;
            case 2:
                return ASPECT_RATIO_1_1;
            case 3:
                return ASPECT_RATIO_2_1;
            case 4:
                return ASPECT_RATIO_4_3;
            default:
                return ASPECT_RATIO_UNKNOWN;

        }
    }
}
