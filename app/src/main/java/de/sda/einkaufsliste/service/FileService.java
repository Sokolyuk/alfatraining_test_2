package de.sda.einkaufsliste.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.List;

import de.sda.einkaufsliste.MainActivity;
import de.sda.einkaufsliste.model.DBOpenHelper;
import de.sda.einkaufsliste.model.DBStruct;
import de.sda.einkaufsliste.model.Product;
import de.sda.einkaufsliste.model.Store;

public class FileService extends Service {
    private DBOpenHelper mOpenHelper;

    public FileService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mOpenHelper = new DBOpenHelper(this);
    }

    protected String getFileName(String tableName) {
        return String.format("%s%s.txt", DBStruct.DB, tableName);
    }

    protected void backupTableToFile(String tableName) {
        List<Store> stores = mOpenHelper.storesSelect();
        if (stores != null && stores.size() > 0) {
            openFileOutput(getFileName(table));
        }
    }

    protected void backupProductsToFile(List<Product> products) {
        1
    }

}
