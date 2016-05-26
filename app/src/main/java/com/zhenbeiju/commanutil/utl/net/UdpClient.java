package com.zhenbeiju.commanutil.utl.net;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;

import com.zhenbeiju.commanutil.utl.BaseContext;
import com.zhenbeiju.commanutil.utl.LogManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class UdpClient {
    private int port;
    private ArrayList<udpOngetData> mGetData = new ArrayList<>();
    private boolean runing = false;
    private BaseContext mBaseContext;
    private DatagramSocket serverSocket = null;
    private Thread mReciverThread;
    private final int maxErrorTimes = 16;
    private int mCurrentErrorTime = 0;


    public interface udpOngetData {
        void ongetdata(DatagramPacket receivePacket);
    }

    public static UdpClient getInstance(int port, BaseContext mBaseContext) {
        UdpClient udpClient;
        udpClient = (UdpClient) mBaseContext.getGlobalObject("UDP" + port);

        if (udpClient == null) {
            udpClient = new UdpClient(port, mBaseContext);
            mBaseContext.setGlobalObject("UDP" + port, udpClient);
        }
        return udpClient;
    }

    private UdpClient(final int port, BaseContext baseContext) {
        this(port);
        this.mBaseContext = baseContext;
    }

    public UdpClient(final int port) {
        runing = true;
        this.port = port;
        Thread mReciverThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (runing) {
                    try {

                        if (serverSocket == null) {
                            try {
                                serverSocket = new DatagramSocket(port);
                                serverSocket.setSoTimeout(60000);
                            } catch (SocketException e) {
                                // TODO Auto-generated catch block
                                LogManager.printStackTrace(e);
                            }
                        }


                        if (serverSocket == null) {
                            if (mCurrentErrorTime < maxErrorTimes) {

                                mCurrentErrorTime++;
                                run();
                            } else {
                                LogManager.e("final error ~~   need have a look ");
                            }
                            return;
                        }
                        byte[] receiveData = new byte[1024];
                        // if(NetWorkUtil.isNetworkConnected(context))
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        serverSocket.receive(receivePacket);
                        ArrayList<udpOngetData> tempGetData = new ArrayList<>();
                        tempGetData.addAll(mGetData);
                        for (udpOngetData data : tempGetData) {
                            data.ongetdata(receivePacket);
                        }
                        tempGetData.clear();
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        mReciverThread.start();

    }

    public void disConnect() {
        LogManager.e("disConnect");
        runing = false;
        if (serverSocket != null) {
            serverSocket.disconnect();
            serverSocket.close();
        }
        if (mBaseContext != null) {
            mBaseContext.setGlobalObject("UDP" + port, null);
        }

    }

    public void send(byte[] send, String ip, int port) {
        SendObj so = new SendObj(send, ip, port);
        try {

            InetAddress broadAddress = InetAddress.getByName(so.ip);

            DatagramPacket packet = new DatagramPacket(so.send, so.send.length, broadAddress, so.port);

            if (serverSocket != null) {
                serverSocket.send(packet);
            } else {
                DatagramSocket sender = new DatagramSocket();
                sender.setBroadcast(true);
                sender.send(packet);
            }
            LogManager.i(new String(so.send) + "  " + so.port + "  " + broadAddress.getHostAddress());
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void sendBroadcast(byte[] send) {
        try {
            String ip = getBroadcastAddress(mBaseContext).getHostAddress();
            send(send, ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    InetAddress getBroadcastAddress(BaseContext baseContext) throws IOException {
        WifiManager wifi = (WifiManager) baseContext.getContext().getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();
        // handle null somehow

        int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++)
            quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
        return InetAddress.getByAddress(quads);
    }

    public void addonGetData(udpOngetData data) {
        if (!mGetData.contains(data)) {
            synchronized (mGetData) {
                mGetData.add(data);
            }

        }
    }

    public void removeonGetData(udpOngetData data) {
        synchronized (mGetData) {
            mGetData.remove(data);
        }

    }

    class SendObj {
        private byte[] send;
        private String ip;
        private int port;

        public SendObj(byte[] send, String ip, int port) {
            this.send = send;
            this.ip = ip;
            this.port = port;
        }
    }

}
