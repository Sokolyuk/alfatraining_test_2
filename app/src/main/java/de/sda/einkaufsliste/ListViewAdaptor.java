package de.sda.einkaufsliste;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import de.sda.einkaufsliste.model.Shopping;

/**
 * Created by Alfa on 08.07.2016.
 */
public class ListViewAdaptor extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            Shopping shopping = (Shopping) getItem(position);
            convertView = layoutInflater.inflate(R.layout.list_view_item_layout, parent, false);
            TextView txtvPerson = (TextView)convertView.findViewById(R.id.txtvShopping);
            txtvPerson.setText(shopping.toString());
        }
        return convertView;
    }
}