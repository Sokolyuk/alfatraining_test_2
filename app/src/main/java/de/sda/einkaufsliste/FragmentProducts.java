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

import de.sda.einkaufsliste.controller.DBMgr;
import de.sda.einkaufsliste.model.Product;
import de.sda.einkaufsliste.utils.IThrRes;

/**
 * Created by Dmitry Sokolyuk on 19.07.2016.
 */
public class FragmentProducts extends Fragment {

    public static ProductListViewAdaptor mProductListViewAdaptor;
    public static ListView mProductsListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmant_products, container, false);

        mProductListViewAdaptor = new ProductListViewAdaptor(getActivity());
        mProductsListView = (ListView)v.findViewById(R.id.listViewProducts);
        mProductsListView.setAdapter(mProductListViewAdaptor);
        registerForContextMenu(mProductsListView);

        return v;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        ListView lv = (ListView) v;
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Product s = (Product) lv.getItemAtPosition(acmi.position);

        menu.setHeaderTitle(String.format("Product: '%s'", s.getName()));
        menu.add(0, v.getId(), ContextMenuConsts.cmProductEdit, "Edit product");
        menu.add(0, v.getId(), ContextMenuConsts.cmProductDelete, "Delete product");
    }

    protected Product ProductByMenuItem(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        return (Product) mProductsListView.getItemAtPosition(info.position);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (getUserVisibleHint()) {

            switch (item.getOrder()){
                case ContextMenuConsts.cmProductEdit:
                    Intent i = new Intent(getActivity(), EditProductActivity.class);
                    i.putExtra("id", ProductByMenuItem(item).getId());
                    startActivity(i);
                    break;
                case ContextMenuConsts.cmProductDelete:
                    Product p = ProductByMenuItem(item);
                    DBMgr.deleteProductThr(getActivity(), p, new IThrRes() {
                        @Override
                        public void onDone() {
                            mProductListViewAdaptor.notifyDataSetChanged();
                            MainActivity.showMess(getContext(), String.format("Product '%s' deleted", p.getName()));
                        }

                        @Override
                        public void onError(String mess) {
                            MainActivity.showMess(getContext(), mess);
                        }
                    });
                    break;
                default:
            }

            return super.onContextItemSelected(item);
        }
        return false;

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

            Product p = (Product) getItem(position);
            ((TextView)convertView.findViewById(R.id.idWare)).setText(p.getName());
            ((TextView)convertView.findViewById(R.id.idShop)).setText(p.getStore_name());
            ImageView imgNone = (ImageView)convertView.findViewById(R.id.imgNone);
            ImageView imgDone = (ImageView)convertView.findViewById(R.id.imgDone);
            if (p.isDone()) {
                imgNone.setVisibility(View.GONE);
                imgDone.setVisibility(View.VISIBLE);
            } else {
                imgNone.setVisibility(View.VISIBLE);
                imgDone.setVisibility(View.GONE);
            }

            convertView.setOnClickListener(v->{
                Product _p = (Product) getItem(position);
                if(_p != null) _p.setDone(!_p.isDone());
                DBMgr.updateProductThr(getActivity(), _p, new IThrRes() {
                    @Override
                    public void onDone() {
                        mProductListViewAdaptor.notifyDataSetInvalidated();
                        ((View)v).clearAnimation();
                    }

                    @Override
                    public void onError(String mess) {
                        MainActivity.showMess(v.getContext(), mess);
                        ((View)v).startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.bounce));
                    }
                });
                ((View)v).startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.bounce));
            });

            convertView.setOnLongClickListener(v->{
                ((Activity)context).openContextMenu(v);
                return true;
            });

            return convertView;
        }
    }


}
