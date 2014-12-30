package com.luan.thermospy.server.hal.impl;

import com.luan.thermospy.server.core.Boundary;
import com.luan.thermospy.server.core.DigitRecognizerConfig;
import com.luan.thermospy.server.hal.DigitRecognizer;
import org.eclipse.jetty.util.log.Log;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by ludwig on 2014-12-24.
 */
public class SevenSegmentOpticalRecognizer extends DigitRecognizer {

    private Boundary imgBounds = null;
    private File img;
    private DigitRecognizerConfig config;
    
    public String recognize(File imgFile, Boundary crop) {
        img = imgFile;
        imgBounds = crop;
        String result = "";

        result = runSSOCR();

        return result;
    }

    public String runSSOCR()
    {
        ProcessBuilder builder = new ProcessBuilder();
        ArrayList<String> commands = new ArrayList<String>();
        commands.add("ssocr");
        commands.add("-d");
        commands.add("-1");
        
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
        commands.add(img.getAbsolutePath());
        builder.command(commands);
        builder.redirectErrorStream(true);
        Process process = null;
        BufferedReader reader = null;
        String output = "";
        int retries = getConfig().getRetryCount();
        while (retries-- > 0)
        {
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
                        break;
                    }
                    catch (NumberFormatException nfe)
                    {
                        Log.getLog().debug("Failed to parse output: "+output);
                    }
                }
            }
            catch (IOException ex)
            {
                Log.getLog().debug("Exception: "+ex.getMessage(), ex);
                break;
            }
            catch (InterruptedException ex2)
            {
                Log.getLog().debug("Exception: "+ex2.getMessage(), ex2);
                break;
            }
            catch (Exception e)
            {
                Log.getLog().debug("Exception: "+e.getMessage(), e);
                break;
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
        }

        return output;

    }

}
