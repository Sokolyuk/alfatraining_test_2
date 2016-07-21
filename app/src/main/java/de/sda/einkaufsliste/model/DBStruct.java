package de.sda.einkaufsliste.model;

/**
 * Created by Dmitry Sokolyuk on 15.07.2016.
 */
public interface DBStruct {
    public static final String ERR_IDUNASSIGNED = "Id is unassigned";

    public static final String DB = "shopping.db";

    public static final String PRODUCTS = "products";
    public static final String PRODUCTS_ID = "id";
    public static final String PRODUCTS_NAME = "name";
    public static final String PRODUCTS_STORE_NAME = "store_name";
    public static final String PRODUCTS_STORE_ID = "store_id";
    public static final String PRODUCTS_ISDONE = "isdone";
    public static final String PRODUCTS_CREATE = "create table products(id integer primary key autoincrement, name text, store_id integer, isdone integer, FOREIGN KEY(store_id) REFERENCES stores(id));";
    public static final String PRODUCTS_DROP = "drop table if exists products;";
    public static final String PRODUCTS_SELECT = "select p.id,p.name,p.store_id,s.name as store_name,p.isdone from products p inner join stores s on p.store_id = s.id;";
//    String PRODUCTS_SELECT_LOJ = "select p.id,p.name,p.store_id,s.name as store_name,p.isdone from products p left outer join stores s on p.store_id = s.id;";
//    String PRODUCTS_SELECT_ALL = "select p.id,p.name,p.store_id,'StoreName' as store_name,p.isdone from products p;";

    public static final String STORES = "stores";
    public static final String STORES_ID = "id";
    public static final String STORES_NAME = "name";
    public static final String STORES_ADDRESS = "address";
    public static final String STORES_LONGITUDE = "longitude";
    public static final String STORES_LATITUDE = "latitude";
    public static final String STORES_ALTITUDE = "altitude";
    public static final String STORES_CREATE = "create table stores(id integer primary key autoincrement, name text, address text, longitude double, latitude double, altitude double);";
    public static final String STORES_DROP = "drop table if exists stores;";
}
