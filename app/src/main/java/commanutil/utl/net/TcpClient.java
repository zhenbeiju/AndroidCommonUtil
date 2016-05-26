package commanutil.utl.net;


import commanutil.utl.LogManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpClient {
    private Socket socket;
    private OutputStream os;
    private InputStream in;
    private onGetData mGetData;
    private String ip;
    private int port;
    private boolean runing;

    public interface onGetData {
        void onGetdata(byte[] value);
    }

    public TcpClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        runing = true;
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (runing) {
                    if (socket == null || !socket.isConnected()) {
                        connect();
                    }
                    try {
                        Thread.sleep(20000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        LogManager.printStackTrace(e);
                    }
                }
            }
        }).start();
        reciver();
    }

    public void connect() {
        socket = new Socket();

        try {
            socket.connect(new InetSocketAddress(ip, port), 5000);
            socket.setKeepAlive(true);
            in = socket.getInputStream();
        } catch (IOException e) {
            LogManager.printStackTrace(e);
            socket = null;
        }
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    public void reciver() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    while (true) {
                        if (in != null) {
                            int size = in.available();
                            if (size > 1) {
                                byte[] values = new byte[size];
                                in.read(values);
                                LogManager.i("get " + new String(values));
                                if (mGetData != null) {
                                    mGetData.onGetdata(values);
                                }
                            }
                        } else {

                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            LogManager.printStackTrace(e);
                        }
                    }
                } catch (UnknownHostException e) {
                    LogManager.printStackTrace(e);

                } catch (IOException e) {
                    LogManager.printStackTrace(e);
                    socket = null;
                }
            }
        }).start();
    }

    public boolean send(byte[] b) {
        try {

            LogManager.e(socket.isOutputShutdown() + " isOutputShutdown  " + socket.isClosed() + "     "
                    + socket.isInputShutdown());
            os = socket.getOutputStream();
            if (os != null && socket.isConnected()) {
                os.write(b);
                return true;
            }
            assert os != null;
            os.close();
        } catch (Exception e) {
            LogManager.printStackTrace(e);
            socket = null;
            if (in == null) {
                LogManager.w("in == null " + ip);
            }
            connect();
        }
        return false;
    }

    public void setonReciver(onGetData getData) {
        mGetData = getData;
    }

    public void disConnect() {
        runing = false;
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            LogManager.printStackTrace(e);
        }
        socket = null;
    }

}
