package de.sda.einkaufsliste.model;

/**
 * Created by SDA on 05.07.2016.
 */
public class Shopping {
    private String productName;
    private String shopName;
    private boolean isDone;
    private long id;

    /**
     * default constructor
     */
    public Shopping() {

    }

    /**
     * convinient constructor
     *
     * @param productName
     * @param shopName
     */
    public Shopping(long id, String productName, String shopName, boolean isDone) {
        this.id = id;
        this.productName = productName.trim();
        this.shopName = shopName.trim();
        this.isDone = isDone;
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
}
