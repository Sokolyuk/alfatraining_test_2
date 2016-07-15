package de.sda.einkaufsliste;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;

import de.sda.einkaufsliste.controller.MainActivityOnClickListner;
import de.sda.einkaufsliste.model.Shopping;
import de.sda.einkaufsliste.utils.IThrRes;

/**
 * Created by Alfa on cat_12.07.2016.
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
            MainActivityOnClickListner.updateThr(this, new IThrRes() {
                @Override
                public void isDone() {
                    MainActivity.listViewAdaptor.notifyDataSetChanged();
                    EditActivity.this.finish();
                }

                @Override
                public void isError(String mess) {
                    ((View)findViewById(R.id.edtLayout)).startAnimation(AnimationUtils.loadAnimation(EditActivity.this, R.anim.bounce));
                    MainActivity.showMess(EditActivity.this, mess);
                }
            }, _shopping);
            ((View)findViewById(R.id.edtLayout)).startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotation));
        });

    }

}
