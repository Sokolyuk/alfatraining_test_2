package de.sda.einkaufsliste.controller;

import android.app.Activity;

import de.sda.einkaufsliste.MainActivity;
import de.sda.einkaufsliste.model.Product;
import de.sda.einkaufsliste.model.Store;
import de.sda.einkaufsliste.utils.IThrRes;

/**
 * Created by Alfa on 20.07.2016.
 */
public class DBMgr {

    public static void addStoreThr(Activity a, Store s, IThrRes i) {
        new Thread(()->{
            try {
                MainActivity.mOpenHelper.storesInsert(s);
                a.runOnUiThread(()->{
                    MainActivity.mStores.add(s);
                    if (i != null) i.isDone();
                });
            }catch(Exception e) {
                e.printStackTrace();
                if (i != null) ((Activity)a).runOnUiThread(()->{i.isError(e.getMessage());});
            }
        }).start();
    }

    public static void updateStoreThr(Activity a, Store s, IThrRes i) {
        new Thread(()->{
            try {
                MainActivity.mOpenHelper.storesUpdate(s);
                if (i != null) a.runOnUiThread(()->{i.isDone();});
            }catch(Exception e) {
                e.printStackTrace();
                if (i != null) a.runOnUiThread(()->{i.isError(e.getMessage());});
            }
        }).start();
    }

    public static void deleteStoreThr(Activity a, Store s, IThrRes i) {
        new Thread(() -> {
            try {
                MainActivity.mOpenHelper.storesDelete(s);
                if (i != null) a.runOnUiThread(()->{i.isDone();});
            }catch(Exception e) {
                e.printStackTrace();
                if (i != null) a.runOnUiThread(()->{i.isError(e.getMessage());});
            }
        }).start();
        MainActivity.mStores.remove(s);
    }

    public static void addProductThr(Activity a, Product p, IThrRes i) {
        new Thread(()->{
            try {
                MainActivity.mOpenHelper.Insert(p);
                a.runOnUiThread(()->{
                    MainActivity.mProducts.add(p);
                    if (i != null) i.isDone();
                });
            }catch(Exception e) {
                e.printStackTrace();
                if (i != null) ((Activity)a).runOnUiThread(()->{i.isError(e.getMessage());});
            }
        }).start();
    }

    public static void updateStoreThr(Activity a, Store s, IThrRes i) {
        new Thread(()->{
            try {
                MainActivity.mOpenHelper.storesUpdate(s);
                if (i != null) a.runOnUiThread(()->{i.isDone();});
            }catch(Exception e) {
                e.printStackTrace();
                if (i != null) a.runOnUiThread(()->{i.isError(e.getMessage());});
            }
        }).start();
    }

    public static void deleteStoreThr(Activity a, Store s, IThrRes i) {
        new Thread(() -> {
            try {
                MainActivity.mOpenHelper.storesDelete(s);
                if (i != null) a.runOnUiThread(()->{i.isDone();});
            }catch(Exception e) {
                e.printStackTrace();
                if (i != null) a.runOnUiThread(()->{i.isError(e.getMessage());});
            }
        }).start();
        MainActivity.mStores.remove(s);
    }

}
