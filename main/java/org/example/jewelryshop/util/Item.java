package org.example.jewelryshop.util;

import org.example.jewelryshop.models.products;

public class Item {
    private products product;
    private int count;
    private double total;
    private String ringSize;

    public Item(products product, int count, double total, String ringSize) {
        this.product = product;
        this.count = count;
        this.total = total;
        this.ringSize = ringSize;
    }

    public products getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    public double getTotal() {
        return total;
    }

    public String getRingSize() {
        return ringSize;
    }
}

