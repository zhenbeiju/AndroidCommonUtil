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

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setSwipeBackEnable(true);
        String classname = getIntent().getStringExtra("classname");
        boolean hasobj = getIntent().getBooleanExtra("HasObj", false);
        Object data = null;
        if (hasobj) {
            String key = getIntent().getStringExtra("key");
            data = MyApplication.mBaseContext.getGlobalObject(key);
            MyApplication.mBaseContext.setGlobalObject(key, null);
        }

        try {
            Object object = Class.forName(classname).newInstance();
            BaseFragment fragment = (BaseFragment) object;
            if (data != null) {
                fragment.setData(data);
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
