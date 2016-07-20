package de.sda.einkaufsliste.model;

/**
 * Created by Dmitry Sokolyuk on 05.07.2016.
 */
public class Product {
    private String name;
    private String store_name;
    private Long store_id;
    private boolean isDone;
    private Long id;

    /**
     * convinient constructor
     *
     * @param name
     * @param store_name
     * @param store_id
     * @param isDone
     */
    public Product(String name, String store_name, Long store_id, boolean isDone) {
        this.id = null;
        this.name = name.trim();
        this.store_name = store_name.trim();
        this.store_id = store_id;
        this.isDone = isDone;
    }

    public Product(Long id, String name, String store_name, Long store_id, boolean isDone) {
        this.id = id;
        this.name = name.trim();
        this.store_name = store_name.trim();
        this.store_id = store_id;
        this.isDone = isDone;
    }

    //region g n s & toStr
    public Long getStore_id() {
        return store_id;
    }

    public void setStore_id(Long store_id) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    //endregion
}
