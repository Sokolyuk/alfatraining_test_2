package de.sda.einkaufsliste.model;

/**
 * Created by Dmitry Sokolyuk on 15.07.2016.
 */
public interface DBStruct {
    public final String ERR_IDUNASSIGNED = "Id is unassigned";

    public final String DB = "shopping.db";

    public final String PRODUCTS = "products";
    public final String PRODUCTS_ID = "id";
    public final String PRODUCTS_NAME = "name";
    public final String PRODUCTS_STORE_NAME = "store_name";
    public final String PRODUCTS_STORE_ID = "store_id";
    public final String PRODUCTS_ISDONE = "isdone";
    public final String PRODUCTS_CREATE = "create table products(id integer primary key autoincrement, name text, store_id integer, isdone integer, FOREIGN KEY(store_id) REFERENCES stores(id));";
    public final String PRODUCTS_DROP = "drop table if exists products;";
    public final String PRODUCTS_SELECT = "select s.id,s.name,s.store_name,t.id as store_id,isdone from products s inner join stores t on s.store = t.id;";

    public final String STORES = "stores";
    public final String STORES_ID = "id";
    public final String STORES_NAME = "name";
    public final String STORES_ADDRESS = "address";
    public final String STORES_LONGITUDE = "longitude";
    public final String STORES_LATITUDE = "latitude";
    public final String STORES_ALTITUDE = "altitude";
    public final String STORES_CREATE = "create table stores(id integer primary key autoincrement, name text, address text, longitude double, latitude double, altitude double);";
    public final String STORES_DROP = "drop table if exists stores;";
}
