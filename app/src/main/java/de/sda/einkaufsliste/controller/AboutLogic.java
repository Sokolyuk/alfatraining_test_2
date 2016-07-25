package de.sda.einkaufsliste.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import de.sda.einkaufsliste.R;

/**
 * Created by Alfa on 25.07.2016.
 */
public class AboutLogic {

    public static void dbBackupToSdCard(Context c) {

    }

    public static void supportCall(Activity a) {
        /*
         * Impliziter Intent Anrufdialog Supporthotline genrieren
         * Intent intentCallDialogHotline = new Intent(Intent.ACTION_DIAL,
         * 		Uri.parse(strTelNr));
         * 	this.activity.startActivity(intentCallDialogHotline);
         */


        //Starten des Direktanrufs
        if (ActivityCompat.checkSelfPermission(a,
                Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {

            //Permission festlegen
            String[] strWantedPermission = {Manifest.permission.CALL_PHONE};

            //Direktanruf Berechtigung Dialog anzeigen
            a.requestPermissions(strWantedPermission, 1);

        } else {
            //Wenn die Berechtigung freigegeben wurde:

            //Telefonnummer aus strings.xml auslesen
            String strPhoneNr = a.getString(R.string.strSupportCallNr);

            //Impliziter Intent Direktanruf Supporthotline genrieren
            Intent intentDirectCallHotline = new Intent(Intent.ACTION_CALL, Uri.parse(strPhoneNr));

            //Direktanruf starten
            a.startActivity(intentDirectCallHotline);
        }
    }


}
