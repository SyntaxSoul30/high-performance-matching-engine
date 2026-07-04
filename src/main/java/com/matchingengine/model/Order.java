package com.matchingengine.model;

public class Order {
    public final long id;
    public final long price;
    public long qty;
    public final char side;

    public Order(long id, long price, long qty, char side) {
        this.id = id;
        this.price = price;
        this.qty = qty;
        this.side = side;
    }
}