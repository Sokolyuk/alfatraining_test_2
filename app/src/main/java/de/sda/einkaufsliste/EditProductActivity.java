package de.sda.einkaufsliste;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import de.sda.einkaufsliste.controller.DBMgr;
import de.sda.einkaufsliste.model.Product;
import de.sda.einkaufsliste.utils.Edit;
import de.sda.einkaufsliste.utils.IThrRes;

/**
 * Created by Alfa on cat_12.07.2016.
 */
public class EditProductActivity extends AppCompatActivity {

    private EditText edit_product_name;
    private EditText edit_product_store;
    private TextInputLayout edit_product_nameWrapper;
    private TextInputLayout edit_product_storeWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        edit_product_name = (EditText)findViewById(R.id.edit_product_name);
        edit_product_store = (EditText)findViewById(R.id.edit_product_store);

        edit_product_nameWrapper = (TextInputLayout)findViewById(R.id.edit_product_nameWrapper);
        edit_product_storeWrapper = (TextInputLayout)findViewById(R.id.edit_product_storeWrapper);

        Product product = null;
        Bundle b = getIntent().getExtras();

        if (b != null) {
            long id = b.getLong("id");

            for(Product _s: MainActivity.mProducts){
                if (_s.getId() == id) {
                    product = _s;
                    break;
                }
            }

            if (product != null) {
                if (edit_product_name != null) {
                    edit_product_name.setText(product.getName());
                }

                if (edit_product_store != null) {
                    edit_product_store.setText(product.getStore_name());
                }
            }
        }

        final Product lproduct = product;

        ((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(v->{
            if (!validate()) return;

            if (lproduct == null || lproduct.getId() == null || lproduct.getId() < 1) {
                //add
                DBMgr.addProductThr(EditProductActivity.this, new Product(edit_product_name.getText().toString(),
                        edit_product_store.getText().toString(),
                        null,
                        false), new IThrRes() {
                    @Override
                    public void onDone() {
                        FragmentProducts.mProductListViewAdaptor.notifyDataSetChanged();
                        finish();
                    }

                    @Override
                    public void onError(String mess) {
                        MainActivity.showMess(EditProductActivity.this, mess);
                    }
                });

            } else {
                lproduct.setName(edit_product_name.getText().toString());
                lproduct.setStore_name(edit_product_store.getText().toString());
                lproduct.setStore_id(null);
                lproduct.setDone(false);

                //update
                DBMgr.updateProductThr(EditProductActivity.this, lproduct, new IThrRes() {
                    @Override
                    public void onDone() {
                        FragmentProducts.mProductListViewAdaptor.notifyDataSetChanged();
                        finish();
                    }

                    @Override
                    public void onError(String mess) {
                        MainActivity.showMess(EditProductActivity.this, mess);
                    }
                });
            }
        });

    }

    protected boolean validate() {
        if (Edit.validateText(this, edit_product_name, edit_product_nameWrapper) &
                Edit.validateText(this, edit_product_store, edit_product_storeWrapper)) {
            return true;
        }
        return false;
    }



}
