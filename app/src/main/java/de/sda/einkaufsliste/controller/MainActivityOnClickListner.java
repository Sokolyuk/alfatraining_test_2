package de.sda.einkaufsliste.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import java.util.List;

import de.sda.einkaufsliste.EditActivity;
import de.sda.einkaufsliste.IThrRes;
import de.sda.einkaufsliste.MainActivity;
import de.sda.einkaufsliste.R;
import de.sda.einkaufsliste.ShoppingOpenHelper;
import de.sda.einkaufsliste.model.Shopping;

/**
 * Created by SDA on 05.07.2016.
 */
public class MainActivityOnClickListner {

    public static void load(MainActivity mainActivity) {
        try{
            List<Shopping> shoppings = MainActivity.shoppingOpenHelper.select();

            MainActivity.shoppingList.clear();

            for(Shopping s: shoppings){
                MainActivity.shoppingList.add(new Shopping(s.getId(), s.getProductName(), s.getShopName(), s.isDone()));
            }

            _render(mainActivity);

        }catch(Exception e){
            Log.e("load", e.getMessage());
        }
    }

    public static void addThr(MainActivity mainActivity, IThrRes i) {
        EditText product = (EditText)mainActivity.findViewById(R.id.txtProduct);
        EditText shop = (EditText)mainActivity.findViewById(R.id.txtShop);

        String productName = product.getEditableText().toString();
        String shopName = shop.getEditableText().toString();

        if (!productName.isEmpty() && !shopName.isEmpty()) {
            Shopping s = new Shopping(-1, productName, shopName, false);

            new Thread(()->{
                try {
                    MainActivity.shoppingOpenHelper.insert(s);
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

        } else {
            mainActivity.showMess("Bitte alle Felder ausfuellen");
        }


/*        try{
            EditText product = (EditText)mainActivity.findViewById(R.id.txtProduct);
            EditText shop = (EditText)mainActivity.findViewById(R.id.txtShop);

            String productName = product.getEditableText().toString();
            String shopName = shop.getEditableText().toString();

            if (!productName.isEmpty() && !shopName.isEmpty()) {
                Shopping s = new Shopping(-1, productName, shopName, false);

                s.setId(MainActivity.shoppingOpenHelper.insert(s));

                mainActivity.shoppingList.add(s);

                _render(mainActivity);

                product.setText("");
                shop.setText("");

            } else {
                mainActivity.showMess("Bitte alle Felder ausfuellen");
            }

            return true;
        }catch(Exception e){
            mainActivity.showMess(e.getMessage());
            Log.e("saveShopping", e.getMessage());
            return false;
        }*/
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
                    MainActivity.shoppingOpenHelper.update(s);
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
            MainActivity.shoppingOpenHelper.delete(s);
        }).start();
        MainActivity.shoppingList.remove(s);
        MainActivity.listViewAdaptor.notifyDataSetInvalidated();

    }



}
