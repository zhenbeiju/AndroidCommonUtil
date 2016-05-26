package commanutil.utl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import commanutil.inf.IGlobalValueOperation;
import commanutil.inf.IPrefrenceOperation;

import java.util.Map;

/**
 * 
 * 使用本类终端额方法有一个前提。 Application类要实现IGloableHeap;
 * 
 */
public class BaseContext implements IPrefrenceOperation, IGlobalValueOperation {

    protected Context mContext;
    private SharedPreferences mPrefs;
    private Editor mEditor;

    private Map<String, Boolean> mGlobalBooleanMap;
    private Map<String, Integer> mGlobalIntMap;
    private Map<String, String> mGlobalStringMap;
    private Map<String, Long> mGlobalLongMap;
    private Map<String, Object> mGlobalObjectMap;
    private Map<String, Float> mGlobalFloatMap;

    /**
     * 
     * @param context
     *            Context
     * 
     *            <pre>
     *      同Context相关联的工具类。
     *      1.存取Prefrence值。
     *      2.存取全局对象
     *      3.发送Umeng统计事件。
     *      3.获取string中的相关属性值
     * </pre>
     * 
     */
    public BaseContext(Context context) {
        mContext = context;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPrefs.edit();
        try {
            final IGloableHeap gloableHeap = (IGloableHeap) context.getApplicationContext();
            mGlobalBooleanMap = gloableHeap.getGlobalBooleanMap();
            mGlobalIntMap = gloableHeap.getGlobalIntegerMap();
            mGlobalStringMap = gloableHeap.getGloabalString();
            mGlobalLongMap = gloableHeap.getGlobalLongMap();
            mGlobalObjectMap = gloableHeap.getGlobalObjectMap();
            mGlobalFloatMap = gloableHeap.getGlobalFloatMap();
            
        } catch (Exception e) {
            LogManager.printStackTrace(e);
            throw new RuntimeException("Application need implements IGloableHeap!");
        }
    }

    /**
     * 
     * @param key
     *            Preference key值
     * @return key对应的value值
     */
    public boolean getPrefBoolean(String key) {
        return mPrefs.getBoolean(key, false);
    }

    /**
     * 
     * @param key
     *            Preference key值
     * @param defVal
     *            默认的value值
     * @return key对应的value值
     */
    public boolean getPrefBoolean(String key, boolean defVal) {
        if (mPrefs != null) {
            return mPrefs.getBoolean(key, defVal);
        } else {
            return false;
        }

    }

    /**
     * 
     * @param key
     *            Preference key值
     * @param value
     *            设置的value值
     */
    public void setPrefBoolean(String key, boolean value) {
        synchronized (mEditor) {
            mEditor.putBoolean(key, value);
            mEditor.commit();
        }

    }

    /**
     * 
     * @param key
     *            Preference key值
     * @return key对应的value值
     */
    public String getPrefString(String key) {
        return mPrefs.getString(key, null);
    }

    /**
     * 
     * @param key
     *            Preference key值
     * @param defVal
     *            默认的value值
     * @return key对应的value值
     */
    public String getPrefString(String key, String defVal) {
        return mPrefs.getString(key, defVal);
    }

    /**
     * 
     * @param key
     *            Preference key值
     * @param value
     *            设置的value值
     */
    public void setPrefInteger(String key, int value) {
        synchronized (mEditor) {
            mEditor.putInt(key, value);
            mEditor.commit();
        }

    }

    /**
     * 
     * @param key
     *            Preference key值
     * @return key对应的value值
     */
    public int getPrefInteger(String key) {
        return mPrefs.getInt(key, 0);
    }

    /**
     * 
     * @param key
     *            Preference key值
     * @param defVal
     *            默认的value值
     * @return key对应的value值
     */
    public int getPrefInteger(String key, int defVal) {
        return mPrefs.getInt(key, defVal);
    }

    /**
     * 
     * @param key
     *            Preference key值
     * @param value
     *            设置的value值
     */
    public void setPrefString(String key, String value) {
        synchronized (mEditor) {
            mEditor.putString(key, value);
            mEditor.commit();
        }
    }

    /**
     * 
     * @param key
     *            Preference key值
     * @return key对应的value值
     */
    public long getPrefLong(String key) {
        return mPrefs.getLong(key, 0L);
    }

    /**
     * 
     * @param key
     *            Preference key值
     * @param defVal
     *            默认的value值
     * @return key对应的value值
     */
    public long getPrefLong(String key, long defVal) {
        return mPrefs.getLong(key, defVal);
    }

