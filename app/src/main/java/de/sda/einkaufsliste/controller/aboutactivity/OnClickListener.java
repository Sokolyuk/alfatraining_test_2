package de.sda.einkaufsliste.controller.aboutactivity;

import android.view.View;
import de.sda.einkaufsliste.AboutActivity;
import de.sda.einkaufsliste.MainActivity;
import de.sda.einkaufsliste.R;

/**
 * Created by Alfa on 25.07.2016.
 */
public class OnClickListener implements View.OnClickListener{

    private AboutActivity aboutActivity;

    public OnClickListener(AboutActivity a) {
        this.aboutActivity = a;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDbBackupToSdCard:
                aboutActivity.getFileService().doExportProductsToSDCard(MainActivity.getProducts());
                break;
            case R.id.btnSupportCall:

                break;
            default:
        }

    }

}
