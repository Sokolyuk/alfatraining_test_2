package de.sda.einkaufsliste;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import de.sda.einkaufsliste.model.DBOpenHelper;
import de.sda.einkaufsliste.model.Product;
import de.sda.einkaufsliste.model.Store;
import de.sda.einkaufsliste.service.FileService;

/**
 * Created by Dmitry Sokolyuk on 05.07.2016.
 */
public class MainActivity extends AppCompatActivity {

    //region app data
    private static List<Product> mProducts = new ArrayList<>();
    private static List<Store> mStores = new ArrayList<>();
    private static DBOpenHelper mOpenHelper;
    //endregion
    //region navigation
    private DrawerLayout mDrawerLayout;
    private static ViewPager mViewPager;
    //endregion
    private FileService mService;
    private static LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region init navigation
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        //endregion

        //region app data init & load
        mOpenHelper = new DBOpenHelper(this);
        mProducts = mOpenHelper.productsSelect();
        mStores = mOpenHelper.storesSelect();
        //this.listView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));
        //endregion

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(v->{
            switch (mViewPager.getCurrentItem()){
                case tabProducts:
                    startActivity(new Intent(this, EditProductActivity.class));
                    break;
                case tabStores:
                    startActivity(new Intent(this, EditStoreActivity.class));
                    break;
                default:
            }
        });

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

    }

    @Override
    protected void onDestroy() {
        if (mOpenHelper != null) {
            mOpenHelper.close();
            mOpenHelper = null;
        }
        if (mProducts != null) {
            mProducts.clear();
            mProducts = null;
        }
        if (mStores != null) {
            mStores.clear();
            mStores = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, FileService.class);
        bindService(intent, mFileServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mFileServiceConnection);
            mBound = false;
        }
    }

    boolean mBound = false;
    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mFileServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            FileService.FileServiceBinder binder = (FileService.FileServiceBinder) service;
            mService = binder.getService();
            mBound = true;
//!!!
Log.d(MainActivity.class.toString(), "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
//!!!
Log.d(MainActivity.class.toString(), "onServiceDisconnected");
        }
    };

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(i->{
            i.setChecked(true);
            mDrawerLayout.closeDrawers();
            switch(i.getItemId()){
                case R.id.mn_home:
                    mViewPager.setCurrentItem(tabHome);
                    break;
                case R.id.mn_products:
                    mViewPager.setCurrentItem(tabProducts);
                    break;
                case R.id.mn_stores:
                    mViewPager.setCurrentItem(tabStores);
                    break;
                case R.id.mn_statistic:
                    mViewPager.setCurrentItem(tabStatistic);
                    break;
                case R.id.mn_about:
                    startActivity(new Intent(this, AboutActivity.class));
                    break;
                default:
            }
            return true;
        });
    }

    public final int tabHome = 0;
    public final int tabProducts = 1;
    public final int tabStores = 2;
    public final int tabStatistic = 3;

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentHome(), "Home");
        adapter.addFragment(new FragmentProducts(), "Products");
        adapter.addFragment(new FragmentStores(), "Stores");
        adapter.addFragment(new FragmentStatistics(), "Statistics");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    //fix bug v.23 - https://code.google.com/p/android/issues/detail?id=183166
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void showMess(Context context, String mess) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)((Activity)context).findViewById(R.id.toast_layout_root));
        TextView text = (TextView)layout.findViewById(R.id.text);
        text.setText(mess);
        Toast toast = new Toast(((Activity)context).getApplicationContext());
        toast.setGravity(Gravity.RIGHT | Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void showMess(String mess) {
        showMess(this, mess);
    }

    public static LocationManager getLocationManager() {
        return mLocationManager;
    }

    public static List<Product> getProducts() {
        return mProducts;
    }

    public static List<Store> getStores() {
        return mStores;
    }

    public static DBOpenHelper getOpenHelper() {
        return mOpenHelper;
    }

    public static ViewPager getViewPager() {
        return mViewPager;
    }

}
