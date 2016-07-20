package de.sda.einkaufsliste;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import de.sda.einkaufsliste.controller.MainActivityOnClickListner;
import de.sda.einkaufsliste.model.Store;
import de.sda.einkaufsliste.utils.IThrRes;

/**
 * Created by Dmitry Sokolyuk on 19.07.2016.
 */
public class FragmentStores extends Fragment {

    public static StoresListViewAdaptor mStoresListViewAdaptor;
    public static ListView mStoresListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmant_stores, container, false);

        mStoresListViewAdaptor = new StoresListViewAdaptor(getActivity());
        mStoresListView = (ListView)v.findViewById(R.id.listView);
        mStoresListView.setAdapter(mStoresListViewAdaptor);
        registerForContextMenu(mStoresListView);

        return v;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        ListView lv = (ListView) v;
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Store s = (Store) lv.getItemAtPosition(acmi.position);

        menu.setHeaderTitle(String.format("Store: '%s'", s.getName()));
        menu.add(0, v.getId(), 1, "Edit store");
        menu.add(0, v.getId(), 2, "Delete store");

//((AdapterView.AdapterContextMenuInfo) menuInfo).targetView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotation));

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//sda+        Shopping s = (Shopping)MainActivity.listView.getItemAtPosition(info.position);

        switch (item.getOrder()){
            case 1:
//sda+                MainActivityOnClickListner.edit(this, s, 1);
                break;
            case 2:
//sda+                MainActivityOnClickListner.deleteThr(this, s);
                break;
//            case 3:
////sda+                startActivity(new Intent(this, RecyclerViewActivity.class));
//                break;
            default:
        }


        return super.onContextItemSelected(item);
    }

    public class StoresListViewAdaptor extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private final Context context;

        public StoresListViewAdaptor(Context context){
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (MainActivity.mStores == null) {
                return 0;
            } else {
                return MainActivity.mStores.size();
            }
        }

        @Override
        public Object getItem(int position) {
            if (MainActivity.mStores == null) {
                return null;
            } else {
                return MainActivity.mStores.get(position);
            }

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.listview_item_store, parent, false);
            }

            Store s = (Store) getItem(position);
            ((TextView)convertView.findViewById(R.id.il_store_name)).setText(s.getName());
            ((TextView)convertView.findViewById(R.id.il_store_address)).setText(s.getAddress());

            convertView.setOnClickListener(v->{
                Intent i = new Intent(getActivity(), EditStoresActivity.class);
                i.putExtra("id", s.getId());
                startActivity(i);
            });

            convertView.setOnLongClickListener(v->{
                ((Activity)context).openContextMenu(v);
                return true;
            });

            return convertView;
        }
    }
}
