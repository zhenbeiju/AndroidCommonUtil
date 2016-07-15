package commanutil.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.zhenbeiju.commanutil.R;

import lib.SwipeBackLayout;
import lib.app.SwipeBackActivity;


/**
 * Created by zhanglin on 5/23/16.
 */
public class BaseActivity extends SwipeBackActivity {
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private SwipeBackLayout mSwipeBackLayout;
    private BaseFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSwipeBackEnable(false);

        fragmentManager = getSupportFragmentManager();
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setScrimColor(Color.TRANSPARENT);
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.mipmap.ic_menu_backup);
        }
    }


    public void changeFragment(BaseFragment frag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, frag);
        fragmentTransaction.commitAllowingStateLoss();
        currentFragment = frag;
    }

    public void changeFragment(Fragment frag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, frag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * if want back ,try call activity's onBackPressed();
     *
     * @param frag
     */
    public void changeFragmentBack(BaseFragment frag) {

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, frag);
        fragmentTransaction.addToBackStack(frag.toString());
        fragmentTransaction.commitAllowingStateLoss();
        currentFragment = frag;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (currentFragment != null && currentFragment.onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
