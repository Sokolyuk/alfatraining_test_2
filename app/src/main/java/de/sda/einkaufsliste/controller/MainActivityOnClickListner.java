package de.sda.einkaufsliste.controller;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.sda.einkaufsliste.MainActivity;
import de.sda.einkaufsliste.R;
import de.sda.einkaufsliste.model.Shopping;

/**
 * Created by SDA on 05.07.2016.
 */
public class MainActivityOnClickListner implements View.OnClickListener {
    private MainActivity mainActivity;
    private EditText txtProduct;
    private EditText txtShop;
    private EditText txtShoppingList;

    public MainActivityOnClickListner(MainActivity mainActivity){
        this.mainActivity = mainActivity;

        txtProduct = (EditText)mainActivity.findViewById(R.id.txtProduct);
        txtShop = (EditText)mainActivity.findViewById(R.id.txtShop);
        txtShoppingList = (EditText)mainActivity.findViewById(R.id.txtShoppingList);
    }

    @Override
    public void onClick(View v) {
        String productName = txtProduct.getEditableText().toString();
        String shopName = txtShop.getEditableText().toString();

        if (!productName.isEmpty() && !shopName.isEmpty()) {
            Shopping shopping = new Shopping(productName, shopName);
            MainActivity.shoppingList.add(shopping);

            String output = "";

            for (Shopping s: MainActivity.shoppingList) {
                output += "\n" + s.toString();
            }
            txtShoppingList.setText(output);

            txtProduct.setText("");
            txtShop.setText("");
            Toast.makeText(mainActivity, "Done", Toast.LENGTH_SHORT);
            Log.i("Inf", "Done.");
        } else {
            Toast.makeText(mainActivity, "Bitte alle Felder ausfuellen", Toast.LENGTH_LONG);
        }
    }
}
