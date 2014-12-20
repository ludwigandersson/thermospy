package com.luan.thermospy.server.worker;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.util.log.Log;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ludwig on 2014-12-26.
 */
public class FileTransferWorker extends Thread implements Runnable {
    private File file2Transfer = null;
    ServerSocket server_socket = null;

    FileTransferWorker(File file2Transfer)
    {
        this.file2Transfer = file2Transfer;
    }

    public int getServerPort()
    {
        return server_socket.getLocalPort();
    }

    public void run()
    {

        Socket socket = null;
        try {
            server_socket = new ServerSocket(0);
            socket = server_socket.accept();
            OutputStream out = socket.getOutputStream();
            InputStream in = new FileInputStream(file2Transfer);
            IOUtils.copy(in, out);

        }
        catch (IOException e)
        {
            Log.getLog().info("Exception: "+e.getMessage(), e);
        }
        finally
        {
            try {
                server_socket.close();
            }
            catch (IOException f)
            {}
        }


    }
}
