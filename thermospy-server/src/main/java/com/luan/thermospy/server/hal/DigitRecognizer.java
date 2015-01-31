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
package com.luan.thermospy.server.hal;

import com.luan.thermospy.server.core.Boundary;
import com.luan.thermospy.server.core.DigitRecognizerConfig;

import java.io.File;
import java.io.IOException;

/**
 * Abstract class. Contains functions for accessing the recognizer config.
 */
public abstract class DigitRecognizer {
    private DigitRecognizerConfig config;
    
    public abstract String recognize(File imgFile, Boundary crop) throws IOException;

    public synchronized void setConfig(DigitRecognizerConfig config)
    {
        this.config = config;
    }
    public synchronized DigitRecognizerConfig getConfig()
    {
        return config;
    }
}
