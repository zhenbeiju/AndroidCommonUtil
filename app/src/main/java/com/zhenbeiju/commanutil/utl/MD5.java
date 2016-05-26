package com.zhenbeiju.commanutil.utl;


import com.zhenbeiju.commanutil.utl.net.NetDataTypeTransform;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    public static String getMd5(String s) {
        byte[] bytesOfMessage;
        try {
            bytesOfMessage = s.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            String result = NetDataTypeTransform.BytesToIntString(thedigest).toUpperCase();
            return result;
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            LogManager.printStackTrace(e);
        }
        return null;
    }
}
