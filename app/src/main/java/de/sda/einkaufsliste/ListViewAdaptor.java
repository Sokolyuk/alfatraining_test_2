package de.sda.einkaufsliste;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import de.sda.einkaufsliste.MainActivity;
import de.sda.einkaufsliste.R;
import de.sda.einkaufsliste.controller.MainActivityOnClickListner;
import de.sda.einkaufsliste.model.Shopping;
import de.sda.einkaufsliste.utils.IThrRes;

/**
 * Created by Alfa on 08.07.2016.
 */
public class ListViewAdaptor extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private final Context context;

    public ListViewAdaptor(Context context){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return MainActivity.shoppingList.size();
    }

    @Override
    public Object getItem(int position) {
        return MainActivity.shoppingList.get(position);
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
                    MainActivity.listViewAdaptor.notifyDataSetInvalidated();
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