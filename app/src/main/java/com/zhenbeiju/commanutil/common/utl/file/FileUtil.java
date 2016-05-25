package com.zhenbeiju.commanutil.common.utl.file;

import android.content.Context;
import android.util.Log;


import com.zhenbeiju.commanutil.common.utl.LogManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtil {

    public static long getFileSize(File f) {
        long size = 0;
        if (f.isFile()) {
            size += f.length();
        } else {
            File[] fs = f.listFiles();
            for (File ff : fs) {
                size += getFileSize(ff);
            }
        }
        return size;
    }

    public static boolean moveFile(File from, File to) {

        // return from.renameTo(to);
        LogManager.e(from.getAbsolutePath() + " \n " + to.getAbsolutePath());

        if (to.getParentFile().exists()) {
            to.getParentFile().length();
            LogManager.e("" + to.getParentFile().length());
        }

        if (!from.exists()) {
            return false;
        }
        if (to.exists()) {
            to.delete();
        } else {
            to.getParentFile().mkdirs();

        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(from);
            fos = new FileOutputStream(to);

            byte[] buf = new byte[10240];
            int number = 0;
            while ((number = fis.read(buf, 0, buf.length)) > 0) {
                fos.write(buf, 0, number);
            }
        } catch (FileNotFoundException e) {
            LogManager.printStackTrace(e);
            return false;
        } catch (IOException e) {
            LogManager.printStackTrace(e);
            if (to.exists()) {
                to.delete();
            }
            return false;
        } finally {
            try {
                fis.close();
                fos.flush();
                fos.close();
            } catch (Exception e) {
                LogManager.printStackTrace(e);
            }

        }
        return true;
    }

    public static File getLastFile(File from) {
        File result = null;
        if (from.isDirectory()) {
            for (File f : from.listFiles()) {
                if (result == null) {
                    result = getLastFile(f);
                }

                File lastFile = getLastFile(f);
                if (lastFile != null && lastFile.lastModified() < result.lastModified()) {
                    result = lastFile;
                }
            }
        } else {
            return from;
        }
        if (result != null) {
            LogManager.e(result.getAbsolutePath());
        }
        return result;
    }

    public static boolean MoveAndLimiteParent(File from, File to, long limiteSize) {
        File parent = to.getParentFile();
        while (parent.exists() && (getFileSize(parent) + from.length()) > limiteSize) {
            if (parent.listFiles().length == 0) {
                break;
            }
            getLastFile(parent).delete();
        }

        return moveFile(from, to);

    }

    public static void writeArrayStringToFileByTrim(ArrayList<String> srcArray, String destFile, boolean isAdd) {
        writeArrayStringToFile(srcArray, destFile, "\r", isAdd);
    }

    public static void writeArrayStringToFile(ArrayList<String> srcArray, String destFile, boolean isAdd) {
        writeArrayStringToFile(srcArray, destFile, null, isAdd);
    }

    public static boolean copyRawFile(Context context, int rawId, String destPath, String name, boolean isOverwrite) {

        if (context == null || destPath == null || destPath.trim().equals("") || name.trim().equals("")) {
            LogManager.e("", "copyRawFile", "params is invaild.");
            return false;
        }

        if (context.getResources() == null) {
            return false;
        }
        InputStream input = context.getResources().openRawResource(rawId);

        return writeFile(input, destPath, name, isOverwrite);
    }

    public static void createPath(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                LogManager.e("create dir failed! " + path);
            }
        }
    }

    public static boolean writeFile(InputStream input, String destPath, String name, boolean isOverwrite) {
        String outFileName = destPath + name;
        File destFile = new File(outFileName);

        createPath(destPath);
        if (destFile.exists()) {
            if (isOverwrite) {
                destFile.delete();
            } else {
                try {
                    input.close();
                } catch (Exception e) {
                    LogManager.printStackTrace(e, "DBHelper", "copyDataBase");
                }
                return false;
            }
        } else {
            try {
                destFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                LogManager.printStackTrace(e);
            }
        }

        OutputStream output = null;
        try {
            output = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            LogManager.printStackTrace(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            LogManager.printStackTrace(e);
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                LogManager.printStackTrace(e);
            } catch (Exception e) {
                LogManager.printStackTrace(e);
            }

        }
        return true;
    }

    public static void writeArrayStringToFile(ArrayList<String> srcArray, String destFile, String trim, boolean isAdd) {
        if (srcArray != null && srcArray.size() > 0) {
            File file = new File(destFile);
            BufferedWriter output = null;

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    LogManager.printStackTrace(e);
                }
            }

            try {
                output = new BufferedWriter(new FileWriter(file, isAdd));
                for (String src : srcArray) {
                    if (trim != null) {
                        output.write(src + trim + "\n");
                    } else {
                        output.write(src + "\n");
                    }

                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                LogManager.printStackTrace(e);
            } finally {
                if (output != null) {

                    try {
                        output.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        LogManager.printStackTrace(e);
                    }
                }
            }
        }
    }

    public static ArrayList<String> getFileContent(String path) {
        File f = new File(path);
        return getFileContent(f);
    }

    public static ArrayList<String> getFileContent(File f) {
        ArrayList<String> mCurrentInfos = new ArrayList<String>();

        try {
            FileInputStream fins = new FileInputStream(f);
            InputStreamReader reader = new InputStreamReader(fins);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                mCurrentInfos.add(line);
            }
            bufferedReader.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            LogManager.printStackTrace(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            LogManager.printStackTrace(e);
        }
        return mCurrentInfos;
    }

    public static void writeStringToFile(String src, String destFile, boolean isAdd) {
        ArrayList<String> list = new ArrayList<String>();
        list.add(src);
        writeArrayStringToFile(list, destFile, isAdd);
    }

    public static String getFileLineContent(String fileName, int lineNumber) {
        ArrayList<String> list = getFileContent(fileName);
        if (list != null && list.size() > lineNumber) {
            return list.get(lineNumber);
        }
        return null;
    }

    public static void Unzip(String zipFile, String targetDir) {
        int BUFFER = 4096; //这里缓冲区我们使用4KB，
        String strEntry; //保存每个zip的条目名称
        try {
            BufferedOutputStream dest = null; //缓冲输出流
            FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry; //每个zip条目的实例
            while ((entry = zis.getNextEntry()) != null) {
                try {
                    Log.i("Unzip: ", "=" + entry);
                    int count;
                    byte data[] = new byte[BUFFER];
                    strEntry = entry.getName();
                    if (entry.isDirectory()) {
                        entry.getName();
                        File entryFile = new File(targetDir + "/" + strEntry);
                        if (entryFile.exists()) {
                            entryFile.mkdirs();
                        }
                    } else {
                        File entryFile = new File(targetDir + "/" + strEntry);
                        File entryDir = new File(entryFile.getParent());
                        if (!entryDir.exists()) {
                            entryDir.mkdirs();
                        }
                        FileOutputStream fos = new FileOutputStream(entryFile);
                        dest = new BufferedOutputStream(fos, BUFFER);
                        while ((count = zis.read(data, 0, BUFFER)) != -1) {
                            dest.write(data, 0, count);
                        }
                        dest.flush();
                        dest.close();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            zis.close();
        } catch (Exception cwj) {
            cwj.printStackTrace();
        }
    }


    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        byte[] md5sum = digest.digest();
        BigInteger bigInt = new BigInteger(1, md5sum);
        String output = bigInt.toString(16);
        // Fill to 32 chars
        output = String.format("%32s", output).replace(' ', '0');
        return output;
    }

}
