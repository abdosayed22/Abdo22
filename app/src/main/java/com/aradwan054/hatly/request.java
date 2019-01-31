package com.aradwan054.hatly;

public class request {
    private String description;
    private String price;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public request() {
    }


/* public request(String description, Double price, String location) {
        this.description = description;
        this.price = price;


    }
    */
}
