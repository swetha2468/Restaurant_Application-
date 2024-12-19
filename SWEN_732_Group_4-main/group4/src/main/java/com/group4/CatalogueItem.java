package com.group4;

public class CatalogueItem {
    private String name;
    private String description;
    private double smallPrice;
    private double mediumPrice;
    private double largePrice;

    public CatalogueItem(String name, String description, double smallPrice, double mediumPrice, double largePrice) {
        this.name = name;
        this.description = description;
        this.smallPrice = smallPrice;
        this.mediumPrice = mediumPrice;
        this.largePrice = largePrice;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getSmallPrice() {
        return smallPrice;
    }

    public double getMediumPrice() {
        return mediumPrice;
    }

    public double getLargePrice() {
        return largePrice;
    }
}
