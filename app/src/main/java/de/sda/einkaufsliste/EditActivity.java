package de.sda.einkaufsliste;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import de.sda.einkaufsliste.controller.MainActivityOnClickListner;
import de.sda.einkaufsliste.model.Shopping;

/**
 * Created by Alfa on 13.07.2016.
 */
public class EditActivity extends AppCompatActivity {
    private EditText product;
    private EditText shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Long id = getIntent().getExtras().getLong("id");

        Shopping shopping = null;
        for(Shopping _s: MainActivity.shoppingList){
            if (_s.getId() == id) {
                shopping = _s;
                break;
            }
        }

        if (shopping != null) {
            product = (EditText)findViewById(R.id.txt_edt_Product);
            if (product != null) {
                product.setText(shopping.getProductName());
            }

            shop = (EditText)findViewById(R.id.txt_edt_Shop);
            if (shop != null) {
                shop.setText(shopping.getShopName());
            }
        }

        final Shopping _shopping = shopping;


        ((ImageButton)findViewById(R.id.btn_edt_AddShopping)).setOnClickListener(v->{
            _shopping.setProductName(product.getText().toString());
            _shopping.setShopName(shop.getText().toString());
            MainActivityOnClickListner.update(this, _shopping);
            MainActivity.listViewAdaptor.notifyDataSetChanged();
            EditActivity.this.finish();
        });

    }


}
