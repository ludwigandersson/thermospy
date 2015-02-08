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
 * Thermospy-server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.luan.thermospy.android.core.serverrequest;

/**
 * Builder class for creating a REST request to the thermospy server.
 */
public class UrlRequest {
    private String mIpAddress;
    private int mPort;
    private UrlRequestType mRequestType;

    public UrlRequest(UrlRequestBuilder request)
    {
        this.mIpAddress = request.mIpAddress;
        this.mPort = request.mPort;
        this.mRequestType = request.mRequestType;
    }
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("http://").append(mIpAddress).
                append(":").
                append(Integer.toString(mPort)).
                append("/").
                append("thermospy-server").
                append("/").
                append(mRequestType.toString());
        return builder.toString();
    }

    public static class UrlRequestBuilder {
        private String mIpAddress;
        private int mPort;
        private UrlRequestType mRequestType;

        public UrlRequestBuilder setIpAddress(String ipAddress) {
            this.mIpAddress = ipAddress;
            return this;
        }

        public UrlRequestBuilder setPort(int port) {
            this.mPort = port;
            return this;
        }

        public UrlRequestBuilder setUrlRequest(UrlRequestType requestType) {
            this.mRequestType = requestType;
            return this;
        }
        public UrlRequest build() {
            return new UrlRequest(this);
        }
    }

}
