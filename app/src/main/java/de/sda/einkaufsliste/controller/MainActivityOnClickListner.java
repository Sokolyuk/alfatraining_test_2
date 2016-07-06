package de.sda.einkaufsliste.controller;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

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

        switch(v.getId()) {
            case R.id.btnAddShopping:
                addShopping();
                break;
            case R.id.btnClearShopping:
                clearShopping();
                break;
            case R.id.btnSaveShopping:
                saveShopping();
                break;
            default:
                mainActivity.showMess("Unexpected caller. Oops.");
        }
    }

    private void saveShopping() {
        try{
            try(FileOutputStream fos = mainActivity.openFileOutput("shoppings.txt", MainActivity.MODE_ENABLE_WRITE_AHEAD_LOGGING);) {

                fos.write(MainActivity.shoppingList.toString().getBytes());
                fos.flush();
            }
            mainActivity.showMess("Saved.");

            try(FileInputStream fi = mainActivity.openFileInput("shoppings.txt");) {
                byte[] b = new byte[100];
                String res = "";
                int n = 0;
                while ((n = fi.read(b)) != -1) {
                    res += new String(b, 0, n);
                }
                mainActivity.showMess(res);
                System.out.println(res);
            }

        }catch(Exception e){
            mainActivity.showMess("Error occured: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearShopping() {
        MainActivity.shoppingList.clear();
        txtShoppingList.setText("");
        mainActivity.showMess("Cleared");
    }

    protected void addShopping() {
        String productName = txtProduct.getEditableText().toString();
        String shopName = txtShop.getEditableText().toString();

        Toast toast;

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
            toast = Toast.makeText(mainActivity.getApplicationContext(), "Done", Toast.LENGTH_SHORT);
            Log.i("Inf", "Done.");
        } else {
            toast = Toast.makeText(mainActivity.getApplicationContext(), "Bitte alle Felder ausfuellen", Toast.LENGTH_LONG);
        }
        toast.setGravity(Gravity.TOP| Gravity.CENTER, 10, 0);
        toast.show();
    }

}
