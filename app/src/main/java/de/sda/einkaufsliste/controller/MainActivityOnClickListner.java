package de.sda.einkaufsliste.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import de.sda.einkaufsliste.EditProductActivity;
import de.sda.einkaufsliste.model.Product;
import de.sda.einkaufsliste.utils.IThrRes;
import de.sda.einkaufsliste.MainActivity;

/**
 * Created by Dmitry Sokolyuk on 05.07.2016.
 */
public class MainActivityOnClickListner {

    public static List<Product> load(MainActivity mainActivity) {
        try{
            return MainActivity.mOpenHelper.shoppingSelect();
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
//sda+            mainActivity.productLayout.setError(mainActivity.getString(R.string.err_Product_invalidate));
//sda+            mainActivity.product.requestFocus();
            res = false;
        } else {
//sda+            mainActivity.productLayout.setErrorEnabled(false);
        }

        if (!validateShop(shopName)) {
//sda+            mainActivity.shopSpinner.setError(mainActivity.getString(R.string.err_Shop_invalidate));
            res = false;
        }

        return res;
    }


    public static boolean addThr(MainActivity mainActivity, IThrRes i) {

//sda+        String productName = mainActivity.product.getEditableText().toString();
//sda+        String shopName = mainActivity.shopSpinner.getText().toString();

//sda+        if (!validateInputAdd(mainActivity, productName, shopName)) return false;



/*        long store_id = ?;

        if (!productName.isEmpty() && !shopName.isEmpty()) {
            Product s = new Product(-1, productName, shopName, store_id, false);

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
//sda+        mainActivity.listViewAdaptor.notifyDataSetChanged();
    }

    public static void edit(Context c, Product s, int resCode) {
        Intent i = new Intent(c, EditProductActivity.class);
        i.putExtra("id", s.getId());
        ((Activity)c).startActivityForResult(i, resCode);
    }

    public static void updateThr(Context c, IThrRes i, Product s) {
            new Thread(()->{
                try {
                    MainActivity.mOpenHelper.shoppingUpdate(s);
                    if (i != null) ((Activity)c).runOnUiThread(()->{i.isDone();});
                }catch(Exception e) {
                    e.printStackTrace();
                    if (i != null) ((Activity)c).runOnUiThread(()->{i.isError(e.getMessage());});
                }
            }).start();
    }

    public static void deleteThr(Context c, Product s) {
        //sda+((MainActivity)c).listView.startAnimation(AnimationUtils.loadAnimation(c, R.anim.bounce));
        new Thread(()->{
            MainActivity.mOpenHelper.shoppingDelete(s);
        }).start();
        MainActivity.mProducts.remove(s);
//sda+        MainActivity.listViewAdaptor.notifyDataSetInvalidated();

    }



}
