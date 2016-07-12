package de.sda.einkaufsliste.controller;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import de.sda.einkaufsliste.ListViewActivity;
import de.sda.einkaufsliste.MainActivity;
import de.sda.einkaufsliste.R;
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
                MainActivity.shoppingList.add(new Shopping(s.getProductName(), s.getShopName()));
            }

            _render(mainActivity);

        }catch(Exception e){
            Log.e("load", e.getMessage());
        }
    }

    public static boolean add(MainActivity mainActivity) {
        try{
            EditText product = (EditText)mainActivity.findViewById(R.id.txtProduct);
            EditText shop = (EditText)mainActivity.findViewById(R.id.txtShop);

            String productName = product.getEditableText().toString();
            String shopName = shop.getEditableText().toString();

            if (!productName.isEmpty() && !shopName.isEmpty()) {
                Shopping s = new Shopping(productName, shopName, false);

                MainActivity.shoppingOpenHelper.insert(s);

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
        }

    }

    protected static void _render(MainActivity mainActivity){
        mainActivity.listViewAdaptor.notifyDataSetChanged();
    }

}
