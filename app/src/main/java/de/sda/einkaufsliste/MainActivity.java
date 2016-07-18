package de.sda.einkaufsliste;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import de.sda.einkaufsliste.positive.RecyclerViewActivity;
import de.sda.einkaufsliste.utils.IThrRes;

/**
 * Created by Dmitry Sokolyuk on 05.07.2016.
 */
public class MainActivity extends AppCompatActivity {
    public static List<Shopping> shoppingList = new ArrayList<>();
    public static List<Shopping> stores = new ArrayList<>();
    public static DBOpenHelper dbOpenHelper;
    public static ListViewAdaptor listViewAdaptor;
    public static ListView listView;

    public static EditText product;
    public static TextInputLayout productLayout;
    public static MaterialBetterSpinner shopSpinner;


String[] SPINNERLIST = {"Android Material Design", "Material Design Spinner", "Spinner Using Material Library", "Material Spinner Example"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        listViewAdaptor = new ListViewAdaptor(this);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(listViewAdaptor);
        registerForContextMenu(listView);

        ((ImageButton)findViewById(R.id.btnAddShopping)).requestFocus();

        dbOpenHelper = new DBOpenHelper(this);
        shoppingList = MainActivityOnClickListner.load(this);
        this.listView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));

        /*View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }*/

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)findViewById(R.id.shop);
        materialDesignSpinner.setAdapter(adapter);


        //Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);

//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //mDrawerList = (ListView) findViewById(R.id.drawer_layout);

        // Set the adapter for the list view
//        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, SPINNERLIST));
        // Set the list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());



    }

    //private DrawerLayout mDrawerLayout;
    //private ListView mDrawerList;

    @Override
    protected void onDestroy() {
        dbOpenHelper.close();
        dbOpenHelper = null;
        if (shoppingList != null) {
            shoppingList.clear();
            shoppingList = null;
        }
        if (stores != null) {
            stores.clear();
            stores = null;
        }
        super.onDestroy();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        ListView lv = (ListView) v;
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Shopping s = (Shopping) lv.getItemAtPosition(acmi.position);

        menu.setHeaderTitle(String.format("'%s'", s.getProductName()));
        menu.add(0, v.getId(), 1, "Edit");
        menu.add(0, v.getId(), 2, "Delete");
        menu.add(0, v.getId(), 3, "Positive");

//((AdapterView.AdapterContextMenuInfo) menuInfo).targetView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotation));


    }

    @Override
    public void openContextMenu(View view) {
        super.openContextMenu(view);
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Shopping s = (Shopping)((ListView)findViewById(R.id.listView)).getItemAtPosition(info.position);

        switch (item.getOrder()){
            case 1:
                MainActivityOnClickListner.edit(this, s, 1);
                break;
            case 2:
                MainActivityOnClickListner.deleteThr(this, s);
                break;
            case 3:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
            default:
        }


        return super.onContextItemSelected(item);
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
                this.listView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));
                break;
            default:
        }
    }
}
