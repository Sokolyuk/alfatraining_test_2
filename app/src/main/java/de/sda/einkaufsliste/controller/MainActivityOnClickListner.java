package de.sda.einkaufsliste.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import java.util.List;

import de.sda.einkaufsliste.EditActivity;
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

    public static boolean add(MainActivity mainActivity) {
        try{
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
        }

    }

    protected static void _render(MainActivity mainActivity){
        mainActivity.listViewAdaptor.notifyDataSetChanged();
    }

    public static void edit(Context c, Shopping s) {
        Intent i = new Intent(c, EditActivity.class);
        i.putExtra("id", s.getId());
        c.startActivity(i);
    }

    public static void update(Context c, Shopping s) {
        try {
            MainActivity.shoppingOpenHelper.update(s);
            MainActivity.listViewAdaptor.notifyDataSetChanged();
        }catch(Exception e) {
            e.printStackTrace();
            MainActivity.showMess(c, e.getMessage());
        }
    }

    public static void delete(Shopping s) {
        MainActivity.shoppingOpenHelper.delete(s);
        MainActivity.shoppingList.remove(s);
        MainActivity.listViewAdaptor.notifyDataSetInvalidated();

    }
}
