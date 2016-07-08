package de.sda.einkaufsliste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.sda.einkaufsliste.controller.MainActivityOnClickListner;
import de.sda.einkaufsliste.model.Shopping;

public class MainActivity extends AppCompatActivity {
    public static List<Shopping> shoppingList = new ArrayList<>();
    public static ShoppingOpenHelper shoppingOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivityOnClickListner l = new MainActivityOnClickListner(this);

        ((Button)findViewById(R.id.btnAddShopping)).setOnClickListener(l);
        ((Button)findViewById(R.id.btnClearShopping)).setOnClickListener(l);
        ((Button)findViewById(R.id.btnSaveShopping)).setOnClickListener(l);
        ((Button)findViewById(R.id.btnSaveDBShopping)).setOnClickListener(l);
        ((Button)findViewById(R.id.btnLoadShopping)).setOnClickListener(l);
        ((Button)findViewById(R.id.btnLoadDBShopping)).setOnClickListener(l);
        ((Button)findViewById(R.id.btnShow)).setOnClickListener(l);

        shoppingOpenHelper = new ShoppingOpenHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shoppingOpenHelper.close();
    }

    public void showMess(String mess) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)findViewById(R.id.toast_layout_root));
        TextView text = (TextView)layout.findViewById(R.id.text);
        text.setText(mess);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.RIGHT | Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
