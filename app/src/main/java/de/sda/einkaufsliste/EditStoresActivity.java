package de.sda.einkaufsliste;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import de.sda.einkaufsliste.controller.DBMgr;
import de.sda.einkaufsliste.model.Store;
import de.sda.einkaufsliste.utils.IThrRes;

/**
 * Created by Dmitry Sokolyuk on 20.07.2016.
 */
public class EditStoresActivity extends AppCompatActivity {

    private EditText edit_store_name;
    private EditText edit_store_address;
    private EditText edit_store_longitude;
    private EditText edit_store_latitude;
    private EditText edit_store_altitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_store);

        edit_store_name = (EditText)findViewById(R.id.edit_store_name);
        edit_store_address = (EditText)findViewById(R.id.edit_store_address);
        edit_store_longitude = (EditText)findViewById(R.id.edit_store_longitude);
        edit_store_latitude = (EditText)findViewById(R.id.edit_store_latitude);
        edit_store_altitude = (EditText)findViewById(R.id.edit_store_altitude);

        Store store = null;
        Bundle b = getIntent().getExtras();
        if (b != null) {
            Long id = b.getLong("id");
            if (id != null) {
                for(Store _s: MainActivity.mStores){
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
        }

        final Store lstore = store;

        ((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(v->{

            if (!validate()) return;

            if (lstore == null || lstore.getId() == null || lstore.getId() < 1) {
                //add

                DBMgr.addStoreThr(EditStoresActivity.this, new Store(edit_store_name.getText().toString(),
                        edit_store_address.getText().toString(),
                        Double.valueOf(edit_store_longitude.getText().toString()),
                        Double.valueOf(edit_store_latitude.getText().toString()),
                        Double.valueOf(edit_store_altitude.getText().toString())), new IThrRes() {
                    @Override
                    public void isDone() {
                        FragmentStores.mStoresListViewAdaptor.notifyDataSetChanged();
                        finish();
                    }

                    @Override
                    public void isError(String mess) {
                        MainActivity.showMess(EditStoresActivity.this, mess);
                    }
                });

            } else {
                lstore.setName(edit_store_name.getText().toString());
                lstore.setAddress(edit_store_address.getText().toString());
                lstore.setLongitude(Double.valueOf(edit_store_longitude.getText().toString()));
                lstore.setLatitude(Double.valueOf(edit_store_latitude.getText().toString()));
                lstore.setAltitude(Double.valueOf(edit_store_altitude.getText().toString()));

                //update
                DBMgr.updateStoreThr(EditStoresActivity.this, lstore, new IThrRes() {
                    @Override
                    public void isDone() {
                        FragmentStores.mStoresListViewAdaptor.notifyDataSetChanged();
                        finish();
                    }

                    @Override
                    public void isError(String mess) {
                        MainActivity.showMess(EditStoresActivity.this, mess);
                    }
                });
            }
        });
    }

    private boolean validate() {
        boolean res = !edit_store_name.getText().toString().isEmpty() &&
            !edit_store_address.getText().toString().isEmpty() &&
            !edit_store_longitude.getText().toString().isEmpty() &&
            !edit_store_latitude.getText().toString().isEmpty() &&
            !edit_store_altitude.getText().toString().isEmpty();

        if (!res) {
            Snackbar.make(findViewById(R.id.edit_store_rootview), "Write plz all fields", Snackbar.LENGTH_LONG).setAction("Edit_validation", null).show();
        }

        try{
            Double.valueOf(edit_store_longitude.getText().toString());
            Double.valueOf(edit_store_latitude.getText().toString());
            Double.valueOf(edit_store_altitude.getText().toString());
        }catch(Exception e){
            res = false;
            Snackbar.make(findViewById(R.id.edit_store_rootview), "GPS data is double", Snackbar.LENGTH_LONG).setAction("Edit_validation", null).show();
        }

        return true;
    }

}
