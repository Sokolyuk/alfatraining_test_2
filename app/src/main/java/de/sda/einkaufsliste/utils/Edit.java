package de.sda.einkaufsliste.utils;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import de.sda.einkaufsliste.R;

/**
 * Created by Project1 on 20.07.2016.
 */
public class Edit {

    public static boolean validateText(Activity a, EditText e, TextInputLayout w) {
        if (e.getText().length() == 0) {
            w.setError(a.getString(R.string.err_field_is_empty));
            //edit_store_name.requestFocus();
            return false;
        } else {
            w.setErrorEnabled(false);
            return true;
        }
    }

    public static boolean validateDouble(Activity a, EditText e, TextInputLayout w) {
        if (validateText(a, e, w)) {
            try{
                Double.valueOf(e.getText().toString());
                w.setErrorEnabled(false);
                return true;
            }catch(Exception ex){
                w.setError(a.getString(R.string.err_incorrect_double_value));
            }
        }
        return false;
    }


}
