package commanutil.utl.net;

import android.text.TextUtils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * 数据类型转换
 */
public class HexStringUtil {

    /**
     * 将int转为低字节在前，高字节在后的byte数组
     */
    public static byte[] IntToByteArray(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }

    /**
     * 用0 填充字符串到指定的长度
     *
     * @param s
     * @param length
     * @return
     */
    public static String fillString(String s, int length) {
        String result = String.format("%" + length + "s", s).replaceAll(" ", "0");
        return result;
    }

    /**
     * byte数组转化为int 将低字节在前转为int，高字节在后的byte数组
     */
    public static int ByteArrayToInt(byte[] res) {

        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }

    public static int bytesToInt(byte b1, byte b2) {
        int targets = (b1 & 0xff) | ((b2 << 8) & 0xff00);
        return targets;
    }

    /**
     * 将byte数组转化成String
     * utf-8 format
     */
    public static String ByteArraytoString(byte[] valArr) {
        String result = null;
        int index = 0;
        while (index < valArr.length) {
            if (valArr[index] == 0) {
                break;
            }
            index++;
        }
        byte[] temp = new byte[index];
        System.arraycopy(valArr, 0, temp, 0, index);
        try {
            result = new String(temp, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将String转化成byte数组
     * utf-8 format
     *
     * @param str
     * @return
     */
    public static byte[] StringToByteArray(String str) {
        byte[] temp = null;
        try {
            if (str == null) {
                temp = new byte[]{0};
            } else {
                temp = str.getBytes("UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * byte转换为char
     *
     * @param b
     * @return
     */
    public static char byteToChar(byte[] b) {
        char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
        return c;
    }

    /**
     * char 转换为byte
     *
     * @param c
     * @return
     */
    public static byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) (c & 0xFF);
        b[1] = (byte) ((c & 0xFF00) >> 8);
        return b;
    }

    /**
     * @param inStream
     * @return 读入的字节数组
     * @throws Exception InputStream 转为 byte
     */
    public static byte[] inputStreamToByte(InputStream inStream) throws Exception {
        int count = 0;
        while (count == 0) {
            if (null != inStream) {
                count = inStream.available();
            }
        }

        byte[] b = null;
        if (count > 2048) {
            b = new byte[2048];
        } else {
            if (count != -1) {
                b = new byte[count];
            }
        }

        if (null != b) {
            inStream.read(b);
        }
        // LoginActivity.logger("receive data len = "+b.length);
        return b;
    }

    public static byte[] hexStringToByte(String s) {
        if (TextUtils.isEmpty(s)) {
            return new byte[0];
        }

        byte[] resulte = new byte[(s.length() + 1) / 2];

        for (int i = 0, j = 0; i < s.length() - 1; i += 2, j++) {
            String info = s.substring(i, i + 2);
            resulte[j] = (byte) Integer.parseInt(info, 16);
            // LogManager.e((byte) Integer.parseInt(info, 16) + "");
        }
        return resulte;
    }

    public static byte[] mergeByte(byte[] bytes1, byte[] bytes2) {
        byte[] result = new byte[bytes1.length + bytes2.length];
        System.arraycopy(bytes1, 0, result, 0, bytes1.length);
        System.arraycopy(bytes2, 0, result, bytes1.length, bytes2.length);
        return result;
    }

    public static byte[] mergeByte(byte[] bytes1, byte[] bytes2, byte[] bytes3) {
        return mergeByte(mergeByte(bytes1, bytes2), bytes3);
    }


    public static String BytesToHexString(byte[] values) {
        StringBuilder s = new StringBuilder();
        for (byte b : values) {
            int i = 0xff & b;
            String temps = Integer.toHexString(i);
            if (temps.length() == 1) {
                temps = '0' + temps;
            }
            s.append(temps);
        }
        return s.toString();
    }


    public static String String2Utf8(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "utf-8");
    }

}
