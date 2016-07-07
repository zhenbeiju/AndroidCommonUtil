package commanutil.base;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;


/**
 * Created by zhanglin on 5/24/16.
 */
public class BaseFragment extends Fragment {


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


    public static void jumpToFragment(String classpath, Object object, Context context) {
        Intent i = new Intent();
        i.putExtra("classname", classpath);
        i.setClass(context, NormalActivity.class);
        if (object != null) {
            i.putExtra("HasObj", true);
            String key = System.currentTimeMillis() + "";
            i.putExtra("key", key);
            MyApplication.mBaseContext.setGlobalObject(key, object);
        }
        context.startActivity(i);
    }


    public void changeFragment(Fragment fragment) {

        ((BaseActivity) getActivity()).changeFragment(fragment);
    }


    public void changeFragmentBack(Fragment fragment) {
        ((BaseActivity) getActivity()).changeFragmentBack(fragment);
    }

    public void setData(Object object) {
        throw new RuntimeException(" please overwrite this method!");
    }
}
