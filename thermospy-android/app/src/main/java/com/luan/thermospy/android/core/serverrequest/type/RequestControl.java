package com.luan.thermospy.android.core.serverrequest.type;

/**
 * Interface for controlling a
 */
public interface RequestControl {
    void request(final String ip, final int port);
    void cancel();
}
