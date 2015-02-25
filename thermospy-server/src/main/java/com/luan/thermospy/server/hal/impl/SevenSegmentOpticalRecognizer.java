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
package com.luan.thermospy.server.hal.impl;

import com.luan.thermospy.server.core.Boundary;
import com.luan.thermospy.server.core.DigitRecognizerConfig;
import com.luan.thermospy.server.hal.DigitRecognizer;
import org.eclipse.jetty.util.log.Log;

import java.io.*;
import java.util.ArrayList;

/**
 * 
 * The seven segment optical recognizer calls the program ssocr to parse 
 * an image.If something goes wrong an IOException is thrown otherwise the
 * output from the ssocr is returned
 */
public class SevenSegmentOpticalRecognizer extends DigitRecognizer {

    private Boundary imgBounds = null;
    private File img;
    private DigitRecognizerConfig config;
    
    @Override
    public String recognize(File imgFile, Boundary crop) throws IOException {
        img = imgFile;
        imgBounds = crop;
        String result = "";

        result = runSSOCR();

        return result;
    }

    public String runSSOCR() throws IOException
    {
        ProcessBuilder builder = new ProcessBuilder();
        ArrayList<String> commands = new ArrayList<>();
        commands.add("ssocr");
        commands.add("make_mono");
        commands.add("-d");
        commands.add("-1");
        if (getConfig().isDebugEnabled())
        {
            commands.add("-D");
        }
        
        if (getConfig().isCropImage())
        {
            commands.add("crop");
            commands.add(Integer.toString(imgBounds.getX()));
            commands.add(Integer.toString(imgBounds.getY()));
            commands.add(Integer.toString(imgBounds.getWidth()));
            commands.add(Integer.toString(imgBounds.getHeight()));
        }
        if (getConfig().getThreshold() != 0)
        {
            commands.add("-t");
            commands.add(Integer.toString(getConfig().getThreshold()));
        }
        else
        {
            // No threshold, use iterative 
            commands.add("-T");
        }
            
        commands.add(img.getAbsolutePath());
        builder.command(commands);
        builder.redirectErrorStream(true);
        Process process = null;
        BufferedReader reader = null;
        String output = "";
        
        try {
            process = builder.start();
            InputStream std = process.getInputStream ();
            reader = new BufferedReader(new InputStreamReader(std));

            int result = process.waitFor();

            if (result == 0)
            {
                output = reader.readLine();
                if (output.startsWith("."))
                {
                    output = output.replace('.', '0');
                    Log.getLog().debug("Found dot at first pos. Remove it!");
                }
                try
                {
                    int temperature = Integer.parseInt(output);
                    Log.getLog().debug("Digits decoded: "+temperature);
                }
                catch (NumberFormatException nfe)
                {
                    Log.getLog().debug("Failed to parse output: "+output);

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
        

        return output;

    }

}
