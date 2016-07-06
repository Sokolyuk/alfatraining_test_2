package de.sda.einkaufsliste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import de.sda.einkaufsliste.controller.MainActivityOnClickListner;
import de.sda.einkaufsliste.model.Shopping;

public class MainActivity extends AppCompatActivity {
    private Button btnAddShopping;

    public static List<Shopping> shoppingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        ((Button)findViewById(R.id.btnAddShopping)).setOnClickListener(
                v->((EditText)findViewById(R.id.txtShoppingList)).setText(
                        ((EditText)findViewById(R.id.txtProduct)).getText().toString() + "/" + ((EditText)findViewById(R.id.txtShop)).getText().toString()
                )
        );*/


        MainActivityOnClickListner l = new MainActivityOnClickListner(this);

        btnAddShopping = (Button)findViewById(R.id.btnAddShopping);

        btnAddShopping.setOnClickListener(l);



    }
}
