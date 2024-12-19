package com.group4;

import java.util.List;

public class Catalogue {
    private List<CatalogueItem> items;

    public Catalogue(List<CatalogueItem> items) {
        this.items = items;
    }

    public List<CatalogueItem> getItems() {
        return items;
    }
}
