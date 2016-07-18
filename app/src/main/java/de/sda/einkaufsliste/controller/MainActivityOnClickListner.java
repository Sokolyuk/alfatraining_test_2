package de.sda.einkaufsliste.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.List;

import de.sda.einkaufsliste.EditActivity;
import de.sda.einkaufsliste.utils.IThrRes;
import de.sda.einkaufsliste.MainActivity;
import de.sda.einkaufsliste.R;
import de.sda.einkaufsliste.model.Shopping;

/**
 * Created by Dmitry Sokolyuk on 05.07.2016.
 */
public class MainActivityOnClickListner {

    public static List<Shopping> load(MainActivity mainActivity) {
        try{
            return MainActivity.dbOpenHelper.shoppingSelect();
        }catch(Exception e){
            e.printStackTrace();
            mainActivity.showMess(e.getMessage());
            return null;
        }
    }

    protected static boolean validateProduct(String product) {
        return product != null && !product.isEmpty();
    }

    protected static boolean validateShop(String shopName) {
        return shopName != null && !shopName.isEmpty();
    }

    public static boolean validateInputAdd(MainActivity mainActivity, String productName, String shopName) {
        boolean res = true;
        if (!validateProduct(productName)) {
            mainActivity.productLayout.setError(mainActivity.getString(R.string.err_Product_invalidate));
            mainActivity.product.requestFocus();
            res = false;
        } else {
            mainActivity.productLayout.setErrorEnabled(false);
        }

        if (!validateShop(shopName)) {
            mainActivity.shopSpinner.setError(mainActivity.getString(R.string.err_Shop_invalidate));
            res = false;
        }

        return res;
    }


    public static boolean addThr(MainActivity mainActivity, IThrRes i) {

        String productName = mainActivity.product.getEditableText().toString();
        String shopName = mainActivity.shopSpinner.getText().toString();

        if (!validateInputAdd(mainActivity, productName, shopName)) return false;



/*        long store_id = ?;

        if (!productName.isEmpty() && !shopName.isEmpty()) {
            Shopping s = new Shopping(-1, productName, shopName, store_id, false);

            new Thread(()->{
                try {
                    MainActivity.dbOpenHelper.shoppingInsert(s);
                    ((Activity)mainActivity).runOnUiThread(()->{
                        mainActivity.shoppingList.add(s);
                        _render(mainActivity);
                        product.setText("");
                        shop.setText("");
                        if (i != null) i.isDone();
                    });
                }catch(Exception e) {
                    e.printStackTrace();
                    if (i != null) ((Activity)mainActivity).runOnUiThread(()->{i.isError(e.getMessage());});
                }
            }).start();

        } else {*/
            mainActivity.showMess("Bitte alle Felder ausfuellen");
//        }
        return true;
    }

    protected static void _render(MainActivity mainActivity){
        mainActivity.listViewAdaptor.notifyDataSetChanged();
    }

    public static void edit(Context c, Shopping s, int resCode) {
        Intent i = new Intent(c, EditActivity.class);
        i.putExtra("id", s.getId());
        ((Activity)c).startActivityForResult(i, resCode);
    }

    public static void updateThr(Context c, IThrRes i, Shopping s) {
            new Thread(()->{
                try {
                    MainActivity.dbOpenHelper.shoppingUpdate(s);
                    if (i != null) ((Activity)c).runOnUiThread(()->{i.isDone();});
                }catch(Exception e) {
                    e.printStackTrace();
                    if (i != null) ((Activity)c).runOnUiThread(()->{i.isError(e.getMessage());});
                }
            }).start();
    }

    public static void deleteThr(Context c, Shopping s) {
        ((MainActivity)c).listView.startAnimation(AnimationUtils.loadAnimation(c, R.anim.bounce));
        new Thread(()->{
            MainActivity.dbOpenHelper.shoppingDelete(s);
        }).start();
        MainActivity.shoppingList.remove(s);
        MainActivity.listViewAdaptor.notifyDataSetInvalidated();

    }



}
