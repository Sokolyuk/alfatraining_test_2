package de.sda.einkaufsliste.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import java.util.List;
import de.sda.einkaufsliste.model.DBOpenHelper;
import de.sda.einkaufsliste.model.Product;

public class FileService extends Service {
    private DBOpenHelper mOpenHelper;

    public FileService() {
    }

    public class FileServiceBinder extends Binder {
        public FileService getService() {
            // Return this instance of LocalService so clients can call public methods
            return FileService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new FileServiceBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mOpenHelper = new DBOpenHelper(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOpenHelper != null) {
            mOpenHelper.close();
            mOpenHelper = null;
        }
    }

    public void doExportProductsToSDCard(List<Product> products) {
        FileServiceLogic.exportProductsToSDCard(products, mOpenHelper);
    }

    public List<Product> doImportProductsFromSDCard() {
        return FileServiceLogic.importProductsFromSDCard(mOpenHelper);
    }

}
