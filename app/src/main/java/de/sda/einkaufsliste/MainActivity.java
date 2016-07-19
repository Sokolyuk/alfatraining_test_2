package de.sda.einkaufsliste;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import de.sda.einkaufsliste.controller.MainActivityOnClickListner;
import de.sda.einkaufsliste.model.DBOpenHelper;
import de.sda.einkaufsliste.model.Shopping;
import de.sda.einkaufsliste.model.Store;
import de.sda.einkaufsliste.positive.RecyclerViewActivity;
import de.sda.einkaufsliste.utils.IThrRes;

/**
 * Created by Dmitry Sokolyuk on 05.07.2016.
 */
public class MainActivity extends AppCompatActivity {

    //public static EditText product;
    //public static TextInputLayout productLayout;
    //public static MaterialBetterSpinner shopSpinner;

//String[] SPINNERLIST = {"Android Material Design", "Material Design Spinner", "Spinner Using Material Library", "Material Spinner Example"};



    //region app data
    public static List<Shopping> mProducts = new ArrayList<>();
    public static List<Store> mStores = new ArrayList<>();
    public static DBOpenHelper mOpenHelper;
    //endregion
    //region navigation
    private DrawerLayout mDrawerLayout;
    public static ViewPager mViewPager;
    //endregion

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
        mProducts = MainActivityOnClickListner.load(this);
        //this.listView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));
        //endregion

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

/*
        product = (EditText)findViewById(R.id.txtProduct);
        productLayout = (TextInputLayout)findViewById(R.id.productLayout);
        shopSpinner = (MaterialBetterSpinner)findViewById(R.id.shop);


        ((ImageButton)findViewById(R.id.btnAddShopping)).setOnClickListener(v->{
            if (MainActivityOnClickListner.addThr(MainActivity.this, new IThrRes() {
                @Override
                public void isDone() {
                    ((View)v).clearAnimation();
                }

                @Override
                public void isError(String mess) {
                    showMess(mess);
                    ((View)v).startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.bounce));
                }
            })) {
                ((View)v).startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.rotation));
            }

        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)findViewById(R.id.shop);
        materialDesignSpinner.setAdapter(adapter);
*/

        mProducts = new ArrayList<>();
        mProducts.add(new Shopping(1, "1", "11", 111, true));
        mProducts.add(new Shopping(1, "1", "11", 111, true));
        mProducts.add(new Shopping(1, "1", "11", 111, true));
        mProducts.add(new Shopping(1, "1", "11", 111, true));
        mProducts.add(new Shopping(1, "1", "11", 111, true));
        mProducts.add(new Shopping(1, "1", "11", 111, true));
        mProducts.add(new Shopping(1, "1", "11", 111, true));
        mProducts.add(new Shopping(1, "1", "11", 111, true));
        mProducts.add(new Shopping(1, "1", "11", 111, true));
        mProducts.add(new Shopping(1, "1", "11", 111, true));
        mProducts.add(new Shopping(1, "1", "11", 111, true));
        mProducts.add(new Shopping(1, "1", "11", 111, true));
        mProducts.add(new Shopping(1, "1", "11", 111, true));
        mProducts.add(new Shopping(1, "1", "11", 111, true));
        mProducts.add(new Shopping(1, "1", "11", 111, true));
        mProducts.add(new Shopping(1, "1", "11", 111, true));
        mProducts.add(new Shopping(1, "1", "11", 111, true));
        mProducts.add(new Shopping(1, "1", "11", 111, true));
        mProducts.add(new Shopping(1, "1", "11", 111, true));


        mStores = new ArrayList<>();
        mStores.add(new Store(2, "2", "222", -1, -2));
        mStores.add(new Store(2, "2", "222", -1, -2));
        mStores.add(new Store(2, "2", "222", -1, -2));
        mStores.add(new Store(2, "2", "222", -1, -2));
        mStores.add(new Store(2, "2", "222", -1, -2));
        mStores.add(new Store(2, "2", "222", -1, -2));
        mStores.add(new Store(2, "2", "222", -1, -2));
        mStores.add(new Store(2, "2", "222", -1, -2));
        mStores.add(new Store(2, "2", "222", -1, -2));
        mStores.add(new Store(2, "2", "222", -1, -2));
        mStores.add(new Store(2, "2", "222", -1, -2));
        mStores.add(new Store(2, "2", "222", -1, -2));
        mStores.add(new Store(2, "2", "222", -1, -2));
        mStores.add(new Store(2, "2", "222", -1, -2));
        mStores.add(new Store(2, "2", "222", -1, -2));
        mStores.add(new Store(2, "2", "222", -1, -2));
        mStores.add(new Store(2, "2", "222", -1, -2));

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

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(i->{
            i.setChecked(true);
            mDrawerLayout.closeDrawers();
            switch(i.getItemId()){
                case R.id.mn_home:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.mn_products:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.mn_stores:
                    mViewPager.setCurrentItem(2);
                    break;
                case R.id.mn_statistic:
                    mViewPager.setCurrentItem(3);
                    break;
                default:
            }
            return true;
        });
    }

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


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(resultCode){
            case 1:
//sda+                this.listView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));
                break;
            default:
        }
    }
}
