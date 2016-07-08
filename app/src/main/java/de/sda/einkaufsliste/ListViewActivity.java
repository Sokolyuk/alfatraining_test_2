package de.sda.einkaufsliste;

import android.app.ListActivity;
import android.os.Bundle;

/**
 * Created by Alfa on 08.07.2016.
 */
public class ListViewActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListViewAdaptor listViewAdaptor = new ListViewAdaptor(this);
        setListAdapter(listViewAdaptor);
    }
}
