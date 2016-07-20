package de.sda.einkaufsliste;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import de.sda.einkaufsliste.model.Product;

/**
 * Created by Alfa on cat_12.07.2016.
 */
public class EditProductActivity extends AppCompatActivity {

    private EditText product;
    private EditText shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        Long id = getIntent().getExtras().getLong("id");

        Product product = null;
        for(Product _s: MainActivity.mProducts){
            if (_s.getId() == id) {
                product = _s;
                break;
            }
        }

        if (product != null) {
            this.product = (EditText)findViewById(R.id.txt_edt_Product);
            if (this.product != null) {
                this.product.setText(product.getName());
            }

            shop = (EditText)findViewById(R.id.txt_edt_Shop);
            if (shop != null) {
                shop.setText(product.getStore_name());
            }
        }

        final Product _product = product;


        /*

        ((ImageButton)findViewById(R.id.btn_edt_AddShopping)).setOnClickListener(v->{
            _product.setName(product.getText().toString());
            _product.setStore_name(shop.getText().toString());
            MainActivityOnClickListner.updateThr(this, new IThrRes() {
                @Override
                public void isDone() {
//sda+                    MainActivity.listViewAdaptor.notifyDataSetChanged();
                    EditProductActivity.this.finish();
                }

                @Override
                public void isError(String mess) {
                    ((View)findViewById(R.id.edtLayout)).startAnimation(AnimationUtils.loadAnimation(EditProductActivity.this, R.anim.bounce));
                    MainActivity.showMess(EditProductActivity.this, mess);
                }
            }, _product);
            ((View)findViewById(R.id.edtLayout)).startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotation));
        });

        */

    }

}
