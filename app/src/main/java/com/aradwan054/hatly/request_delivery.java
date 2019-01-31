package com.aradwan054.hatly;

public class request_delivery {
    private String description;
    private String price;
    private String latitude;
    private String longitude;


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public request_delivery() {

    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
