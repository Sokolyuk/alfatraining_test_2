package de.sda.einkaufsliste.model;

/**
 * Created by Dmitry Sokolyuk on 05.07.2016.
 */
public class Product {
    private String name;
    private String store_name;
    private long store_id;
    private boolean isDone;
    private long id;

    /**
     * convinient constructor
     *
     * @param id
     * @param name
     * @param store_name
     * @param store_id
     * @param isDone
     */
    public Product(long id, String name, String store_name, long store_id, boolean isDone) {
        this.id = id;
        this.name = name.trim();
        this.store_name = store_name.trim();
        this.store_id = store_id;
        this.isDone = isDone;
    }

    //region g n s & toStr
    public long getStore_id() {
        return store_id;
    }

    public void setStore_id(long store_id) {
        this.store_id = store_id;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    @Override
    public String toString() {
        return name + "->" + store_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    //endregion
}
