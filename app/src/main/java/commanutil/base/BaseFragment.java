package commanutil.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import java.util.Random;

import commanutil.utl.LogManager;
import commanutil.utl.StringUtil;


/**
 * Created by zhanglin on 5/24/16.
 */
public class BaseFragment extends Fragment {
    protected String name;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        if (bundle != null & bundle.size() > 0) {
            Object[] objects = new Object[bundle.size()];
            for (int i = 0; i < bundle.size(); i++) {
                String key = bundle.getString("key" + i);
                objects[i] = BaseApplication.mBaseContext.getFragmentCache(key);
            }
            setData(objects);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((BaseActivity) getActivity()).getSupportActionBar() != null && !StringUtil.isEmpty(getStringTag())) {
            ((BaseActivity) getActivity()).getSupportActionBar().setTitle(getStringTag());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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

    public void jumpToFragment(Class jumpClass, Class target) {
        jumpToFragment(jumpClass.getName(), target);
    }


    /**
     * open fragment in a new activity
     *
     * @param classpath
     */
    public void jumpToFragment(String classpath) {
        jumpToFragment(classpath, getContext(), NormalActivity.class);
    }

    public void jumpToFragment(String classpath, Class targetClass) {
        jumpToFragment(classpath, getContext(), targetClass);
    }

    public void jumpToFragment(String classpath, Object... object) {
        jumpToFragment(classpath, getContext(), object);
    }

    public void jumpToFragment(String classpath, Class target, Object... object) {
        jumpToFragment(classpath, getContext(), target, object);
    }

    public void jumpToFragment(Class jumpClass, Object... object) {
        jumpToFragment(jumpClass.getName(), getContext(), object);
    }

    public void jumpToFragment(Class jumpClass, Class targetClass, Object... object) {
        jumpToFragment(jumpClass.getName(), getContext(), targetClass, object);
    }


    public static void jumpToFragment(String classpath, Context context, Object... objects) {
        jumpToFragment(classpath, context, NormalActivity.class, objects);
    }


    public static void jumpToFragment(String classpath, Context context, Class targetClass, Object... objects) {
        Intent i = new Intent();
        i.putExtra("classname", classpath);
        i.setClass(context, targetClass);

        if (objects != null && objects.length > 0) {
            Bundle bundle = new Bundle();
            for (int j = 0; j < objects.length; j++) {
                Object object = objects[j];
                String key = object.hashCode() + "";
                BaseApplication.mBaseContext.setFragmentCache(key, object);
                bundle.putString("key" + j, key);
            }
            i.putExtra("bundle", bundle);
        }
        context.startActivity(i);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    public void changeFragment(BaseFragment fragment) {
        ((BaseActivity) getActivity()).changeFragment(fragment);
    }


    public void changeFragmentBack(BaseFragment fragment) {
        ((BaseActivity) getActivity()).changeFragmentBack(fragment);
    }

    public void setData(Object... objects) {
        throw new RuntimeException(" please overwrite this method!");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
