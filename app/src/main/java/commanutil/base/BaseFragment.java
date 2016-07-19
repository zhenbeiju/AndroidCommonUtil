package commanutil.base;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import java.util.Random;

import commanutil.utl.StringUtil;


/**
 * Created by zhanglin on 5/24/16.
 */
public class BaseFragment extends Fragment {


    protected String name;


    @Override
    public void onResume() {
        super.onResume();
        if (getActivity().getActionBar() != null && !StringUtil.isEmpty(name)) {
            getActivity().getActionBar().setTitle(name);
        }
    }

    public String getStringTag() {
        return name;
    }

    public void setStringTag(String tag) {
        this.name = tag;
    }

    /**
     * open fragment in a new activity
     *
     * @param jumpClass
     */
    public void jumpToFragment(Class jumpClass) {
        jumpToFragment(jumpClass.getName());
    }

    public void jumpToFragment(Class jumpClass, Object object) {
        jumpToFragment(jumpClass.getName(), object);
    }

    /**
     * open fragment in a new activity
     *
     * @param classpath
     */
    public void jumpToFragment(String classpath) {
        jumpToFragment(classpath, null);
    }


    public void jumpToFragment(String classpath, Object object) {
        jumpToFragment(classpath, object, getContext());
    }

    public void jumpToFragment(String classpath, Object object, Object object2) {
        jumpToFragment(classpath, object, object2, getContext());
    }

    public void jumpToFragment(Class jumpClass, Object object, Object object2) {
        jumpToFragment(jumpClass.getName(), object, object2, getContext());
    }


    public static void jumpToFragment(String classpath, Object object, Context context) {
        Intent i = new Intent();
        i.putExtra("classname", classpath);
        i.setClass(context, NormalActivity.class);
        if (object != null) {
            i.putExtra("HasObj", true);
            String key = System.currentTimeMillis() + "";
            i.putExtra("key", key);
            BaseApplication.mBaseContext.setGlobalObject(key, object);
        }
        context.startActivity(i);
    }

    public static void jumpToFragment(String classpath, Object object, Object object2, Context context) {
        Intent i = new Intent();
        i.putExtra("classname", classpath);
        i.setClass(context, NormalActivity.class);
        if (object != null || object2 != null) {
            i.putExtra("HasObj", true);
            String key = System.currentTimeMillis() + "";
            i.putExtra("key", key);
            BaseApplication.mBaseContext.setGlobalObject(key, object);
            String key2 = new Random().nextLong() + "";
            i.putExtra("key2", key2);
            BaseApplication.mBaseContext.setGlobalObject(key2, object2);
        }
        context.startActivity(i);
    }


    public void changeFragment(BaseFragment fragment) {

        ((BaseActivity) getActivity()).changeFragment(fragment);
    }


    public void changeFragmentBack(BaseFragment fragment) {
        ((BaseActivity) getActivity()).changeFragmentBack(fragment);
    }

    public void setData(Object object) {
        throw new RuntimeException(" please overwrite this method!");
    }


    public void setData(Object object, Object object2) {
        throw new RuntimeException(" please overwrite this method!");
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
