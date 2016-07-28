package de.sda.einkaufsliste;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import de.sda.einkaufsliste.controller.DBMgr;
import de.sda.einkaufsliste.model.Store;
import de.sda.einkaufsliste.utils.Edit;
import de.sda.einkaufsliste.utils.IThrRes;

/**
 * Created by Dmitry Sokolyuk on 20.07.2016.
 */
public class EditStoreActivity extends AppCompatActivity {

    private EditText edit_store_name;
    private EditText edit_store_address;
    private EditText edit_store_longitude;
    private EditText edit_store_latitude;
    private EditText edit_store_altitude;
    private TextInputLayout edit_store_nameWrapper;
    private TextInputLayout edit_store_addressWrapper;
    private TextInputLayout edit_store_longitudeWrapper;
    private TextInputLayout edit_store_latitudeWrapper;
    private TextInputLayout edit_store_altitudeWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_store);

        edit_store_name = (EditText)findViewById(R.id.edit_store_name);
        edit_store_address = (EditText)findViewById(R.id.edit_store_address);
        edit_store_longitude = (EditText)findViewById(R.id.edit_store_longitude);
        edit_store_latitude = (EditText)findViewById(R.id.edit_store_latitude);
        edit_store_altitude = (EditText)findViewById(R.id.edit_store_altitude);

        edit_store_nameWrapper = (TextInputLayout)findViewById(R.id.edit_store_nameWrapper);
        edit_store_addressWrapper = (TextInputLayout)findViewById(R.id.edit_store_addressWrapper);
        edit_store_longitudeWrapper = (TextInputLayout)findViewById(R.id.edit_store_longitudeWrapper);
        edit_store_latitudeWrapper = (TextInputLayout)findViewById(R.id.edit_store_latitudeWrapper);
        edit_store_altitudeWrapper = (TextInputLayout)findViewById(R.id.edit_store_altitudeWrapper);

        Store store = null;
        Bundle b = getIntent().getExtras();
        if (b != null) {
            long id = b.getLong("id");

            for(Store _s: MainActivity.getStores()){
                if (_s.getId() == id) {
                    store = _s;
                    break;
                }
            }

            if (store != null) {
                edit_store_name = (EditText)findViewById(R.id.edit_store_name);
                if (edit_store_name != null) {
                    edit_store_name.setText(store.getName());
                }

                edit_store_address = (EditText)findViewById(R.id.edit_store_address);
                if (edit_store_address != null) {
                    edit_store_address.setText(store.getAddress());
                }

                edit_store_longitude = (EditText)findViewById(R.id.edit_store_longitude);
                if (edit_store_longitude != null) {
                    edit_store_longitude.setText(String.valueOf(store.getLongitude()));
                }

                edit_store_latitude = (EditText)findViewById(R.id.edit_store_latitude);
                if (edit_store_latitude != null) {
                    edit_store_latitude.setText(String.valueOf(store.getLatitude()));
                }

                edit_store_altitude = (EditText)findViewById(R.id.edit_store_altitude);
                if (edit_store_altitude != null) {
                    edit_store_altitude.setText(String.valueOf(store.getAltitude()));
                }

            }
        }

        final Store lstore = store;

        ((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(v->{
            if (!validate()) return;

            if (lstore == null || lstore.getId() == null || lstore.getId() < 1) {
                //add
                DBMgr.addStoreThr(EditStoreActivity.this, new Store(edit_store_name.getText().toString(),
                        edit_store_address.getText().toString(),
                        Double.valueOf(edit_store_longitude.getText().toString()),
                        Double.valueOf(edit_store_latitude.getText().toString()),
                        Double.valueOf(edit_store_altitude.getText().toString())), new IThrRes() {
                    @Override
                    public void onDone() {
                        FragmentStores.mStoresListViewAdaptor.notifyDataSetChanged();
                        finish();
                    }

                    @Override
                    public void onError(String mess) {
                        MainActivity.showMess(EditStoreActivity.this, mess);
                    }
                });

            } else {
                lstore.setName(edit_store_name.getText().toString());
                lstore.setAddress(edit_store_address.getText().toString());
                lstore.setLongitude(Double.valueOf(edit_store_longitude.getText().toString()));
                lstore.setLatitude(Double.valueOf(edit_store_latitude.getText().toString()));
                lstore.setAltitude(Double.valueOf(edit_store_altitude.getText().toString()));

                //update
                DBMgr.updateStoreThr(EditStoreActivity.this, lstore, new IThrRes() {
                    @Override
                    public void onDone() {
                        FragmentStores.mStoresListViewAdaptor.notifyDataSetChanged();
                        finish();
                    }

                    @Override
                    public void onError(String mess) {
                        MainActivity.showMess(EditStoreActivity.this, mess);
                    }
                });
            }
        });
    }

    protected boolean validate() {
        if (Edit.validateText(this, edit_store_name, edit_store_nameWrapper) &
                Edit.validateText(this, edit_store_address, edit_store_addressWrapper) &
                Edit.validateDouble(this, edit_store_longitude, edit_store_longitudeWrapper) &
                Edit.validateDouble(this, edit_store_latitude, edit_store_latitudeWrapper) &
                Edit.validateDouble(this, edit_store_altitude, edit_store_altitudeWrapper)) {
            return true;
        }
        return false;
    }

}
