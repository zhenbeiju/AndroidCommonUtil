package commanutil.utl;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Log管理类 打印log建议都使用这个类。
 */
public class LogManager {

    private static String TAG = "shahome";
    private static final String TAG_CALLED = "has be called";
    private static final String TAG_EXCEPTION = "Exception==============";
    public static boolean mIsOutLog = true;
    public static boolean DEBUG_MODE = true;
    private static boolean mIsOutToFile = false;
    private static String mPath = "/sdcard/shahomelog.txt";
    private static int logLevel;


    public static void initConfig(String tag, int logLevel, boolean logToFile) {
        TAG = tag;
        LogManager.logLevel = logLevel;
        mIsOutToFile = logToFile;
        if (logLevel > Log.ASSERT) {
            mIsOutLog = false;
        }
    }

    private static boolean checkSdCardVaild() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 输出到文件
     *
     * @param log String log
     */
    public static void outputToFile(String log) {
        outputToFile(log, mPath, false);
    }

    public static void outputToFile(String log, String filePath, boolean isRealSave) {
        if ((log == null || !mIsOutToFile) && !isRealSave) {
            return;
        }
        if (!checkSdCardVaild()) {
            Log.e(TAG, "No sdcard!");
            return;
        }

        final File saveFile = new File(filePath);
        try {
            final FileOutputStream outStream = new FileOutputStream(saveFile, true);
            outStream.write((log + "\n").getBytes());
            outStream.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 输出异常
     *
     * @param exc Exception
     */
    public static void outputToFile(Throwable exc) {
        if (exc == null) {
            return;
        }

        // Environment.getExternalStorageDirectory();
        final StackTraceElement[] stack = exc.getStackTrace();
        outputToFile(exc.toString());
        for (StackTraceElement item : stack) {
            outputToFile(item.toString(), mPath, true);
        }
    }

    private static String bulidTag(String objectName, String methodName) {
        return "[" + objectName + "|" + methodName + "]";
    }

    // /**
    // *
    // * @param isShowLog
    // * 是否打印出Log
    // * @param isOutToFile
    // * 是否将log打印到文件
    // */
    // public static void initLogManager(boolean isShowLog, boolean isOutToFile)
    // {
    // mIsOutLog = isShowLog;
    // mIsOutToFile = isOutToFile;
    // }

    // public static int getLogFlag() {
    // return mIsShowLog ? 1 : 0;
    // }

    // private static boolean getFlgFromString(String flg) {
    // boolean ret = false;
    // if (flg != null) {
    // if (flg.equals("yes")) {
    // ret = true;
    // } else {
    // ret = false;
    // }
    // }
    //
    // return ret;
    // }

    // public static void initLogManager(String showLogFlg, String OutToFileFlg)
    // {
    // mIsShowLog = getFlgFromString(showLogFlg);
    // mIsOutToFile = getFlgFromString(OutToFileFlg);
    // }

    public static boolean getDebugMode() {
        return mIsOutLog;
    }


    public static void f(String objectName, String methodName) {
        f(objectName, methodName, TAG_CALLED);

    }

    public static void f(String objectName, String methodName, String msg) {
        if (mIsOutToFile) {
            final String log = bulidTag(objectName, methodName) + msg;
            Log.e(TAG, log);
            outputToFile(log, mPath, true);
        }

    }

    /**
     * @param e          Exception
     * @param objectName ObjectName
     * @param methodName MethodName
     */
    public static void printStackTrace(Exception e, String objectName, String methodName) {
        if (mIsOutLog && logLevel <= Log.WARN) {
            e(objectName, methodName, TAG_EXCEPTION);
            e(objectName, methodName, e.toString());
            e.printStackTrace();
            if (mIsOutToFile) {
                outputToFile(e);
            }
        }


    }

    public static void printStackTrace() {
        if (mIsOutLog && logLevel <= Log.WARN)
            for (StackTraceElement e : Thread.currentThread().getStackTrace()) {
                Log.e(TAG, e.getFileName() + "|" + e.getMethodName() + "|" + e.getLineNumber());
            }
    }


    /**
     * @param objectName ObjectName
     * @param methodName MethodName
     */
    public static void e(String objectName, String methodName) {
        e(objectName, methodName, TAG_CALLED);
    }


    /**
     * @param objectName ObjectName
     * @param methodName MethodName
     */
    public static void w(String objectName, String methodName) {
        w(objectName, methodName, TAG_CALLED);

    }

    /**
     * @param objectName ObjectName
     * @param methodName MethodName
     */
    public static void d(String objectName, String methodName) {
        d(objectName, methodName, TAG_CALLED);

    }

    /**
     * @param objectName ObjectName
     * @param methodName MethodName
     */
    public static void v(String objectName, String methodName) {
        v(objectName, methodName, TAG_CALLED);

    }

    /**
     * @param objectName ObjectName
     * @param methodName MethodName
     */
    public static void i(String objectName, String methodName) {
        i(objectName, methodName, TAG_CALLED);
    }


    /**
     * @param objectName ObjectName
     * @param methodName MethodName
     * @param msg        Message
     */
    public static void e(String objectName, String methodName, String msg) {
        if (mIsOutLog && logLevel <= Log.ERROR) {
            final String log = bulidTag(objectName, methodName) + msg;
            Log.e(TAG, log);
            outputToFile(log);
        }
    }


    /**
     * @param objectName ObjectName
     * @param methodName MethodName
     * @param msg        Message
     */
    public static void w(String objectName, String methodName, String msg) {
        if (mIsOutLog && logLevel <= Log.WARN) {
            final String log = bulidTag(objectName, methodName) + msg;
            Log.w(TAG, log);
            outputToFile(log);
        }
    }

    /**
     * @param objectName ObjectName
     * @param methodName MethodName
     * @param msg        Message
     */
    public static void d(String objectName, String methodName, String msg) {

        if (mIsOutLog && logLevel <= Log.DEBUG) {
            final String log = bulidTag(objectName, methodName) + msg;
            Log.d(TAG, log);
            outputToFile(log);
        }

    }

    /**
     * @param objectName ObjectName
     * @param methodName MethodName
     * @param msg        Message
     */
    public static void v(String objectName, String methodName, String msg) {
        if (mIsOutLog && logLevel > Log.VERBOSE) {
            final String log = bulidTag(objectName, methodName) + msg;
            Log.v(TAG, log);
            outputToFile(log);
        }

    }

    /**
     * @param objectName ObjectName
     * @param methodName MethodName
     * @param msg        Message
     */
    public static void i(String objectName, String methodName, String msg) {
        if (mIsOutLog && logLevel <= Log.INFO) {
            final String log = bulidTag(objectName, methodName) + msg;
            Log.i(TAG, log);
            outputToFile(log);
        }
    }

    /**
     * @param e Exception
     */

    public static void printStackTrace(Throwable e) {
        if (mIsOutLog && logLevel <= Log.WARN && e != null) {
            final String objectName = Thread.currentThread().getStackTrace()[3].getFileName();
            final String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();

            e(objectName, methodName, TAG_EXCEPTION);
            e(objectName, methodName, e.toString());
            e.printStackTrace();
            if (mIsOutToFile) {
                outputToFile(e);
            }

        }
    }

    public static String bulidTag(String msg) {
        String objectName = Thread.currentThread().getStackTrace()[4].getFileName();
        String methodName = Thread.currentThread().getStackTrace()[4].getMethodName();

        return bulidTag(objectName, methodName) + msg;
    }

    /**
     * e
     * Print error log information
     *
     * @param msg
     */
    public static void e(String msg) {
        if (mIsOutLog && logLevel <= Log.ERROR) {
            String log = bulidTag(msg);
            int position = 0;
            while (position < log.length()) {
                if ((position + 500) < log.length()) {
                    Log.e(TAG, log.substring(position, position + 500));
                    position += 500;
                } else {
                    Log.e(TAG, log.substring(position, log.length()));
                    position = log.length();
                }
            }
            outputToFile(log);
        }
    }

    public static void f(String msg) {
        String log = bulidTag(msg);
        Log.e(TAG, log);
        outputToFile(log, mPath, true);
    }

    /**
     * w
     * Print Warnning log information
     *
     * @param msg
     */
    public static void w(String msg) {
        if (mIsOutLog && logLevel <= Log.WARN) {
            String log = bulidTag(msg);
            Log.w(TAG, log);
            outputToFile(log);
        }
    }

    /**
     * d
     * Print debug log information
     *
     * @param msg
     */
    public static void d(String msg) {

        if (mIsOutLog && logLevel <= Log.DEBUG) {
            String log = bulidTag(msg);
            Log.d(TAG, log);
            outputToFile(log);
        }
    }

    /**
     * v
     * Print void log information
     *
     * @param msg
     */
    public static void v(String msg) {
        if (mIsOutLog && logLevel <= Log.VERBOSE) {
            String log = bulidTag(msg);
            Log.v(TAG, log);
            outputToFile(log);
        }
    }

    /**
     * Print info log information
     * msg String message
     */
    public static void i(String msg) {
        if (mIsOutLog && logLevel <= Log.INFO) {
            String log = bulidTag(msg);
            Log.i(TAG, log);
            outputToFile(log);
        }
    }

}
