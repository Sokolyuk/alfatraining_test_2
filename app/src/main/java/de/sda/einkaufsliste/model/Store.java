package de.sda.einkaufsliste.model;

/**
 * Created by Dmitry Sokolyuk on 15.07.2016.
 */
public class Store {
    //region fields
    private Long id;
    private String name;
    private String address;
    private double longitude;
    private double latitude;
    private double altitude;

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
    //endregion

    public Store(String name, String address, double longitude, double latitude, double altitude) {
        this.id = null;
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    public Store(Long id, String name, String address, double longitude, double latitude, double altitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    //region g n s
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
