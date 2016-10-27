package commanutil.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import commanutil.utl.BaseContext;
import commanutil.utl.IGloableHeap;
import commanutil.utl.net.volleyrequest.OkHttpStack;

import java.util.HashMap;
import java.util.Map;

public class BaseApplication extends Application implements IGloableHeap {
    public static BaseContext mBaseContext;
    public static Context context;
    public static Handler mHandler;
    public static RequestQueue mQueue;

    private Map<String, Boolean> mGlobalBooleanMap = new HashMap<String, Boolean>();
    private Map<String, Integer> mGlobalIntMap = new HashMap<String, Integer>();
    private Map<String, String> mGlobalStringMap = new HashMap<String, String>();
    private Map<String, Long> mGlobalLongMap = new HashMap<String, Long>();
    private Map<String, Object> mGlobalObjectMap = new HashMap<String, Object>();
    private Map<String, Float> mGlobalFloatMap = new HashMap<String, Float>();
    private LruCache<String, Object> mFragmentCache = new LruCache<String, Object>(512 * 1024);

    public String getStr() {
        return "";
    }

    private static BaseApplication myApplication;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mBaseContext = new BaseContext(getApplicationContext());
        context = this;
        mHandler = new Handler();
        myApplication = this;
        mQueue = Volley.newRequestQueue(getApplicationContext(), new OkHttpStack());
        mQueue.start();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mQueue.cancelAll(context);
    }

    public static Application getInstance() {
        return myApplication;
    }


    @Override
    public Map<String, String> getGloabalString() {
        // TODO Auto-generated method stub
        return mGlobalStringMap;
    }

    @Override
    public Map<String, Object> getGlobalObjectMap() {
        // TODO Auto-generated method stub
        return mGlobalObjectMap;
    }

    @Override
    public Map<String, Integer> getGlobalIntegerMap() {
        // TODO Auto-generated method stub
        return mGlobalIntMap;
    }

    @Override
    public Map<String, Float> getGlobalFloatMap() {
        // TODO Auto-generated method stub
        return mGlobalFloatMap;
    }

    @Override
    public Map<String, Long> getGlobalLongMap() {
        // TODO Auto-generated method stub
        return mGlobalLongMap;
    }

    @Override
    public Map<String, Boolean> getGlobalBooleanMap() {
        // TODO Auto-generated method stub
        return mGlobalBooleanMap;
    }

    @Override
    public LruCache<String, Object> getFragmentCache() {
        return mFragmentCache;
    }

}
