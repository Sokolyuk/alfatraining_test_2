package de.sda.einkaufsliste;

import android.app.Activity;
import android.content.Context;
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
import de.sda.einkaufsliste.model.Shopping;
import de.sda.einkaufsliste.utils.IThrRes;

/**
 * Created by Alfa on 19.07.2016.
 */
public class FragmentProducts extends Fragment {

    public static ProductListViewAdaptor mListViewAdaptor;
    public static ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmant_products, container, false);

        mListViewAdaptor = new ProductListViewAdaptor(getActivity());
        mListView = (ListView)v.findViewById(R.id.listView);
        mListView.setAdapter(mListViewAdaptor);
        registerForContextMenu(mListView);

        return v;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        ListView lv = (ListView) v;
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Shopping s = (Shopping) lv.getItemAtPosition(acmi.position);

        menu.setHeaderTitle(String.format("'%s'", s.getProductName()));
        menu.add(0, v.getId(), 1, "Edit");
        menu.add(0, v.getId(), 2, "Delete");
        menu.add(0, v.getId(), 3, "Positive");

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
            case 3:
//sda+                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
            default:
        }


        return super.onContextItemSelected(item);
    }


    public class ProductListViewAdaptor extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private final Context context;

        public ProductListViewAdaptor(Context context){
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (MainActivity.mProducts == null) {
                return 0;
            } else {
                return MainActivity.mProducts.size();
            }
        }

        @Override
        public Object getItem(int position) {
            if (MainActivity.mProducts == null) {
                return null;
            } else {
                return MainActivity.mProducts.get(position);
            }

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_view_item_layout, parent, false);
            }

            Shopping s = (Shopping) getItem(position);
            ((TextView)convertView.findViewById(R.id.idWare)).setText(s.getProductName());
            ((TextView)convertView.findViewById(R.id.idShop)).setText(s.getShopName());
            ImageView imgNone = (ImageView)convertView.findViewById(R.id.imgNone);
            ImageView imgDone = (ImageView)convertView.findViewById(R.id.imgDone);
            if (s.isDone()) {
                imgNone.setVisibility(View.GONE);
                imgDone.setVisibility(View.VISIBLE);
            } else {
                imgNone.setVisibility(View.VISIBLE);
                imgDone.setVisibility(View.GONE);
            }

            convertView.setOnClickListener(v->{
                Shopping _s = (Shopping) getItem(position);
                if(_s != null) _s.setDone(!_s.isDone());
                MainActivityOnClickListner.updateThr(v.getContext(), new IThrRes() {
                    @Override
                    public void isDone() {
//sda+                    MainActivity.listViewAdaptor.notifyDataSetInvalidated();
                        ((View)v).clearAnimation();
                    }

                    @Override
                    public void isError(String mess) {
                        MainActivity.showMess(v.getContext(), mess);
                        ((View)v).startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.bounce));
                    }
                }, _s);
                ((View)v).startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.rotation));
            });

            convertView.setOnLongClickListener(v->{
                ((Activity)context).openContextMenu(v);
                return true;
            });

            return convertView;
        }
    }


}
