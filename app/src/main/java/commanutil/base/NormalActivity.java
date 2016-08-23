package commanutil.base;

import android.os.Bundle;
import android.view.MenuItem;

import com.zhenbeiju.commanutil.R;

/**
 * need add this activity to manifest
 * Created by zhanglin on 5/23/16.
 */
public class NormalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    protected void onCreate(Bundle savedInstanceState, boolean useDefaultLayout) {
        super.onCreate(savedInstanceState);
        if (useDefaultLayout) {
            setContentView(R.layout.main);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setSwipeBackEnable(true);
        String classname = getIntent().getStringExtra("classname");
        boolean hasobj = getIntent().getBooleanExtra("HasObj", false);
        Object data = null;
        Object data2 = null;
        if (hasobj) {
            String key = getIntent().getStringExtra("key");
            data = BaseApplication.mBaseContext.getGlobalObject(key);
            BaseApplication.mBaseContext.setGlobalObject(key, null);

            if (getIntent().hasExtra("key2")) {
                String key2 = getIntent().getStringExtra("key2");
                data2 = BaseApplication.mBaseContext.getGlobalObject(key2);
                BaseApplication.mBaseContext.setGlobalObject(key2, null);
            }
        }

        try {
            Object object = Class.forName(classname).newInstance();
            BaseFragment fragment = (BaseFragment) object;
            if (data != null || data2 != null) {
                if (data2 != null) {
                    fragment.setData(data, data2);
                } else {
                    fragment.setData(data);
                }
            }
            changeFragment(fragment);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
