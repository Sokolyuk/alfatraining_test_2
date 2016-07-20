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
        db.execSQL(DBStruct.SHOPPING_CREATE);
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
        db.execSQL(DBStruct.SHOPPING_DROP);
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
    public synchronized long shoppingInsert(Shopping s) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBStruct.SHOPPING_PRODUCTNAME, s.getProductName());
        cv.put(DBStruct.SHOPPING_STORE_ID, s.getShopName());
        cv.put(DBStruct.SHOPPING_ISDONE, s.isDone()?1:0);
        long _id = db.insert(DBStruct.SHOPPING, null, cv);
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

    public synchronized List<Shopping> shoppingSelect() {
        ArrayList<Shopping> res = new ArrayList<>();
        try{
            SQLiteDatabase d = getReadableDatabase();
            try(Cursor cur = d.rawQuery(DBStruct.SHOPPING_SELECT, null);){
                if (cur != null) {
                    while(cur.moveToNext()){
                        long id = cur.getLong(cur.getColumnIndex(DBStruct.SHOPPING_ID));
                        String productName = cur.getString(cur.getColumnIndex(DBStruct.SHOPPING_PRODUCTNAME));
                        String shopName = cur.getString(cur.getColumnIndex(DBStruct.SHOPPING_STORE_NAME));
                        long store_id = cur.getLong(cur.getColumnIndex(DBStruct.SHOPPING_STORE_ID));
                        boolean isDone = cur.getInt(cur.getColumnIndex(DBStruct.SHOPPING_ISDONE)) > 0;
                        res.add(new Shopping(id, productName, shopName, store_id, isDone));
                    }
                }
            }
            return res;
        }catch(Exception e){
            Log.e(getClass().toString(), e.getMessage());
            return null;
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
            return null;
        }
    }

    public synchronized void shoppingUpdate(Shopping s) throws Exception {
        if (s.getId() < 1) throw new Exception(DBStruct.ERR_IDUNASSIGNED);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBStruct.SHOPPING_PRODUCTNAME, s.getProductName());
        cv.put(DBStruct.SHOPPING_STORE_ID, s.getStore_id());
        cv.put(DBStruct.SHOPPING_ISDONE, s.isDone()?1:0);
        db.update(DBStruct.SHOPPING, cv, String.format("%s=%s", DBStruct.SHOPPING_ID, String.valueOf(s.getId())), null);
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

    public synchronized void shoppingDelete(Shopping s) {
        if (s.getId() < 1) return;
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DBStruct.SHOPPING, String.format("%s=%s", DBStruct.SHOPPING_ID, String.valueOf(s.getId())), null);
    }

    public synchronized void storesDelete(Store s) {
        if (s.getId() < 1) return;
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DBStruct.STORES, String.format("%s=%s", DBStruct.STORES_ID, String.valueOf(s.getId())), null);
    }

}
