package commanutil.base;

import android.os.Bundle;
import android.view.MenuItem;

import com.zhenbeiju.commanutil.R;

import commanutil.utl.LogManager;

import static android.R.attr.data;

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
        boolean hasobj = getIntent().hasExtra("bundle");

        try {
            Object object = Class.forName(classname).newInstance();
            BaseFragment fragment = (BaseFragment) object;
            if (hasobj) {
                Bundle bundle = getIntent().getBundleExtra("bundle");
                LogManager.e(bundle.size()+"");
                fragment.setArguments(bundle);
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
