package de.sda.einkaufsliste.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry Sokoltyk on 08.07.2016.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context) {
        super(context, DBStruct.DB, null, 1);
    }

    /**
     * Diese Methode muss befuellt werden, damit die Datenbanktabellen erzeugt werdern.
     * @param db
     */
    @Override
    public synchronized void onCreate(SQLiteDatabase db) {
        db.execSQL(DBStruct.STORES_CREATE);
        db.execSQL(DBStruct.PRODUCTS_CREATE);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    /**
     * Diese Callback-Methode wird aufgerufen, wenn die Datenbank-Version sich aendert.
     * @param db
     * @param i
     * @param i1
     */
    @Override
    public synchronized void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DBStruct.PRODUCTS_DROP);
        db.execSQL(DBStruct.STORES_DROP);
        onCreate(db);
    }

    /**
     * Speichert einen Datensatz in die Tabelle person.
     * Siese Methode nutzt die SQLiteDatabase Klasse,
     * und schreibt in die Felder fuer den Vornamen und den Nachnamen.
     * @param s
     * @return Did Der Primaerschluessel der eingefuegten Zeile.
     */
    public synchronized long productInsert(Product s) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBStruct.PRODUCTS_NAME, s.getName());
        cv.put(DBStruct.PRODUCTS_STORE_ID, s.getStore_id());
        cv.put(DBStruct.PRODUCTS_ISDONE, s.isDone()?1:0);
        long _id = db.insert(DBStruct.PRODUCTS, null, cv);
        s.setId(_id);
        return _id;
    }

    public synchronized long storesInsert(Store s) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBStruct.STORES_NAME, s.getName());
        cv.put(DBStruct.STORES_ADDRESS, s.getAddress());
        cv.put(DBStruct.STORES_LONGITUDE, s.getLongitude());
        cv.put(DBStruct.STORES_LATITUDE, s.getLatitude());
        cv.put(DBStruct.STORES_ALTITUDE, s.getAltitude());
        long _id = db.insert(DBStruct.STORES, null, cv);
        s.setId(_id);
        return _id;
    }

    public synchronized List<Product> productsSelect() {
        ArrayList<Product> res = new ArrayList<>();
        try{
            SQLiteDatabase d = getReadableDatabase();
            try(Cursor cur = d.rawQuery(DBStruct.PRODUCTS_SELECT, null);){
                if (cur != null) {
                    while(cur.moveToNext()){
                        long id = cur.getLong(cur.getColumnIndex(DBStruct.PRODUCTS_ID));
                        String productName = cur.getString(cur.getColumnIndex(DBStruct.PRODUCTS_NAME));
                        String shopName = cur.getString(cur.getColumnIndex(DBStruct.PRODUCTS_STORE_NAME));
                        long store_id = cur.getLong(cur.getColumnIndex(DBStruct.PRODUCTS_STORE_ID));
                        boolean isDone = cur.getInt(cur.getColumnIndex(DBStruct.PRODUCTS_ISDONE)) > 0;
                        res.add(new Product(id, productName, shopName, store_id, isDone));
                    }
                }
            }
            return res;
        }catch(Exception e){
            Log.e(getClass().toString(), e.getMessage());
            return new ArrayList<>();
        }
    }

    public synchronized List<Store> storesSelect() {
        ArrayList<Store> res = new ArrayList<>();
        try{
            SQLiteDatabase d = getReadableDatabase();
            try(Cursor cur = d.query(DBStruct.STORES, null, null, null, null, null, null);){
                if (cur != null) {
                    while(cur.moveToNext()){
                        long id = cur.getLong(cur.getColumnIndex(DBStruct.STORES_ID));
                        String name = cur.getString(cur.getColumnIndex(DBStruct.STORES_NAME));
                        String address = cur.getString(cur.getColumnIndex(DBStruct.STORES_ADDRESS));
                        double longitude = cur.getDouble(cur.getColumnIndex(DBStruct.STORES_LONGITUDE));
                        double latitude = cur.getDouble(cur.getColumnIndex(DBStruct.STORES_LATITUDE));
                        double altitude = cur.getDouble(cur.getColumnIndex(DBStruct.STORES_ALTITUDE));
                        res.add(new Store(id, name, address, longitude, latitude, altitude));
                    }
                }
            }
            return res;
        }catch(Exception e){
            Log.e(getClass().toString(), e.getMessage());
            return new ArrayList<>();
        }
    }

    public synchronized void productUpdate(Product s) throws Exception {
        if (s.getId() < 1) throw new Exception(DBStruct.ERR_IDUNASSIGNED);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBStruct.PRODUCTS_NAME, s.getName());
        cv.put(DBStruct.PRODUCTS_STORE_ID, s.getStore_id());
        cv.put(DBStruct.PRODUCTS_ISDONE, s.isDone()?1:0);
        db.update(DBStruct.PRODUCTS, cv, String.format("%s=%s", DBStruct.PRODUCTS_ID, String.valueOf(s.getId())), null);
    }

    public synchronized void storesUpdate(Store s) throws Exception {
        if (s.getId() < 1) throw new Exception(DBStruct.ERR_IDUNASSIGNED);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBStruct.STORES_NAME, s.getName());
        cv.put(DBStruct.STORES_ADDRESS, s.getAddress());
        cv.put(DBStruct.STORES_LONGITUDE, s.getLongitude());
        cv.put(DBStruct.STORES_LATITUDE, s.getLatitude());
        cv.put(DBStruct.STORES_ALTITUDE, s.getAltitude());
        db.update(DBStruct.STORES, cv, String.format("%s=%s", DBStruct.STORES_ID, String.valueOf(s.getId())), null);
    }

    public synchronized void productDelete(Product s) {
        if (s.getId() < 1) return;
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DBStruct.PRODUCTS, String.format("%s=%s", DBStruct.PRODUCTS_ID, String.valueOf(s.getId())), null);
    }

    public synchronized void storesDelete(Store s) {
        if (s.getId() < 1) return;
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DBStruct.STORES, String.format("%s=%s", DBStruct.STORES_ID, String.valueOf(s.getId())), null);
    }

}
