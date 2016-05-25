package com.zhenbeiju.commanutil.common.utl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.util.Log;


import com.zhenbeiju.commanutil.common.base.MyApplication;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 网络连接管理。
 */
public class NetWorkUtil {

    private static final String RUNTIMEEXCEPTION_MSG = "Context is null.";

    /**
     * @param context Context
     * @return true:有连接 false:无网络连接
     */

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            final ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        } else {
            throw new RuntimeException(RUNTIMEEXCEPTION_MSG);
        }
        return false;
    }

    /**
     * @param context Context
     * @return true:已连接到wifi false:不连接wifi。
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            final ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        } else {
            throw new RuntimeException(RUNTIMEEXCEPTION_MSG);
        }
        return false;
    }

    /**
     * @param context Context
     * @return true:有数据GPRS连接。 false :无数据GPRS连接
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            final ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        } else {
            throw new RuntimeException(RUNTIMEEXCEPTION_MSG);
        }
        return false;
    }

    /**
     * @param context Context
     * @return <pre>
     *  ConnectivityManager.TYPE_MOBILE  数据联系
     *  ConnectivityManager.TYPE_WIFI    wifi连接
     *  -1 No Connected
     * </pre>
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            final ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        } else {
            throw new RuntimeException(RUNTIMEEXCEPTION_MSG);
        }
        return -1;
    }

    /**
     * 获得本地ip地址
     *
     * @return
     */
    public static String getLocalIpAddress() {
        String networkadd = "";
        if (!StringUtil.isEmpty(MyApplication.mBaseContext.getGlobalString("networkadd"))) {
            return MyApplication.mBaseContext.getGlobalString("networkadd");
        } else {
            try {
                String tempIp = null;
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                    NetworkInterface intf = en.nextElement();

                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String HostAdd = inetAddress.getHostAddress().toString();
                            if (!StringUtil.isEmpty(HostAdd) && !HostAdd.equals("::1")) {
                                MyApplication.mBaseContext.setGlobalString("networkadd", HostAdd);
                                return HostAdd;
                            }
                        }
                    }
                }
                return tempIp;
            } catch (SocketException ex) {
                ex.printStackTrace();
                Log.e("IpAddress", ex.toString());
            }
        }

        return null;
    }

    public static String getWIfiGatewayIp() {
        android.net.wifi.WifiManager wm = (android.net.wifi.WifiManager) MyApplication.context.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo di = wm.getDhcpInfo();
        long getewayIpL = di.gateway;
        String getwayIpS = long2ip(getewayIpL);//网关地址
        return getwayIpS;
    }


    public static String long2ip(long ip) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf((int) (ip & 0xff)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 8) & 0xff)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 16) & 0xff)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 24) & 0xff)));
        return sb.toString();
    }
}
