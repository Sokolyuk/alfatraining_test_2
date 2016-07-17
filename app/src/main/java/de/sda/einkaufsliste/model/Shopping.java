package de.sda.einkaufsliste.model;

/**
 * Created by Dmitry Sokolyuk on 05.07.2016.
 */
public class Shopping {
    private String productName;
    private String shopName;
    private long store_id;
    private boolean isDone;
    private long id;

    /**
     * convinient constructor
     *
     * @param id
     * @param productName
     * @param shopName
     * @param store_id
     * @param isDone
     */
    public Shopping(long id, String productName, String shopName, long store_id, boolean isDone) {
        this.id = id;
        this.productName = productName.trim();
        this.shopName = shopName.trim();
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Override
    public String toString() {
        return productName + "->" + shopName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    //endregion
}
