package de.sda.einkaufsliste.model;

/**
 * Created by Alfa on 15.07.2016.
 */
public interface DBStruct {
    public final String ERR_IDUNASSIGNED = "Id is unassigned";

    public final String DB = "shopping.db";

    public final String SHOPPING = "shopping";
    public final String SHOPPING_ID = "id";
    public final String SHOPPING_PRODUCTNAME = "productname";
    public final String SHOPPING_STORE = "store";
    public final String SHOPPING_ISDONE = "isdone";
    public final String SHOPPING_CREATE = "create table shopping(id integer primary key autoincrement, productname text, store integer, isdone integer, FOREIGN KEY(store) REFERENCES stores(id));";
    public final String SHOPPING_DROP = "drop table if exists shopping;";

    public final String STORES = "stores";
    public final String STORES_ID = "id";
    public final String STORES_NAME = "name";
    public final String STORES_ADDRESS = "address";
    public final String STORES_LONGITUDE = "longitude";
    public final String STORES_LATITUDE = "latitude";
    public final String STORES_CREATE = "create table stores(id integer primary key autoincrement, name text, address text, longitude double, latitude double);";
    public final String STORES_DROP = "drop table if exists stores;";
}
