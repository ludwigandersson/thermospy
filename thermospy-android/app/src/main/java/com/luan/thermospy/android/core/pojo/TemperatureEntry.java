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

    public String toCsv() {
        StringBuilder builder = new StringBuilder();
        builder.append(id).append(",")
               .append(timestamp).append(",")
               .append(temperature);

        return builder.toString();

    }
}
