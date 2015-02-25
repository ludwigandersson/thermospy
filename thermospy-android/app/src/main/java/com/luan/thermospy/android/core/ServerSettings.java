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

package com.luan.thermospy.android.core;

/**
 * Holds information about the thermospy server
 */
public class ServerSettings {
    public boolean isValid() {
        return this.ipAddress != null && !ipAddress.isEmpty() &&port > 0;
    }

    static interface ServerSettingsStrings
    {
        String IP_ADDRESS = "ipaddress";
        String PORT = "port";
        String SERVER_RUNNING = "running";
    }
    private boolean running;
    private int port;
    private String ipAddress;

    public ServerSettings(int port, String ipAddress, boolean running)
    {
        this.port = port;
        this.ipAddress = ipAddress;
        this.running = running;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }

    public static ServerSettings fromDb(Database database) throws Exception {
        return new ServerSettings(database.getInt(ServerSettingsStrings.PORT),
                                  database.getString(ServerSettingsStrings.IP_ADDRESS),
                                  database.getBoolean(ServerSettingsStrings.SERVER_RUNNING));
    }

    protected void serialize(Database database) {
        try {
            database.putInt(ServerSettingsStrings.PORT, this.port);
            database.putString(ServerSettingsStrings.IP_ADDRESS, this.ipAddress);
            database.putBoolean(ServerSettingsStrings.SERVER_RUNNING, this.running);
        } catch (Exception e) {

        }
    }
}
