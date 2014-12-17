package com.luan.thermospy.server.actions;

import org.eclipse.jetty.util.log.Log;

/**
 * Created by ludde on 14-12-17.
 */
public class TakePhotoAction implements CameraAction {
    public TakePhotoAction() {}

    public int run() {
        Log.getLog().info("Snap!");
        return 0;
    }
}
