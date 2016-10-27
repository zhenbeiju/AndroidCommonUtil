package commanutil.utl;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.ContentResolver;
import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import commanutil.base.BaseApplication;


/**
 * 屏幕控制。包括 1.屏幕亮度调整 2.去除锁屏。
 */
public class ScreenUtil {
    /**
     *
     */
    private static final int MAX_BRIGHTNESS = 255;
    private static final int DEFAULT_BRIGHTNESS = 30;
    private static final int DEFAULT_STEP = 10;
    private Context mContext;
    private KeyguardLock mKeyguardLock;
    private WakeLock mWakelock;

    public ScreenUtil(Context context) {
        mContext = context;
    }

    /**
     * @param brightness 屏幕亮度。
     * @throws SettingNotFoundException
     */
    private void adjustScreenBrightness(int brightness) throws SettingNotFoundException {
        final ContentResolver contentResolver = mContext.getContentResolver();
        final int brightnessMode = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE);
        if (brightnessMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
            Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        }

        if (mContext instanceof Activity) {
            final WindowManager.LayoutParams layoutParams = ((Activity) mContext).getWindow().getAttributes();
            layoutParams.screenBrightness = (float) brightness / MAX_BRIGHTNESS;
            ((Activity) mContext).getWindow().setAttributes(layoutParams);
        }

        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS,
                brightness);
        LogManager.d("now brightNess is " + brightness);
    }

    private int getScreenBrightness() throws SettingNotFoundException {
        final ContentResolver contentResolver = mContext.getContentResolver();
        final int brightnessMode = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE);
        if (brightnessMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
            return DEFAULT_BRIGHTNESS;
        }
        int brightness = 0;
        try {
            brightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (SettingNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return brightness;
    }

    public boolean riseBrightness() throws SettingNotFoundException {
        int brightness = getScreenBrightness();

        if (brightness == MAX_BRIGHTNESS) {
            return false;
        }
        brightness += DEFAULT_STEP;
        if (brightness >= DEFAULT_BRIGHTNESS) {
            brightness = MAX_BRIGHTNESS;
        }
        adjustScreenBrightness(MAX_BRIGHTNESS);
        return true;
    }

    public boolean reduceBrightness() throws SettingNotFoundException {
        int brightness = getScreenBrightness();

        if (brightness == DEFAULT_BRIGHTNESS) {
            return false;
        }
        brightness -= DEFAULT_STEP;
        if (brightness >= DEFAULT_BRIGHTNESS) {
            brightness = MAX_BRIGHTNESS;
        }
        adjustScreenBrightness(MAX_BRIGHTNESS);
        return true;
    }

//    public void unLockAndLightScreen() {
//        final PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
//        if (!pm.isScreenOn()) {
//            mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,
//                    "SimpleTimer");
//            mWakelock.acquire();
//        }
//        final KeyguardManager mKeyguardManager = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
//        if (mKeyguardLock == null) {
//            mKeyguardLock = mKeyguardManager.newKeyguardLock("");
//            mKeyguardLock.disableKeyguard();
//        }
//    }
//
//    public void unLignthAndLock() {
//        if (mKeyguardLock != null) {
//            mKeyguardLock.reenableKeyguard();
//            mKeyguardLock = null;
//        }
//        if (mWakelock != null) {
//            mWakelock.release();
//            mWakelock = null;
//        }
//    }

    public static int dip2px(float dipValue) {
        final float scale = BaseApplication.context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public static int px2dip(float pxValue) {
        final float scale = BaseApplication.context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static int getRelativeTop(View myView) {
//	    if (myView.getParent() == myView.getRootView())
        if (myView.getId() == android.R.id.content)
            return myView.getTop();
        else
            return myView.getTop() + getRelativeTop((View) myView.getParent());
    }

    public static int getRelativeLeft(View myView) {
//	    if (myView.getParent() == myView.getRootView())
        if (myView.getId() == android.R.id.content)
            return myView.getLeft();
        else
            return myView.getLeft() + getRelativeLeft((View) myView.getParent());
    }


    //获取屏幕的宽度
    public static int getScreenWidth() {
        WindowManager manager = (WindowManager) BaseApplication.context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    //获取屏幕的高度
    public static int getScreenHeight() {
        WindowManager manager = (WindowManager) BaseApplication.context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }
}
