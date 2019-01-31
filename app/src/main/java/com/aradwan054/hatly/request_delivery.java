package com.aradwan054.hatly;

public class request_delivery {
    private String description;
    private String price;

    public request_delivery() {
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public request_delivery(String description, String price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }
}
