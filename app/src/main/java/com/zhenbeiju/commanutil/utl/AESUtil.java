package com.zhenbeiju.commanutil.utl;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by zhanglin on 15-8-26.
 */
public class AESUtil {

    private static final String base64_key = "RrCjN1i5m1I6ufPllBsBnw==";
    private static final String base64_iv = "At1kSTvetpT+telTVg1Rwg==";
    private static final String characterEncoding = "UTF-8";
    private static final String cipherTransformation = "AES/CBC/PKCS5Padding";
    private static final String aesEncryptionAlgorithm = "AES";

    public static byte[] decrypt(byte[] cipherText, byte[] key, byte[] initialVector) throws Exception {
        Cipher cipher = Cipher.getInstance(cipherTransformation);
        SecretKeySpec secretKeySpecy = new SecretKeySpec(key, aesEncryptionAlgorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
        cipherText = cipher.doFinal(cipherText);
        return cipherText;
    }

    public static byte[] decryptWithBase64(String cipherText, String key, String initialVector) throws Exception {
        return decrypt(Base64.decode(cipherText, 0), Base64.decode(key, 0), Base64.decode(initialVector, 0));
    }

    public static String decrypt(String value) throws Exception {
        return new String(decryptWithBase64(value, base64_key, base64_iv));
    }

    public static void main(String args[]) throws Exception {
        byte[] clearText = decryptWithBase64("d5NlAxyR+ShXPTvXcKRRUA==", "RrCjN1i5m1I6ufPllBsBnw==", "At1kSTvetpT+telTVg1Rwg==");
        System.out.println("ClearText:" + new String(clearText, characterEncoding));
    }


}