    /**
     * 
     * @param key
     *            Preference key值
     * @param value
     *            设置的value值
     */
    public void setPrefLong(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    /**
     * 
     * @param key
     *            Preference key值
     * @return key对应的value值
     */
    public float getPrefFloat(String key) {
        return mPrefs.getFloat(key, 0.0F);
    }

    /**
     * 
     * @param key
     *            Preference key值
     * @param defVal
     *            默认的value值
     * @return key对应的value值
     */
    public float getPrefFloat(String key, float defVal) {
        return mPrefs.getFloat(key, defVal);
    }

    /**
     * 
     * @param key
     *            Preference key值
     * @param value
     *            设置的value值
     */
    public void setPrefFloat(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.commit();
    }

    /**
     * 
     * @param key
     *            全局对象的key值
     * @param value
     *            全局对象key对应的value值
     */
    public void setGlobalBoolean(String key, boolean value) {
        mGlobalBooleanMap.put(key, value);
    }

    /**
     * 
     * @param key
     *            全局对象对应的key值
     * @param value
     *            全局对象对应的value值
     */
    public void setGlobalString(String key, String value) {
        mGlobalStringMap.put(key, value);
    }

    /**
     * 
     * @param key
     *            全局对象的key值
     * @param value
     *            全局对象key对应的value值
     */
    public void setGlobalInteger(String key, Integer value) {
        mGlobalIntMap.put(key, value);
    }

    /**
     * 
     * @param key
     *            全局对象的key值
     * @param value
     *            全局对象key对应的value值
     */
    public void setGlobalLong(String key, Long value) {
        LogManager.e(key + value);
        mGlobalLongMap.put(key, value);
    }

    /**
     * 
     * @param key
     *            全局对象的key值
     * @param value
     *            全局对象key对应的value值
     */
    public void setGlobalObject(String key, Object value) {
        mGlobalObjectMap.put(key, value);
    }

    /**
     * 
     * @param key
     *            全局对象的key值
     * @param defVal
     *            全局对象key对应的默认value值
     * @return 对应的value值
     */
    public boolean getGlobalBoolean(String key, boolean defVal) {
        Boolean booleanValue = mGlobalBooleanMap.get(key);
        if (booleanValue == null) {
            booleanValue = defVal;
        }
        return booleanValue;
    }

    /**
     * 
     * @param key
     *            全局对象的key值
     * @param defVal
     *            全局对象key对应的默认value值
     * @return 对应的value值
     */
    public String getGlobalString(String key, String defVal) {
        String gloableStringValue = mGlobalStringMap.get(key);
        if (gloableStringValue == null) {
            gloableStringValue = defVal;
        }
        return gloableStringValue;
    }

    /**
     * 
     * @param key
     *            全局对象的key值
     * @param defVal
     *            全局对象key对应的默认value值
     * @return 对应的value值
     */
    public int getGlobalInteger(String key, Integer defVal) {
        Integer integerValue = mGlobalIntMap.get(key);
        if (integerValue == null) {
            integerValue = defVal;
        }
        return integerValue;
    }

    /**
     * 
     * @param key
     *            全局对象的key值
     * @param defVal
     *            全局对象key对应的默认value值
     * @return 对应的value值
     */
    public long getGlobalLong(String key, Long defVal) {
        Long value = mGlobalLongMap.get(key);
        if (value == null) {
            value = defVal;
        }
        return value;
    }

    /**
     * 
     * @param key
     *            全局对象的key值
     * @param defVal
     *            全局对象key对应的默认value值
     * @return 对应的value值
     */
    public Object getGlobalObject(String key, Object defVal) {
        Object value = mGlobalObjectMap.get(key);
        if (value == null) {
            value = defVal;
        }
        return value;
    }

    /**
     * 
     * @param key
     *            全局对象的key值
     * @return key对应的value值
     */
    public boolean getGlobalBoolean(String key) {
        final Boolean value = mGlobalBooleanMap.get(key);
        if (value == null) {
            return false;
        }
        return value;
    }

    /**
     * 
     * @param key
     *            全局对象的key值
     * @return key对应的value值
     */
    public float getGlobalFloat(String key) {
        final Float value = mGlobalFloatMap.get(key);
        if (value == null) {
            return 0f;
        }
        return value;
    }

    /**
     * 
     * @param key
     *            全局对象的key值
     * @return key对应的value值
     */
    public String getGlobalString(String key) {
        return mGlobalStringMap.get(key);
    }

    /**
     * 
     * @param key
     *            全局对象的key值
     * @return key对应的value值
     */
    public int getGlobalInteger(String key) {
        if (mGlobalIntMap.containsKey(key)) {
            return mGlobalIntMap.get(key);
        }
        // LogManager.e(key);
        return 0;
        // } else {
        // return 0;
        // }

    }

    /**
     * 
     * @param key
     *            全局对象的key值
     * @return key对应的value值
     */
    public long getGlobalLong(String key) {
        if (mGlobalLongMap.containsKey(key)) {
            return mGlobalLongMap.get(key);
        } else {
            return 0L;
        }

    }

    /**
     * 
     * @param key
     *            全局对象的key值
     * @return key对应的value值
     */
    public Object getGlobalObject(String key) {
        return mGlobalObjectMap.get(key);
    }

    /**
     * 
     * @param resId
     *            资源id
     * @return 资源对应的String值
     */
    public String getString(int resId) {
        return mContext.getString(resId);
    }

    /**
     * @param resId
     *            资源id
     * @return 资源对应的String[]值
     */
    public String[] getStringArray(int resId) {
        return getStringArray(resId, mContext);
    }

    private static String[] getStringArray(int resId, Context context) {
        return context.getResources().getStringArray(resId);
    }

    /**
     * 
     * @return Context对象。
     */
    public Context getContext() {
        return mContext;
    }

    public void startActivities(Class cls) {
        Intent i = new Intent(mContext, cls);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
    }

    // /**
    // *
    // * @param eventId
    // * Umeng事件ID。需要事先到Umeng注册该ID才能统计得到。
    // * @param content
    // * 事件内容。
    // */
    // public void sendUmengEvent(String eventId, String content) {
    // UmengUtil.onEvent(mContext, eventId, content);
    // }
}
