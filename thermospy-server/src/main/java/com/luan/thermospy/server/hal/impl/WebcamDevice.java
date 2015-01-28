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
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package com.luan.thermospy.server.hal.impl;

import com.luan.thermospy.server.core.Boundary;
import com.luan.thermospy.server.hal.CameraDevice;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.eclipse.jetty.util.log.Log;

/**
 * Controls the command "fswebcam" and is used to capture images from a webcam.
 */ 
public class WebcamDevice extends CameraDevice {

    public WebcamDevice()
    {
       
    }
    
    @Override
    public File capture(Boundary bounds) throws IOException {

        ProcessBuilder builder = new ProcessBuilder();
        ArrayList<String> commands = new ArrayList<>();
        commands.add("fswebcam");
        commands.add("-d");
        commands.add(getConfig().getCameraDevice());
        if (getConfig().isEnableGrayscale())
        {
            commands.add("--greyscale");
        }
        
	commands.add("-S 2");
        commands.add("-r");
        commands.add(getConfig().getWidth()+"x"+getConfig().getHeight());
        
        if (getConfig().isCropImage())
        {
            commands.add("--crop"); //--crop 10x10,0x0  Crops the 10x10 area at the top left corner of the image.
            commands.add(Integer.toString(bounds.getWidth())+"x"+Integer.toString(bounds.getHeight())+","+
                         Integer.toString(bounds.getX())+"x"+Integer.toString(bounds.getY()));
        }
        commands.add("--no-banner");
        commands.add(getConfig().getFilePath());
        builder.command(commands);
        builder.redirectErrorStream(true);
        Process process = null;
        BufferedReader reader = null;
        File capturedFile = new File("");
        try {
            process = builder.start();
            InputStream std = process.getInputStream ();
            reader = new BufferedReader(new InputStreamReader(std));

            int result = process.waitFor();

            if (result == 0)
            {
                capturedFile = new File(getConfig().getFilePath());
                if (getConfig().isEnableMonochrome())
                {
                    //toMonochrome(capturedFile);
                    Log.getLog().debug("Not implemented img to monochrome...yet!");
                }
            }
        }
        catch (Exception e)
        {
            Log.getLog().info("Exception: "+e.getMessage(), e);
            throw new IOException(e.getMessage(), e);
        }
        finally
        {
            try {
                if (reader != null) reader.close();
            } catch (IOException exio)
            {

            }
            if (process != null) process.destroy();
        }

        return capturedFile;

    }
}
