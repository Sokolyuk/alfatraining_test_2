package de.sda.einkaufsliste;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.sda.einkaufsliste.model.Shopping;

/**
 * Created by Alfa on 08.07.2016.
 */
public class ShoppingOpenHelper extends SQLiteOpenHelper {

    public ShoppingOpenHelper(Context context) {
        super(context, "shopping.db", null, 1);
    }

    /**
     * Diese Methode muss befuellt werden, damit die Datenbanktabellen erzeugt werdern.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table shopping(id integer primary key autoincrement, productname text, shopname text, isdone integer);");
    }

    /**
     * Diese Callback-Methode wird aufgerufen, wenn die Datenbank-Version sich aendert.
     * @param db
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists shopping;");
        onCreate(db);
    }

    /**
     * Speichert einen Datensatz in die Tabelle person.
     * Siese Methode nutzt die SQLiteDatabase Klasse,
     * und schreibt in die Felder fuer den Vornamen und den Nachnamen.
     * @param s
     * @return Did Der Primaerschluessel der eingefuegten Zeile.
     */
    public long insert(Shopping s) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("productname", s.getProductName());
        cv.put("shopname", s.getShopName());
        cv.put("isdone", s.isDone()?1:0);
        return db.insert("shopping", null, cv);
    }

    public List<Shopping> select() {
        ArrayList<Shopping> res = new ArrayList<>();
        try{
            SQLiteDatabase d = getReadableDatabase();
            try(Cursor cur = d.query("shopping", null, null, null, null, null, null);){
                if (cur != null) {
                    while(cur.moveToNext()){
                        long id = cur.getLong(cur.getColumnIndex("id"));
                        String productName = cur.getString(cur.getColumnIndex("productname"));
                        String shopName = cur.getString(cur.getColumnIndex("shopname"));
                        boolean isDone = cur.getInt(cur.getColumnIndex("isdone")) > 0;
                        res.add(new Shopping(id, productName, shopName, isDone));
                    }
                }
            }
            return res;
        }catch(Exception e){
            Log.e(getClass().toString(), e.getMessage());
            return null;
        }
    }

    public void update(Shopping s) throws Exception {
        if (s.getId() < 1) throw new Exception("Id is unassigned");
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("productname", s.getProductName());
        cv.put("shopname", s.getShopName());
        cv.put("isdone", s.isDone()?1:0);
        db.update("shopping", cv, "id="+s.getId(), null);
    }

    public void delete(Shopping s) {
        if (s.getId() < 1) return;
        SQLiteDatabase db = getWritableDatabase();
        db.delete("shopping", "id="+s.getId(), null);
    }
}
