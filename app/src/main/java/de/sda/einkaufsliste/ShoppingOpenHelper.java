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
        db.execSQL("create table shopping(id integer primary key autoincrement, productname text, shopname text);");
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
     * @param productName
     * @param shopName
     * @return Did Der Primaerschluessel der eingefuegten Zeile.
     */
    public long insert(String productName, String shopName) {
        //getWritableDatabase()
        //getReadableDatabase()
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("productname", productName);
        cv.put("shopname", shopName);
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
                        res.add(new Shopping(productName, shopName));
                    }
                }
            }
            return res;
        }catch(Exception e){
            Log.e(getClass().toString(), e.getMessage());
            return null;
        }
    }

}
