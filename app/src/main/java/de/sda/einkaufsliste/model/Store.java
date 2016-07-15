package de.sda.einkaufsliste.model;

/**
 * Created by Alfa on 15.07.2016.
 */
public class Store {
    //region fields
    private long id;
    private String name;
    private String address;
    private double longitude;
    private double latitude;
    //endregion

    public Store(long id, String name, String address, double longitude, double latitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    //region g n s
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    //endregion


    @Override
    public String toString() {
        return "name='" + name + '\'' + ", address='" + address + '\'';
    }
}
