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
                    if (i != null) i.onDone();
                });
            }catch(Exception e) {
                e.printStackTrace();
                if (i != null) ((Activity)a).runOnUiThread(()->{i.onError(e.getMessage());});
            }
        }).start();
    }

    public static void updateStoreThr(Activity a, Store s, IThrRes i) {
        new Thread(()->{
            try {
                MainActivity.mOpenHelper.storesUpdate(s);
                if (i != null) a.runOnUiThread(()->{i.onDone();});
            }catch(Exception e) {
                e.printStackTrace();
                if (i != null) a.runOnUiThread(()->{i.onError(e.getMessage());});
            }
        }).start();
    }

    public static void deleteStoreThr(Activity a, Store s, IThrRes i) {
        new Thread(() -> {
            try {
                MainActivity.mOpenHelper.storesDelete(s);
                if (i != null) a.runOnUiThread(()->{i.onDone();});
            }catch(Exception e) {
                e.printStackTrace();
                if (i != null) a.runOnUiThread(()->{i.onError(e.getMessage());});
            }
        }).start();
        MainActivity.mStores.remove(s);
    }

    public static void addProductThr(Activity a, Product p, IThrRes i) {
        new Thread(()->{
            try {
                MainActivity.mOpenHelper.productInsert(p);
                a.runOnUiThread(()->{
                    MainActivity.mProducts.add(p);
                    if (i != null) i.onDone();
                });
            }catch(Exception e) {
                e.printStackTrace();
                if (i != null) ((Activity)a).runOnUiThread(()->{i.onError(e.getMessage());});
            }
        }).start();
    }

    public static void updateProductThr(Activity a, Product p, IThrRes i) {
        new Thread(()->{
            try {
                MainActivity.mOpenHelper.productUpdate(p);
                if (i != null) a.runOnUiThread(()->{i.onDone();});
            }catch(Exception e) {
                e.printStackTrace();
                if (i != null) a.runOnUiThread(()->{i.onError(e.getMessage());});
            }
        }).start();
    }

    public static void deleteProductThr(Activity a, Product p, IThrRes i) {
        new Thread(() -> {
            try {
                MainActivity.mOpenHelper.productDelete(p);
                if (i != null) a.runOnUiThread(()->{i.onDone();});
            }catch(Exception e) {
                e.printStackTrace();
                if (i != null) a.runOnUiThread(()->{i.onError(e.getMessage());});
            }
        }).start();
        MainActivity.mProducts.remove(p);
    }

}
